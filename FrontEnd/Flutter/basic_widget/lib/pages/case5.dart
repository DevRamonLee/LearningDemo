import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';


// 路由换肤功能
class MyAppFive extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MyAppFiveState();
}

class _MyAppFiveState extends State<MyAppFive> {
  Color _themeColor = Colors.teal;  // 当前路由主题色
  @override
  Widget build(BuildContext context) {
    ThemeData themeData = Theme.of(context);
    return Theme(
      data: ThemeData(
        primarySwatch: _themeColor, // 用于导航栏，FloatingActionButton
        iconTheme: IconThemeData(color: _themeColor)  // 用于 Icon 颜色
      ),
      child: Scaffold(
        appBar: AppBar(title: Text('主题测试'),),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
             // 第一行 icon 使用主题的 iconTheme
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(Icons.favorite),
                Icon(Icons.airport_shuttle),
                Text(" 跟随主题颜色")
              ],
            ),
            // 第二行 Icon 自定义颜色
            Theme(  // 可以用局部主题覆盖全局主题
              data: themeData.copyWith(
                iconTheme: themeData.iconTheme.copyWith(
                    color: Colors.black
                ),
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(Icons.favorite),
                  Icon(Icons.airport_shuttle),
                  Text(" 颜色固定黑色")
                ],
              ),
            )
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => setState(
              () =>
                  _themeColor = _themeColor == Colors.teal ? Colors.purple : Colors.teal
          ),
          child: Icon(Icons.palette),
        ),
      ),
    );
  }
}