# 第 1 部分 大厂规范

## 第01节：大厂码农开发基础

<img src="E:\Document\Typora笔记\Pictures\基础内容介绍.png" alt="基础内容介绍" style="zoom:150%;" />



## 第02节：需求怎么来的

<img src="E:\Document\Typora笔记\Pictures\需求怎么来的.png" alt="需求怎么来的" style="zoom:150%;" />



## 第03节：系统架构设计

<img src="E:\Document\Typora笔记\Pictures\系统架构设计.png" alt="系统架构设计" style="zoom: 200%;" />



## 第04节：进入开发阶段

<img src="E:\Document\Typora笔记\Pictures\进入开发阶段.png" alt="进入开发阶段"  />



## 第05节：系统上线维护

![系统上线维护](E:\Document\Typora笔记\Pictures\系统上线维护.png)



# 第 2 部分 领域开发

## 第01节：环境、配置、规范

## 第02节：搭建DDD四层架构

> - 分支：230504_neil_initProject
> - 目的：搭建工程结构

**DDD分层架构介绍**

![2-02](E:\Document\Typora笔记\Pictures\2-02.png)

- 应用层{application}
- 领域层{domain}
- 基础层{infrastructure}
- 接口层{interfaces}

**DDD + RPC，模块分离系统搭建**

![2-03](E:\Document\Typora笔记\Pictures\2-03.png)

**为什么要把RPC层单独拆分出来**

使用 RPC 框架的时候，需要对外提供描述接口信息的 Jar 让外部调用方引入才可以通过反射调用到具体的方法提供者，那么这个时候，RPC 需要暴露出来，而 DDD 的系统结构又比较耦合，怎么进行模块化的分离就成了问题点。

### 2.2 项目实现

1. 通过git创建新的项目，使用ssh或者用户密码登录将项目clone到本地

   - 检查Git是否已经有SSH密钥，没有进行生成[(104条消息) Git 使用 —设置 Git 的SSH秘钥_git配置ssh密钥_嵌入式之入坑笔记的博客-CSDN博客](https://blog.csdn.net/weixin_43866583/article/details/125680653)

   - 将密钥添加到对应的gitcode中

     ![image-20230505142103597](E:\Document\Typora笔记\Pictures\image-20230505142103597.png)

2. 按照对应结构创建对应的module

   ![image-20230505142229618](E:\Document\Typora笔记\Pictures\image-20230505142229618.png)

   

