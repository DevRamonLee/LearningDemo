import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

/// 综合案例3：ListView 加载更多
class MyAppThree extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MyAppThreeState();
}

class _MyAppThreeState extends State<MyAppThree> {

  static const loadingTag = "loading";  // 表尾标志
  var _words = <String>[loadingTag];

  ScrollController _controller = new ScrollController();
  bool showToTopBtn = false;  // 是否显示"返回顶部"按钮
  String _progress = "0%";  // 滚动进度百分比
  @override
  void initState() {
    super.initState();
    _retriveData();
    // 监听滚动事件
    _controller.addListener(() {
      print("offset is ${_controller.offset}");  // 打印滚动位置
      if (_controller.offset < 1000 && showToTopBtn) {
        setState(() {
          showToTopBtn = false;
        });
      } else if (_controller.offset >= 1000 && showToTopBtn == false) {
        setState(() {
          showToTopBtn = true;
        });
      }
    });
  }

  @override
  void dispose() {
    // 避免内存泄露
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: AppBar(
        title: Text("Infinite ListView"),
      ),
      body: NotificationListener<ScrollNotification>(
        onNotification: (ScrollNotification notification) {
          double progress = notification.metrics.pixels / notification.metrics.maxScrollExtent;
          setState(() {
            _progress = "${(progress * 100).toInt()}%";
          });
          return true;
        },
        child: Stack(
          alignment: Alignment.center,
            children: <Widget>[
              ListView.separated(
                  itemCount: _words.length,
                  controller: _controller,
                  itemBuilder: (context, index) {
                    // 如果到了表尾
                    if (_words[index] == loadingTag) {
                      // 不足 100 条，继续获取数据
                      if (_words.length - 1 < 100) {
                        _retriveData();
                        // 加载时显示 loading
                        return Container(
                          padding: const EdgeInsets.all(16.0),
                          alignment: Alignment.center,
                          child: SizedBox(
                            width: 24,
                            height: 24,
                            child: CircularProgressIndicator(strokeWidth: 2.0,),
                          ),
                        );
                      } else {
                        // 已经加载了100 条，没有更多
                        return Container(
                          alignment: Alignment.center,
                          padding: EdgeInsets.all(16),
                          child: Text("没有更多了", style: TextStyle(color: Colors.grey)),
                        );
                      }
                    }
                    // 显示单词列表项
                    return ListTile(title: Text(_words[index]));
                  },
                  separatorBuilder: (context, index) => Divider(height: 1.0, color: Colors.red)),
              CircleAvatar( // 显示进度百分比
                radius: 30.0,
                child: Text(_progress),
                backgroundColor: Colors.black54,
              )
            ],
        ),
      ),
      floatingActionButton: !showToTopBtn ? null: FloatingActionButton(
        child: Icon(Icons.arrow_upward),
        onPressed: () {
          // 返回顶部时执行动画
          _controller.animateTo(0, duration: Duration(milliseconds: 200), curve: Curves.ease);
        },
      ),
    );
  }

  void _retriveData() {
    Future.delayed(Duration(seconds: 2)).then((e) {
      setState(() {
        _words.insertAll(_words.length - 1,
          // 使用 english_words 每次生成 20 个单词
          generateWordPairs().take(20).map((e) => e.asPascalCase).toList()
        );
      });
    });
  }
}
