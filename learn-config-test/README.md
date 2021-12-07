基于长轮训实现简易配置中心

客户端发起长轮询，如果服务端的数据没有发生变更，会 hold 住请求，直到服务端的数据发生变化，或者等待一定时间超时才会返回。返回后，客户端又会立即再次发起下一次长轮询。

```shell
curl -X GET "localhost:8080/publishConfig?dataId=user&configInfo=helloworld"
```