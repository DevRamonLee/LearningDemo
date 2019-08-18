import 'package:flutter/material.dart';
import 'package:gesture_animation/message.dart';

class MessageForm extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _MessageFormState();
}

class _MessageFormState extends State<MessageForm> {
  final editController = TextEditingController();

  // 对象被从对象树种销毁的时候调用 dispose ，我们需要释放 editController 资源
  @override
  void dispose() {
    super.dispose();
    editController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("echo 客户端"),
        ),
        body: Padding(
          padding: EdgeInsets.all(16.0),
          child: Row(
            children: <Widget>[
              // 让输入框占满一行里除了按钮外的其他空间
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(right: 8.0),
                  // 使用 Scallfold 包裹 TextField
                  child: TextField(
                    decoration: InputDecoration(
                        hintText: 'input message',
                        contentPadding: EdgeInsets.all(0.0)),
                    style: TextStyle(fontSize: 22.0, color: Colors.black54),
                    controller: editController,
                    // 自动获取焦点
                    autofocus: true,
                  ),
                ),
              ),
              InkWell(
                onTap: (){
                  debugPrint('send: ${editController.text}');
                  // 返回的数据
                  final msg = Message(
                    editController.text,
                    DateTime.now().millisecondsSinceEpoch
                  );
                  Navigator.pop(context, msg);
                },
                onDoubleTap: () => debugPrint('double tapped'),
                onLongPress: () => debugPrint('long pressed'),
                child: Container(
                  padding:
                      EdgeInsets.symmetric(vertical: 10.0, horizontal: 16.0),
                  decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.circular(5.0)),
                  child: Text('send'),
                ),
              )
            ],
          ),
        ));
  }
}
