import 'package:flutter/material.dart';

// 动画是有状态的
class AnimationScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AnimationScreenState();
}

class _AnimationScreenState extends State<AnimationScreen>
    with SingleTickerProviderStateMixin {
  AnimationController controller; // 线性效果执行动画
  CurvedAnimation curveController; // 非线性执行动画

  @override
  void initState() {
    super.initState();
    controller = AnimationController(
        duration: Duration(milliseconds: 5000), // 动画的时长
        vsync: this // 提供 vsync 最简单的方式，就是直接继承 SingleTickerProviderStateMixin
        );
    curveController =
        CurvedAnimation(parent: controller, curve: Curves.easeInOut);
    controller.forward(); // 开始动画
  }

  @override
  Widget build(BuildContext context) {
    var scaled = ScaleTransition(
      child: FlutterLogo(size: 200.0),
      scale: curveController,
    );
    return Scaffold(
      appBar: AppBar(
        title: Text("动画"),
      ),
      body: Column(
        children: <Widget>[
          ScaleTransition(
            scale: curveController,
            child: FlutterLogo(
              size: 200.0,
            ),
          ),
          FadeTransition(child: scaled, opacity: curveController) // 组合动画
        ],
      ),
    );
  }
}
