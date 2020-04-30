import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class BasicWidget extends StatefulWidget {
  BasicWidget(Key key) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _BasicWidgetState();
  }
}

class _BasicWidgetState extends State<BasicWidget>{
  var editController = TextEditingController();
  String _operation = "No Gesture detected";

  TapGestureRecognizer _tapRecognizer = TapGestureRecognizer();

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
        child: new Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Text(
              'Text Widget',
              maxLines: 1, // 最多多少行
              overflow: TextOverflow.ellipsis, // 溢出显示 ...
              textScaleFactor: MediaQueryData().textScaleFactor,  // 缩放因子，相当于在 style 中指定 fontSize
              // 添加样式
              style: TextStyle(
                  decoration: TextDecoration.underline, // 添加下划线，一般使用 none
                  decorationStyle: TextDecorationStyle.dashed,  // 虚线
                  background: new Paint()..color = Colors.pink, // 设置背景颜色
                  fontSize: 30.0, // 文字大小
                  color: Colors.blue,
                  fontWeight: FontWeight.bold),
            ),
            Text.rich(TextSpan( // 富文本
              children:[
                TextSpan(
                  text: "Site:"
                ),
                TextSpan(
                  text:"flutter.com",
                  style: TextStyle(
                    color: Colors.blue
                  ),
                  recognizer: _tapRecognizer..onTap = (){
                    print("Tap link");
                  },
                )
              ]
            )),

            DefaultTextStyle(   //  指定文本默认样式
              style: TextStyle(
                color: Colors.red,
                fontSize: 20.0
              ),
              textAlign: TextAlign.center,
              child: Column(
                children: <Widget>[
                  Text("Hello Flutter"),
                  Text("I am Ramon"),
                  Text("I like programing",
                    style: TextStyle(
                      inherit: false, // 不继承默认样式
                      color: Colors.blue
                    ),
                  )
                ],
              ),
            ),
            RaisedButton.icon(  // 带 icon 的 button
              icon:Icon(Icons.send),
              label: Text("RaisedButton"),
              onPressed: (){},
            ),
            FlatButton(   // 自定义 Button 外观
              color: Colors.blue,
              highlightColor: Colors.blue[700],
              colorBrightness: Brightness.dark, // 指定按钮主题，设置文字颜色为浅色
              splashColor: Colors.grey,
              child: Text("FlatButton"),
              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
              onPressed: (){},
            ),
            OutlineButton(
              child: Text("OutlineButton"),
              onPressed: (){},
            ),
            IconButton(
              icon: Icon(Icons.thumb_up),
              onPressed: (){},
            ),

            /*
                  Image.asset(name);
                  Image.file(file);
                  Image.memory(bytes);
                  Image.network(src);
               */
            Image.network(
              "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg",
              width: 200.0,
              height: 100.0,
              fit: BoxFit.fill,
            ),

            Image.asset(
              'images/c0.jpg',
              width: 200,
              height: 100,
              fit: BoxFit.cover,
            ),
            // 实现圆形图片
            Container(
              width: 150,
              height: 150,
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(75),
                  image: DecorationImage(
                      image: NetworkImage(
                        'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg',
                      ),
                      fit: BoxFit.cover)),
            ),

            // 使用 ClipOval 实现圆形图片
            ClipOval(
              child: Image.network(
                'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg',
                width: 100,
                height: 100,
                fit: BoxFit.cover,
              ),
            ),
            // FadeInImage 增加渐入效果和 placeholder
            FadeInImage.assetNetwork(
                placeholder: 'loading',
                image:
                'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg'),
            // 文本输入框
            TextField(
              controller: editController,
            ),
            RaisedButton(
                child: Text("click"),
                onPressed: () {
                  print("text inputted is ${editController.text}");
                  showDialog(
                    // 第一个 context 是参数名字，第二个 context 是 State 的成员变量
                      context: context,
                      builder: (_) {
                        return AlertDialog(
                          // dialog 的内容
                          content: Text(editController.text),
                          // action 设置 dialog 的按钮
                          actions: <Widget>[
                            FlatButton(
                              child: Text("Ok"),
                              // 用户点击按钮后，关闭弹框
                              onPressed: () => Navigator.pop(context),
                            )
                          ],
                        );
                      });
                }),
            Container(
              child: Text('Container'),
              padding: EdgeInsets.all(8.0),
              margin: EdgeInsets.all(4.0),
              width: 80.0,
              decoration: BoxDecoration(
                  color: Colors.yellow,
                  borderRadius: BorderRadius.all(Radius.circular(10))),
            ),
            Padding(
              child: Text('Padding'),
              padding: EdgeInsets.all(20),
            ),
            // Center 组件的使用
            Container(
              padding: EdgeInsets.all(8.0),
              margin: EdgeInsets.all(4.0),
              width: 200.0,
              decoration: BoxDecoration(
                  color: Colors.red,
                  borderRadius: BorderRadius.all(Radius.circular(10))),
              // 把文本放到 Container 中间
              child: Center(
                child: Text('Center widget'),
              ),
            ),
            Flex(
              direction: Axis.horizontal,
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new Flexible(
                  flex: 2,
                  fit: FlexFit.loose,
                  child: new Container(
                    color: Colors.blue,
                    height: 60.0,
                    alignment: Alignment.center,
                    child: const Text(
                      'left',
                      textAlign: TextAlign.center,
                      style: TextStyle(color: Colors.black),
                      textDirection: TextDirection.ltr,
                    ),
                  ),
                ),
                new Flexible(
                    flex: 1,
                    fit: FlexFit.loose,
                    child: new Container(
                      color: Colors.red,
                      height: 60.0,
                      alignment: Alignment.center,
                      child: const Text(
                        'right',
                        textAlign: TextAlign.center,
                        style: TextStyle(color: Colors.black),
                        textDirection: TextDirection.ltr,
                      ),
                    )),
              ],
            ),
            Row(
              children: <Widget>[
                Expanded(
                  // 占一行的 2/3
                  flex: 2,
                  child: RaisedButton(
                    child: Text('btn1'),
                  ),
                ),
                Expanded(
                  // 占一行的 1/3
                  flex: 1,
                  child: RaisedButton(
                    child: Text('btn2'),
                  ),
                ),
              ],
            ),
            Container(
              width: double.infinity,
              height: 300,
              child: Stack(
                // Alignment 取值范围为 [-1, 1], Stack 中心为 (0, 0)
                // 下面设置 (-0.5, -0.5) 后，可以让文本对齐到 Container 的 1/4 处
                alignment: const Alignment(0.5, 0.5), // 指定未定位组件的位置
                fit:StackFit.expand,  // 未定位组件占满 Stack 整个空间
                children: <Widget>[
                  Container(
                    child: Text("Hello world",style: TextStyle(color: Colors.white)),
                    color: Colors.red,
                  ),
                  Positioned(
                    left: 0,
                    top: 0,
                    child: Text('Stack text 1'),
                  ),
                  Positioned(
                    right: 0,
                    top: 0,
                    child: Text('Stack text 2'),
                  ),
                  Align(
                    alignment: Alignment.center,
                    child: Icon(Icons.home, color: Colors.white,size: 40),
                  ),
                ],
              ),
            ),
            // 手势操作
            GestureDetector(
              child: Container(
                alignment:Alignment.center,
                width: 200,
                height:200,
                color:Colors.blue,
                child: Text(
                    _operation,
                    style: TextStyle(
                        color: Colors.white
                    )
                ),
              ),
              onTap: ()=>updateText('Tap'), // 点击
              onDoubleTap: ()=>updateText('DoubleTab'),
              onLongPress: ()=>updateText('LongPress'),
            ),
            // AspecRatio 约束子元素的宽高比例
            Container(
              width: 400,
              child:AspectRatio(
                aspectRatio: 16.0 / 9.0, // 设置宽高比为 16 ：9
                child: Image.asset('images/c0.jpg', fit: BoxFit.cover,),
              ),
            ),
            SizedBox(
              height: 210,
              child: Card(
                elevation: 15.0,  // 设置阴影
                shape: const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(14.0))),
                child: new Column(
                  children: <Widget>[
                    new ListTile(
                      title: Text('标题', style: TextStyle(
                          fontWeight: FontWeight.w600
                      ),),
                      subtitle: Text('子标题'),
                      leading: Icon(
                        Icons.restaurant,
                        color: Colors.blue[500],
                      ),
                    ),
                    Divider(),
                    ListTile(
                      title: Text('内容一', style: TextStyle(fontWeight: FontWeight.w500),),
                      leading: Icon(Icons.contact_phone, color:Colors.blue[500],),
                    ),
                    ListTile(
                      title: Text('内容二', style: TextStyle(fontWeight: FontWeight.w500),),
                      leading: Icon(Icons.contact_mail, color:Colors.blue[500],),
                    )
                  ],
                ),
              ),
            ),
            SizedBox(
              height: 300.0,
              child: Wrap(
                children: <Widget>[
                  createBook('斗罗大陆'),
                  createBook('斗破苍穹'),
                  createBook('盘龙'),
                  createBook('遮天'),
                  createBook('神墓'),
                  createBook('诛仙'),
                  createBook('凡人修仙传')
                ],
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget createBook(String name) {
    return Container(
      margin: EdgeInsets.only(left: 10,top: 10, right: 10),
      child: Text(name ,style: TextStyle(color: Colors.blue, fontWeight: FontWeight.w600),),
      padding: EdgeInsets.all(7),
      decoration: BoxDecoration(
          border:Border.all(
              color: Colors.blue,
              width: 2.0
          ),
          color: Colors.white,
          borderRadius: BorderRadius.all(Radius.circular(10))
      ),
    );
  }

  void updateText(String text) {
    // 更新事件名字
    setState((){
      _operation = text;
    });
  }

  @override
  void dispose() {
    super.dispose();
    editController.dispose();
  }
}