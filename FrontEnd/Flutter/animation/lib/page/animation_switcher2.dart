
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

// AnimateSwitcher 高级用法： 实现一个类似路由的平移动画，旧组件从左侧平移退出，新组件从右侧平移进入
class AnimationSwitcher2 extends StatefulWidget {

  const AnimationSwitcher2({Key key}): super(key: key);

  @override
  State<StatefulWidget> createState() => _AnimationSwitcherState2();
}

class _AnimationSwitcherState2 extends State<AnimationSwitcher2> with SingleTickerProviderStateMixin {
  int _count = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("组件切换动画"),),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            AnimatedSwitcher(
              duration: const Duration(milliseconds: 500),
              transitionBuilder: (Widget child, Animation<double> animation) {
       /*         var tween = Tween<Offset>(begin: Offset(1, 0), end: Offset(0, 0));
                return MySlideTransition(
                  child: child,
                  position: tween.animate(animation),
                );*/
                // 使用通用滑动动画
                return SlideTransitionX(
                  child: child,
                  position: animation,
                  direction: AxisDirection.up,
                );
              },
              child: Text(
                '$_count',
                // 显式指定 key， 不同的 key 会被认为是不同的 Text， 这样才能执行动画
                key: ValueKey<int>(_count),
                style: Theme.of(context).textTheme.headline4,
              ),
            ),
            RaisedButton(
              child: const Text("+1"),
              onPressed: () {
                setState(() {
                  _count += 1;
                });
              },
            )
          ],
        ),
      ),
    );
  }
}

class MySlideTransition extends AnimatedWidget {
  MySlideTransition({Key key, @required Animation<Offset> position, this.transformHitTests = true, this.child})
      : assert(position != null),
        super(key: key, listenable: position);

  Animation<Offset> get position => listenable;
  final bool transformHitTests;
  final Widget child;

  @override
  Widget build(BuildContext context) {
    Offset offset = position.value;
    // 动画反向执行时，调整 x 偏移，使得其从"左边滑动隐藏"
    if (position.status == AnimationStatus.reverse) {
      offset = Offset(-offset.dx, offset.dy);
    }
    return FractionalTranslation(
      translation: offset,
      transformHitTests: transformHitTests,
      child: child,
    );
  }
}

// 封装一个通用的 X 入 Y 出 组件
class SlideTransitionX extends AnimatedWidget {
  SlideTransitionX({
    Key key,
    @required Animation<double> position,
    this.transformHitTests = true,
    this.direction = AxisDirection.down,
    this.child
  }): assert(position != null),
    super(key: key, listenable: position) {
    // 偏移在内部处理
    switch (direction) {
      case AxisDirection.up:
        _tween = Tween(begin: Offset(0, 1), end: Offset(0, 0));
        break;
      case AxisDirection.right:
        _tween = Tween(begin: Offset(-1, 0), end: Offset(0, 0));
        break;
      case AxisDirection.left:
        _tween = Tween(begin: Offset(1, 0), end: Offset(0, 0));
        break;
      case AxisDirection.down:
        _tween = Tween(begin: Offset(0, -1), end: Offset(0, 0));
        break;
    }
  }

  Animation<double> get position => listenable;
  final bool transformHitTests;
  final Widget child;
  // 退场方向
  final AxisDirection direction;
  Tween<Offset> _tween;

  @override
  Widget build(BuildContext context) {
    Offset offset = _tween.evaluate(position);
    if (position.status == AnimationStatus.reverse) {
      switch(direction) {
        case AxisDirection.up:
          offset = Offset(offset.dx, -offset.dy);
          break;
        case AxisDirection.right:
          offset = Offset(-offset.dx, offset.dy);
          break;
        case AxisDirection.left:
          offset = Offset(-offset.dx, offset.dy);
          break;
        case AxisDirection.down:
          offset = Offset(offset.dx, -offset.dy);
          break;
      }
    }
    return FractionalTranslation(
      translation: offset,
      transformHitTests: transformHitTests,
      child: child,
    );
  }
}