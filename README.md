# awesome-boot-netty


## Test

connect to tcp server,

```shell
telnet localhost 9494
```

send a login message,

```shell
{0F8643330470988270000100251241BE4F9C8A2672A4}
```

list all clients,

```shell
curl -v http://localhost:8080/clients
```

send command,

```shell
curl -v http://localhost:8080/clients/864333047098827000/cmd/123 -X POST
```
