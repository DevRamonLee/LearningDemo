import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

// StreamBuilder 使用，创建一个计数器，每隔 1 秒，计数 + 1
class MyAppSeven extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MyAppSevenState();
}

class _MyAppSevenState extends State<MyAppSeven> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("StreamBuilder"),
      ),
      body: Center(
        child: StreamBuilder<int> (
          stream: counter(),
          builder: (BuildContext context, AsyncSnapshot snapshot) {
            if (snapshot.hasError) {
              // 请求失败，显示错误
              return Text("Error ${snapshot.error}");
            }
            switch (snapshot.connectionState) {
              case ConnectionState.none:
                return Text("没有 Stream");
                break;
              case ConnectionState.waiting:
                return Text("等待数据...");
                break;
              case ConnectionState.active:
                return Text("active: ${snapshot.data}");
                break;
              case ConnectionState.done:
                return Text("Stream 已关闭");
                break;
            }
            return null;
          },
        ),
      ),
    );
  }

  // 使用 Stream 实现每隔 1 秒生成一个数字
  Stream<int> counter() {
    return Stream.periodic(Duration(seconds: 1), (i) {
      return i;
    });
  }
}
