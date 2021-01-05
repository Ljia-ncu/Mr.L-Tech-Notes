## 【Mr.L-技术栈 Draft】

- [x] github: https://github.com/Ljia-ncu

[TOC]

> “理论和思想的提出永远高于其实现” 

```
记录一些常用技术栈的模板化API/demo以及基本组件的部署，以备参考；
完善中...
```



| env/dev-tools                                          |
| ------------------------------------------------------ |
| jdk1.8、maven 3.6.1、IntelliJ IDEA Community Edition； |
| MySQL Server 8.0、Navicat Premium 15；                 |
| Redis 6.0、RedisDesktopManager；                       |
| Xshell/SmarTTy/SecureCRT、Xftp6/FileZila/SFTP；        |
| 云服务器 ECS 1核2G、对象存储OSS；                      |
| Docker/docker-compse 、Jenkins、K8s；                  |
| Postman、Jmeter、MemoryAnalyzer；                      |
| Beyond Compare 4；                                     |



### 1 Alibaba Cloud

| dependency                        | version       |
| --------------------------------- | ------------- |
| spring-cloud-alibaba-dependencies | 2.2.1.RELEASE |

#### 1.1 nacos

- [x] docker-compose搭建nacos-standalone注册/配置中心；(mysql8,nacos/nacos-server:1.4.0)

- [x] @RefreshScope 刷新配置

#### 1.2 sentinel

- [x] 由于没有做内网穿透，所以将组件部署到本地；

- [x] @SentinelResource 根据resource/url进行限流（包括feign），根据blockHandler进行降级；

- [x] 通过nacos将流控规则以json方式持久化到mysql；

#### 1.3 seata 

- [x] 搭建本地seata组件，配置seata-server db,以及业务db(加undo_log)

- [x] @GlobalTransactional 测试AT模式下的分布式事务提交与回滚

- [ ] jmeter测试发现 大量请求下仅有极少数事务成功
  exception：Could not register branch into global session / Read timeout(通过配置feign.readTimeout并不能有效解决)

  

### 2 Aliyun

#### 2.1 OSS

- [x] 激活对象存储OSS，配置bucket、RAM访问控制、获取accessKeyId & accessKeySecret；
- [x] 使用policy签名上传文件，并通过callback回调上传结果；
- [x] docker-compose 部署、测试；



### 3 Canal

| dependency   | version |
| ------------ | ------- |
| canal.client | 1.1.4   |

- [x] 本地搭建mysql8 + canal；
- [x] 解决caching_sha2_password Auth failed错误；

- [x] 解析增量日志（message 1:1 batch 1:n entry 1:1 rowchange 1:1 rowdatalist ）



### 4 Dubbo

- [x] 基于nacos + dubbo 进行RPC调用；



### 5 ElasticSearch

- [x] 本地搭建**ELK**环境(ECS内存资源有限)，并配置ik分词器；

- [x] 基于spring-boot-starter-data-elasticsearch，使用ElasticsearchRepository实现基本CRUD、分页操作；

- [x] 基于RestHighLevelClient实现复合查询，match、filter、sort、highlighter、aggregation...,并解析聚合结果；

- [ ] keyword大小写敏感，待解决

#### 5.1 logstash

- [x] 配置logback-spring.xml，将sl4j日志输出到logstash_input端口，再由logstash输出到es，使用index-%{+YYYY.MM.dd}形式进行索引；

- [x] 通过kibana控制台查看日志信息；



### 6 Kafka

- [x] kafka通过配置实现消息可靠投递；



### 7 MyBatisPlus

- [x] 基于mybatisplus-generator生成指定库指定表的entity、mapper、service、impl；
- [x] 配置MybatisPlusInterceptor分页/乐观锁拦截器，以及FieldFill的插入&更新自动填充；
- [x] 基于lamada表达式的CRUD操作；
- [x] 测试IdWorker的分布式主键



### 8 Netty



### 9 Nginx.

- [ ] 搭建lvs + nginx + keepalived双机热备；
- [ ] 阿里云ALB；
- [x] 测试负载均衡、动静分离；



### 10 RabbitMq

- [x] docker搭建mq组件；
- [ ] 镜像集群；
- [x] confirm模式通过ConfirmCallback实现publisher -> exchange的可靠投递；
- [x] 基于ReturnCallback实现exchange -> queue的可靠投递；
- [x] 基于acknowledge-mode: manual进行手动ack；
- [x] 死信队列；



### 11 Redis

- [x] 脚本+docker-compose搭建redis集群(单机)
- [x] 使用RedisTemplate操作常用数据结构；
- [ ] executePipelined批量操作；

#### 11.1 SpringCache

- [x] 基于SpringCache实现方法缓存；

#### 11.2 Redisson

- [x] 修复redisson异常 Can't connect to master: redis://172.28.85.145:6373 with slot ranges: [[10923-16383]]

- [x] 测试RLock、RedissonRedLock分布式锁；
- [ ] 布隆过滤器；



### 12 RocketMq

| dependency                   | version |
| ---------------------------- | ------- |
| rocketmq-client 或           | 4.7     |
| rocketmq-spring-boot-starter | 2.1.1   |

- [x] 本地部署单master；
- [ ] docker-compose部署 同步双写 + mq-console;

- [x] 同步发送、异步发送、单向发送；
- [x] 延迟消息、顺序消息、事务消息；

- [x] 整合rocketmq-spring-boot-starter；



### 13 ThreadPool

- [x] ThreadPoolExecutor、newFixedThreadPool、newSingleThreadExecutor、newCachedThreadPool、newScheduledThreadPool、newWorkStealingPool；
- [x] SynchronousQueue、DelayedWorkQueue
- [ ] ForkJoinPool



### 14 XXL-Job

| dependency   | version |
| ------------ | ------- |
| xxl-job-core | 2.2.0   |

- [x] docker-compose搭建xxl-job-admin；
- [x] docker-compose部署多个executors，并测试定时任务负载均衡调度；



### 15 Zookeeper

| dependency        | version |
| ----------------- | ------- |
| curator-recipes   | 4.3.0   |
| curator-framework | 4.3.0   |
| zookeeper         | 3.6.2   |

- [x] docker-compose搭建zk集群(单机)；
- [x] 测试InterProcessMutex分布式锁；
- [ ] addr异常，版本问题；



### 16 SpringCloud

| dependency                | version    |
| ------------------------- | ---------- |
| spring-cloud-dependencies | Hoxton.SR9 |

#### 16.1 Spring Admin

- [x] 搭建Spring Admin监控服务



#### 16.2 Eureka

- [x] 搭建eureka集群；



#### 16.3 Gateway

- [x] @EnableWebFlux + routes配置路由表
- [ ] jwt鉴权




#### 16.4 OpenFeign

- [x] 基于@FeignClient调用远程服务，通过fallback实现降级；



### 17 SpringBoot

| dependency                 | version       |
| -------------------------- | ------------- |
| spring-boot-starter-parent | 2.3.2.RELEASE |

#### 17.1 SpringSecurity

- [x] 参考**Java Brain** 实现**jwt**+SpringSecurity鉴权；



#### 17.2 Aspect

- [x] @Aspect + @Pointcut 切自定义注解/方法；



#### 17.3 Schedule

- [x] 使用Cron表达式实现定时任务；



#### 17.4 Stream

- [x] stream api；
- [x] 函数式接口；
- [x] CompletableFutrue；



### 18 Shardingsphere



### 19 K8s



### 20 Prometheus + Grafana
