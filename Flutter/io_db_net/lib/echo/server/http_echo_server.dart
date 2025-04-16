import 'dart:async';
import 'dart:io';
import 'package:io_db_net/echo/message.dart';
import 'dart:convert';
import 'package:path_provider/path_provider.dart' as path_provider;
import 'package:sqflite/sqflite.dart';

/// 例：echo 客户端 -- 服务端
class HttpEchoServer {
  static const GET = 'GET';
  static const POST = 'POST';

  List<Message> messages = [];

  String historyFilepath;

  final int port;
  HttpServer httpServer;

  static const tableName = "History";
  static const columnId = "id";
  static const columnMsg = 'msg';
  static const columnTimeStamp = 'timestamp';

  Database database;

  Future _initDatabase() async {
    var path = await getDatabasesPath() + '/history.db';
    database = await openDatabase(
        path,
        version: 1,
        onCreate: (db, version) async {
          var sql = "CREATE TABLE $tableName($columnId INTEGER PRIMARY KEY, $columnMsg Text, $columnTimeStamp INTEGER)";
          await db.execute(sql);
        });
  }

  // Dart 里，函数也可以作为 Map 的参数
  Map<String, void Function(HttpRequest)> routes;

  // 构造函数
  HttpEchoServer(this.port) {
    _initRoutes();
  }

  void _initRoutes() {
    routes = {
      // 我们只支持 path 为 '/history' 和 '/echo' 的请求
      // history 用于获取历史记录
      // echo 用于提供 echo 服务
      '/history':_history,
      '/echo':_echo,
    };
  }

  // 返回一个 future，这样客户端能够在 start 完成后做一些事
  Future start() async {
    _initDatabase();  // 初始化数据库
    historyFilepath = await _historyPath();
    // 在启动服务器前先加载历史记录
    await _loadMessages();

    // 1. 创建一个 HttpServer
    httpServer = await HttpServer.bind(InternetAddress.loopbackIPv4, port);
    // 2. 开始监听客户端请求
    return httpServer.listen((request) {
      final path = request.uri.path;
      final handler = routes[path];
      if(handler != null) {
        handler(request);
      } else {
        // 给客户端返回一个 404
        request.response.statusCode = HttpStatus.notFound;
        request.response.close();
      }
    });
  }

  Future<String> _historyPath() async {
    // 获取应用私有的文件目录
    final directory = await path_provider.getApplicationDocumentsDirectory();
    return directory.path + '/message.json';
  }

  void _history(HttpRequest request) {
    if(request.method != GET) {
      __unsupportedMethod(request);
      return;
    }

    String historyData = json.encode(messages);
    request.response.write(historyData);
    request.response.close();
  }

  __unsupportedMethod(HttpRequest request) {
    request.response.statusCode = HttpStatus.methodNotAllowed;
    request.response.close();
  }

  void _echo(HttpRequest request) async {
    if(request.method != POST) {
      __unsupportedMethod(request);
      return;
    }

    // 获取从客户端 post 请求的 body，更多可参考 https://www.dartlang.org/tutorials/dart-vm/httpserver
    String body = await request.transform(utf8.decoder).join();
    var message;
    if (body != null) {
      message = Message.create(body);
      messages.add(message);
      request.response.statusCode = HttpStatus.ok;
      // json 是 convert 包里的对象，encode 方法还有第二个参数 toEncodable。当遇到对象不是      
      // Dart 的内置对象时，如果提供这个参数，就会调用它对对象进行序列化；这里我们没有提供,
      // 所以 encode 方法会调用对象的 toJson 方法，这个方法在前面我们已经定义了

      var data = json.encode(message);
      // 把响应写回给客户端
      request.response.write(data);
    } else {
      request.response.statusCode = HttpStatus.badRequest;
    }
    request.response.close();
    // 为了简单，这里多保存几次
//    _storeMessages();
      // 保存收到的这条消息
      _storeMessages(message);
  }

  Future _storeMessages(Message message) async {
    database.insert(tableName, message.toJson());
  }

  // 保存历史记录, 保存在文件中
  /*Future<bool> _storeMessages() async {
    try{
      // json encode 支持 List Map
      final data = json.encode(messages);
      // File 是 dart:io 里的类
      final file = File(historyFilepath);
      final exists =  await file.exists();
      if(!exists) {
        await file.create();
      }
      file.writeAsString(data);
    }catch(e) {
      print('_storeMessages: $e');
      return false;
    }
  }*/

  Future _loadMessages() async {
    // 从文件读取
    /*try{
      var file = File(historyFilepath);
      var exists = await file.exists();
      if(!exists) return;

      var content = await file.readAsString();
      var list = json.decode(content);
      for(var msg in list) {
        var message = Message.fromJson(msg);
        messages.add(message);
      }
    }catch(e) {
      print('_loadMessages: $e');
    }*/

    // 从数据库读取
    var list = await database.query(
      tableName,
      columns: [columnMsg, columnTimeStamp],
      orderBy: columnId,
    );
    for (var item in list) {
      // fromJson 也适用于从数据库读取的场景
      var messge = Message.fromJson(item);
      messages.add(messge);
    }
  }

  void close() async {
    var server = httpServer;
    httpServer = null;
    await server?.close();

    // 关闭数据库
    var db = database;
    database = null;
    db?.close();
  }
}