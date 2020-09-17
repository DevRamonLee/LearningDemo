import 'package:event_notification/config/router_config.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../event_bus.dart';

class A extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _AState();
  }
}

class _AState extends State<A> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("A"),),
      body: Center(
        child: FlatButton(
          color: Colors.blue,
          child: Text("登陆"),
          onPressed: () {
            bus.emit("login");
            Navigator.pop(context);
            },
        ),
      ),
    );
  }
}