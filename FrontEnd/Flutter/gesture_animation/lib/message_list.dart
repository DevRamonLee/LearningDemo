import 'package:flutter/material.dart';
import 'package:gesture_animation/message_form.dart';
import 'package:gesture_animation/message.dart';
/// 列表展示页

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
            debugPrint("result: $result");
            if (result is Message) {
              messageListKey.currentState.addMessage(result);
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

class _ShowListState extends State<ShowList> {
  final List<Message> messages = [];
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

  void addMessage(Message msg) {
    setState(() {
      messages.add(msg);
    });
  }
}