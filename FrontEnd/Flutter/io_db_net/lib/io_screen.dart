import 'dart:typed_data';
// Stream 是 async 包里的类
import 'dart:async';
// utf8、LineSplitter 属于 convert 包, json 相关也在这个包
import 'dart:convert';
import 'package:flutter/material.dart';
import 'dart:io';
import 'package:path_provider/path_provider.dart';

class IoScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _IoScreenState();
  }
}

class _IoScreenState extends State<IoScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('文件读写'),
      ),
      body: Column(
        children: <Widget>[
          FlatButton(onPressed: writeString, child: Text("写入文件, writeAsString 方法")),
          FlatButton(onPressed: readString, child: Text("读取文件 readAsString")),
          FlatButton(onPressed: writeBytes, child: Text("写入文件, writeAsBytes 方法")),
          FlatButton(onPressed: readBytes, child: Text("读取文件 readAsBytes")),
          FlatButton(onPressed: writeByIOSink,child: Text('写入文件 writeByIOSink 方法,追加写入'),),
          FlatButton(onPressed: readByOpenRead,child: Text('读取文件 openRead 方法'),),
          FlatButton(onPressed: jsonTest,child: Text('Json 转换'),)
        ],
      ),
    );
  }

  // 获取文件目录
  loadPath() async {
    try {
      var tempDir = await getTemporaryDirectory();
      String tempPath = tempDir.path;

      var appDocDir = await getApplicationDocumentsDirectory();
      String appDocPath = appDocDir.path;

      print('临时目录 ' + tempPath);
      print('文档目录 ' + appDocPath);
      return tempPath;
    } catch (err) {
      print(err);
    }
  }

  // 创建文件, 异步的方式
  loadFile(path) async {
    var file = new File('$path/counter.json');
    try {
      bool exists = await file.exists();
      if(!exists) {
        await file.create();
      }
      return file;
    } catch(err) {
      print(err);
    }
  }

  // 创建文件，同步的方式,方法名带有 Sync 后缀
  loadFileSync(path) {
    var file = File(path);
    try {
      bool exists = file.existsSync();
      if(!exists) {
        file.createSync();
      }
      return file;
    } catch(e) {
    }
  }

  // 写入数据，使用 writeAsString 方法
  writeString() async {
    try {
      final file = await loadFile(await loadPath());
      return file.writeAsString("Hello,IO!");
    }
    catch (err) {
      print(err);
    }
  }

  // 写入数据，使用 writeAsBytes 方法
  // 这里写入好像有编码问题，写进入读不出来
  writeBytes() async {
    try {
      final file = await loadFile(await loadPath());
      List<int> toBeWritten = [1, 2, 3];
      return  file.writeAsBytes(toBeWritten);
    } catch (err) {
      print(err);
    }
  }

  // 使用 openWrite 写入
  writeByIOSink() async {
    var file = await loadFile(await loadPath());
    IOSink sink;
    try {
//      sink = file.openWrite();
      // 默认的写文件操作会覆盖原有内容，如果需要追加内容，则要使用 append 模式
      sink = file.openWrite(mode: FileMode.append);
    // write() 的参数是一个 object， 它会执行 obj.toString 把转换后的 string 写入文件
      sink.write("IOSink");
      // 调用 flush 后才会真正把数据写进去
      await sink.flush();
    }catch(e) {
      print(e);
    } finally {
      sink?.close();
    }
  }

  // 读取数据 readAsString
  readString() async {
    try {
      final file = await loadFile(await loadPath());
      String str = await file.readAsString();
      print('readAsString 读取：' + str);
      return str;
    }
    catch (err) {
      print(err);
    }
  }

  // 读取数据 readAsBytes
  readBytes() async {
    try {
      final file = await loadFile(await loadPath());

// Unit8List 转 String: String s = new String.fromCharCodes(inputAsUint8List);
// String 转 Unit8List: var outputAsUint8List = new Uint8List.fromList(s.codeUnits);
      Uint8List str = await file.readAsBytes();
      print('readAsBytes 读取：' + new String.fromCharCodes(str));
      return str;
    } catch (err) {
      print(err);
    }
  }
  
  // 读取，使用 openRead 方法
  readByOpenRead() async {
    var file = await loadFile(await loadPath());
    try {
      Stream<List<int>> stream = file.openRead();
      var lines = stream
            // 把内容用 utf-8 解码
          .transform(utf8.decoder)
          .transform(LineSplitter());
          await for(var line in lines) {
            print(line);
          }
    } catch (e) {
      print(e);
    }
  }

  // 对象转换为 Json
  jsonTest() {
    var point = Point(2, 12, 'some point');
    var pointJson = json.encode(point);
    print('pointSjon = $pointJson');

    // 支持 List/Map
    var points = [point, point];
    var pointsJson = json.encode(points);
    print('pointsJson = $pointsJson');

    // json 转对象
    var decoded = json.decode(pointJson);
    print('decoded.runtimeType = ${decoded.runtimeType}');  // _InternalLinkedHashMap<String,dynamic>

    var point2 = Point.fromJson(decoded);
    print('point2 = $point2');

    decoded = json.decode(pointsJson);
    print('decoded.runtimeType = ${decoded.runtimeType}');
    var points2 = <Point>[];
    for(var map in decoded) {
      points2.add(Point.fromJson(map));
    }
    print('points2 = $points2');
  }

  // json 转对象
}

// json 对象的转换
class Point{
  int x;
  int y;
  String description;

  Point(this.x, this.y, this.description);

  // 为了把对象转换为 json，我们需要定义一个 toJson 方法
  Map<String, dynamic>toJson() => {
    'x':x,
    'y':y,
    'desc':description
  };

  // 为了把 json 转对象，我们需要给类加一个构造函数
  Point.fromJson(Map<String,dynamic> map) :x = map['x'],y = map['y'],description = map['desc'];

  // 为了方便演示，加入一个 toString
  @override
  String toString() {
    return "Point{x=$x,y=$y,desc=$description}";
  }
}
