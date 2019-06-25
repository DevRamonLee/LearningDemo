import 'package:flutter/material.dart';

// 基本控件
void main() => runApp(MyBasicWidget());

// 布局综合案例1
//void main() => runApp(MyAppOne());

// 布局综合案例2
//void main() => runApp(MyAppTwo());

// 布局综合案例1
class MyAppOne extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    final titleSection = _TitleSection(
        'Oeschinen Lake Campground', 'Kandersteg,Switzerland', 41);
    Widget buttonSection = Container(
      child: Row(
        // 沿着水平方向平均放置
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          _buildButtonColumn(context, Icons.call, 'CALL'),
          _buildButtonColumn(context, Icons.near_me, 'ROUTE'),
          _buildButtonColumn(context, Icons.share, 'SHARE'),
        ],
      ),
    );
    final textSection = Container(
      padding: const EdgeInsets.all(32.0),
      child: Text('''
      One of the most important human emotions is love,However there are many different kinds of love. Romantic love is certainly one of the most interesting kind of love,It can be beautiful,wonderful,and heartbreaking,sometimes all at the same time.Romantic love can make our lives full and meaningful,but it can also be an escape from loneliness and suffering.If romantic love has a purpose,neither psychology nor biology has discovered it.However,throughout history,philosophers have offered opinions about it.The Greek philosopher,Plato,said love makes us complete.He relates a comic story in which humans originally had 4 arms,4 legs and 2 faces.Then,when they angered the Gods,they were cut in half.Since then every person has been searching for their soulmate,the ohter half of his or her self.Another philosopher believed that love is an illusion.In his view,people fall in love because they believe the other person can make them happy.In fact,this is just an illusion designed to make us have children.Once we have children,we are right back to where we were,still searching for happiness.For nature this is a success,because we have childre to maintain our species.But it leaves us still searching for something more.In Buddhism,romantic love is seen as an attempt to sastify our desires.These desires are a defect,something we need to overcome.The way to free ourselves from suffering is to remove desire.Once free of desires we can reach a state of peace and wisdom.This state of being is called nirvana.
      '''),
    );
    return MaterialApp(
        title: 'Flutter UI basic',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: new Scaffold(
          appBar: AppBar(
            title: Text('Top pool'),
          ),
          // 为了防止内容过多，把组件放到 ListView 里
          body: ListView(
            children: <Widget>[
              Image.asset(
                'images/pool.jpg',
                width: 600.0,
                height: 240.0,
                // cover 类似于 Android 中的 centerCrop
                fit: BoxFit.cover,
              ),
              titleSection,
              buttonSection,
              textSection
            ],
          ),
        ));
  }
}

// 布局综合案例1: 标题区域
class _TitleSection extends StatelessWidget {
  final String title;
  final String subtitle;
  final int starCount;

  _TitleSection(this.title, this.subtitle, this.starCount);

  @override
  Widget build(BuildContext context) {
    // 使用 Container 给内容增加 padding
    return Container(
      // 设置上下左右 padding 为 32
      padding: EdgeInsets.all(32.0),
      child: Row(
        children: <Widget>[
          // 为了让标题占满屏幕宽度的剩余控件，使用 Expanded 包起来
          Expanded(
            child: Column(
              // Column 是竖直方向的，cross 是交叉的意思，也就是设置的是水平方向
              // 的对齐，在水平方向，让文本对齐到 start
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                  padding: const EdgeInsets.only(bottom: 8.0),
                  child: Text(
                    title,
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                ),
                Text(
                  subtitle,
                  style: TextStyle(color: Colors.grey[500]),
                ),
              ],
            ),
          ),
          // Row 的第二个元素，收藏按钮
          Icon(
            Icons.star,
            color: Colors.red[500],
          ),
          Text(starCount.toString())
        ],
      ),
    );
  }
}

// 布局综合案例1: 按钮区域
Widget _buildButtonColumn(BuildContext context, IconData icon, String label) {
  final color = Theme.of(context).primaryColor;

  return Column(
    // main axis 对于 column 来说，就是指竖直方向
    // 在放置完子控件后，屏幕上可能还有一些剩余空间，min 表示尽量少占用
    // 类似 Android 的 wrap_content, 对应的还有 MainAxisSize.max
    mainAxisSize: MainAxisSize.min,
    // 沿着 main axis 居中放置
    mainAxisAlignment: MainAxisAlignment.center,
    children: <Widget>[
      Icon(icon, color: color),
      Container(
        margin: const EdgeInsets.only(top: 8.0),
        child: Text(
          label,
          style: TextStyle(
            fontSize: 12.0,
            fontWeight: FontWeight.w400,
            color: color,
          ),
        ),
      )
    ],
  );
}

// 布局综合案例2： 实现一个 ListView
class MyAppTwo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
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
    return MaterialApp(
      title: 'ListView demo',
      home: Scaffold(
        appBar: AppBar(
          title: Text('Buildings'),
        ),
        body: BuildingListView(
            buildings, (index) => debugPrint('item $index clicked')),
      ),
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

/*---------------------- 简单基本控件 -------------------------------------*/

class MyBasicWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
      title: 'Basic widget',
      home: Scaffold(
          appBar: AppBar(
            title: Text('Basic widget'),
          ),
//          body:TextWidget() // 文本 Text
//          body:ImageWidget()  // 图片 Image
//          body: ContainerWidget() // Container 布局
//          body:FlexWidget() // Flex 居中
//          body:FlexWidgetWeight() // Flex 设置权重左右为 2:1
          body:StackWidget()  // Stack 布局
          ),
    );
  }
}

// 文本 Text( 设置文字 & 文字大小 & 颜色 & 行数限制 & 文本对齐)
class TextWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Text(
      'Hello Text Widget',
      maxLines: 1,
      overflow: TextOverflow.ellipsis, // 溢出显示 ...
      // 添加样式
      style: TextStyle(
          fontSize: 30.0, // 文字大小
          color: Colors.blue,
          fontWeight: FontWeight.bold),
    );
  }
}

// 图片 Image
class ImageWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    /* return Image.network(
      "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg",
      width: 400.0,
      height: 300.0,
      fit: BoxFit.fill,
    );*/
    // FadeInImage 增加渐入效果和 placeholder
    return FadeInImage.assetNetwork(
        placeholder: 'loading',
        image:
            'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg');
  }
}

// 布局 Container( 设置边框 & padding & margin & 圆角 & 背景图)
class ContainerWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center,
      padding: const EdgeInsets.all(15.0),
      margin: const EdgeInsets.all(15.0),
      decoration: new BoxDecoration(
        border: new Border.all(
          color: Colors.red,
        ),
        image: const DecorationImage(
          image: const NetworkImage(
              'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg'),
          fit: BoxFit.contain,
        ),
        // 设置所有的圆角
//        borderRadius: const BorderRadius.all(const Radius.circular(6.0)),
        // 单独设置
        borderRadius: const BorderRadius.only(
          topLeft: const Radius.circular(3.0),
          topRight: const Radius.circular(6.0),
          bottomLeft: const Radius.circular(9.0),
          bottomRight: const Radius.circular(0.0),
        ),
      ),
      child: Text('Container demo'),
    );
  }
}

// Flex 布局1，居中

class FlexWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Flex(
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
    );
  }
}

// Flex 布局2，设置左右布局宽度比例为 2:1
class FlexWidgetWeight extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Flex(
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
                style: TextStyle(
                  color: Colors.black
                ),
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
            ))
      ],
    );
  }
}

// Stack 布局（有两种常用方式 no-positioned 和 Positioned）
class StackWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.yellow,
      height: 150.0,
      width: 500.0,
      child: new Stack(
        // 设置 no-positioned 的位置
        // Alignment 的取值为 [-1, 1],中心点位置为 (0, 0)
        alignment: const Alignment(0, 0),
        // 默认为 no-positioned
        children: <Widget>[
          new Container(
            color: Colors.blueAccent,
            height: 50.0,
            width: 100.0,
            alignment: Alignment.center,
            child: Text('unPositioned'),
          ),
          // Positioned, 可以通过 left top right bottom 设置位置
          new Positioned(
            left: 40.0,
            top:80.0,
            child: new Container(
              color: Colors.pink,
              height: 50.0,
              width: 95.0,
              alignment: Alignment.center,
              child: Text('Positioned'),
            ),
          )
        ],
      ),
    );
  }
}

// 按钮
class ButtonWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var flatBtn = FlatButton(
      onPressed: () => print('FatButton pressed'),
      child: Text('FlatButton'),
    );
    var raiseButton = RaisedButton(
      onPressed: () => print('RaiseButton pressed'),
      child: Text('RaiseButton'),
    );
    return raiseButton;
  }
}

// 文本输入框, Container、Center
class MessageForm extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MessageFormState();
  }
}

class _MessageFormState extends State<MessageForm> {
  // 设置一个 controller，通过这个 controller 拿到文本框里的内容
  var editController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    // Row、Expand 是用于布局的控件
    return Center(
      child: Container(
        child: Row(
          children: <Widget>[
            Expanded(
              child: TextField(
                controller: editController,
              ),
            ),
            RaisedButton(
              child: Text('click'),
              onPressed: () {
                showDialog(
                    // 第一个 context 是参数名，第二个 context 是 State 的成员变量
                    context: context,
                    builder: (_) {
                      return AlertDialog(
                        content: Text(editController.text),
                        actions: <Widget>[
                          FlatButton(
                            child: Text('Ok'),
                            // 点击按钮后，关闭弹框
                            onPressed: () => Navigator.pop(context),
                          )
                        ],
                      );
                    });
              },
            )
          ],
        ),
        padding: EdgeInsets.all(30.0),
        margin: EdgeInsets.all(4.0),
        decoration: BoxDecoration(
          // 背景色
          color: Colors.grey,
          // 圆角
          borderRadius: BorderRadius.circular(5.0),
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
    // 手动调用 controller 的 dispose 方法释放资源
    editController.dispose();
  }
}

class ExpandWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Row(
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
    );
  }
}
