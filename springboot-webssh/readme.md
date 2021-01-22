 SpringBoot+Websocket+jsch+xterm.js
 
 修改webssh.html中的
 ```$xslt
openTerminal( {
        operate:'connect',
        host: '',//IP
        port: '',//端口号
        username: '',//用户名
        password: ''//密码
    });
```
以及webssh.js中的
```$xslt
var endpoint = protocol+'127.0.0.1:8080/webssh';
    return endpoint;
```