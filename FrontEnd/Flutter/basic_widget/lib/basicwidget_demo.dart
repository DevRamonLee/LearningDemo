import 'package:flutter/material.dart';

/// 基本组件的用法
class BasicWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _BasicWidgetState();
}

class _BasicWidgetState extends State<BasicWidget> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(title: new Text('Basic widget')),
      body: SingleChildScrollView(
        child: Container(
          child: new Column(
            children: <Widget>[
              Text(
                'Hello Text Widget',
                maxLines: 1,
                overflow: TextOverflow.ellipsis, // 溢出显示 ...
                // 添加样式
                style: TextStyle(
                    fontSize: 30.0, // 文字大小
                    color: Colors.blue,
                    fontWeight: FontWeight.bold),
              ),
              Image.network(
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg",
                width: 400.0,
                height: 300.0,
                fit: BoxFit.fill,
              ),
              // FadeInImage 增加渐入效果和 placeholder
              FadeInImage.assetNetwork(
                  placeholder: 'loading',
                  image:
                      'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg'),
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
              FlatButton(
                onPressed: () => print('FatButton pressed'),
                child: Text('FlatButton'),
              ),
              RaisedButton(
                onPressed: () => print('RaiseButton pressed'),
                child: Text('RaiseButton'),
              ),
              Flex(
                direction: Axis.horizontal,
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  new Container(
                    width: 40.0,
                    height: 60.0,
                    color: Colors.pink,
                    child: const Center(
                      child: const Text('left'),
                    ),
                  ),
                  new Container(
                    width: 80.0,
                    height: 60.0,
                    color: Colors.grey,
                    child: const Center(
                      child: const Text('middle'),
                    ),
                  ),
                  new Container(
                    width: 60.0,
                    height: 60.0,
                    color: Colors.yellow,
                    child: const Center(
                      child: const Text('right'),
                    ),
                  )
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
            ],
          ),
        ),
      ),
    );
  }
}
