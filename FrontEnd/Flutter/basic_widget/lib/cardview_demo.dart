import 'package:flutter/material.dart';

/// Card 的基本使用
class CardViewDemoPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _CardViewPageDemoState();
}

class _CardViewPageDemoState extends State<CardViewDemoPage> {
  @override
  Widget build(BuildContext context) {
    List<Widget> list = <Widget>[];
    for (int i = 0; i < 30; i++) {
      list.add(new Card(
        elevation: 15.0,  //设置阴影
        shape: const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(10.0))),  //设置圆角
        child: new Column(
          children: <Widget>[
            new Container(
              width: 250,
              height: 150,
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(10),
                  image: DecorationImage(
                    image: NetworkImage('https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg'),
                    fit: BoxFit.cover,
                  )
              ),
            ),
//            new Image.asset("images/c3.jpg"), // 这样写没有圆角效果
            new ListTile(
              title: new Text('title$i', style: _itemTextStyle),
              subtitle: new Text('A'),
              leading: i % 3 == 0
                ? new Icon(Icons.theaters, color: Colors.blue)
                : new Icon(Icons.restaurant,color: Colors.blue),
            )
          ],
        ),
      ));
    }
    return new Scaffold(
      appBar: new AppBar(title: new Text('ListView Demo'),),
      body: new Center(child: new ListView(children: list),),
    );
  }

  TextStyle _itemTextStyle = new TextStyle(
      fontWeight: FontWeight.w200,
      fontSize: 14.0
  );
}