
/// 例：echo 客户端
// 对我们要返回的数据进行建模
class Message{
  final String msg;
  final int timestamp;
  Message(this.msg, this.timestamp);

  Message.create(String msg) :msg = msg, timestamp = DateTime.now().millisecondsSinceEpoch;

  Message.fromJson(Map<String, dynamic> json):msg = json['msg'], timestamp = json['timestamp'];
  // 增加 toJson 方法， 对象转 json
  Map<String, dynamic> toJson() => {
    "msg":"$msg",
    "timestamp": timestamp
  };

  @override
  String toString() {
    return "Message(msg: $msg, timestamp: $timestamp)";
  }
}
