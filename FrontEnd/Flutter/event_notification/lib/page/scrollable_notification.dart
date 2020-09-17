import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ScrollableNotification extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("滚动通知"),),
      body: NotificationListener(
        onNotification: (notificaiton) {
          switch (notificaiton.runtimeType) {
            case ScrollStartNotification: print("开始滚动"); break;
            case ScrollUpdateNotification: print("正在滚动"); break;
            case ScrollEndNotification: print("滚动停止"); break;
            case OverscrollNotification: print("滚动到边界"); break;
          }
          return false; // 继续冒泡
        },
        child: ListView.builder(
            itemCount: 100,
            itemBuilder: (context, index) {
              return ListTile(title: Text("$index"),);
            }),
      )
    );
  }
}