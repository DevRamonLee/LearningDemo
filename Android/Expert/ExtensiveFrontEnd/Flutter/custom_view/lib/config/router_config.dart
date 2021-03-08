import 'package:custom_view/page/five_in_a_row_map.dart';
import 'package:custom_view/page/gradient_button_route.dart';
import 'package:custom_view/page/gradient_circular_progress_route.dart';
import 'package:custom_view/page/home.dart';
import 'package:custom_view/page/turn_box_route.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class RouteName {
  static const String homePage = "homePage";
  static const String gradientButton = "gradientButton";
  static const String turnBox = "turnBox";
  static const String fiveInARow = "fiveInARow";
  static const String gradientCircularProgress = "gradientCircularProgress";
}

class Router {
  static Route<dynamic> generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case RouteName.homePage:
        return NoAnimRouteBuilder(Home());
        break;
      case RouteName.gradientButton:
        return FadeRoute(
          builder: (_) => GradientButtonRoute()
        );
        break;
      case RouteName.turnBox:
        return FadeRoute(
            builder: (_) => TurnBoxRoute()
        );
        break;
      case RouteName.fiveInARow:
        return FadeRoute(
            builder: (_) => FiveInARowMap()
        );
        break;
      case RouteName.gradientCircularProgress:
        return FadeRoute(
            builder: (_) => GradientCircularProgressRoute()
        );
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

