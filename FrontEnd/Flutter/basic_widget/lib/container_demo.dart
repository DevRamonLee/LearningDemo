import 'package:flutter/material.dart';

/// Container 的基本使用
class ContainerDemoPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _ContainerDemoPageState();
}

class _ContainerDemoPageState extends State<ContainerDemoPage> {
  @override
  Widget build(BuildContext context) {
    Expanded imageExpanded(String img) {
      return new Expanded(
          child: new Container(
        decoration: new BoxDecoration(
          border: new Border.all(width: 10.0, color: Colors.black12),
//          borderRadius: new BorderRadius.all(
//            const Radius.circular(8.0)
//          ),
          // 单独设置
          borderRadius: const BorderRadius.only(
            topLeft: const Radius.circular(3.0),
            topRight: const Radius.circular(6.0),
            bottomLeft: const Radius.circular(9.0),
            bottomRight: const Radius.circular(0.0),
          ),
          // 设置背景图片
//          image: const DecorationImage(
//            image: const NetworkImage(
//                'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg'),
//            fit: BoxFit.contain,
//          ),
            color: Colors.amber
        ),
        margin: const EdgeInsets.all(4.0),
        child: new Image.asset(img),
      ));
    }

    var container = new Container(
      decoration: new BoxDecoration(color: Colors.black12),
      child: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            new Row(
              children: <Widget>[
                imageExpanded("images/c1.jpg"),
                imageExpanded("images/c2.jpg")
              ],
            ),
            new Row(
              children: <Widget>[
                imageExpanded("images/c3.jpg"),
                imageExpanded("images/c4.jpg")
              ],
            ),
            new Row(
              children: <Widget>[imageExpanded("images/c5.jpg")],
            )
          ],
        ),
      ),
    );
    return new Scaffold(
      appBar: new AppBar(title: new Text('Container demo page')),
      body: new Center(
        child: container,
      ),
    );
  }
}
