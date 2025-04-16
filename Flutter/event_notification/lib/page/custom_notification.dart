import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

// 自定义通知
class CustomNotification extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _CustomNotificationState();
  }
}

class _CustomNotificationState extends State<CustomNotification> {
  String _msg = "";
  @override
  Widget build(BuildContext context) {
    return NotificationListener<MyNotification>(
      onNotification: (notification) {
        setState(() {
          _msg += notification.msg + " ";
        });
        return true;
      },
      child: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Builder(
              builder: (context) {
                return RaisedButton(
                  onPressed: () => MyNotification("Hi").dispatch(context),
                  child: Text("Send Notification"),
                );
              },
            ),
            Text(_msg)
          ],
        ),
      ),
    );
  }
}

class MyNotification extends Notification {
  final String msg;
  MyNotification(this.msg);
}