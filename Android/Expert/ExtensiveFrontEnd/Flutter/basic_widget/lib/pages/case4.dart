import 'dart:collection';

import 'package:flutter/material.dart';

// 使用 InheritedWidget 实现我们自己的 Provider 并实现一个简单的购物车例子

// 商品信息
class Item {
  Item(this.price, this.count);
  double price; // 单价
  int count;  // 数量
}

// 保存购物车内商品信息的类
class CartModel extends ChangeNotifier {
  // 用于保存购物车中商品列表
  final List<Item> _items = [];

  // 禁止改变购物车里商品信息
  UnmodifiableListView<Item> get items => UnmodifiableListView(_items);

  // 购物车中商品总价
  double get totalPrice=>
      _items.fold(0, (value, item) => value + item.count * item.price);

  // 将 [item] 添加到购物车
  void add(Item item) {
    _items.add(item);
    // 通知监听器，重新构建 InheritedProvider，更新状态
    notifyListeners();
  }
}

class MyAppFour extends StatefulWidget {
  @override
  _MyAppFourState createState() => _MyAppFourState();
}

class _MyAppFourState extends State<MyAppFour> {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: ChangeNotifierProvider<CartModel>(
        data: CartModel(),
        child: Builder(builder: (context) {
          print("build");     // 这个不会打印
          return Column(
            children: <Widget>[
//              Builder(builder: (context) {
//                print("Text build");
//                var cart = ChangeNotifierProvider.of<CartModel>(context);
//                return Text("总价: ${cart.totalPrice}");
//              },),
              // 使用我们创建的 Widget，语义更清晰
              Consumer<CartModel>(
                builder: (context, cart) => Text("总价: ${cart.totalPrice}"),
              ),
              Builder(builder: (context) {
                print("RaiseButton build");
                return RaisedButton(    // 这里会有一次不必要的重建
                  child: Text("添加商品"),
                  onPressed: () {
                    // 给购物车添加商品，添加后总价会更新
                    ChangeNotifierProvider.of<CartModel>(context, listen: false).add(Item(20.0, 1));
                  },
                );
              },)
            ],
          );
        },),
      ),
    );
  }
}



/*-------------------我是分割线----------------*/
// 一个通用的 InheritedWidget，保存需要跨组件共享的状态
class InheritedProvider<T> extends InheritedWidget {
  InheritedProvider({@required this.data, Widget child}): super(child: child);

  // 共享状态使用泛型
  final T data;
  @override
  bool updateShouldNotify(InheritedWidget oldWidget) {
    // 这里简单返回 true，则每次更新都会调用依赖其的子孙的 didChangeDependencies
    return true;
  }
}

// 实现订阅者类
class ChangeNotifierProvider<T extends ChangeNotifier> extends StatefulWidget {
  ChangeNotifierProvider({
    Key key,
    this.data,
    this.child
  });

  final Widget child;
  final T data;

  // 定义一个便捷方法，方便子树中的 widget 获取共享数据
//  static T of<T>(BuildContext context) {
//    final provider = context.dependOnInheritedWidgetOfExactType<InheritedProvider<T>>();
//    return provider.data;
//  }

  // 优化上面的方法，增加一个 listen 参数，来决定是否建立依赖关系，避免不必要的重建
  static T of<T>(BuildContext context, {bool listen = true}) {
    final provider = listen
        ? context.dependOnInheritedWidgetOfExactType<InheritedProvider<T>>()    // 会建立子 widget 和 InheritedWidget 的依赖关系
        : context.getElementForInheritedWidgetOfExactType<InheritedProvider<T>>()?.widget as InheritedProvider<T>;  // 不会注册依赖关系
    return provider.data;
  }

  @override
  _ChangeNotifierProviderState<T> createState() =>  _ChangeNotifierProviderState<T>();
}

// 监听 model 改变时重新构建 widget 树
class _ChangeNotifierProviderState<T extends ChangeNotifier> extends State<ChangeNotifierProvider<T>> {

  void update() {
    // 如果数据发生变化（model 类调用了 notifiListeners），重新构建 InheritedProvider
    setState(() {});
  }

  @override
  void didUpdateWidget(ChangeNotifierProvider<T> oldWidget) {
    // 当 Provider 更新时，如果新旧数据不 ==，则解绑旧数据监听，同时添加新数据监听
    if (widget.data != oldWidget.data) {
      oldWidget.data.removeListener(update);
      widget.data.addListener(update);
    }
    super.didUpdateWidget(oldWidget);
  }

  @override
  void initState() {
    // 给 model 添加监听
    widget.data.addListener(update);
    super.initState();
  }

  @override
  void dispose() {
    // 移除 model 监听
    widget.data.removeListener(update);
    super.dispose();
  }

  // 调用 setState 时， widget.child  始终是同一个，所以执行 build 时 widget.child  不会重新 build，这里相当于对 child 做了缓存
  // 但如果是 ChangeNotifierProvider 的父级 widget 重新 build 时，传入的 child就可能发生变化
  @override
  Widget build(BuildContext context) {
    return InheritedProvider<T>(
      data: widget.data,
      child: widget.child,
    );
  }
}

// 为了优化 Builder 语义不明确，我们创建一个具有明确语义的 Widget
class Consumer<T> extends StatelessWidget {
  Consumer({
    Key key,
    @required this.builder,
    this.child,
  }) : assert(builder != null),
      super(key: key);

  final Widget child;
  final Widget Function(BuildContext context, T value) builder;

  @override
  Widget build(BuildContext context) {
    return builder(context, ChangeNotifierProvider.of<T>(context)); // 自动获取 Model
  }
}