# AndroidHttpClient
Android 设备间通过 TCP 进行局域网通信，该项目作为客户端使用。请配合 [AndroidHttpServer](https://github.com/hgncxzy/AndroidHttpServer) 项目作为服务器使用。
### 说明
1. PC 分配给所有的模拟器 IP 地址均为 10.0.2.2 
2. 模拟器之间是无法直接进行通讯的，必须通过端口映射，比如将模拟器 M1 6302 的端口映射到模拟器 M2 的 7302 端口，映射成功后，就可以通过模拟器 M1 的 10.0.2.2:6302 地址访问到模拟器 M2 的 10.0.2.2:7302 地址。

### 模拟器调试步骤
1. 同时打开服务端模拟器、客户端模拟器
2. 执行如下命令，关联端口映射  adb -s emulator-5554 forward tcp:6302 tcp:7302

ps:将 emulator-5554 替换为你本地的服务器端模拟器名称，另外客户端端口 6302 和服务端端口 7302 均为自定义端口，可更改。

### 真机调试步骤
1. 设定服务端固定 IP，并修改客户端请求 URL 为该 IP 地址
2. 客户端连接服务端 WIFI，保证在同一局域网
3. 客户端发起网络请求，即可收到服务端数据回调

### 参考
1. [多个Android模拟器之间如何进行通信](https://blog.csdn.net/jdsjlzx/article/details/7362239)
