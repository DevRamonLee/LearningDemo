import 'package:flutter/material.dart';

/// GridView用法
class GridViewWidget extends StatelessWidget {
  List<Widget> icons = new List();
  @override
  Widget build(BuildContext context) {
    icons.add(Icon(Icons.event));
    icons.add(Icon(Icons.code));
    return Column(children: <Widget>[
//      Expanded(
//          flex: 1,
//          child: GridView(
//            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount (
//              crossAxisCount: 3,
//              childAspectRatio: 1
//            ),
//            children: <Widget>[
//              Icon(Icons.ac_unit),
//              Icon(Icons.airport_shuttle),
//              Icon(Icons.all_inclusive),
//              Icon(Icons.beach_access),
//              Icon(Icons.cake),
//              Icon(Icons.free_breakfast)
//            ],
//          )
//        ),
      Expanded(
        child: GridView.count(  // 效果和上面相同，内入使用了 SliverGridDelegateWithFixedAxisCount
          crossAxisCount: 3,
          childAspectRatio: 1,
          children: <Widget>[
            Icon(Icons.ac_unit),
            Icon(Icons.airport_shuttle),
            Icon(Icons.all_inclusive),
            Icon(Icons.beach_access),
            Icon(Icons.cake),
            Icon(Icons.free_breakfast)
          ],
        ),
      ),
//      Expanded(
//          flex: 1,
//          child: GridView(
//            gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent (
//                maxCrossAxisExtent: 120,
//                childAspectRatio: 2.0 // 宽高比
//            ),
//            children: <Widget>[
//              Icon(Icons.ac_unit),
//              Icon(Icons.airport_shuttle),
//              Icon(Icons.all_inclusive),
//              Icon(Icons.beach_access),
//              Icon(Icons.cake),
//              Icon(Icons.free_breakfast)
//            ],
//          )
//      ),
      Expanded(
        flex: 1,
        child: GridView.extent(
            maxCrossAxisExtent: 120.0,  // 效果和上面相同，内部使用了 SliverGridDelegateWithMaxCrossAxisExtent
            childAspectRatio: 2.0,
            children: <Widget>[
              Icon(Icons.ac_unit),
              Icon(Icons.airport_shuttle),
              Icon(Icons.all_inclusive),
              Icon(Icons.beach_access),
              Icon(Icons.cake),
              Icon(Icons.free_breakfast),
          ],
        )),
      // 上面两种方式只适合少量元素，大量元素还是使用 builder
       Expanded(
         flex: 1,
         child: GridView.builder(
             gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
               crossAxisCount: 2,
               childAspectRatio: 1
             ),
             itemCount: icons.length,
             itemBuilder: (context, index){
               return icons[index];
             }),
       )
    ],);
  }
}