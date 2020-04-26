import 'package:basic_widget/pages/tabs/basic_widget.dart';
import 'package:basic_widget/pages/tabs/listview_widget.dart';
import 'package:flutter/material.dart';

/// 基本组件的用法
class PageBasic extends StatefulWidget {
  PageBasic(Key key): super(key: key);

  @override
  State<StatefulWidget> createState() => new _PageBasic();
}

class _PageBasic extends State<PageBasic> {
  int _currentIndex = 0;

  List _pageList = [
    new BasicWidget(Key('basic_widget')),
    new ListViewWidget(Key('listview_widget'))
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('基本组件'),
      ),
      body: _pageList[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
          fixedColor: Colors.red,
          onTap: (int index){
            setState(() {
              _currentIndex = index;
            });
          },
          currentIndex: _currentIndex,
          items: [
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          title: Text('基本组件')
        ),
        BottomNavigationBarItem(
            icon: Icon(Icons.home),
            title: Text('列表组件')
        )
      ]),
    );
  }
}
