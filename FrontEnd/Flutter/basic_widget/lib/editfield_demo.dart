import 'package:flutter/material.dart';

/// 文本输入框
class MessageForm extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MessageFormState();
  }
}

class _MessageFormState extends State<MessageForm> {
  // 设置一个 controller，通过这个 controller 拿到文本框里的内容
  var editController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    // Row、Expand 是用于布局的控件
    return  Scaffold(
      appBar: new AppBar(title: new Text('文本输入框')),
      body: Center(
      child: Container(
        child: Row(
          children: <Widget>[
            Expanded(
              child: TextField(
                controller: editController,
              ),
            ),
            RaisedButton(
              child: Text('click'),
              onPressed: () {
                showDialog(
                  // 第一个 context 是参数名，第二个 context 是 State 的成员变量
                    context: context,
                    builder: (_) {
                      return AlertDialog(
                        content: Text(editController.text),
                        actions: <Widget>[
                          FlatButton(
                            child: Text('Ok'),
                            // 点击按钮后，关闭弹框
                            onPressed: () => Navigator.pop(context),
                          )
                        ],
                      );
                    });
              },
            )
          ],
        ),
        padding: EdgeInsets.all(30.0),
        margin: EdgeInsets.all(4.0),
        decoration: BoxDecoration(
          // 背景色
          color: Colors.grey,
          // 圆角
          borderRadius: BorderRadius.circular(5.0),
        ),
      ),
    ),);
  }

  @override
  void dispose() {
    super.dispose();
    // 手动调用 controller 的 dispose 方法释放资源
    editController.dispose();
  }
}