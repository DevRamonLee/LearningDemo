
import 'package:flutter/cupertino.dart';

class GrowTransition extends StatelessWidget {
  GrowTransition({this.child, this.animation});
  final Widget child;
  final Animation<double> animation;

  @override
  Widget build(BuildContext context) {
    return new Center(
      child: new AnimatedBuilder(animation: animation, builder: (BuildContext context, Widget child) {
        return new Container(
          height: animation.value,
          width: animation.value,
          child: child,
        );
      },
        child: child,
      ),
    );
  }
}

class AnimationScale3 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AnimationScale3();
}

// 需要继承 TickerProvider， 如果有多个 AnimationController，则应该使用 TickerProviderStateMixin
class _AnimationScale3 extends State<AnimationScale3> with SingleTickerProviderStateMixin {
  Animation<double> animation;
  AnimationController controller;

  @override
  void initState() {
    super.initState();
    controller = new AnimationController(duration: const Duration(seconds: 3), vsync: this);
    // 图片宽度从 0 到 300
    animation = new Tween(begin: 0.0, end: 300.0).animate(controller);
    // 启动动画
    controller.forward();
  }

  @override
  Widget build(BuildContext context) {
    return GrowTransition(
      child: Image.asset('images/ocean.jpg'),
      animation: animation,
    );
  }

  @override
  void dispose() {
    // 路由销毁时需要释放动画资源，防止内存泄漏
    controller.dispose();
    super.dispose();
  }
}