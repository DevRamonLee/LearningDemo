import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class GesturePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _GesturePageState();
}

class _GesturePageState extends State<GesturePage> {
  String _operation = "No Gesture detected!"; // 保存事件名

  double _top = 0.0;  // 距顶部的偏移
  double _left = 0.0; // 距左边的偏移
  double _width = 200;

  TapGestureRecognizer _tapGestureRecognizer = new TapGestureRecognizer();
  bool _toggle = false; // 变色

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("手势识别"),),
      body:
      SingleChildScrollView(
        child: Column(
          children: <Widget>[
            // 点击/双击/长按
            GestureDetector(
              child: Container(
                alignment: Alignment.center,
                color: Colors.blueAccent,
                width: 200.0,
                height: 100.0,
                child: Text(_operation, style: TextStyle(color: Colors.white),),
              ),
              onTap: ()=> updateText("Tap"),
              onDoubleTap: () => updateText("DoubleTap"),
              onLongPress: () => updateText("Long press"),
            ),
            ConstrainedBox(
              constraints: BoxConstraints.tight(Size(double.infinity, 200)),
              child: Stack(
                children: <Widget>[
                  Positioned(
                      top: _top,
                      left: _left,
                      // 拖动/滑动
                      child: GestureDetector(
                        child: CircleAvatar(child: Text("A"),),
                        // 手指按下触发
                        onPanDown: (DragDownDetails e) {
                          // 打印手指按下的位置（相对于屏幕）
                          print("用户手指按下 ${e.globalPosition}");
                        },
                        // 手指滑动触发
                        onPanUpdate: (DragUpdateDetails e) {
                          // 手指滑动时，更新偏移，重新构建
                          setState(() {
                            _left += e.delta.dx;
                            _top += e.delta.dy;
                          });
                        },
                        onPanEnd: (DragEndDetails e) {
                          print(e.velocity);  // 打印滑动结束时在 x y 上的速度
                        },
                      )
                  )
                ],
              ),
            ),
            // 单一方向拖动
            ConstrainedBox(
              constraints: BoxConstraints.tight(Size(double.infinity, 200)),
              child:
              Stack(
                children: <Widget>[
                  Positioned(
                    top: _top,
                    child: GestureDetector(
                      child: CircleAvatar(child: Text("A"),),
                      // 垂直方向拖动事件
                      onVerticalDragUpdate: (DragUpdateDetails details) {
                        setState(() {
                          _top += details.delta.dy;
                        });
                      },
                    ),
                  )
                ],
              ),
            ),
            // 缩放
            GestureDetector(
              child: Image.asset("./images/shark.jpeg", width: _width,),
              onScaleUpdate: (ScaleUpdateDetails details) {
                setState(() {
                  // 缩放倍数在 0.8 到 10 倍之间
                  _width = 200 * details.scale.clamp(.8, 10.0);
                });
              },
            ),
            // GestureRecognizer
            Text.rich(
              TextSpan(
                children: [
                  TextSpan(text: "点我变色",
                    style: TextStyle(
                      fontSize: 30.0,
                      color: _toggle ? Colors.blue : Colors.red
                    ),
                    recognizer: _tapGestureRecognizer
                      ..onTap = () {
                        setState(() {
                          _toggle = !_toggle;
                        });
                      },
                  )
                ]
              )
            ),
            // 手势竞争，同时监听垂直和水平方向滑动
            ConstrainedBox(
              constraints: BoxConstraints.tight(Size(double.infinity, 200)),
              child:
              Stack(
                children: <Widget>[
                  Positioned(
                    top: _top,
                    left: _left,
                    child: GestureDetector(
                      child: CircleAvatar(child: Text("A"),),
                      // 垂直方向拖动事件
                      onVerticalDragUpdate: (DragUpdateDetails details) {
                        setState(() {
                          _top += details.delta.dy;
                        });
                      },
                      // 水平方向拖动事件
                      onHorizontalDragUpdate: (DragUpdateDetails details) {
                        setState(() {
                          _left += details.delta.dx;
                        });
                      },
                    ),
                  )
                ],
              ),
            ),
            // 手势冲突
            ConstrainedBox(
              constraints: BoxConstraints.tight(Size(double.infinity, 200)),
              child:
              Stack(
                children: <Widget>[
                  Positioned(
                    left: _left,
                    child: GestureDetector(
                      child: CircleAvatar(child: Text("A"),),
                      // 水平方向拖动事件
                      onHorizontalDragUpdate: (DragUpdateDetails details) {
                        setState(() {
                          _left += details.delta.dx;
                        });
                      },
                      onHorizontalDragEnd: (details){
                        print("onHorizontalDragEnd");
                      },
                      onTapDown:  (details) {
                        print("down");
                      },
                      onTapUp: (details) {
                        print("up");
                      },
                    ),
                  )
                ],
              ),
            ),
            ConstrainedBox(
              constraints: BoxConstraints.tight(Size(double.infinity, 200)),
              child:
              Stack(
                children: <Widget>[
                  Positioned(
                    left: _left,
                    top:80.0,
                    child: Listener (
                      onPointerDown: (details) {
                        print("down");
                      },
                      onPointerUp: (details) {
                        print("up");
                      },
                      child: GestureDetector(
                        child: CircleAvatar(child: Text("A"),),
                        // 水平方向拖动事件
                        onHorizontalDragUpdate: (DragUpdateDetails details) {
                          setState(() {
                            _left += details.delta.dx;
                          });
                        },
                        onHorizontalDragEnd: (details){
                          print("onHorizontalDragEnd");
                        },
                      ),
                    )
                  )
                ],
              ),
            ),
          ],
        ),
      )
    );
  }

  void updateText(String text) {
    setState(() {
      _operation = text;
    });
  }

  @override
  void dispose() {
    //  用到 GestureRecognizer 一定要调用 dispose 释放资源
    _tapGestureRecognizer.dispose();
    super.dispose();
  }
}