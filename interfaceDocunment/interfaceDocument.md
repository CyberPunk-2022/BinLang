# User

## 登录接口

### 接口描述

#### 接口请求域名：

GET http://localhost:8808/user-tokens

#### 输入参数

| 参数名称   | 必选 | 类型    | 描述                       |
| ---------- | ---- | ------- | -------------------------- |
| user       | 是   | Object  | 封装类                     |
| id         | 否   | Long    | 用户唯一id，存储到数据库中 |
| phone      | 否   | String  | 用户手机号                 |
| email      | 否   | String  | 用户邮箱                   |
| password   | 是   | String  | 用户密码，md5加密          |
| salt       | 否   | String  | 盐值                       |
| createTime | 否   | Date    | 创建时间，后端自动生成     |
| updateTime | 否   | Date    | 更新时间，后端自动生成     |
| userInfo   | 否   | Object  | 封装类，存储用户其他信息   |
| id         | 否   | Long    | 唯一id，存储到数据库中     |
| userId     | 否   | Long    | 用户唯一id                 |
| nick       | 否   | String  | 用户昵称                   |
| avatar     | 否   | String  | 用户头像url                |
| sign       | 否   | String  | 用户个性签名               |
| gender     | 否   | String  | 性别                       |
| birth      | 否   | String  | 生日                       |
| createTime | 否   | Date    | 创建时间，后端自动生成     |
| updateTime | 否   | Date    | 更新时间，后端自动生成     |
| followed   | 否   | Boolean | 冗余字段，获取粉丝列表用   |

#### 输出参数

| 参数名称 | 必选 | 类型   | 描述                                         |
| -------- | ---- | ------ | -------------------------------------------- |
| token    | 是   | String | 通过查询数据库的用户生成的JWT，有效期默认7天 |

## 用户注册接口

### 接口描述

#### 接口请求域名

GET http://localhost:8808/users

#### 输入参数

| 参数名称   | 必选 | 类型    | 描述                       |
| ---------- | ---- | ------- | -------------------------- |
| user       | 是   | Object  | 封装类                     |
| id         | 否   | Long    | 用户唯一id，存储到数据库中 |
| phone      | 否   | String  | 用户手机号                 |
| email      | 否   | String  | 用户邮箱                   |
| password   | 是   | String  | 用户密码，md5加密          |
| salt       | 否   | String  | 盐值                       |
| createTime | 否   | Date    | 创建时间，后端自动生成     |
| updateTime | 否   | Date    | 更新时间，后端自动生成     |
| userInfo   | 否   | Object  | 封装类，存储用户其他信息   |
| id         | 否   | Long    | 唯一id，存储到数据库中     |
| userId     | 否   | Long    | 用户唯一id                 |
| nick       | 否   | String  | 用户昵称                   |
| avatar     | 否   | String  | 用户头像url                |
| sign       | 否   | String  | 用户个性签名               |
| gender     | 否   | String  | 性别                       |
| birth      | 否   | String  | 生日                       |
| createTime | 否   | Date    | 创建时间，后端自动生成     |
| updateTime | 否   | Date    | 更新时间，后端自动生成     |
| followed   | 否   | Boolean | 冗余字段，获取粉丝列表用   |

#### 输出参数