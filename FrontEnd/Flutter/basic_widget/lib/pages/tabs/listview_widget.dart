import 'package:flutter/material.dart';

class ListViewWidget extends StatefulWidget {
  ListViewWidget(Key key): super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _ListViewWidgetState();
  }
}

class _ListViewWidgetState extends State<ListViewWidget> {
  List<String> list = new List();
  @override
  void initState() {
    super.initState();
    list.add("电影院");
    list.add("KTV");
    list.add("汽车站");
  }
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: this.list.length,
      itemExtent: 200,
      itemBuilder:(context, index) {
        return ListTile(
          leading: Icon(Icons.movie),
          title: Text("${list[index]}"),
        );
      },
    );
  }
}