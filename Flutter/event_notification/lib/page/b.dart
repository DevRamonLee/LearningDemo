import 'package:event_notification/config/router_config.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../event_bus.dart';

class B extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _BState();
  }
}

class _BState extends State<B> {
  bool _isLogin = false;

  @override
  void initState() {
    super.initState();
    bus.on("login", (args) {
      setState(() {
        _isLogin = true;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("B"),),
      body: Center(
        child: _isLogin ?
        Text(
            "已登陆",
            style: TextStyle(
              fontSize: 30.0,
              color: Colors.green
            ) ,
        ):
        FlatButton(
          color: Colors.cyanAccent,
          child: Text("去登陆"),
          onPressed: () {Navigator.pushNamed(context, RouteName.busAPage);},
        )
      ),
    );
  }

  @override
  void dispose() {
    bus.off("login");
    super.dispose();
  }
}