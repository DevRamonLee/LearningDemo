import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/// 自定义组件，支持以下功能
/// 1.背景支持渐变色
/// 2.手指按下时有涟漪效果
/// 3.可以支持圆角
/// 实现方式：采用组合方式， DecoratedBox 可以支持背景色渐变和圆角，InkWell 支持涟漪效果，我们通过组合这两个组件实现上面的效果
class GradientButton extends StatelessWidget {
  // 渐变色数组
  final List<Color> colors;
  // 按钮宽高
  final double width;
  final double height;
  final Widget child;
  final BorderRadius borderRadius;
  // 点击回调
  final GestureTapCallback onPressed;

  GradientButton({
    this.colors,
    this.width,
    this.height,
    this.onPressed,
    this.borderRadius,
    @required this.child
  });

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    // 确保 colors 数组不为空
    List<Color> _colors = colors ?? [theme.primaryColor, theme.primaryColorDark, theme.primaryColor];
    return DecoratedBox(
      decoration: BoxDecoration(
        gradient: LinearGradient(colors: _colors),
        borderRadius: borderRadius
      ),
      child: Material(
        type: MaterialType.transparency,
        child: InkWell(
          splashColor: _colors.last,
          highlightColor: Colors.transparent,
          borderRadius: borderRadius,
          onTap: onPressed,
          child: ConstrainedBox(
            constraints: BoxConstraints.tightFor(height: height, width: width),
            child: Center(
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: DefaultTextStyle(
                  style: TextStyle(fontWeight: FontWeight.bold),
                  child: child,
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}