# Đồ Án Môn Học J2EE - Hệ Thống Quản Lý Hồ Sơ Y Tế Gia Đình

## Giới Thiệu
Medifamily là một hệ thống quản lý hồ sơ y tế gia đình, giúp các thành viên trong gia đình dễ dàng theo dõi tình trạng sức khỏe, lịch sử khám chữa bệnh và các thông tin y tế quan trọng. Hệ thống cung cấp các tính năng bảo mật, quản lý tài khoản người dùng, lưu trữ và truy xuất hồ sơ y tế một cách hiệu quả.

## Thành Viên Nhóm
- **Lương Gia Tuấn** - 31215600
- **Ngô Hữu Hoàng** - 3121560032
- **Phan Huỳnh Thanh Phong** - 3121560003

## Công Nghệ Sử Dụng
- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Cơ sở dữ liệu**: ... Database
- **Frontend**: ReactJS
- **Authentication**: JWT (JSON Web Token)
- **API Documentation**: Swagger/OpenAPI
- **Quản lý dự án**: GitHub, Maven

## Chức Năng Chính
1. **Quản lý tài khoản**
   - Đăng ký, đăng nhập, xác thực người dùng bằng JWT
   - Phân quyền người dùng (Admin, Bác sĩ, Thành viên gia đình)
   
2. **Quản lý hồ sơ y tế**
   - Lưu trữ và truy xuất thông tin bệnh án, đơn thuốc
   - Theo dõi chỉ số sức khỏe (cân nặng, huyết áp, đường huyết,...)
   - Quản lý hồ sơ tiêm chủng
   
3. **Quản lý gia đình**
   - Kết nối các thành viên trong gia đình
   - Chia sẻ hồ sơ y tế giữa các thành viên
   
4. **Hỗ trợ bác sĩ**
   - Quản lý bệnh nhân
   - Xem và cập nhật thông tin y tế của bệnh nhân
   
5. **Hệ thống thông báo & nhắc nhở**
   - Nhắc nhở lịch uống thuốc, lịch khám
   - Gửi thông báo khi có cập nhật về hồ sơ y tế

## Cấu Trúc Dự Án
```
├── frontend                     # Giao diện người dùng ReactJS
├── pom.xml                      # Cấu hình Maven
├── src                          # Mã nguồn backend
│   ├── main
│   │   ├── java/sgu/j2ee/medifamily
│   │   │   ├── configs          # Cấu hình hệ thống (Security, Exception Handling)
│   │   │   ├── controllers      # API Controllers
│   │   │   ├── dtos             # Data Transfer Objects
│   │   │   ├── entities         # Lớp thực thể trong database
│   │   │   ├── repositories     # Spring Data JPA repositories
│   │   │   ├── services         # Business logic
│   │   │   ├── filters          # JWT authentication filter
│   │   │   ├── utils            # Tiện ích hỗ trợ
│   │   ├── resources
│   │   │   ├── application.properties  # Cấu hình ứng dụng
│   │   └── MedifamilyApplication.java  # Main application
└── README.md                    # Hướng dẫn dự án
```

## Cách Chạy Dự Án
### 1. Chạy Backenddev
```sh
mvn spring-boot:run
```

### 2. Chạy Frontend
```sh
cd frontend
npm install
npm dev
```
