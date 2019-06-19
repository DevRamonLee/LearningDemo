import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

// 代码块只有单行代码时可以这样写
void main() => runApp(new MyApp());

// Stateless 组件是不可变的，它们的属性是不能改变的，所有的值都是 final 的
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
//    final wordPair = new WordPair.random();// 随机获取一个单词
    return new MaterialApp(
      title: 'Startup name generator',
      // 使用新的主题
      theme: new ThemeData(
        primaryColor: Colors.white,
        dividerColor: Colors.red
      ),
      home: new RandomWords(),
    );
  }
}

// Stateful 组件可以保存状态，它在其生命周期内可以改变状态，实现了 stateful 的组件至少需要两个类：StatefulWidget 与 State 。
// StatefulWidget 创建一个 State 的实例，StatefulWidget 本身是不可变的，但是 State 是可变的，并且存在生命周期。
class RandomWords extends StatefulWidget {
  @override
  State<RandomWords> createState() {
    return new RandowWordsState();
  }
}

class RandowWordsState extends State<RandomWords> {
  // 保存建议的单词对，在 Dart 中，以下划线开头的变量表示是私有的
  final _suggestions = <WordPair>[];
  final _biggerFont  = const TextStyle(fontSize: 18.0);
  // 定义 Set 集合保存用户喜欢的单词对
  final _saved = new Set<WordPair>();

  Widget _buildRow(WordPair pair) {
    // 判断单词是否已经加入了 _saved 中
    final alreadySaved = _saved.contains(pair);
    return new ListTile(
      title: new Text(
        pair.asPascalCase,
        style: _biggerFont,
      ),
      // 添加心形图标
      trailing: new Icon(
        alreadySaved ? Icons.favorite : Icons.favorite_border,
        color: alreadySaved ? Colors.red : null,
      ),
      // 添加点击事件，收藏和取消收藏
      onTap: (){
        // 在 Flutter 的响应式框架中，调用 setState() 会触发对 State 对象的 build 方法的调用，从而导致 UI 刷新
        setState(() {
          if (alreadySaved) {
            _saved.remove(pair);
          } else {
            _saved.add(pair);
          }
        });
      },
    );
  }

  // 构造 ListView
  Widget _buildSuggestions() {
    return new ListView.builder(
      padding: const EdgeInsets.all(16.0),
      // itemBuilder 在每次生成一个单词对时被回调
      // 每一行都是用 ListTile 代表
      // 对于偶数行，这个回调都添加一个 ListTile 组件来保存单词对
      // 对于奇数行，这个回调函数会添加一个 Divider 组件来在视觉上分割单词对
      itemBuilder: (context, i){
        // 在 ListView 组件的每行之前，先添加一个像素高度的分割
        if(i.isOdd) return new Divider();
        // 这个"i ~/ 2"的表达式将i 除以 2，然后会返回一个整数结果。
        // 例： 1, 2, 3, 4, 5 会变成 0, 1, 1, 2, 2。
        // 这个表达式会计算 ListView 中单词对的真实数量
        final index = i ~/2;

        // 如果到达了单词对列表的结尾处
        if(index >= _suggestions.length){
          // 生成 10 个单词对到建议的名称列表中
          _suggestions.addAll(generateWordPairs().take(10));
        }
        return _buildRow(_suggestions[index]);
      },
    );
  }

  void _pushSaved() {
    // 点击列表图标时，添加一条新路径,打开新的界面
    Navigator.of(context).push(
      new MaterialPageRoute(
       builder:(context) {
         final tiles = _saved.map(
               (pair) {
             return new ListTile(
               title: new Text(pair.asPascalCase,
                 style: _biggerFont,
               ),
             );
           },
         );
         final divided = ListTile.divideTiles(
           context: context,
           tiles: tiles,
         ).toList();
         return new Scaffold(
           appBar: new AppBar(
             title: new Text('Saved Suggestions'),
           ),
           body: new ListView(children: divided),
         );
       },
      ),
    );
    }

  @override
  Widget build(BuildContext context) {
    debugPrint('call build');
    return new Scaffold (
      appBar: new AppBar(
        title: new Text('Startup Name Generator'),
        // 在 AppBar 上添加一个列表图标，当用户点击列表图标时，一个包含收藏项的新 route 被推送到导航器
        actions: <Widget>[
          new IconButton(icon: new Icon(Icons.list), onPressed: _pushSaved),
        ],
      ),
      body: _buildSuggestions(),
    );
  }
}

