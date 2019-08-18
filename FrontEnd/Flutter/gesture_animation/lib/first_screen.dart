import 'package:flutter/material.dart';
import 'package:gesture_animation/second_screen.dart';

class FirstScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _FirstScreenState();
}

class _FirstScreenState extends State<FirstScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("First Screen"),
      ),
      body: Center(
        child: RaisedButton(
          child: Text("First screen"),
            onPressed: ()async {
              var msg =await Navigator.push(context,
              MaterialPageRoute(builder: (_)=>SecondScreen()));
              debugPrint('msg = $msg');
            }),
      ),
    );
  }
}