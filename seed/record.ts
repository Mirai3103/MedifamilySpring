import dayjs from "dayjs";
import { GeminiDataFaker } from "./GeminiDataFaker";
import { PrismaClient } from "./generated/prisma";
import { faker } from "@faker-js/faker/locale/vi";
import customParseFormat from "dayjs/plugin/customParseFormat";
dayjs.extend(customParseFormat);

const prisma = new PrismaClient();

const allProfiles = await prisma.profiles.findMany({
  where: {
    medical_records: {
      none: {},
    },
  },
});
console.log(allProfiles.length);
//  tạo health record cho tất cả các profile

const client = new GeminiDataFaker(process.env.GEMINI_API_KEY!);
function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
for await (const profile of allProfiles) {
  const numberOfRecords = Math.floor(Math.random() * 10) + 1;
  for (let i = 0; i < numberOfRecords; i++) {
    console.log(
      `Tạo hồ sơ y tế cho ${profile.full_name} (${profile.id}) - ${
        i + 1
      }/${numberOfRecords}`
    );
    await sleep(2000); // Giới hạn tốc độ gọi API 15 lần mỗi phút
    const generatedRecord = await client.generate(
      {
        diagnosis: "Chẩn đoán",
        medical_facility: "Cơ sở y tế",
        visit_date: "Ngày khám DD/MM/YYYY",
        doctor_name: "Tên bác sĩ",
        followup_date: "Ngày hẹn tái khám(trống nếu không có tái khám)",
        notes: "Ghi chú (không bắt buộc có thể trống)",
        title: "Tiêu đề về lần khám",
        treatment: "Phác đồ điều trị",
      },
      "Thông tin bệnh án khi khám bệnh"
    );
    await prisma.medical_records.create({
      data: {
        created_at: faker.date.past(),
        created_by: "1",
        updated_at: faker.date.past(),
        diagnosis: generatedRecord.diagnosis,
        medical_facility: generatedRecord.medical_facility,
        visit_date: dayjs(generatedRecord.visit_date, "DD/MM/YYYY").toDate(),
        doctor_name: generatedRecord.doctor_name,
        is_followup: generatedRecord.followup_date ? true : false,
        followup_date: generatedRecord.followup_date
          ? dayjs(generatedRecord.followup_date, "DD/MM/YYYY").toDate()
          : null,
        notes: generatedRecord.notes,
        title: generatedRecord.title,
        treatment: generatedRecord.treatment,
        profiles: {
          connect: {
            id: profile.id,
          },
        },
      },
    });
  }
}
