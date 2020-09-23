import 'package:flutter/material.dart';

import 'config/router_config.dart';

void main() {
  runApp(new MaterialApp(
    onGenerateRoute: Router.generateRoute,
    initialRoute: RouteName.homePage,
  ));
}