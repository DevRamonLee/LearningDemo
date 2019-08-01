import 'package:flutter/material.dart';

/// ListView.Build 构造函数
class ListViewPageDemo3 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _ListViewPageDemo3State();
}

class _ListViewPageDemo3State extends State<ListViewPageDemo3> {

  final List<String> entries = <String>['A', 'B', 'C'];
  final List<int> colorCodes = <int>[600, 500, 100];

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        appBar: new AppBar(title: new Text('ListView Demo'),),
        body: new Center(child: new ListView.separated(
            padding: EdgeInsets.all(8.0),
            itemCount: entries.length,
            itemBuilder: (BuildContext context, int index) {
              return Container(
                height: 50,
                color: Colors.amber[colorCodes[index]],
                child: Center(child: Text('Entry ${entries[index]}')),
              );
            },
            separatorBuilder: (BuildContext context, int index) => const Divider(),
            ),
        )
    );
  }
}