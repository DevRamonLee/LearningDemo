import 'package:flutter/material.dart';

/// GridView.count 用法
class GridDemo2Page extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _GridDemo2PageState();
}

class _GridDemo2PageState extends State<GridDemo2Page> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('Grid page1 Demo'),
      ),
      body: new Center(
        child: buildGrid(),
      ),
    );
  }

  List<Container> _buildGridTileList(int count) {
    return new List<Container>.generate(
        count,
        (int index) => new Container(
              child: new Image.asset("images/c${index % 6}.jpg"),
            ));
  }

  Widget buildGrid() {
    var countGrid = new GridView.count(
      crossAxisCount: 2,
      mainAxisSpacing: 4.0,
      crossAxisSpacing: 4.0,
      padding: const EdgeInsets.all(4.0),
      childAspectRatio: 1.2,  // 控制子组件大小
      children: _buildGridTileList(30),
    );
    return countGrid;
  }
}
