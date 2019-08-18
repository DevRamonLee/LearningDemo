import 'package:flutter/material.dart';

class SecondScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _SecondScreenState();
}

class _SecondScreenState extends State<SecondScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Navigation deme'),
      ),
      body: Center(
        child: RaisedButton(
            child: Text("Second screen"),
            onPressed: (){
              Navigator.pop(context, 'message from second screen');
            }),
      ),
    );
  }
}