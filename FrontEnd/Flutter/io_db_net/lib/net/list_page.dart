import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class ListPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _ListPageState();
  }
}

class _ListPageState extends State<ListPage> {
  // 数据源
  List data;

  @override
  void initState() {
    super.initState();
    _pullNet();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: AppBar(
        title: Text('网络请求列表'),
      ),
      body: new ListView(
//        children: <Widget>[getItem2(), getItem2()],
          children: data != null ? _getItem() :_showLoading()
      ),
    );
  }

  // 预加载界面
  List<Widget> _showLoading() {
    return <Widget>[
      new Container(
        height: 300.0,
        child: new Center(
          child: new Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              new CircularProgressIndicator(
                strokeWidth: 1.0,
              ),
              new Text("正在加载"),
            ],
          ),
        ),
      )
    ];
  }

  // http 包 get 请求
  void _pullNet() async {
    await http
        .get("http://www.wanandroid.com/project/list/1/json?cid=1")
        .then((http.Response response) {
      var convertDataToJson = json.decode(response.body);
      convertDataToJson = convertDataToJson["data"]["datas"];
      //打印请求的结果
      print(convertDataToJson);
      //更新数据
      setState(() {
        data = convertDataToJson;
      });
    });
  }

  List<Widget> _getItem() {
    return data.map((item) {
      return new Card(
        child: new Padding(
            padding: const EdgeInsets.all(10.0), child: _getRowWidget(item)),
        elevation: 3.0,
        margin: const EdgeInsets.all(10.0),
      );
    }).toList();
  }

  Widget _getRowWidget(item) {
    return new Row(children: <Widget>[
      new Flexible(
          flex: 1,
          fit: FlexFit.tight, // 和 android weight = 1 效果一样
          child: new Stack(
            children: <Widget>[
              new Column(
                children: <Widget>[
                  new Text(
                    "${item["title"]}".trim(),
                    style: new TextStyle(color: Colors.black, fontSize: 20.0),
                    textAlign: TextAlign.left,
                  ),
                  new Text(
                    "${item["desc"]}",
                    maxLines: 3,
                  )
                ],
              )
            ],
          )),
      new ClipRect(
        child: new FadeInImage.assetNetwork(
          placeholder: "images/ic_normal_holder.png",
          image: "${item['envelopePic']}",
          width: 50,
          height: 50,
          fit: BoxFit.fitWidth,
        ),
      )
    ]);
  }

  Widget getItem2() {
    return new Card(
      child: new Padding(
          padding: const EdgeInsets.all(10.0), child: _getRowWeight2()),
      elevation: 3.0,
      margin: const EdgeInsets.all(10.0),
    );
  }

  Widget _getRowWeight2() {
    return new Row(
      children: <Widget>[
        new Flexible(
            flex: 1,
            fit: FlexFit.tight, // 和 android weight = 1 效果一样
            child: new Stack(
              children: <Widget>[
                new Column(
                  children: <Widget>[
                    new Text(
                      "title".trim(),
                      style: new TextStyle(color: Colors.black, fontSize: 20.0),
                      textAlign: TextAlign.left,
                    ),
                    new Text(
                      "desc",
                      maxLines: 3,
                    )
                  ],
                )
              ],
            )),
        new ClipRect(
          child: new FadeInImage.assetNetwork(
            placeholder: "images/ic_normal_holder.png",
            image: "images/ic_normal_holder.png",
            width: 50,
            height: 50,
          ),
        )
      ],
    );
  }
}
