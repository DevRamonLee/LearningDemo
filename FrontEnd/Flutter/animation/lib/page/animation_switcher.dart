
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

// 例：实现一个计数器，在每一次自增的过程中，旧数字执行缩小动画隐藏，新数字执行放大动画显示
class AnimationSwitcher extends StatefulWidget {

  const AnimationSwitcher({Key key}): super(key: key);

  @override
  State<StatefulWidget> createState() => _AnimationSwitcherState();
}

class _AnimationSwitcherState extends State<AnimationSwitcher> with SingleTickerProviderStateMixin {
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
                // 执行缩放动画
                return ScaleTransition(child: child, scale: animation);
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