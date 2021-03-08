// 绘制五子棋盘
import 'dart:math';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FiveInARowMap extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: CustomPaint(
        size: Size(300.0, 300.0),
        painter: MyPainter(),
      ),
    );
  }
}

class MyPainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    double everyWidth = size.width / 15;
    double everyHeight = size.height / 15;

    // 画棋盘背景
    var paint = Paint()
      ..isAntiAlias = true
      ..style = PaintingStyle.fill  // 填充
      ..color = Color(0x77cdb175);

    canvas.drawRect(Offset.zero & size, paint);

    // 画棋盘网格
    paint
      ..style = PaintingStyle.stroke  // 线
      ..color = Colors.black87
      ..strokeWidth = 1.0;

    for (int i = 0; i <= 15; i++) {
      double dy = everyHeight * i;
      canvas.drawLine(Offset(0, dy), Offset(size.width, dy), paint);
    }

    for (int i = 0; i <= 15; i++) {
      double dx = everyWidth * i;
      canvas.drawLine(Offset(dx, 0), Offset(dx, size.height), paint);
    }

    // 画一个黑子
    paint
      ..style = PaintingStyle.fill
      ..color = Colors.black;

    canvas.drawCircle(
        Offset(size.width / 2 - everyWidth / 2, size.height / 2 - everyHeight / 2),
        min(everyWidth / 2, everyHeight / 2) - 2,
        paint);

    // 画一个白子
    paint.color = Colors.white;
    canvas.drawCircle(
        Offset(size.width / 2 + everyWidth / 2, size.height / 2 - everyHeight / 2),
        min(everyWidth / 2, everyHeight / 2) - 2,
        paint);
  }

  // 实际场景中正确使用此回调可以避免重绘开销
  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    return true;
  }
}