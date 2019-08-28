
- [ButterKnife github 地址](https://github.com/JakeWharton/butterknife)
- [ButterKnife 文档](http://jakewharton.github.io/butterknife/)
- [Android Butterknife（黄油刀） 使用方法总结](https://www.jianshu.com/p/2967d0342d80)

## 简介
为了减少频繁的调用`findViewById(R.id...)`，采用一些注入框架，可以简化我们的代码，让你更专注于实际的功能开发。

使用注解的方式绑定View和字段与方法，这种声明的方式可以帮助你生成 findViewById 或者 setOnClickListener 这种类似的模板代码。下面是它的几个作用：

- 在字段上使用 @BindView 减少 findViewById 的使用。
- 将多个视图分组到一个列表中，使用 action、setters、或者 properties 一次操作所有的视图。
- 在方法上使用 @OnClick 或者其他可以减少内部监听类的使用。
- 在字段上声明注解可以减少 getResources 的使用。

使用 `@BindView` 和 `id` 注解字段来自动寻找和映射相关的视图和布局文件中的组件。
```
class ExampleActivity extends Activity {
  @BindView(R.id.title) TextView title;
  @BindView(R.id.subtitle) TextView subtitle;
  @BindView(R.id.footer) TextView footer;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    ButterKnife.bind(this);
    // TODO Use fields...
  }
}
```

> **注意：ButterKnife 绑定的字段不能用 private 或者 static 修饰** 。绑定 Activity 必须在 setContentView 之后，推荐在 BaseActivity 中完成绑定，子类继承。

**ButterKnife 生成去执行寻找视图的工作的代码（编译时生成），而不是在运行时去使用会影响效率的反射（EventBus 在未使用 Index 的时候就是使用的反射）**。上面的代码生成的代码大致如下：
```
public void bind(ExampleActivity activity) {
  activity.subtitle = (android.widget.TextView) activity.findViewById(2130968578);
  activity.footer = (android.widget.TextView) activity.findViewById(2130968579);
  activity.title = (android.widget.TextView) activity.findViewById(2130968577);
}
```

#### 注意点

- 1、在 `Activity` 类中绑定 ：`ButterKnife.bind(this);`必须在 `setContentView();`之后绑定；且父类 `bind `绑定后，子类不需要再 `bind`。
- 2、在非Activity 类（eg：Fragment、ViewHold）中绑定： `ButterKnife.bind(this，view);`这里的this不能替换成`getActivity（）`。
- 3、在 `Activity` 中不需要做解绑操作，在Fragment 中必须在onDestroyView()中做解绑操作。
- 4、使用 `ButterKnife` 修饰的方法和控件，不能用 `private or static` 修饰，否则会报错。错误:` @BindView fields must not be private or static. (com.zyj.wifi.ButterknifeActivity.button1)`
- 5、`setContentView()` 不能通过注解实现。（其他的有些注解框架可以）
- 6、使用Activity为根视图绑定任意对象时，如果你使用类似 `MVC `的设计模式你可以在 `Activity` 调用 `ButterKnife.bind(this, activity)` ，来绑定 `Controller`。
- 7、使用 `ButterKnife.bind(this，view)` 绑定一个`view`的子节点字段。如果你在子 `View` 的布局里或者自定义 `view`的构造方法里 使用了`inflate`,你可以立刻调用此方法。或者，从XML inflate来的自定义view类型可以在 `onFinishInflate` 回调方法中使用它。

以上几点来自简书：作者：Donkor 链接：https://www.jianshu.com/p/2967d0342d80

## 在 AndroidStudio 中引入

#### Gradle 中引入
```
dependencies {
  implementation 'com.jakewharton:butterknife:9.0.0-rc3'
  annotationProcessor 'com.jakewharton:butterknife-compiler:9.0.0-rc3'
}
```
> Error: Static interface methods are only supported starting with Android N (--min-api 24): void butterknife.Unbinder.lambda$static$0()

上面报错的解决方法是引入如下代码：

```
android {
...........................
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```
> ButterKnife 更新日志 (https://github.com/JakeWharton/butterknife/blob/master/CHANGELOG.md）

如果你使用的是 Kotlin，是要用 `kapt` 替换 `annotationProcessor`

#### 把Butter Knife 作为 Library projects 使用
1.添加插件到你的 buildscript 中。
```
buildscript {
  repositories {
    mavenCentral()
   }
  dependencies {
    classpath 'com.jakewharton:butterknife-gradle-plugin:9.0.0-rc3'
  }
}
```
2.在 module 中 apply
```
apply plugin: 'com.android.library'
apply plugin: 'com.jakewharton.butterknife'
```
3.确保在注解中使用 `R2`替代 `R`
```
class ExampleActivity extends Activity {
  @BindView(R2.id.user) EditText username;
  @BindView(R2.id.pass) EditText password;
...
}
```

## ButterKnife 混淆

以下混淆规则适用于 `com.jakewharton:butterknife:7.0.1` 版本，其他版本应该也是可以的。
```
#Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
```

## 绑定资源
使用 @BindBool, @BindColor, @BindDimen, @BindDrawable, @BindInt, @BindString 来绑定预定义的资源。
```
class ExampleActivity extends Activity {
  @BindString(R.string.title) String title;
  @BindDrawable(R.drawable.graphic) Drawable graphic;
  @BindColor(R.color.red) int red; // int or ColorStateList field
  @BindDimen(R.dimen.spacer) Float spacer; // int (for pixel size) or float (for exact value) field
  // ...
}
```

## Fragment 中绑定

通过提供根 View 对象，你可以在任意的对象里执行 binding 操作。Fragment 和 Activity 的视图生命周期不同，在 `onCreateView` 中绑定的 View 需要在`onDestroyView` 中设置为 null。Butter Knife 返回一个 `Unbinder` 实例当你调用 `bind`方法的时候。Unbinder 对象的 unbind 方法可以释放掉绑定的视图。

```
public class FancyFragment extends Fragment {
  @BindView(R.id.button1) Button button1;
  @BindView(R.id.button2) Button button2;
  
  private Unbinder unbinder;
  
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fancy_fragment, container, false);
    unbinder = ButterKnife.bind(this, view);
    // TODO Use fields...
    return view;
  }
  
  ...
  @Override
  public void onDestroyView() {
        super.onDestroyView();
        // 和 fragment 进行解绑
        unbinder.unbind();
  }
}
```

## 绑定 ViewHolder

在 list 的 adapter 中简化 view holder 模式。

```
public class MyAdapter extends BaseAdapter {
  @Override public View getView(int position, View view, ViewGroup parent) {
    ViewHolder holder;
    if (view != null) {
      holder = (ViewHolder) view.getTag();
    } else {
      view = inflater.inflate(R.layout.whatever, parent, false);
      holder = new ViewHolder(view);
      view.setTag(holder);
    }

    holder.name.setText("John Doe");
    // etc...

    return view;
  }

  static class ViewHolder {
    @BindView(R.id.title) TextView name;
    @BindView(R.id.job_title) TextView jobTitle;

    public ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }
  }
}
```

## 视图分组

你可以把多个视图分组到一个 `List` 或者 `Array`
```
@BindViews({ R.id.first_name, R.id.middle_name, R.id.last_name })
List<EditText> nameViews;
```
`apply`方法允许你对一个列表中的多个方法进行操作
```
ButterKnife.apply(nameViews, DISABLE);
ButterKnife.apply(nameViews, ENABLED, false);
```
`Action` 和 `Setter` interfaces 允许指定简单的行为
```
static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
  @Override public void apply(View view, int index) {
    view.setEnabled(false);
  }
};
//上面的第二个调用，可以传参
static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
  @Override public void set(View view, Boolean value, int index) {
    view.setEnabled(value);
  }
};
```

一个Android 的 `Property`也可以被`apply`方法调用
```
ButterKnife.apply(nameViews, View.ALPHA, 0.0f);
```

## 绑定监听

监听器也可以自动配置到方法上
```
@OnClick(R.id.submit)
public void submit(View view) {
  // TODO submit data to server...
}
```
监听器方法的所有参数是可选的
```
@OnClick(R.id.submit)
public void submit() {
  // TODO submit data to server...
}
```
定义一个特定的类型，它会被自动映射
```
@OnClick(R.id.submit)
public void sayHi(Button button) {
  button.setText("Hello!");
}
```
在单个绑定中指定多个ID以进行常见事件处理。
```
@OnClick({ R.id.door1, R.id.door2, R.id.door3 })
public void pickDoor(DoorView door) {
  if (door.hasPrizeBehind()) {
    Toast.makeText(this, "You win!", LENGTH_SHORT).show();
  } else {
    Toast.makeText(this, "Try again", LENGTH_SHORT).show();
  }
}
```
自定义视图可以不指定 id 绑定自己的监听器
```
public class FancyButton extends Button {
  @OnClick
  public void onClick() {
    // TODO do something!
  }
}
```

## 可选绑定
默认情况下，需要 `@Bind` 和侦听器绑定。 如果找不到目标视图，则抛出异常。

要阻止抛出异常并创建可选绑定，可以将 `@Nullable ` 注释添加到字段或将 `@Optional` 注释添加到方法。

> 注意：任何名为@Nullable的注释都可用于字段。 鼓励使用Android的“support-annotations”库中的@Nullable注释。

```
@Nullable @BindView(R.id.might_not_be_there) TextView mightNotBeThere;

@Optional @OnClick(R.id.maybe_missing) void onMaybeMissingClicked() {
  // TODO ...
}
```

## 绑定监听器回调事件

每个监听器有默认的回调，我们也可以使用 `callback` 参数来指定它的回调。
```
@OnItemSelected(R.id.list_view)
void onItemSelected(int position) {
  // TODO ...
}

@OnItemSelected(value = R.id.maybe_missing, callback = NOTHING_SELECTED)
void onNothingSelected() {
  // TODO ...
}
```

## ButterKnife zelezny 插件安装

File - Settings - Plugins 搜索 zelezny 然后安装，安装完成后重启 Android Studio.

使用的时候，将鼠标光标移动到 `setContentView(R.layout.activity_main);` 的 `R.layout.activity_main`,然后右键 Generate,接下来就可以看到 `Generate butterKnife injection`菜单了，根据需要进行生成。