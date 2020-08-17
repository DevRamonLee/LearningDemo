import 'package:flutter/material.dart';
import 'package:oktoast/oktoast.dart';

/// 综合案例2
// 布局综合案例2： 实现一个 ListView
class MyAppTwo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MyAppTwoState();
  }
}

class _MyAppTwoState extends State<MyAppTwo> {
  final buildings = [
    Building(
        BuildingType.theater, 'CineArts at the Empire', '85 W Portal Ave'),
    Building(BuildingType.theater, 'The Castro Theater', '429 Castro St'),
    Building(
        BuildingType.theater, 'Alamo Drafthouse Cinema', '2550 Mission St'),
    Building(BuildingType.theater, 'Roxie Theater', '3117 16th St'),
    Building(BuildingType.theater, 'United Artists Stonestown Twin',
        '501 Buckingham Way'),
    Building(BuildingType.theater, 'AMC Metreon 16', '135 4th St #3000'),
    Building(BuildingType.restaurant, 'K\'s Kitchen', '1923 Ocean Ave'),
    Building(BuildingType.restaurant, 'Chaiya Thai Restaurant',
        '72 Claremont Blvd'),
    Building(BuildingType.restaurant, 'La Ciccia', '291 30th St'),

    // double
    Building(
        BuildingType.theater, 'CineArts at the Empire', '85 W Portal Ave'),
    Building(BuildingType.theater, 'The Castro Theater', '429 Castro St'),
    Building(
        BuildingType.theater, 'Alamo Drafthouse Cinema', '2550 Mission St'),
    Building(BuildingType.theater, 'Roxie Theater', '3117 16th St'),
    Building(BuildingType.theater, 'United Artists Stonestown Twin',
        '501 Buckingham Way'),
    Building(BuildingType.theater, 'AMC Metreon 16', '135 4th St #3000'),
    Building(BuildingType.restaurant, 'K\'s Kitchen', '1923 Ocean Ave'),
    Building(BuildingType.restaurant, 'Chaiya Thai Restaurant',
        '72 Claremont Blvd'),
    Building(BuildingType.restaurant, 'La Ciccia', '291 30th St'),
  ];

  @override
  Widget build(BuildContext context) {
    return new  Scaffold(
        appBar: AppBar(
          title: Text('Buildings'),
        ),
        body: BuildingListView(
            buildings, (index) => print("click $index")),
      );
  }
}

enum BuildingType { theater, restaurant }

// 数据模型
class Building {
  final BuildingType type;
  final String title;
  final String address;

  Building(this.type, this.title, this.address);
}

// 给 item 加上点击事件
typedef OnItemClickListener = void Function(int positon);

// 每个 item 的 UI
class ItemView extends StatelessWidget {
  final int position;
  final Building building;
  final OnItemClickListener listener;

  // listener 从 listView 传入
  ItemView(this.position, this.building, this.listener);

  @override
  Widget build(BuildContext context) {
    final icon = Icon(
        building.type == BuildingType.restaurant
            ? Icons.restaurant
            : Icons.theaters,
        color: Colors.blue[500]);

    final widget = Row(
      children: <Widget>[
        Container(
          margin: EdgeInsets.all(16.0),
          child: icon,
        ),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Text(
                building.title,
                style: TextStyle(
                  fontSize: 20.0,
                  fontWeight: FontWeight.w500,
                ),
              ),
              Text(building.address)
            ],
          ),
        )
      ],
    );
    // 监听点击事件，使用 InkWell 添加水波纹效果
    return InkWell(
      onTap: () => listener(position),
      child: widget,
    );
  }
}

class BuildingListView extends StatelessWidget {
  final List<Building> buildings;
  final OnItemClickListener listener;

  // 外部通过构造函数传入数据和 listener
  BuildingListView(this.buildings, this.listener);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: buildings.length,
        itemBuilder: (context, index) {
          return new ItemView(index, buildings[index], listener);
        });
  }
}