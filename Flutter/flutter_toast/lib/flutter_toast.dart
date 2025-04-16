import 'dart:async';
import 'package:flutter/services.dart';

enum ToastDuration {
  short, long
}

class FlutterToast {
  static const MethodChannel _channel = const MethodChannel('flutter_toast');

  static Future<bool> toast(String msg, ToastDuration duration) async {
    var argument = {
      'content': msg,
      'duration': duration.toString()
    };
    var success = await _channel.invokeMethod('toast', argument);
    return success;
  }
}
