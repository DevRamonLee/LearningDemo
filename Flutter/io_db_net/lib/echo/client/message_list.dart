import 'package:flutter/material.dart';
import 'package:io_db_net/echo/client/message_form.dart';
import 'package:io_db_net/echo/message.dart';
import 'package:io_db_net/echo/server/http_echo_server.dart';
import 'package:io_db_net/echo/client/http_echo_client.dart';
/// 例：echo 客户端，列表展示页
final messageListKey = GlobalKey<_ShowListState>(debugLabel: 'showListKey');
class MessageList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("列表详情页"),),
      body: ShowList(key: messageListKey),
      floatingActionButton: FloatingActionButton(
          onPressed: () async{
            final result = await Navigator.push(
            context,
            MaterialPageRoute(builder: (_) => MessageForm()));  // push 一个新的 route 到 Navigator 管理栈，以此来打开一个新的页面

            // 以下是修改了的地方
            if (_client == null) return;
            // 现在，我们不是直接构造一个 Message，而是通过 _client 把消息发送给服务器
            var msg = await _client.send(result);
            if (msg != null) {
              messageListKey.currentState.addMessage(msg);
            } else {
              debugPrint('fail to send $result');
            }
          },
        tooltip: 'add mesage',
        child: Icon(Icons.add),
      ),
    );
  }
}

class ShowList extends StatefulWidget {
  // 这里的 key 有什么用，后面会讲到
  ShowList({Key key}):super(key: key);
  @override
  State<StatefulWidget> createState() => _ShowListState();
}

HttpEchoServer _server;   // 创建服务器
HttpEchoClient _client;   // 创建客户端

// 为了使用 WidgetsBinding，我们继承 WidgetsBindingObserver 然后覆盖相应的方法
class _ShowListState extends State<ShowList>  with WidgetsBindingObserver {
  final List<Message> messages = [];

  @override
  void initState() {
    super.initState();
    const port = 6060;
    _server = HttpEchoServer(port);
    // initState 不是一个 async 函数，这里我们不能直接 await _server.start(),
    // future.then(...) 跟 await 是等价的
    _server.start().then((_){
      // 等服务器启动后才创建客户端
      _client = HttpEchoClient(port);
      // 创建客户端后马上去拉取历史数据
      _client.getHistory().then((list){
        setState(() {
          messages.addAll(list);
        });
      });
    });
    // 注册生命周期回调
    WidgetsBinding.instance.addObserver(this);
  }
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: messages.length,
        itemBuilder: (context, index) {
          final msg = messages[index];
          final subTitle = DateTime.fromMicrosecondsSinceEpoch(msg.timestamp).toLocal().toIso8601String();
          return ListTile(
            title: Text(msg.msg),
            subtitle: Text(subTitle),
          );
      });
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    // 这里监听的是整个 app 的生命周期变化
    if(state == AppLifecycleState.paused) {
      var server = _server;
      _server = null;
      server?.close();
    }
  }

  void addMessage(Message msg) {
    setState(() {
      messages.add(msg);
    });
  }
}