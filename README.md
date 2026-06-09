# README – Hệ thống Ngân hàng Mini theo DDD + Clean Architecture + CQRS

## 1. Giới thiệu dự án

Đây là một **hệ thống ngân hàng mini** được xây dựng bằng **Spring Boot**, phục vụ mục tiêu học tập và trình bày kiến trúc phần mềm hiện đại.

Hệ thống hiện tại tập trung vào 3 chức năng cốt lõi:

- **Gửi tiền (Deposit)**
- **Rút tiền (Withdraw)**
- **Xem số dư (Get Balance)**

Dự án được thiết kế theo định hướng:

- **Domain-Driven Design (DDD)**
- **Clean Architecture**
- **CQRS (Command Query Responsibility Segregation)**
- **Transactional Outbox Pattern**
- **Kafka để đồng bộ hóa dữ liệu giữa write/read side**
- **Redis để xử lý bài toán eventual consistency ở tầng truy vấn**
- **MySQL cho write side**
- **MongoDB cho read side**

---

## 2. Mục tiêu kiến trúc

1. **Tách biệt rõ business logic với framework / hạ tầng**.
2. **Đảm bảo domain độc lập**.
3. **Tách read và write**.
4. **Giải quyết các vấn đề thực tế của distributed system** như:
   - eventual consistency
   - duplicated event
   - mất event khi Kafka/broker gặp lỗi

---

## 3. Công nghệ sử dụng

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Spring Data MongoDB
- Spring Data Redis
- Spring Kafka

### Database / Messaging
- **MySQL**: write database
- **MongoDB**: read database
- **Redis**: recent-write flag
- **Kafka**: event broker

### Kiến trúc / Pattern chính
- Domain-Driven Design (DDD)
- Clean Architecture
- CQRS
- Transactional Outbox Pattern
- Idempotent Consumer

---

## 4. Các chức năng hiện tại

### 4.1. Gửi tiền (Deposit)
- Người dùng gửi một khoản tiền vào tài khoản.
- Đây là nghiệp vụ **write**.
- Hệ thống cập nhật số dư trong **MySQL**.
- Đồng thời tạo event và lưu vào **Outbox Table**.

### 4.2. Rút tiền (Withdraw)
- Người dùng rút tiền khỏi tài khoản.
- Đây là nghiệp vụ **write**.
- Business rule quan trọng:
  - **Không được rút quá số dư hiện có**.
- Nếu hợp lệ, số dư được cập nhật trong **MySQL** và event được lưu vào **Outbox Table**.

### 4.3. Xem số dư (Get Balance)
- Đây là nghiệp vụ **read**.
- Mặc định dữ liệu sẽ được đọc từ **MongoDB** (read model).
- Tuy nhiên, nếu vừa có thao tác write gần đây, hệ thống sẽ tạm thời đọc từ **MySQL** trong 3 giây để đảm bảo user thấy dữ liệu mới nhất.

---

## 5. Cấu trúc source code hiện tại

```text
src/main/java/com.example.bank
├── application
│   ├── command
│   ├── dto
│   ├── port
│   ├── query
│   └── service
├── bootstrap
│   ├── api
│   └── config
├── domain
│   ├── event
│   ├── exception
│   └── model
├── infrastructure
│   ├── config
│   ├── outbox
│   ├── read
│   ├── redis
│   └── write
└── BankApplication
```
