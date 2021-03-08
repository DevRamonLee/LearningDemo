// 交织动画
// 柱状图增长动画
// 1. 开始时高度从 0 增长到 300 像素， 同时颜色由绿色渐变为红色，过程占动画时间的 60%
// 2. 高度增长到 300 后，开始沿 X 轴向右平移 100 像素，过程占动画时间的 40%
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class StaggerAnimation extends StatelessWidget {
  final Animation<double> controller;
  Animation<double> height;
  Animation<EdgeInsets> padding;
  Animation<Color> color;

  StaggerAnimation({Key key, this.controller}): super(key: key) {
    // 高度动画
    height = Tween<double>(
      begin: 0.0,
      end: 300.0
    ).animate(CurvedAnimation(
      parent: controller,
      curve: Interval(
        0.0, 0.6, // 间隔，前 60% 时间
        curve: Curves.ease,
      )
    ));

    color = ColorTween(
      begin: Colors.green,
      end: Colors.red
    ).animate(CurvedAnimation(
      parent: controller,
      curve: Interval(
        0.0, 0.6, // 间隔，前 60%
        curve: Curves.ease
      )
    ));

    padding = Tween<EdgeInsets> (
      begin: EdgeInsets.only(left: 0.0),
      end: EdgeInsets.only(left: 100.0)
    ).animate(CurvedAnimation(
      parent: controller,
      curve: Interval(
        0.6, 1.0, // 间隔，后 40% 的动画时间
        curve: Curves.ease
      )
    ));

  }

  Widget _buildAnimation(BuildContext context, Widget child) {
    return Container(
      alignment: Alignment.bottomCenter,
      padding: padding.value,
      child: Container(
        color: color.value,
        width: 50.0,
        height: height.value,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      builder: _buildAnimation,
      animation: controller,
    );
  }
}

class StaggerRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _StaggerRouteState();
}

class _StaggerRouteState extends State<StaggerRoute> with SingleTickerProviderStateMixin {
  AnimationController _controller;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      duration: const Duration(milliseconds: 2000),
      vsync: this
    );
  }

  Future<Null> _playAnimation() async {
    try {
        // 先正向执行动画
        await _controller.forward().orCancel;
        // 反向执行动画
        await _controller.reverse().orCancel;
    } on TickerCanceled {

    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("交织动画"),),
      body: GestureDetector(
        behavior: HitTestBehavior.opaque,
        onTap: () {
          _playAnimation();
        },
        child: Center(
          child: Container(
            width: 300.0,
            height: 300.0,
            decoration: BoxDecoration(
                color: Colors.black.withOpacity(0.1),
                border: Border.all(color: Colors.black.withOpacity(0.5))
            ),
            child: StaggerAnimation(
              controller: _controller,
            ),
          ),
        ),
      ),
    ) ;
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }
}