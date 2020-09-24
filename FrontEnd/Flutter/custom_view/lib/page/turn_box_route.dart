import 'package:custom_view/view/TurnBox.dart';
import 'package:flutter/material.dart';

class TurnBoxRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _TurnBoxRouteState();
}

class _TurnBoxRouteState extends State<TurnBoxRoute> {
  double _turns = 0.0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("旋转组件"),),
      body:
      Center(
        child: Column(
          children: [
            TurnBox(
              turns: _turns,
              speed: 500,
              child: Icon(Icons.refresh, size: 150, color: Colors.blue,),
            ),
            RaisedButton(
              child: Text("顺时针旋转 1/5 圈"),
              onPressed: () {
                setState(() {
                  _turns += 0.2;
                });
              },
            ),
            RaisedButton(
              child: Text("逆时针旋转 1/5 圈"),
              onPressed: () {
                setState(() {
                  _turns -= 0.2;
                });
              },
            )
          ],
        ),
      ),
    );
  }
}