获取ip地址，当ip地址变动时，进行相应提醒

四种方法查询本地ip
```from urllib2 import urlopen
from json import load

my_ip = urlopen('http://ip.42.pl/raw').read()
print('ip.42.pl', my_ip)

my_ip = load(urlopen('http://jsonip.com'))['ip']
print('jsonip.com', my_ip)

my_ip = load(urlopen('http://httpbin.org/ip'))['origin']
print('httpbin.org', my_ip)

my_ip = load(urlopen('https://api.ipify.org/?format=json'))['ip']
print('api.ipify.org', my_ip)```


根据ip查询本地信息
网上教程多为新浪、淘宝等接口，当然可以，但是淘宝的ip库访问是有权限的

淘宝api: http://ip.taobao.com/service/getIpInfo.php?ip=[ip地址字串]

返回的数据格式是json格式，附上限制条件： http://ip.taobao.com/

所以今天推荐一个新的api查询
请求api： https://api.pi.do/api/v1/queryip?ip=[ip地址字符串]

示例： https://api.pi.do/api/v1/queryip?ip=124.236.219.174

返回结果为json字符串

交给配置文件管理的方式
方法1
@Configuration  //为配置类
public class RedisConfig {
    @Bean   //向spring注册bean  类似于@service的注解
    @Qualifier("iRedis")
    @ConditionalOnProperty(name = "redisFlag", havingValue = "true") //由配置文件操控
    public IRedis getRedis1() {
        return new RedisImp1();
    }

    @Bean
    @Qualifier("iRedis")
    @ConditionalOnProperty(name = "redisFlag", havingValue = "false")
    public IRedis getRedis2() {
        return new RedisImp2();
    }
}
方法2
@Configuration
@ConditionalOnProperty(name = "redisFlag", havingValue = "true")
public class RedisImp1 implements IRedis {

}
