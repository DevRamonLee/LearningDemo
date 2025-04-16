import 'package:animation/page/animation_hero_b.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

// Hero 动画
class HeroAnimationA extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("头像"),),
      body: Container(
        alignment: Alignment.topCenter,
        child: InkWell(
          child: Hero(
            tag: "ocean",  // 唯一标记，前后两个路由页 Hero 的 tag 必须相同
            child: ClipOval(
              child: Image.asset("images/ocean.jpg", width: 50.0),
            ),
          ),
          onTap: () {
            // 打开 HeroAnimationB
            Navigator.push(context, PageRouteBuilder(
              pageBuilder: (BuildContext context, Animation animation, Animation secondaryAnimation) {
                return new FadeTransition(opacity: animation, child: Scaffold(
                  appBar: AppBar(
                    title: Text("原图"),
                  ),
                  body: HeroAnimationB(),
                ));
              },
            ));
          },
        ),
      )
    );
  }
}