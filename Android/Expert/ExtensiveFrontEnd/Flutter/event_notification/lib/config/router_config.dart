
import 'package:event_notification/page/a.dart';
import 'package:event_notification/page/b.dart';
import 'package:event_notification/page/custom_notification.dart';
import 'package:event_notification/page/gesture.dart';
import 'package:event_notification/page/home.dart';
import 'package:event_notification/page/scrollable_notification.dart';
import 'package:event_notification/page/touch.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class RouteName {
  static const String homePage = "homePage";
  static const String touchEvent = "touchEvent";
  static const String gestureDetector = "gestureDetector";
  static const String busAPage = "busAPage";
  static const String busBPage = "busBPage";
  static const String scrollableNotification = "scrollableNotification";
  static const String customNotification = "customNotification";
}

class Router {
  static Route<dynamic> generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case RouteName.homePage:
        return NoAnimRouteBuilder(Home());
        break;
      case RouteName.touchEvent:
        return CupertinoPageRoute(
          builder: (_) => TouchPage()
        );
        break;
      case RouteName.gestureDetector:
        return CupertinoPageRoute(
          builder: (_) => GesturePage()
        );
        break;
      case RouteName.busAPage:
        return CupertinoPageRoute(
          builder: (_) => A()
        );
        break;
      case RouteName.busBPage:
        return CupertinoPageRoute(
            builder: (_) => B()
        );
        break;
      case RouteName.scrollableNotification:
        return CupertinoPageRoute(
            builder: (_) => ScrollableNotification()
        );
        break;
      case RouteName.customNotification:
        return CupertinoPageRoute(
            builder: (_) => CustomNotification()
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
