// 使用 ImplicitlyAnimatedWidget 和 ImplicitlyAnimatedWidgetState 优化动画过渡组件的创建

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AnimatedDecoratedBox extends ImplicitlyAnimatedWidget {
  AnimatedDecoratedBox({
    Key key,
    @required this.decoration,
    this.child,
    Curve curve = Curves.linear, //动画曲线
    @required Duration duration, // 正向动画执行时长
  }) : super(
    key: key,
    curve: curve,
    duration: duration,
  );
  final BoxDecoration decoration;
  final Widget child;

  @override
  _AnimatedDecoratedBoxState createState() {
    return _AnimatedDecoratedBoxState();
  }
}

class _AnimatedDecoratedBoxState extends ImplicitlyAnimatedWidgetState<AnimatedDecoratedBox> {
  DecorationTween _decoration; //定义一个Tween

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: _decoration.evaluate(animation),
      child: widget.child,
    );
  }

  @override
  void forEachTween(visitor) {
    // 在需要更新Tween时，基类会调用此方法
    _decoration = visitor(_decoration, widget.decoration,
            (value) => DecorationTween(begin: value));
  }
}

class AnimatedDecoratedBoxDemo2 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AnimatedDecoratedBoxDemoState2();
}

class _AnimatedDecoratedBoxDemoState2 extends State<AnimatedDecoratedBoxDemo2> {
  Color _decorationColor = Colors.green;
  var duration = Duration(seconds: 1);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text("动画过渡组件"),),
        body: Center(
          child: AnimatedDecoratedBox(
            decoration: BoxDecoration(color: _decorationColor),
            duration: duration,
            child: FlatButton(
              onPressed: () {
                setState(() {
                  _decorationColor = Colors.pink;
                });
              },
              child: Text(
                "AnimatedDecoratedBox",
                style: TextStyle(color: Colors.white),
              ),
            ),
          ),
        )
    );
  }
}