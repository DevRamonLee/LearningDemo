import 'package:basic_widget/pages/case1.dart';
import 'package:basic_widget/pages/case2.dart';
import 'package:basic_widget/pages/case3.dart';
import 'package:basic_widget/pages/case4.dart';
import 'package:basic_widget/pages/case5.dart';
import 'package:basic_widget/pages/case6.dart';
import 'package:basic_widget/pages/case7.dart';
import 'package:basic_widget/pages/case8.dart';
import 'package:basic_widget/pages/page_home.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../pages/page_basic.dart';

class RouteName {
  static const String homePage = "homePage";
  static const String basicWidgets = "basicWidgets";
  static const String case1 = "case1";
  static const String case2 = "case2";
  static const String case3 = "case3";
  static const String case4 = "case4";
  static const String case5 = "case5";
  static const String case6 = "case6";
  static const String case7 = "case7";
  static const String case8 = "case8";
}

class Router {
  static Route<dynamic> generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case RouteName.homePage:
        return NoAnimRouteBuilder(HomePage());
        break;
      case RouteName.basicWidgets:
        return CupertinoPageRoute(builder: (_) => PageBasic());
        break;
      case RouteName.case1:
        return CupertinoPageRoute(builder: (_) => MyAppOne());
        break;
      case RouteName.case2:
        return CupertinoPageRoute(builder: (_) => MyAppTwo());
        break;
      case RouteName.case3:
        return CupertinoPageRoute(builder: (_) => MyAppThree());
        break;
      case RouteName.case4:
        return CupertinoPageRoute(builder: (_) => MyAppFour());
        break;
      case RouteName.case5:
        return CupertinoPageRoute(builder: (_) => MyAppFive());
        break;
      case RouteName.case6:
        return CupertinoPageRoute(builder: (_) => MyAppSix());
        break;
      case RouteName.case7:
        return CupertinoPageRoute(builder: (_) => MyAppSeven());
        break;
      case RouteName.case8:
        return CupertinoPageRoute(builder: (_) => MyAppEight());
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
