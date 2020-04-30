import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CustomScrollViewWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: <Widget>[
        // AppBar, 包含一个导航栏
         SliverAppBar(
           pinned: true,
           expandedHeight: 250,
           flexibleSpace: FlexibleSpaceBar(
             title: const Text("CustomScrollView"),
             background: Image.asset("./images/c0.jpg", fit: BoxFit.cover,),
           ),
         ),

        SliverPadding(
          padding: const EdgeInsets.all(8),
          sliver: new SliverGrid(
            gridDelegate: new SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2,
                mainAxisSpacing: 10,
                crossAxisSpacing: 10,
                childAspectRatio: 4
            ),
            delegate: new SliverChildBuilderDelegate(
                (BuildContext context, int index) {
                  // 创建子 Widget
                  return new Container(
                    alignment: Alignment.center,
                    color: Colors.cyan[100 * (index % 9)],
                    child: new Text("grid item $index"),
                  );
                },
              childCount: 20
            ),
          ),
        ),

        // List
        new SliverFixedExtentList(
            itemExtent: 50,
            delegate: new SliverChildBuilderDelegate(
                (BuildContext context, int index) {
                  // 创建列表项
                  return new Container(
                    alignment: Alignment.center,
                    color: Colors.lightBlue[100 * (index % 9)],
                    child: Text("list item $index"),
                  );
                },
              childCount: 50
            ))
      ],
    );
  }
}