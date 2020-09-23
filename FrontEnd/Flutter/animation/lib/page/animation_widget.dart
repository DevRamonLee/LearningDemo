// 自定义动画过渡组件，内部管理 AnimationController
// 实现一个 AnimatedDecoratedBox, 它可以在 decoration 属性发生变化时，从旧状态变换到新状态的过程执行一个过渡动画
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AnimatedDecoratedBox1 extends StatefulWidget {
  AnimatedDecoratedBox1(
  {
    Key key,
    @required this.decoration,
    this.child,
    this.curve = Curves.linear,
    @required this.duration,
    this.reverseDuration
  });

  @override
  State<StatefulWidget> createState() => _AnimatedDecorationBoxState();

  final BoxDecoration decoration;
  final Widget child;
  final Duration duration;
  final Curve curve;
  final Duration reverseDuration;
}

class _AnimatedDecorationBoxState extends State<AnimatedDecoratedBox1>
    with SingleTickerProviderStateMixin {
  @protected
  AnimationController get controller => _controller;
  AnimationController _controller;

  Animation<double> get animation => _animation;
  Animation<double> _animation;

  DecorationTween _tween;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      duration: widget.duration,
      reverseDuration: widget.reverseDuration,
      vsync: this
    );
    _tween = DecorationTween(begin: widget.decoration);
    _updateCurve();
  }

  void _updateCurve() {
    if (widget.curve != null) {
      _animation = CurvedAnimation(parent: _controller, curve: widget.curve);
    } else {
      _animation = controller;
    }
  }

  @override
  void didUpdateWidget(AnimatedDecoratedBox1 oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.curve != oldWidget.curve)
      _updateCurve();
    _controller.duration = widget.duration;
    _controller.reverseDuration = widget.reverseDuration;
    if (widget.decoration != (_tween.end ?? _tween.begin)) {
      _tween
        ..begin = _tween.evaluate(_animation)
        ..end = widget.decoration;
      _controller
        ..value = 0.0
        ..forward();
    }
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: _animation,
      builder: (context, child) {
        return DecoratedBox(
          decoration: _tween.animate(_animation).value,
          child: child,
        );
      },
      child: widget.child,
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }
}

class AnimatedDecoratedBoxDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AnimatedDecoratedBoxDemoState();
}

class _AnimatedDecoratedBoxDemoState extends State<AnimatedDecoratedBoxDemo> {
  Color _decorationColor = Colors.blue;
  var duration = Duration(seconds: 1);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("动画过渡组件"),),
      body: Center(
        child: AnimatedDecoratedBox1(
          decoration: BoxDecoration(color: _decorationColor),
          duration: duration,
          child: FlatButton(
            onPressed: () {
              setState(() {
                _decorationColor = Colors.red;
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