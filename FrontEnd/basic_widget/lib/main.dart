import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: new Scaffold(body: LayoutExampleOne()),
    );
  }
}

// 文本
class TextWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Text(
      'Hello Text Widget',
      // 添加样式
      style: TextStyle(
          color: Colors.blue, fontSize: 16.0, fontWeight: FontWeight.bold),
    );
  }
}

// 图片
class ImageWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Image.network(
      "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg",
      width: 200.0,
      height: 150.0,
    );
  }
}

// 按钮
class ButtonWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var flatBtn = FlatButton(
      onPressed: () => print('FatButton pressed'),
      child: Text('FlatButton'),
    );
    var raiseButton = RaisedButton(
      onPressed: () => print('RaiseButton pressed'),
      child: Text('RaiseButton'),
    );
    return raiseButton;
  }
}

// 文本输入框, Container、Center
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
    return Center(
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
    );
  }

  @override
  void dispose() {
    super.dispose();
    // 手动调用 controller 的 dispose 方法释放资源
    editController.dispose();
  }
}

class ExpandWidget extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Row(
      children: <Widget>[
      Expanded(
        // 占一行的 2/3
        flex:2,
        child: RaisedButton(
          child:Text('btn1'),
        ),
     ),
      Expanded(
      // 占一行的 1/3
        flex:1,
        child:RaisedButton(child:Text('btn2'),
        ),
      ),
      ],
    );
  }
}

// 可以对组件进行堆叠摆放
class StackWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Stack(
      // Alignment 指定组件的堆叠位置，取值范围为 [-1,1] Stack 中心为 (0,0)
      // (-0.5, -0.5) 可以让文本对齐到 Container 的 1/4 处
      alignment: const Alignment(-0.5, -0.5),
      children: <Widget>[
        Container(
          width: 200.0,
          height: 200.0,
          color: Colors.blue,
        ),
        Text('foobar'),
      ],
    );
  }
}


// 布局综合案例1
class LayoutExampleOne extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _LayoutExampleOneState();
  }
}

class _LayoutExampleOneState extends State<LayoutExampleOne> {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        Row(
          children: <Widget>[
            Expanded(
              child: Image.network(
        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg"),
            )
          ],
        ),
        Container(
          child: Text('Oeschinen Lake Campground'),
          margin: EdgeInsets.all(30.0),
          padding: EdgeInsets.all(10.0),
          decoration: BoxDecoration(
            color: Colors.blue,
            borderRadius: BorderRadius.circular(10.0),
          ),
        )
      ],
    );
  }
}