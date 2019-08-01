import 'package:flutter/material.dart';

/// GridView.extent 用法
class GridDemo1Page extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _GridDemo1PageState();
}

class _GridDemo1PageState extends State<GridDemo1Page> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(title: new Text('Grid page1 Demo'),),
      body: new Center(
        child: buildGrid(),
      ),
    );
  }

  List<Container> _buildGridTileList(int count) {
    return new List<Container>.generate(count, (int index) =>
      new Container(child: new Image.asset('images/c${index % 6}.jpg'),));
  }
  Widget buildGrid() {
    // 指定子项占据的最大宽度
    return new GridView.extent(
        maxCrossAxisExtent: 100.0,
        padding: const EdgeInsets.all(4.0),
        mainAxisSpacing: 4.0,
        crossAxisSpacing: 4.0,
        children: _buildGridTileList(30),);
  }
}