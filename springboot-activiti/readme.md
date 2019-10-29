1. 启动微服务
2. swagger路径：http://localhost:8085/swagger-ui.html  使用相应接口
3. 未完成。。。


其他
springboot activiti 配置项详解
 

asyncExecutorEnabled属性设置设置true后将代替那些老的Job executor
spring.activiti.async-executor-enabled=false
spring.activiti.job-executor-activate=false

asyncExecutorActivate是指示activiti在流程引擎启动就激活AsyncExecutor,异步
spring.activiti.async-executor-activate=

校验流程文件，默认校验resources下的processes文件夹里的流程文件
spring.activiti.check-process-definitions=

使用自定义mybatis-mapper
spring.activiti.custom-mybatis-mappers=
spring.activiti.custom-mybatis-xmlmappers=

数据源指定
spring.activiti.database-schema=

建表规则
flase： 默认值。activiti在启动时，会对比数据库表中保存的版本，如果没有表或者版本不匹配，将抛出异常。
true： activiti会对数据库中所有表进行更新操作。如果表不存在，则自动创建。
create_drop： 在activiti启动时创建表，在关闭时删除表（必须手动关闭引擎，才能删除表）。
drop-create： 在activiti启动时删除原来的旧表，然后在创建新表（不需要手动关闭引擎）
spring.activiti.database-schema-update=false

检测历史表是否存在
spring.activiti.db-history-used=false

检测身份信息表是否存在
spring.activiti.db-identity-used=false

流程部署名称
spring.activiti.deployment-name=

记录历史等级 可配置的历史级别有none, acitivity, audit, all
spring.activiti.history-level=

spring jpa使用
spring.activiti.jpa-enabled=false

邮件发送服务配置
spring.activiti.mail-server-default-from=
spring.activiti.mail-server-host=
spring.activiti.mail-server-password=
spring.activiti.mail-server-port=
spring.activiti.mail-server-use-ssl=
spring.activiti.mail-server-use-tls=
spring.activiti.mail-server-user-name=

自定义流程文件位置
spring.activiti.process-definition-location-prefix=
spring.activiti.process-definition-location-suffixes=

activiti rest 配置
spring.activiti.rest-api-enabled=false
spring.activiti.rest-api-mapping=
spring.activiti.rest-api-servlet-name=