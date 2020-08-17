import 'package:basic_widget/pages/tabs/basic_widget.dart';
import 'package:basic_widget/pages/tabs/custom_scoll_view_widget.dart';
import 'package:basic_widget/pages/tabs/gridview_widget.dart';
import 'package:basic_widget/pages/tabs/inherited_widget.dart';
import 'package:basic_widget/pages/tabs/listview_widget.dart';
import 'package:flutter/material.dart';

/// 基本组件的用法
class PageBasic extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _PageBasic();
}

class _PageBasic extends State<PageBasic> with SingleTickerProviderStateMixin {
  int _currentIndex = 0;

  List<Widget> _pageList = [
    new BasicWidget(),
    new ListViewWidget(),
    new GridViewWidget(),
    new CustomScrollViewWidget(),
    new InheritedWidgetTest()
  ];

  TabController _tabController; // TabBar  需要定义一个 controller
  List tabs = ["基本组件", "ListView", "GridView", "CustomScrollView", "InheritedWidget"];

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: tabs.length, vsync: this);
    _tabController.addListener(() {
      setState(() {
        _currentIndex = _tabController.index;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Basic Widget'),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.share), onPressed: (){},) // 导航右侧菜单
        ],
        bottom: TabBar(
          controller: _tabController,
          tabs: tabs.map((e) => Tab(text: e)).toList(),
        ),
      ),
      drawer: new MyDrawer(), // 抽屉
      body: TabBarView( // 配合 TabBar 来实现
        controller: _tabController,
        children: _pageList.toList()
      ),
      bottomNavigationBar: BottomNavigationBar( // 底部导航
          fixedColor: Colors.red,
          onTap: (int index){
            setState(() {
              _currentIndex = index;
              _tabController.index = index;
            });
          },
          type: BottomNavigationBarType.fixed,
          currentIndex: _currentIndex,
          items: [
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          title: Text('基本组件')
        ),
        BottomNavigationBarItem(
            icon: Icon(Icons.list),
            title: Text('ListView')
        ),
        BottomNavigationBarItem(
            icon: Icon(Icons.list),
            title: Text('GridView')
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.list),
          title: Text("CustomScrollView")
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.assignment_ind),
          title: Text("InheritedWidget")
        )
      ]),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: (){},
      ),
    );
  }
}

class MyDrawer extends StatelessWidget {
  const MyDrawer({
    Key key,}): super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: MediaQuery.removePadding(
          context: context,
          removeTop: true,  // 移除抽屉菜单顶部默认留白
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.only(top: 38.0),
                child: Row(
                  children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 16.0),
                      child: ClipOval(
                        child: Image.asset(
                          "images/c2.jpg",
                          height: 70,
                        ),
                      ),
                    ),
                    Text(
                      "Ramon",
                      style: TextStyle(fontWeight: FontWeight.bold),
                    )
                  ],
                ),
              ),
              Expanded(
                child: ListView(
                  children: <Widget>[
                    ListTile(
                      leading: const Icon(Icons.add),
                      title: const Text('Add account'),
                    ),
                    ListTile(
                      leading: const Icon(Icons.settings),
                      title: const Text('Manage accounts'),
                    ),
                  ],
                ),
              )
            ],
          )),
    );
  }
}
