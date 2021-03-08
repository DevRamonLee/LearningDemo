
import 'package:flutter/cupertino.dart';

class AnimatedImage extends AnimatedWidget {
  AnimatedImage({Key key, Animation<double> animation}): super(key: key, listenable: animation);

  @override
  Widget build(BuildContext context) {
    final Animation<double> animation = listenable;
    return new Center(
      child: Image.asset("images/ocean.jpg",
          width: animation.value,
          height: animation.value),
    );
  }
}

class AnimationScale2 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AnimationScale2();
}

// 需要继承 TickerProvider， 如果有多个 AnimationController，则应该使用 TickerProviderStateMixin
class _AnimationScale2 extends State<AnimationScale2> with SingleTickerProviderStateMixin {
  Animation<double> animation;
  AnimationController controller;

  @override
  void initState() {
    super.initState();
    controller = new AnimationController(duration: const Duration(seconds: 3), vsync: this);
    // 图片宽度从 0 到 300
    animation = new Tween(begin: 0.0, end: 300.0).animate(controller);
    controller.forward();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedImage(animation: animation);
  }

  @override
  void dispose() {
    // 路由销毁时需要释放动画资源，防止内存泄漏
    controller.dispose();
    super.dispose();
  }
}