import dayjs from "dayjs";
import { GeminiDataFaker } from "./GeminiDataFaker";
import { PrismaClient } from "./generated/prisma";
import { faker } from "@faker-js/faker/locale/vi";
import customParseFormat from "dayjs/plugin/customParseFormat";
dayjs.extend(customParseFormat);

const prisma = new PrismaClient();

const allProfiles = await prisma.profiles.findMany({});
//  tạo health record cho tất cả các profile

const client = new GeminiDataFaker(process.env.GEMINI_API_KEY!);
function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

const allMedicalRecords = await prisma.medical_records.findMany({
  select: {
    profile_id: true,
    diagnosis: true,
    id: true,
    visit_date: true,
  },
  where: {
    prescriptions: {
      none: {},
    },
  },
});
let totalChat = 0;
console.log(allMedicalRecords.length);
for await (const record of allMedicalRecords) {
  const diagnosis = record.diagnosis;

  const generatedMedical: any = await client.generate(
    {
      dosage: "Liều dùng ví dụ: 4viên/lần",
      duration_in_days:
        "Thời gian dùng thuốc tính bằng ngày(1 số nguyên dương), ví dụ 3,7,30",
      frequency: "Tần suất dùng thuốc ví dụ 3 lần/ngày",
      instructions:
        "Hướng dẫn sử dụng thuốc ví dụ: sau khi ăn(không bắt buộc, có thể trống)",
      medication_name: "Tên thuốc",
      notes: "Ghi chú (không bắt buộc có thể trống)",
    },
    "Thông tin dùng thuốc cho bệnh " +
      diagnosis +
      " trả về 1 mảng các loại thuốc"
  );
  const prescription = await prisma.prescriptions.create({
    data: {
      created_at: faker.date.past(),
      updated_at: faker.date.past(),
      medical_record_id: record.id,
      created_by: "1",
    },
  });

  try {
    await prisma.prescription_items.createMany({
      data: generatedMedical.map((item: any) => {
        return {
          dosage: item.dosage,
          frequency: item.frequency,
          medication_name: item.medication_name,
          prescription_id: prescription.id,
          start_use_date: dayjs(record.visit_date).add(1, "day").toDate(),
          duration_in_days: Number(item.duration_in_days),
          instructions: item.instructions,
          notes: item.notes,
        };
      }),
    });
  } catch (error) {
    await prisma.prescriptions.delete({
      where: {
        id: prescription.id,
      },
    });
  }
  await sleep(5000); // Giới hạn tốc độ gọi API 15 lần mỗi phút
  totalChat++;
  console.log(
    `Tạo đơn thuốc cho ${record.diagnosis} (${record.id}) - ${totalChat}/${allMedicalRecords.length}`
  );

  if (totalChat > 10) {
    totalChat = 0;
    client.initChat();
  }
}
