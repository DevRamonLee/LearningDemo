import 'package:custom_view/view/GradientButton.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class GradientButtonRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _GradientButtonRouteState();
}

class _GradientButtonRouteState extends State<GradientButtonRoute> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("渐变按钮"),),
      body: Container(
        child: Column(
          children: [
            GradientButton(
              colors: [Colors.orange, Colors.red],
              height: 50.0,
              child: Text("Submit"),
              onPressed: onTap,
            )
          ],
        ),
      ),
    );
  }

  onTap() {
    print("onTap");
  }
}