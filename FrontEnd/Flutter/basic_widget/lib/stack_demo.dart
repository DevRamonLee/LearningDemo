import 'package:flutter/material.dart';

/// Stack 布局（有两种常用方式 no-positioned 和 Positioned）
class StackWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: AppBar(
          title: new Text('Stack')
      ),
      body: Container(
        color: Colors.yellow,
        height: 150.0,
        width: 500.0,
        child: new Stack(
          // 设置 no-positioned 的位置
          // Alignment 的取值为 [-1, 1],中心点位置为 (0, 0)
          alignment: const Alignment(0, 0),
          // 默认为 no-positioned
          children: <Widget>[
            new Container(
              color: Colors.blueAccent,
              height: 50.0,
              width: 100.0,
              alignment: Alignment.center,
              child: Text('unPositioned',style: _textStyle),
            ),
            // Positioned, 可以通过 left top right bottom 设置位置
            new Positioned(
              left: 40.0,
              top:80.0,
              child: new Container(
                color: Colors.pink,
                height: 50.0,
                width: 95.0,
                alignment: Alignment.center,
                child: Text('Positioned', style: _textStyle),
              ),
            )
          ],
        ),
      ),
    );
  }

  TextStyle _textStyle = new TextStyle(
    fontSize: 12.0,
    color: Colors.black
  );
}