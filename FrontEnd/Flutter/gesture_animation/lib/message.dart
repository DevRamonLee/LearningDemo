// 对我们要返回的数据进行建模
class Message{
  final String msg;
  final int timestamp;
  Message(this.msg, this.timestamp);
  @override
  String toString() {
    return "Message(msg: $msg, timestamp: $timestamp)";
  }
}
