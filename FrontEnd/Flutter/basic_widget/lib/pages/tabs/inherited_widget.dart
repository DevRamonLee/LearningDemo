import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class InheritedWidgetTest extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => InheritedWidgetTestState();
}

class InheritedWidgetTestState extends State<InheritedWidgetTest> {
  int count  = 0;
  @override
  Widget build(BuildContext context) {
    return Center(
      child: ShareDataWidget(data: count,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.only(bottom: 20.0),
            child: _TestWidget(), // 子 widget 中依赖 ShareDataWidget
          ),
          RaisedButton(
            child: Text("Increment"),
            // 每点击一次， count 自增，重新 build 后 ShareDataWidget 的 data 会更新
            onPressed: ()=> setState(() => ++ count),
          )
        ],
      ),),
    );
  }
}


// testWidget，在其 build 方法中引用 ShareDataWidget 中的数据
class _TestWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _TestWidgetState();
}

class _TestWidgetState extends State<_TestWidget> {
  @override
  Widget build(BuildContext context) {
    print("build");
    // 使用 InheritedWidget 中的共享数据
    return Text(ShareDataWidget.of(context).data.toString());
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    // 父或者祖先 widget 中的 InheritedWidget 改变（updateShouldNotify 返回 true）时会被调用，如果 build 中没有依赖 InheritedWidget，则该回调不会调用
    print("Dependencies change");
  }
}


class ShareDataWidget extends InheritedWidget {
  final int data; // 需要在子树中共享的数据，保存点击次数
  ShareDataWidget({@required this.data,
  Widget child}) : super(child: child);

  // 定义一个便捷方法，方便子树中的widget获取共享数据
  static ShareDataWidget of(BuildContext context) {
    return context.inheritFromWidgetOfExactType(ShareDataWidget);
//    return context.dependOnInheritedWidgetOfExactType<ShareDataWidget>(); // 新版本上使用这个方法
  }

  // 该回调决定当 data 发生变化时，是否通知子树中依赖 data 的 widget
  @override
  bool updateShouldNotify(ShareDataWidget oldWidget) {
    // 如果返回 true，则子树中依赖（build 函数中有使用）本 widget的子widget 的 state.didChangeDependencies 会调用
    return oldWidget.data != data;
  }
}