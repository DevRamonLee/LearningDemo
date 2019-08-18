import 'package:flutter/material.dart';
import 'dart:math' as math;

/// 绘制一段圆弧
class PaintScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _PaintScreenState();
  }
}

class _PaintScreenState extends State<PaintScreen>
    with SingleTickerProviderStateMixin {
  AnimationController _animationController;

  /*@override
  void initState() {
    super.initState();
    _animationController = AnimationController(
        vsync: this,
        duration: Duration(milliseconds: 3000)
        )
    ..repeat()    // 重复播放
    ..addListener((){
      setState(() {
        // 刷新重建 widget
      });
    });
  }*/

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }
  /*@override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text('Flutter 绘图'),),
        body: Container(
//          child: CustomPaint(painter: DemoPainter(0.0, math.pi),),  // 把 DemoPainter 作为参数传递给 CustomPaint
          child: CustomPaint(painter: DemoPainter(0.0, _animationController.value * math.pi * 2),), // 第二个参数是动态产生的
          height: 200,
          width: 200,
          color: Colors.deepOrange,
          padding: EdgeInsets.all( 30.0),
        )
    );
  }*/

  // 以下是借助 AnimateBuilder 改写了上面的代码
  @override
  void initState() {
    _animationController =
        AnimationController(vsync: this, duration: Duration(milliseconds: 1500))
          ..repeat();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Flutter 绘图'),
        ),
        body: Container(
          child: AnimatedBuilder(
              animation: _animationController,
              builder: (context, child) {
                return CustomPaint(
//              painter: DemoPainter(0.0, _animationController.value * math.pi * 2),
                  painter: DemoPainter(
                      Tween(begin: math.pi * 1.5, end: math.pi * 3.5)
                          .chain(CurveTween(curve: Interval(0.5, 1.0)))
                          .evaluate(_animationController),
                      math.sin(Tween(begin: 0.0, end: math.pi)
                              .evaluate(_animationController)) *
                          math.pi),
                );
              }),
          height: 200,
          width: 200,
          color: Colors.deepOrange,
          padding: EdgeInsets.all(30.0),
        ));
  }
}

/// 类似 Android 中自定义 View ，Flutter 继承 CustomPainter
class DemoPainter extends CustomPainter {
  final double _arcStart;
  final double _arcSweep;

  DemoPainter(this._arcStart, this._arcSweep);
  @override
  void paint(Canvas canvas, Size size) {
    // TODO: paint 决定绘制什么
    double side = math.min(size.width, size.height);
    Paint paint = Paint()
      ..color = Colors.blue
      ..strokeCap = StrokeCap.round
      ..strokeWidth = 4.0
      ..style = PaintingStyle.stroke;
    canvas.drawArc(
        Offset.zero & Size(side, side), _arcStart, _arcSweep, false, paint);
  }

  @override
  bool shouldRepaint(DemoPainter oldDelegate) {
    // TODO: shouldRepaint 决定什么时候重绘
    return _arcStart != oldDelegate._arcStart ||
        _arcSweep != oldDelegate._arcSweep;
  }
}
