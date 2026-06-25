# 📇 通讯录管理系统（AddressBook FullStack）

一个基于 **Spring Boot + Vue 3** 的全栈通讯录管理系统，支持用户的注册登录、联系人增删改查和搜索功能。

## 🛠 技术栈

| 后端 | 前端 |
|------|------|
| Spring Boot 3.2.5 | Vue 3 |
| MyBatis 3 | Vite 8 |
| MySQL 8 | Axios |
| REST API | Vue Router |

## 📋 功能

- ✅ 用户注册 / 登录 / 退出
- ✅ 联系人增删改查（CRUD）
- ✅ 搜索联系人（姓名 / 电话 / 邮箱）
- ✅ 登录拦截（未登录自动跳转）
- ✅ 响应式设计（手机 / 平板 / 桌面）

## 🚀 快速开始

### 1. 启动后端

```bash
cd backend
# 复制配置模板并填写你的数据库信息
cp src/main/resources/application.yml.example src/main/resources/application.yml
# 用 IDEA 打开 backend/ 目录，运行 AddressBookApplication
```

### 2. 启动前端

```bash
cd frontend
npm install
npm run dev
```

### 3. 访问

打开浏览器访问 **http://localhost:5173**

## 📂 项目结构

```
addressbook-fullstack/
├── backend/                  ← Spring Boot 后端
│   ├── src/main/java/
│   │   ├── controller/       ← REST API 接口
│   │   ├── service/          ← 业务逻辑
│   │   ├── mapper/           ← MyBatis 数据访问
│   │   ├── model/            ← 数据模型
│   │   └── interceptor/      ← 登录拦截器
│   └── src/main/resources/
│       ├── application.yml.example  ← 配置模板
│       └── mappers/           ← SQL 映射
│
└── frontend/                 ← Vue 3 前端
    └── src/
        ├── views/            ← 页面组件
        ├── router/           ← 路由配置
        └── api/              ← API 调用
```

## 👤 测试账号

```
用户名：admin
密码：123456
```

也可注册新账号使用。

## 📄 开源协议

MIT
