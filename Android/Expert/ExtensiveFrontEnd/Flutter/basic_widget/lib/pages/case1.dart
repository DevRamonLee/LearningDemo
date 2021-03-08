import 'package:flutter/material.dart';

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
      // padding all sides.
      padding: const EdgeInsets.all(32.0),
      child: Text('''
      One of the most important human emotions is love,However there are many different kinds of love. Romantic love is certainly one of the most interesting kind of love,It can be beautiful,wonderful,and heartbreaking,sometimes all at the same time.Romantic love can make our lives full and meaningful,but it can also be an escape from loneliness and suffering.If romantic love has a purpose,neither psychology nor biology has discovered it.However,throughout history,philosophers have offered opinions about it.The Greek philosopher,Plato,said love makes us complete.He relates a comic story in which humans originally had 4 arms,4 legs and 2 faces.Then,when they angered the Gods,they were cut in half.Since then every person has been searching for their soulmate,the ohter half of his or her self.Another philosopher believed that love is an illusion.In his view,people fall in love because they believe the other person can make them happy.In fact,this is just an illusion designed to make us have children.Once we have children,we are right back to where we were,still searching for happiness.For nature this is a success,because we have childre to maintain our species.But it leaves us still searching for something more.In Buddhism,romantic love is seen as an attempt to sastify our desires.These desires are a defect,something we need to overcome.The way to free ourselves from suffering is to remove desire.Once free of desires we can reach a state of peace and wisdom.This state of being is called nirvana.
      '''),
    );
       return new Scaffold(
          appBar: AppBar(
            title: Text('Top pool'),
          ),
          // 为了防止内容过多，把组件放到 ListView 里
          body: ListView(
            children: <Widget>[
              Image.asset(
                'images/c0.jpg',
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
        );
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
