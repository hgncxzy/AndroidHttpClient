# AndroidHttpClient
Android 设备间通过 TCP 进行局域网通信，该项目作为客户端使用。请配合 [AndroidHttpServer](https://github.com/hgncxzy/AndroidHttpServer) 项目作为服务器使用。
### 模拟器调试步骤
1. 同时打开服务端模拟器、客户端模拟器\n
2. 执行如下命令，关联端口映射  adb -s emulator-5554 forward tcp:6302 tcp:7302\n
ps:将 emulator-5554 替换为你本地的服务器端模拟器名称，另外客户端端口 6302 和服务端端口 7302 均为自定义端口，可更改。
### 真机调试步骤
1. 设定服务端固定 IP，并修改客户端请求 URL 为该 IP 地址
2. 客户端连接服务端 WIFI，保证在同一局域网
3. 客户端发起网络请求，即可收到服务端数据回调
