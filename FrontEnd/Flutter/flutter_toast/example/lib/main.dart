import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_toast/flutter_toast.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
          child: RaisedButton(
            child: Text('toast'),
            onPressed: () => FlutterToast.toast('Toast from Flutter', ToastDuration.short),
          ),
        ),
      ),
    );
  }
}