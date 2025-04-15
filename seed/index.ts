import dayjs from "dayjs";
import { GeminiDataFaker } from "./GeminiDataFaker";
import { PrismaClient } from "./generated/prisma";
import { faker } from "@faker-js/faker/locale/vi";
import customParseFormat from "dayjs/plugin/customParseFormat";
dayjs.extend(customParseFormat);
const prisma = new PrismaClient();

const client = new GeminiDataFaker(process.env.GEMINI_API_KEY!);
function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
console.log("Tạo tài khoản người dùng...");
for (let i = 0; i <= 30; i++) {
  const generatedUser = await client.generate(
    {
      address: "Địa chỉ",
      allergies: "Dị ứng (không bắt buộc)",
      bio: "Tiểu sử (không bắt buộc)",
      blood_type: "Nhóm máu",
      chronic_diseases: "Lịch sử bệnh mãn tính(không bắt buộc)",
      date_of_birth: "Ngày sinh DD/MM/YYYY",
      email: "Email",
      full_name: "Họ và tên",
      gender: "Giới tính FEMALE/MALE",
      health_insurance_number: "Số thẻ bảo hiểm y tế",
      height: "Chiều cao",
      weight: "Cân nặng",
      phone_number: "Số điện thoại",
      notes: "Ghi chú (không bắt buộc)",
    },
    "Thông tin y tế của người dùng"
  );
  const user = await prisma.users.create({
    data: {
      password: await Bun.password.hash("Password@123", {
        algorithm: "bcrypt",
      }),
      created_at: faker.date.past(),
      updated_at: faker.date.past(),
      email: generatedUser.email,
    },
  });
  const profile = await prisma.profiles.create({
    data: {
      address: generatedUser.address,
      allergies: generatedUser.allergies,
      avatar_url: faker.image.avatar(),
      bio: generatedUser.bio,
      blood_type: generatedUser.blood_type,
      chronic_diseases: generatedUser.chronic_diseases,
      created_at: user.created_at,
      updated_at: user.updated_at,
      date_of_birth: dayjs(generatedUser.date_of_birth, "DD/MM/YYYY").toDate(),
      email: generatedUser.email,
      full_name: generatedUser.full_name,
      gender: generatedUser.gender,
      health_insurance_number: generatedUser.health_insurance_number,
      height: Number(generatedUser.height),
      notes: generatedUser.notes,
      phone_number: generatedUser.phone_number,
      weight: Number(generatedUser.weight),
      users: {
        connect: {
          id: user.id,
        },
      },
    },
  });
  console.log("success", profile);
  await sleep(2000); // Thêm delay 1 giây giữa các lần gọi API
}
console.log("Tạo tài khoản người dùng thành công!");
const allUsers = await prisma.users.findMany({
  include: {
    profiles: true,
  },
});
const random10Users = allUsers.sort(() => 0.5 - Math.random()).slice(0, 10);
//tạo 10 family mới
console.log("Tạo gia đình mới...");
for (let i = 0; i < 10; i++) {
  const chuHo = random10Users[i]!;
  const family = await prisma.families.create({
    data: {
      address: chuHo.profiles[0]?.address!,
      family_name: `Gia đình của ${chuHo.profiles[0]?.full_name}`,
      created_at: faker.date.past(),
      is_active: true,
      email: chuHo.email,
      phone_number: chuHo.profiles[0]?.phone_number!,
      profiles: {
        connect: {
          id: chuHo.profiles[0]?.id,
        },
      },
    },
  });
  await prisma.family_members.create({
    data: {
      relationship: "Chủ hộ",
      created_at: faker.date.past(),
      is_active: true,
      families: {
        connect: {
          id: family.id,
        },
      },
      profiles: {
        connect: {
          id: chuHo.profiles[0]?.id,
        },
      },
    },
  });
}
console.log("Tạo gia đình thành công!");

const userNotOwnerIds = await prisma.profiles.findMany({});
// thêm thành viên vào gia đình
const relationships = [
  "Bố",
  "Mẹ",
  "Con",
  "Vợ",
  "Chồng",
  "Con gái",
  "Con trai",
  "Ông",
  "Bà",
  "Cháu",
];
const allFamilies = await prisma.families.findMany({});
console.log("Thêm thành viên vào gia đình...");
for await (const family of allFamilies) {
  const randomUser = userNotOwnerIds
    .sort(() => 0.5 - Math.random())
    .slice(0, 3);
  // user đã có tài khoản
  for await (const user of randomUser) {
    try {
      await prisma.family_members.create({
        data: {
          relationship: faker.helpers.arrayElement(relationships),
          created_at: faker.date.past(),
          is_active: true,
          profiles: {
            connect: {
              id: user.id,
            },
          },
          families: {
            connect: {
              id: family.id,
            },
          },
        },
      });
    } catch (error) {
      console.log("lỗi", error);
    }
  }
  // profile chưa có tài khoản
  for (let i = 0; i < 2; i++) {
    const generatedUser = await client.generate(
      {
        address: "Địa chỉ",
        allergies: "Dị ứng (không bắt buộc)",
        bio: "Tiểu sử (không bắt buộc)",
        blood_type: "Nhóm máu",
        chronic_diseases: "Lịch sử bệnh mãn tính(không bắt buộc)",
        date_of_birth: "Ngày sinh DD/MM/YYYY",
        full_name: "Họ và tên",
        gender: "Giới tính FEMALE/MALE",
        health_insurance_number: "Số thẻ bảo hiểm y tế",
        height: "Chiều cao",
        weight: "Cân nặng",
        notes: "Ghi chú (không bắt buộc)",
      },
      "Thông tin y tế của trẻ (từ 0-12 tuổi)"
    );
    const profile = await prisma.profiles.create({
      data: {
        address: generatedUser.address,
        allergies: generatedUser.allergies,
        avatar_url: faker.image.avatar(),
        bio: generatedUser.bio,
        blood_type: generatedUser.blood_type,
        chronic_diseases: generatedUser.chronic_diseases,
        created_at: faker.date.past(),
        updated_at: faker.date.past(),
        date_of_birth: dayjs(
          generatedUser.date_of_birth,
          "DD/MM/YYYY"
        ).toDate(),
        full_name: generatedUser.full_name,
        gender: generatedUser.gender,
        health_insurance_number: generatedUser.health_insurance_number,
        height: Number(generatedUser.height),
        notes: generatedUser.notes,
        weight: Number(generatedUser.weight),
      },
    });
    await prisma.family_members.create({
      data: {
        relationship: faker.helpers.arrayElement([
          "Con",
          "Con gái",
          "Con trai",
          "Cháu",
        ]),
        created_at: faker.date.past(),
        is_active: true,
        profiles: {
          connect: {
            id: profile.id,
          },
        },
        families: {
          connect: {
            id: family.id,
          },
        },
      },
    });
    await sleep(2000); // Thêm delay 1 giây giữa các lần gọi API
  }
}
console.log("Thêm thành viên vào gia đình thành công!");
