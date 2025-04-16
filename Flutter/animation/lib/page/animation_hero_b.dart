import 'package:flutter/cupertino.dart';

class HeroAnimationB extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Hero(
        tag: "ocean",  // 唯一标记，前后两个路由页 Hero 的 tag 必须相同
        child: Image.asset("images/ocean.jpg"),
      ),
    );
  }
}