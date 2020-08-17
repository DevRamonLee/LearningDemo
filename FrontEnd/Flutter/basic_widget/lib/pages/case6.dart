import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

// FutureBuilder 使用，当打开路由时从网络获取数据，获取数据时显示加载框，获取成功则显示数据，获取失败则显示错误
class MyAppSix extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MyAppSixState();
}

class _MyAppSixState extends State<MyAppSix> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("FutureBuilder"),
      ),
      body: Center(
        child: FutureBuilder<String> (
          future: mockNetWorkData(),
          builder: (BuildContext context, AsyncSnapshot snapshot) {
            // 请求已结束
            if (snapshot.connectionState == ConnectionState.done) {
              if (snapshot.hasError) {
                // 请求失败，显示错误
                return Text("Error ${snapshot.error}");
              } else {
                //请求成功
                return Text("Content ${snapshot.data}");
              }
            } else {
              // 请求未结束，显示进度条
              return CircularProgressIndicator();
            }
          },
        ),
      ),
    );
  }

  Future<String> mockNetWorkData() async {
    return Future.delayed(Duration(seconds: 2), ()=> "我是从网上来的");
  }
}
