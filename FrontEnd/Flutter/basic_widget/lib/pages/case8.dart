import 'package:flutter/material.dart';

// Flutter 对话框使用
class MyAppEight extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MyAppEightState();
}

class _MyAppEightState extends State<MyAppEight> {
  bool _hideDialogCode = true;
  DialogType currentType;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("对话框"),),
      body: Builder(
        builder: (BuildContext context) {
          return SingleChildScrollView(
              child: Padding(
                padding: EdgeInsets.all(15),
                child: Column(
                  children: <Widget>[
                    createDialog(DialogType._AlertDialog, " AlertDialog", alertDialog),
                    createDialog(DialogType._SimpleDialog, "simpleDialog", simpleDialog),
                    createDialog(DialogType._ListDialog, "ListDialog", listDialog),
                    RaisedButton(
                      child: Text("自定义 Dialog 动画与遮罩"),
                      onPressed: () {
                        showCustomDialog<bool>(
                            context: context,
                            builder: (context) {
                              return AlertDialog(
                                title: Text("提示"),
                                content: Text("您确定删除当前文件吗"),
                                actions: <Widget>[
                                  FlatButton(
                                    child: Text("取消"),
                                    onPressed: () => Navigator.of(context).pop(),
                                  ),
                                  FlatButton(
                                    child: Text("删除"),
                                    onPressed: () {
                                      Navigator.of(context).pop(true);
                                    },
                                  )
                                ],
                              );
                            }
                        );
                      },
                    ),
                    RaisedButton(
                      child: Text("对话框状态管理"),
                      onPressed: ()async {
                        bool deleteTree = await showDeleteConfirmDialogWithState();
                        if (deleteTree == null) {
                          print("取消删除");
                        } else {
                          print("同时删除子目录 $deleteTree");
                        }
                      },
                    ),
                    RaisedButton(
                      child: Text("底部弹出对话框"),
                      onPressed: ()async {
                        int type = await _showModelBottomSheet();
                        print("type is $type");
                      },
                    ),
                    RaisedButton(
                      child: Text("底部弹出全屏对话框"),
                      onPressed: () {
                        Scaffold.of(context).showBottomSheet((context) {
                          return ListView.builder(
                              itemCount: 30,
                              itemBuilder: (BuildContext context, int index) {
                                return ListTile(
                                  title: Text("$index"),
                                  onTap: () {
                                    print("$index");
                                    Navigator.of(context).pop();
                                  },
                                );
                              });
                        });
                      },
                    ),
                    RaisedButton(
                      child: Text("Loading"),
                      onPressed: () {
                        showLoadingDialog();
                      },
                    ),
                    RaisedButton(
                      child: Text("时间选择"),
                      onPressed: () {
                        _showDatePicker();
                      },
                    )
                  ],
                ),
              )
          );
        },
      )
    );
  }
  
  Widget createDialog(DialogType type, String dialogName, String dialogCode) {
    return Column(
        children: <Widget>[
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Expanded(
                child:RaisedButton(
                    child: Text(dialogName),
                    onPressed: () async {
                      //弹出对话框并等待其关闭
                      switch (type) {
                        case DialogType._AlertDialog:
                          bool delete = await showDeleteConfirmDialog();
                          if (delete == null) {
                            print("取消删除");
                          } else {
                            print("已确认删除");
                          }
                          break;
                        case DialogType._SimpleDialog:
                          showSimpleDialog();
                          break;
                        case DialogType._ListDialog:
                          showListDialog();
                          break;
                        default:
                          print("unknown dialog type");
                      }
                    },
                )
              ),
              IconButton(
                icon: Icon(Icons.arrow_drop_down_circle),
                onPressed: () {
                  setState(() {
                    _hideDialogCode = !_hideDialogCode;
                    currentType = type;
                  });
                },
              )
            ],
          ),
          Offstage(
            offstage: _hideDialogCode || currentType != type,
            child: Text(dialogCode),
          )
        ],
      );
  }

  Future<bool> showDeleteConfirmDialog() {
    return showDialog<bool>(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text("提示"),
          content: Text("您确定要删除当前文件吗?"),
          actions: <Widget>[
            FlatButton(
              child: Text("取消"),
              onPressed: () => Navigator.of(context).pop(), // 关闭对话框
            ),
            FlatButton(
              child: Text("删除"),
              onPressed: () {
                //关闭对话框并返回true
                Navigator.of(context).pop(true);
              },
            ),
          ],
        );
      },
    );
  }

  Future<void> showSimpleDialog() async {
    int i = await showDialog(
        context: context,
        builder: (BuildContext context) {
          return SimpleDialog(
            title: const Text('请选择语言'),
            children: [
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, 1);
                },
                child: Padding(
                  padding: const EdgeInsets.symmetric(vertical: 6),
                  child: const Text("中文简体"),
                ),
              ),
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, 2);
                },
                child: Padding(
                  padding: const EdgeInsets.symmetric(vertical: 6),
                  child: const Text("美国英语"),
                ),
              )
            ],
          );
        });
    if (i != null) {
      print("选择了：${i == 1 ? "中文简体" : "美国英语"}");
    }
  }

  Future<void> showListDialog() async {
    int index = await showDialog<int>(
      context: context,
      builder: (BuildContext context) {
        var child = Column(
          children:<Widget>[
            ListTile(title: Text("请选择"),),
            Expanded(
              child: ListView.builder(
                  itemCount: 30,
                  itemBuilder: (BuildContext context, int index) {
                    return ListTile(
                      title: Text("$index"),
                      onTap: ()=> Navigator.of(context).pop(index),
                    );
                  }),
            )
          ],
        );
        // 使用 AlertDialog 会报错
//        return AlertDialog(content: child);
        return Dialog(child: child);
      });
    if (index != null) {
      print("点击了：$index");
    }
  }

  Future<T> showCustomDialog<T> ({
    @required BuildContext context,
    bool barrierDismissible = true,
    WidgetBuilder builder,
  }) {
    final ThemeData theme = Theme.of(context, shadowThemeOnly: true);
    return showGeneralDialog(
        context: context,
        pageBuilder: (BuildContext buildContext, Animation<double> animation, Animation<double> secondaryAnimation) {
          final Widget pageChild = Builder(builder: builder,);
          return SafeArea(
            child: Builder(builder: (BuildContext context) {
              return theme != null
                  ? Theme(data: theme, child: pageChild)
                  : pageChild;
            },),
          );
        },
        barrierDismissible: barrierDismissible,
        barrierLabel: MaterialLocalizations.of(context).modalBarrierDismissLabel,
        barrierColor: Colors.black87,   // 自定义遮罩颜色
        transitionDuration: const Duration(milliseconds: 150),
        transitionBuilder: _buildMaterialDialogTransitions);
  }

  Widget _buildMaterialDialogTransitions(
      BuildContext context,
      Animation<double> animation,
      Animation<double> secondaryAnimation,
      Widget child) {
    // 使用缩放动画
    return ScaleTransition(
      scale: CurvedAnimation(
        parent: animation,
        curve: Curves.easeOut
      ),
      child: child,
    );
  }

  Future<bool> showDeleteConfirmDialogWithState() {
    bool _withTree = false; // 记录复选框是否选中
    return showDialog(
        context: context,
    builder: (context) {
          return AlertDialog(
            title: Text("提示"),
            content: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.min,
              children: [
                Text("您确定删除当前文件吗"),
                Row(
                  children: [
                    Text("同时删除子目录"),
                    /*DialogCheckbox(
                      value: _withTree, // 默认不选中
                      onChanged: (bool value) {
                        // 更新选中状态
                        _withTree = !_withTree;
                      },
                    )*/
                    //  使用 StatefulBuilder
                    /*StatefulBuilder(
                      builder: (context, _setState) {
                        return Checkbox(
                          value: _withTree, // 默认不选中
                          onChanged: (bool value) {
                            // _setState 方法实际就是该 StatefulWidget 的 setState 方法，调用后 builder 会被重新调用
                            _setState((){
                              _withTree = !_withTree;
                            });
                          },
                        );
                      },
                    )*/
                    // 使用 setState 的原理 markNeedsBuild, 下面这种写法会导致整个对话框 UI 组件 rebuild
                    /*Checkbox(
                      value: _withTree,
                      onChanged: (bool value) {
                        // 此时 context 为对话框 UI 的根 Element， 我们直接将对话框 UI 对应的 Element 标记为 dirty
                        (context as Element).markNeedsBuild();
                        _withTree = !_withTree;
                      },
                    )*/
                    //  使用 Builder 方法缩小 context 的范围
                    Builder(
                      builder: (BuildContext context) {
                        return Checkbox(
                          value: _withTree,
                          onChanged: (bool value) {
                            (context as Element).markNeedsBuild();
                            _withTree = !_withTree;
                          },
                        );
                      },
                    )
                  ],
                ),
              ],
            ),
            actions: [
              FlatButton(
                child: Text("取消"),
                onPressed: () => Navigator.of(context).pop(),
              ),
              FlatButton(
                child: Text("删除"),
                onPressed: () {
                  // 将选中状态返回
                  Navigator.of(context).pop(_withTree);
                },
              )
            ],
          );
    });
  }

  // 底部弹出菜单
  Future<int> _showModelBottomSheet() {
    return showModalBottomSheet(
        context: context,
        builder: (BuildContext context) {
          return ListView.builder(
              itemCount: 30,
              itemBuilder: (BuildContext context, int index) {
                return ListTile(
                  title: Text("$index"),
                  onTap: () => Navigator.of(context).pop(index),
                );
              });
        });
  }

  showLoadingDialog() {
    showDialog(
      context: context,
      barrierDismissible: false,  // 点击遮罩不关闭对话框
      builder: (context) {
       return UnconstrainedBox(
          constrainedAxis: Axis.vertical,
          child: SizedBox(
            width: 210,
            child: AlertDialog(
              content: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  CircularProgressIndicator(),
                  Padding(
                    padding: const EdgeInsets.only(top: 26.0),
                    child: Text("正在加载，请稍候"),
                  )
                ],
              ),
            ),
          ),
        );
      }
    );
  }

  Future<DateTime> _showDatePicker() {
    var date = DateTime.now();
    return showDatePicker(context: context, initialDate: date, firstDate: date, lastDate: date.add(
      Duration(days: 30)  // 未来 30 天可选
    ));
  }
}

enum DialogType{
  _AlertDialog,
  _SimpleDialog,
  _ListDialog
}

const String alertDialog =
    """
    Future<bool> showDeleteConfirmDialog() {
      return showDialog<bool>(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text("提示"),
            content: Text("您确定要删除当前文件吗?"),
            actions: <Widget>[
              FlatButton(
                child: Text("取消"),
                onPressed: () => Navigator.of(context).pop(), // 关闭对话框
              ),
              FlatButton(
                child: Text("删除"),
                onPressed: () {
                  //关闭对话框并返回true
                  Navigator.of(context).pop(true);
                },
              ),
            ],
          );
        },
      );
    }
    """;

const String simpleDialog =
    """
      Future<void> showSimpleDialog() async {
    int i = await showDialog(
        context: context,
        builder: (BuildContext context) {
          return SimpleDialog(
            title: const Text('请选择语言'),
            children: [
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, 1);
                },
                child: Padding(
                  padding: const EdgeInsets.symmetric(vertical: 6),
                  child: const Text("中文简体"),
                ),
              ),
              SimpleDialogOption(
                onPressed: () {
                  Navigator.pop(context, 2);
                },
                child: Padding(
                  padding: const EdgeInsets.symmetric(vertical: 6),
                  child: const Text("美国英语"),
                ),
              )
            ],
          );
        });
  }
    """;

const String listDialog =
    """
    Future<void> showListDialog() async {
    int index = await showDialog<int>(
      context: context,
      builder: (BuildContext context) {
        var child = Column(
          children:<Widget>[
            ListTile(title: Text("请选择"),),
            Expanded(
              child: ListView.builder(
                  itemCount: 30,
                  itemBuilder: (BuildContext context, int index) {
                    return ListTile(
                      title: Text("\$index"),
                      onTap: ()=> Navigator.of(context).pop(index),
                    );
                  }),
            )
          ],
        );
        // 使用 AlertDialog 会报错
    //        return AlertDialog(content: child);
            return Dialog(child: child);
          });
        if (index != null) {
          print("点击了：\$index");
        }
      }
    }
    """;


// 对话框状态管理，单独封装一个内部管理选中状态的复选框组件
// 这个方法缺点明显，每个需要状态管理的组件都需要封装一下
class DialogCheckbox extends StatefulWidget {
  DialogCheckbox({
    Key key,
    this.value,
    @required this.onChanged
});
  final ValueChanged<bool> onChanged;
  final bool value;

  @override
  _DialogCheckboxState createState() => _DialogCheckboxState();
}

class _DialogCheckboxState extends State<DialogCheckbox> {
  bool value;

  @override
  void initState() {
    value = widget.value;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Checkbox(
      value: value,
      onChanged: (v) {
        // 将选中状态通过事件的形式抛出
        widget.onChanged(v);
        setState(() {
          // 更新自身选中状态
          value = v;
        });
      },
    );
  }
}

// 对话框状态管理，抽离出一个组件创建 StatefulWidget 的上下文
class StatefulBuilder extends StatefulWidget {
  const StatefulBuilder({
    Key key,
    @required this.builder
  }): assert(builder != null),
      super(key: key);
  final StatefulWidgetBuilder builder;

  @override
  State<StatefulWidget> createState() => _StatefulBuilderState();
}

class _StatefulBuilderState extends State<StatefulBuilder> {
  @override
  Widget build(BuildContext context) => widget.builder(context, setState);
}