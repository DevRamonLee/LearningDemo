import 'package:flutter/material.dart';

class ListViewWidget extends StatelessWidget {
  List<String> list = new List();
  ListViewWidget() {
    list.add("Movie");
    list.add("KTV");
  }

  @override
  Widget build(BuildContext context) {
    Widget divider1 = Divider(color: Colors.blue);
    Widget divider2 = Divider(color: Colors.red);

    return Column(
      children: <Widget>[
        ListView( // 默认构造函数
          shrinkWrap: true,
          children: <Widget>[
            const Text("Hello", textScaleFactor: 2),
            const Text("flutter", textScaleFactor: 2)
          ],
        ),

        ListView.builder( // 适用于大量数据
          shrinkWrap: true,
          itemCount: list.length,
          itemExtent: 50,
          itemBuilder:(context, index) {
            return ListTile(
              leading: Icon(Icons.movie),
              title: Text("${list[index]}"),
            );
          },
        ),
        Expanded(
          child:ListView.separated(
                  itemCount: 8,
                  // 列表项构造器
                  itemBuilder: (BuildContext context, int index) {
                    return ListTile(title: Text("$index"));
                  },
                  // 分割器构造器
                  separatorBuilder: (BuildContext context, int index) {
                    return index%2 == 0 ? divider1:divider2;
                  },
              )
        )
      ],
    );
  }
}