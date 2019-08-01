import 'package:flutter/material.dart';

/// ListView  默认构造函数
class ListViewPageDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _ListViewPageDemoState();
}

class _ListViewPageDemoState extends State<ListViewPageDemo> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(title: new Text('ListView Demo'),),
      body: new Center(child: new ListView(
          padding: EdgeInsets.all(8.0),
          children: <Widget>[
            Container(
              height: 50,
              color: Colors.amber[600],
              child: const Center(child: Text('Enter A')),
            ),
            Container(
              height: 50,
              color: Colors.amber[500],
              child: const Center(child: Text('Enter B')),
            ),
            Container(
              height: 50,
              color: Colors.amber[100],
              child: const Center(child: Text('Enter C')),
            )
          ],)
      ),
    );
  }
}