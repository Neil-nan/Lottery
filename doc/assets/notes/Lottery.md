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




## 第03节：跑通广播模式RPC过程调用

### 3.1 创建抽奖活动表

- 安装`mysql`和`Navicat`（如果之前有安装mysql和navicat，一定要卸载干净），连接Navicat

  [MySQL 8.0保姆级下载、安装及配置教程（我妈看了都能学会）_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV12q4y1477i/?spm_id_from=333.880.my_history.page.click&vd_source=b1ff0fefaa80f9539a2f636f1d95bd71)

- 使用idea中的database创建`活动表`

  ```sql
  CREATE TABLE `activity` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `activity_name` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '活动名称',
    `activity_desc` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '活动描述',
    `begin_date_time` datetime DEFAULT NULL COMMENT '开始时间',
    `end_date_time` datetime DEFAULT NULL COMMENT '结束时间',
    `stock_count` int(11) DEFAULT NULL COMMENT '库存',
    `take_count` int(11) DEFAULT NULL COMMENT '每人可参与次数',
    `state` tinyint(2) DEFAULT NULL COMMENT '活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启',
    `creator` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_activity_id` (`activity_id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='活动配置';
  ```

**出现问题**

1. Mysql服务无法自启动

   > 垃圾校园网，每次开机都断网，还以为是服务管理器的问题

   [(105条消息) MySQL服务无法自启动_mysql服务无法自动启动_Енох-燚的博客-CSDN博客](https://blog.csdn.net/qq_54515850/article/details/126448534)

   [(105条消息) mysql设置开机自启后依然无法自启动_是秃头终会发光的博客-CSDN博客](https://blog.csdn.net/weixin_46030885/article/details/128440800)

   [(105条消息) MySQL开机无法启动，需手动启动才可以。_mysql每次都要手动启动_格林-scorpio的博客-CSDN博客](https://blog.csdn.net/pengwupeng2008/article/details/125551926?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~Rate-1-125551926-blog-126448534.235^v32^pc_relevant_default_base3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~Rate-1-125551926-blog-126448534.235^v32^pc_relevant_default_base3&utm_relevant_index=2&ydreferer=aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzU0NTE1ODUwL2FydGljbGUvZGV0YWlscy8xMjY0NDg1MzQ%3D)

### 3.2 POM文件配置

- 总工程下不配任何依赖

- 模块类POM配置中确什么补什么，每个模块都依赖`common`

  ```xml
  <dependencies>
      <dependency>
          <groupId>cn.itedus.lottery</groupId>
          <artifactId>lottery-common</artifactId>
          <version>1.0-SNAPSHOT</version>
      </dependency>
  </dependencies>
  ```

  

- war包pom配置：lottery-interfaces 是整个程序的出口，也是用于构建 War 包的工程模块，所以你会看到一个 `<packaging>war</packaging>` 的配置

### 3.3 配置Mybatis

- interfaces中的mysql依赖要和下载的mysql版本对应上

  ```xml
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
              <version>8.0.33</version>
          </dependency>
  ```

- 配置yml

  > 若本地是MySQL8版本，启动项目前记得将lottery-interfaces模块下的pom文件mysql-connector-java 部分版本号升级为8.0.22 。并在该模块下的yml配置文件中将driver-class-name 调整为com.mysql.cj.jdbc.Driver ，且因为MySQL8连接要求，必须在连接url后跟上serverTimezone 的配置，即url: jdbc:mysql://127.0.0.1:3306/lottery?useUnicode=true&serverTimezone=Asia/Shanghai

  ```yml
  server:
    port: 8080
  
  spring:
    datasource:
      username: root
      password: 123456
      url: jdbc:mysql://127.0.0.1:3306/lottery?useUnicode=true&serverTimezone=Asia/Shanghai
      driver-class-name: com.mysql.cj.jdbc.Driver
  
  mybatis:
    mapper-locations: classpath:/mybatis/mapper/*.xml
    config-location:  classpath:/mybatis/config/mybatis-config.xml
  ```

**测试**

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    IActivityDao activityDao;

    @Test
    public void test_insert(){
        Activity activity = new Activity();
        activity.setActivityId(100001L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("neilnan");
        activityDao.insert(activity);
    }

    @Test
    public void test_select(){
        Activity activity = activityDao.queryActivityById(100001L);
        log.info("测试结果：{}", JSON.toJSONString(activity));
    }
}
```

### 3.4配置广播模式Dubbo

- 配置yml

  ```yml
  # Dubbo 广播方式配置
  dubbo:
    application:
      name: Lottery
      version: 1.0.0
    registry:
      address: N/A #multicast://224.5.6.7:1234
    protocol:
      name: dubbo
      port: 20880
    scan:
      base-packages: cn.itedus.lottery.rpc
  ```

  > - 广播模式的配置唯一区别在于注册地址，registry.address = multicast://224.5.6.7:1234，服务提供者和服务调用者都需要配置相同的📢广播地址。或者配置为 N/A 用于直连模式使用
  > - application，配置应用名称和版本
  > - protocol，配置的通信协议和端口
  > - scan，相当于 Spring 中自动扫描包的地址，可以把此包下的所有 rpc 接口都注册到服务中

- 这里注意引用依赖的版本要相同

  ```xml
          <dependency>
              <groupId>org.apache.dubbo</groupId>
              <artifactId>dubbo</artifactId>
              <version>2.7.1</version>
          </dependency>
          <dependency>
              <groupId>org.apache.dubbo</groupId>
              <artifactId>dubbo-spring-boot-starter</artifactId>
              <version>2.7.1</version>
          </dependency>
  ```

### 3.5定义和开发RPC接口

> 由于 RPC 接口在通信的过程中，需要提供接口的描述文件，也就是接口的定义信息。所以这里你会看到我们会把所有的 RPC 接口定义都放到 `lottery-rpc` 模块下，这种方式的使用让外部就只依赖这样一个 pom 配置引入的 Jar 包即可。

1. **定义接口**

   ```java
   public interface IActivityBooth {
       ActivityRes queryActivityById(ActivityReq req);
   }
   ```

2. **开发接口**

   ```java
   @Service
   public class ActivityBooth implements IActivityBooth {
   
       @Resource
       private IActivityDao activityDao;
   
       @Override
       public ActivityRes queryActivityById(ActivityReq req){
   
           Activity activity = activityDao.queryActivityById(req.getActivityId());
   
           ActivityDto activityDto = new ActivityDto();
           activityDto.setActivityId(activity.getActivityId());
           activityDto.setActivityName(activity.getActivityName());
           activityDto.setActivityDesc(activity.getActivityDesc());
           activityDto.setBeginDateTime(activity.getBeginDateTime());
           activityDto.setEndDateTime(activity.getEndDateTime());
           activityDto.setStockCount(activity.getStockCount());
           activityDto.setTakeCount(activity.getTakeCount());
   
           return new ActivityRes(new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo()), activityDto);
       }
   }
   ```

   > - 用于实现 RPC 接口的实现类 ActivityBooth 上有一个注解 @Service，这个注解是来自于 Dubbo 的 `org.apache.dubbo.config.annotation.Service`，也就是这个包下含有此注解配置的类可以被 Dubbo 管理。
   > - 在 queryActivityById 功能实现中目前还比较粗糙，但大体可以看出这是对数据库的操作以及对结果的封装，提供 DTO 的对象并返回 Res 结果。*目前dto的创建后续可以使用门面模式和工具类进行处理*

### 3.6搭建测试工程调用RPC

- 测试之前需要把 Lottery 工程中的 lottery-rpc 进行构建打包，便于测试工程引入

  ![3-03](E:\Document\Typora笔记\Pictures\3-03.png)

- 在统一广播地址下使用

- 单元测试

  ```java 
  @RunWith(SpringRunner.class)
  @SpringBootTest
  public class ApiTest {
  
      private Logger logger = LoggerFactory.getLogger(ApiTest.class);
  
      @Reference(interfaceClass = IActivityBooth.class, url = "dubbo://127.0.0.1:20880")
      private IActivityBooth activityBooth;
  
      @Test
      public void test_rpc() {
          ActivityReq req = new ActivityReq();
          req.setActivityId(100001L);
          ActivityRes result = activityBooth.queryActivityById(req);
          logger.info("测试结果：{}", JSON.toJSONString(result));
      }
  
  }
  ```

### 问题

- mybatis中select `resultType`和`resultMap`的不同
- Serializable接口的作用
- 异常分类

> Java 文件中的 IActivityBooth 接口是一个远程服务接口，它定义了客户端可以远程调用的方法。ActivityBooth 类实现此接口并提供 queryActivityById 方法的实现。此方法接受 ActivityReq 对象作为输入，并返回 ActivityRes 对象作为输出。该方法使用 activityDao 对象从数据库查询 Activity 对象，并将 Activity 对象的属性映射到 ActivityDto 对象的相应属性。最后，它返回一个包含 ActivityDto 对象的 ActivityRes 对象和一个包含成功状态代码和消息的 Result 对象。
>
> Java 文件中的 Result 类是一个表示响应状态的简单包装类。它包含状态代码的整数代码属性和状态消息的字符串 info 属性。这个类的目的是使得在应用程序中的各种远程服务方法之间返回一致的响应状态信息变得容易。



## 第04节：抽奖活动策略库表设计

### 4.1 建表逻辑

![4-01](E:\Document\Typora笔记\Pictures\4-01.png)

![抽奖活动策略库表设计](E:\Document\Typora笔记\Pictures\抽奖活动策略库表设计.png)

- 活动配置，activity：提供活动的基本配置
- 策略配置，strategy：用于配置抽奖策略，概率，玩法，库存，奖品
- 策略明细，strategy_detail：抽奖策略的具体明细配置
- 奖品配置，award：用于配置具体可以得到的奖品
- 用户参与活动记录表，user_take_activity：每个用户参与活动都会记录下他的参与信息，时间，次数
- 用户活动参与次数表，user_take_activity_count：用于记录当前参与了多少次
- 用户策略计算结果表，user_strategy_export_001~004：最终策略结果的一个记录，也就是奖品中奖信息的内容



### 4.2 建表语句

lottery.sql

```sql
create database lottery;

-- auto-generated definition
create table activity
(
    id            bigint auto_increment comment '自增ID',
    activityId    bigint       null comment '活动ID',
    activityName  varchar(64)  not null comment '活动名称',
    activityDesc  varchar(128) null comment '活动描述',
    beginDateTime datetime     not null comment '开始时间',
    endDateTime   datetime     not null comment '结束时间',
    stockCount    int          not null comment '库存',
    takeCount     int          null comment '每人可参与次数',
    state         int          null comment '活动状态：编辑、提审、撤审、通过、运行、拒绝、关闭、开启',
    creator       varchar(64)  not null comment '创建人',
    createTime    datetime     not null comment '创建时间',
    updateTime    datetime     not null comment '修改时间',
    constraint activity_id_uindex
        unique (id)
)
    comment '活动配置';

alter table activity
    add primary key (id);

-- auto-generated definition
create table award
(
    id           bigint(11) auto_increment comment '自增ID'
        primary key,
    awardId      bigint                             null comment '奖品ID',
    awardType    int(4)                             null comment '奖品类型（文字描述、兑换码、优惠券、实物奖品暂无）',
    awardCount   int                                null comment '奖品数量',
    awardName    varchar(64)                        null comment '奖品名称',
    awardContent varchar(128)                       null comment '奖品内容「文字描述、Key、码」',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null comment 'updateTime'
)
    comment '奖品配置';

-- auto-generated definition
create table strategy
(
    id           bigint(11) auto_increment comment '自增ID'
        primary key,
    strategyId   bigint(11)   not null comment '策略ID',
    strategyDesc varchar(128) null comment '策略描述',
    strategyMode int(4)       null comment '策略方式「1:单项概率、2:总体概率」',
    grantType    int(4)       null comment '发放奖品方式「1:即时、2:定时[含活动结束]、3:人工」',
    grantDate    datetime     null comment '发放奖品时间',
    extInfo      varchar(128) null comment '扩展信息',
    createTime   datetime     null comment '创建时间',
    updateTime   datetime     null comment '修改时间',
    constraint strategy_strategyId_uindex
        unique (strategyId)
)
    comment '策略配置';

-- auto-generated definition
create table strategy_detail
(
    id         bigint(11) auto_increment comment '自增ID'
        primary key,
    strategyId bigint(11)    not null comment '策略ID',
    awardId    bigint(11)    null comment '奖品ID',
    awardCount int           null comment '奖品数量',
    awardRate  decimal(5, 2) null comment '中奖概率',
    createTime datetime      null comment '创建时间',
    updateTime datetime      null comment '修改时间'
)
    comment '策略明细';

```

lottery_01.sql ~ lottery_02.sql

```sql
create database lottery_01;

-- auto-generated definition
create table user_take_activity
(
    id           bigint    null,
    uId          tinytext  null,
    takeId       bigint    null,
    activityId   bigint    null,
    activityName tinytext  null,
    takeDate     timestamp null,
    takeCount    int       null,
    uuid         tinytext  null,
    createTime   timestamp null,
    updateTime   timestamp null
)
    comment '用户参与活动记录表';

-- auto-generated definition
create table user_take_activity_count
(
    id         bigint    null,
    uId        tinytext  null,
    activityId bigint    null,
    totalCount int       null,
    leftCount  int       null,
    createTime timestamp null,
    updateTime timestamp null
)
    comment '用户活动参与次数表';

-- auto-generated definition
create table user_strategy_export_001(id           bigint     null,uId          mediumtext null,activityId   bigint     null,orderId      bigint     null,strategyId   bigint     null,strategyType int        null,grantType    int        null,grantDate    timestamp  null,grantState   int        null,awardId      bigint     null,awardType    int        null,awardName    mediumtext null,awardContent mediumtext null,uuid         mediumtext null,createTime   timestamp  null,updateTime   timestamp  null) comment '用户策略计算结果表';
create table user_strategy_export_002(id           bigint     null,uId          mediumtext null,activityId   bigint     null,orderId      bigint     null,strategyId   bigint     null,strategyType int        null,grantType    int        null,grantDate    timestamp  null,grantState   int        null,awardId      bigint     null,awardType    int        null,awardName    mediumtext null,awardContent mediumtext null,uuid         mediumtext null,createTime   timestamp  null,updateTime   timestamp  null) comment '用户策略计算结果表';
create table user_strategy_export_003(id           bigint     null,uId          mediumtext null,activityId   bigint     null,orderId      bigint     null,strategyId   bigint     null,strategyType int        null,grantType    int        null,grantDate    timestamp  null,grantState   int        null,awardId      bigint     null,awardType    int        null,awardName    mediumtext null,awardContent mediumtext null,uuid         mediumtext null,createTime   timestamp  null,updateTime   timestamp  null) comment '用户策略计算结果表';
create table user_strategy_export_004(id           bigint     null,uId          mediumtext null,activityId   bigint     null,orderId      bigint     null,strategyId   bigint     null,strategyType int        null,grantType    int        null,grantDate    timestamp  null,grantState   int        null,awardId      bigint     null,awardType    int        null,awardName    mediumtext null,awardContent mediumtext null,uuid         mediumtext null,createTime   timestamp  null,updateTime   timestamp  null) comment '用户策略计算结果表';


```

<img src="E:\Document\Typora笔记\Pictures\image-20230512110639503.png" alt="image-20230512110639503"  />

## 第05节：抽奖策略领域模块开发

### 5.1需求

![5-01](E:\Document\Typora笔记\Pictures\5-01.png)

策略单独独立出来，和奖品没有关系

### 5.2 领域功能结构

<img src="E:\Document\Typora笔记\Pictures\领域功能结构.png" alt="领域功能结构"  />

### 5.3 抽奖算法实现

![抽奖算法](E:\Document\Typora笔记\Pictures\抽奖算法.png)

![抽奖算法2](E:\Document\Typora笔记\Pictures\抽奖算法2.png)

### 5.4 问题

- `ConcurrentHashMap`

  [ConcurrentHashMap](https://so.csdn.net/so/search?q=ConcurrentHashMap&spm=1001.2101.3001.7020)和HashMap原理基本类似，只是在HashMap的基础上需要支持并发操作，保证多线程情况下对HashMap操作的安全性。

- `Springboot 常用注解`

  - 启动注解 @SpringBootApplication
  - @Component
  - @Slf4j
  - @Data
  - @Resource
  - @Before
  - @PostConstruct

  
