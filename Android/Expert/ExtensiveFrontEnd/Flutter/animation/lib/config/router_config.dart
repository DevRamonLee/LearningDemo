
import 'package:animation/page/animation_dot_screen.dart';
import 'package:animation/page/animation_hero_a.dart';
import 'package:animation/page/animation_preset_widget.dart';
import 'package:animation/page/animation_scale.dart';
import 'package:animation/page/animation_scale2.dart';
import 'package:animation/page/animation_scale3.dart';
import 'package:animation/page/animation_screen.dart';
import 'package:animation/page/animation_stagger.dart';
import 'package:animation/page/animation_switcher.dart';
import 'package:animation/page/animation_switcher2.dart';
import 'package:animation/page/animation_widget.dart';
import 'package:animation/page/animation_widget2.dart';
import 'package:animation/page/home.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class RouteName {
  static const String homePage = "homePage";
  static const String imgScale = "imgScale";
  static const String imgScale2 = "imgScale2";
  static const String imgScale3 = "imgScale3";
  static const String innerAnim = "innerAnim";
  static const String rollingBall = "rollingBall";
  static const String heroAnimationA = "heroAnimationA";
  static const String heroAnimationB = "heroAnimationB";
  static const String staggerAnimation = "staggerAnimation";
  static const String switcherAnimation = "switcherAnimation";
  static const String switcherAnimation2 = "switcherAnimation2";
  static const String animatedDecorationBox = "animatedDecorationBox";
  static const String animatedDecorationBox2 = "animatedDecorationBox2";
  static const String animatedPresetWidget = "animatedPresetWidget";
}

class Router {
  static Route<dynamic> generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case RouteName.homePage:
        return NoAnimRouteBuilder(Home());
        break;
      case RouteName.rollingBall:
        return FadeRoute(
          builder: (_) => AnimationDotScreen()
        );
        break;
      case RouteName.imgScale:
        return CupertinoPageRoute(
          builder: (_) => AnimationScale()
        );
        break;
      case RouteName.imgScale2:
        return CupertinoPageRoute(
            builder: (_) => AnimationScale2()
        );
        break;
      case RouteName.imgScale3:
        return CupertinoPageRoute(
            builder: (_) => AnimationScale3()
        );
        break;
      case RouteName.innerAnim:
        return FadeAnimRouteBuilder(AnimationScreen());
        break;
      case RouteName.heroAnimationA:
        return CupertinoPageRoute(builder: (_) => HeroAnimationA());
        break;
      case RouteName.staggerAnimation:
        return CupertinoPageRoute(builder: (_) => StaggerRoute());
        break;
      case RouteName.switcherAnimation:
        return CupertinoPageRoute(builder: (_) => AnimationSwitcher());
        break;
      case RouteName.switcherAnimation2:
        return CupertinoPageRoute(builder: (_) => AnimationSwitcher2());
        break;
      case RouteName.animatedDecorationBox:
        return CupertinoPageRoute(builder: (_) => AnimatedDecoratedBoxDemo());
        break;
      case RouteName.animatedDecorationBox2:
        return CupertinoPageRoute(builder: (_) => AnimatedDecoratedBoxDemo2());
        break;
      case RouteName.animatedPresetWidget:
        return CupertinoPageRoute(builder: (_) => AnimatedPresetWidget());
        break;
      default:
        return CupertinoPageRoute(
            builder: (_) => Scaffold(
              body: Center(
                child: Text('No route defined for ${settings.name}'),
              ),
            ));
    }
  }
}

class NoAnimRouteBuilder extends PageRouteBuilder {
  final Widget page;

  NoAnimRouteBuilder(this.page)
      : super(
            opaque: false,
            pageBuilder: (context, animation, secondaryAnimation) => page,
            transitionDuration: Duration(milliseconds: 0),
            transitionsBuilder:
                (context, animation, secondaryAnimation, child) => child);
}

class FadeAnimRouteBuilder extends PageRouteBuilder {
  final Widget page;

  FadeAnimRouteBuilder(this.page)
    :super(
      opaque: false,
      pageBuilder: (context, animation, secondaryAnimation) {
        return new FadeTransition(opacity: animation, child: page,);
      },
      transitionDuration: Duration(milliseconds: 500));
}

// 直接继承 PageRoute 实现路由动画
class FadeRoute extends PageRoute {

  FadeRoute(
  {
    @required this.builder,
    this.transitionDuration = const Duration(milliseconds: 300),
    this.opaque = true,
    this.barrierDismissible = false,
    this.barrierColor,
    this.barrierLabel,
    this.maintainState = true
  });

  final WidgetBuilder builder;

  @override
  final Duration transitionDuration;

  @override
  final bool opaque;

  @override
  final bool barrierDismissible;

  @override
  final Color barrierColor;

  @override
  final String barrierLabel;

  @override
  final bool maintainState;

  @override
  Widget buildPage(BuildContext context, Animation<double> animation, Animation<double> secondaryAnimation) => builder(context);

  @override
  Widget buildTransitions(BuildContext context, Animation<double> animation, Animation<double> secondaryAnimation, Widget child) {

    // 当前路由被激活，是打开新路由
    if (isActive) {
      return FadeTransition(
        opacity: animation,
        child: builder(context),
      );
    } else {
      // 是返回，不使用过渡动画
       return Padding(padding: EdgeInsets.zero);
    }
  }
}

