
- [ButterKnife github ��ַ](https://github.com/JakeWharton/butterknife)
- [ButterKnife �ĵ�](http://jakewharton.github.io/butterknife/)
- [Android Butterknife�����͵��� ʹ�÷����ܽ�](https://www.jianshu.com/p/2967d0342d80)

## ���
Ϊ�˼���Ƶ���ĵ���`findViewById(R.id...)`������һЩע���ܣ����Լ����ǵĴ��룬�����רע��ʵ�ʵĹ��ܿ�����

ʹ��ע��ķ�ʽ��View���ֶ��뷽�������������ķ�ʽ���԰��������� findViewById ���� setOnClickListener �������Ƶ�ģ����롣���������ļ������ã�

- ���ֶ���ʹ�� @BindView ���� findViewById ��ʹ�á�
- �������ͼ���鵽һ���б��У�ʹ�� action��setters������ properties һ�β������е���ͼ��
- �ڷ�����ʹ�� @OnClick �����������Լ����ڲ��������ʹ�á�
- ���ֶ�������ע����Լ��� getResources ��ʹ�á�

ʹ�� `@BindView` �� `id` ע���ֶ����Զ�Ѱ�Һ�ӳ����ص���ͼ�Ͳ����ļ��е������
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

> **ע�⣺ButterKnife �󶨵��ֶβ����� private ���� static ����** ���� Activity ������ setContentView ֮���Ƽ��� BaseActivity ����ɰ󶨣�����̳С�

**ButterKnife ����ȥִ��Ѱ����ͼ�Ĺ����Ĵ��루����ʱ���ɣ���������������ʱȥʹ�û�Ӱ��Ч�ʵķ��䣨EventBus ��δʹ�� Index ��ʱ�����ʹ�õķ��䣩**������Ĵ������ɵĴ���������£�
```
public void bind(ExampleActivity activity) {
  activity.subtitle = (android.widget.TextView) activity.findViewById(2130968578);
  activity.footer = (android.widget.TextView) activity.findViewById(2130968579);
  activity.title = (android.widget.TextView) activity.findViewById(2130968577);
}
```

#### ע���

- 1���� `Activity` ���а� ��`ButterKnife.bind(this);`������ `setContentView();`֮��󶨣��Ҹ��� `bind `�󶨺����಻��Ҫ�� `bind`��
- 2���ڷ�Activity �ࣨeg��Fragment��ViewHold���а󶨣� `ButterKnife.bind(this��view);`�����this�����滻��`getActivity����`��
- 3���� `Activity` �в���Ҫ������������Fragment �б�����onDestroyView()������������
- 4��ʹ�� `ButterKnife` ���εķ����Ϳؼ��������� `private or static` ���Σ�����ᱨ������:` @BindView fields must not be private or static. (com.zyj.wifi.ButterknifeActivity.button1)`
- 5��`setContentView()` ����ͨ��ע��ʵ�֡�����������Щע���ܿ��ԣ�
- 6��ʹ��ActivityΪ����ͼ���������ʱ�������ʹ������ `MVC `�����ģʽ������� `Activity` ���� `ButterKnife.bind(this, activity)` ������ `Controller`��
- 7��ʹ�� `ButterKnife.bind(this��view)` ��һ��`view`���ӽڵ��ֶΡ���������� `View` �Ĳ���������Զ��� `view`�Ĺ��췽���� ʹ����`inflate`,��������̵��ô˷��������ߣ���XML inflate�����Զ���view���Ϳ����� `onFinishInflate` �ص�������ʹ������

���ϼ������Լ��飺���ߣ�Donkor ���ӣ�https://www.jianshu.com/p/2967d0342d80

## �� AndroidStudio ������

#### Gradle ������
```
dependencies {
  implementation 'com.jakewharton:butterknife:9.0.0-rc3'
  annotationProcessor 'com.jakewharton:butterknife-compiler:9.0.0-rc3'
}
```
> Error: Static interface methods are only supported starting with Android N (--min-api 24): void butterknife.Unbinder.lambda$static$0()

���汨��Ľ���������������´��룺

```
android {
...........................
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```
> ButterKnife ������־ (https://github.com/JakeWharton/butterknife/blob/master/CHANGELOG.md��

�����ʹ�õ��� Kotlin����Ҫ�� `kapt` �滻 `annotationProcessor`

#### ��Butter Knife ��Ϊ Library projects ʹ��
1.��Ӳ������� buildscript �С�
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
2.�� module �� apply
```
apply plugin: 'com.android.library'
apply plugin: 'com.jakewharton.butterknife'
```
3.ȷ����ע����ʹ�� `R2`��� `R`
```
class ExampleActivity extends Activity {
  @BindView(R2.id.user) EditText username;
  @BindView(R2.id.pass) EditText password;
...
}
```

## ButterKnife ����

���»������������� `com.jakewharton:butterknife:7.0.1` �汾�������汾Ӧ��Ҳ�ǿ��Եġ�
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

## ����Դ
ʹ�� @BindBool, @BindColor, @BindDimen, @BindDrawable, @BindInt, @BindString ����Ԥ�������Դ��
```
class ExampleActivity extends Activity {
  @BindString(R.string.title) String title;
  @BindDrawable(R.drawable.graphic) Drawable graphic;
  @BindColor(R.color.red) int red; // int or ColorStateList field
  @BindDimen(R.dimen.spacer) Float spacer; // int (for pixel size) or float (for exact value) field
  // ...
}
```

## Fragment �а�

ͨ���ṩ�� View ���������������Ķ�����ִ�� binding ������Fragment �� Activity ����ͼ�������ڲ�ͬ���� `onCreateView` �а󶨵� View ��Ҫ��`onDestroyView` ������Ϊ null��Butter Knife ����һ�� `Unbinder` ʵ��������� `bind`������ʱ��Unbinder ����� unbind ���������ͷŵ��󶨵���ͼ��

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
        // �� fragment ���н��
        unbinder.unbind();
  }
}
```

## �� ViewHolder

�� list �� adapter �м� view holder ģʽ��

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

## ��ͼ����

����԰Ѷ����ͼ���鵽һ�� `List` ���� `Array`
```
@BindViews({ R.id.first_name, R.id.middle_name, R.id.last_name })
List<EditText> nameViews;
```
`apply`�����������һ���б��еĶ���������в���
```
ButterKnife.apply(nameViews, DISABLE);
ButterKnife.apply(nameViews, ENABLED, false);
```
`Action` �� `Setter` interfaces ����ָ���򵥵���Ϊ
```
static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
  @Override public void apply(View view, int index) {
    view.setEnabled(false);
  }
};
//����ĵڶ������ã����Դ���
static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
  @Override public void set(View view, Boolean value, int index) {
    view.setEnabled(value);
  }
};
```

һ��Android �� `Property`Ҳ���Ա�`apply`��������
```
ButterKnife.apply(nameViews, View.ALPHA, 0.0f);
```

## �󶨼���

������Ҳ�����Զ����õ�������
```
@OnClick(R.id.submit)
public void submit(View view) {
  // TODO submit data to server...
}
```
���������������в����ǿ�ѡ��
```
@OnClick(R.id.submit)
public void submit() {
  // TODO submit data to server...
}
```
����һ���ض������ͣ����ᱻ�Զ�ӳ��
```
@OnClick(R.id.submit)
public void sayHi(Button button) {
  button.setText("Hello!");
}
```
�ڵ�������ָ�����ID�Խ��г����¼�����
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
�Զ�����ͼ���Բ�ָ�� id ���Լ��ļ�����
```
public class FancyButton extends Button {
  @OnClick
  public void onClick() {
    // TODO do something!
  }
}
```

## ��ѡ��
Ĭ������£���Ҫ `@Bind` ���������󶨡� ����Ҳ���Ŀ����ͼ�����׳��쳣��

Ҫ��ֹ�׳��쳣��������ѡ�󶨣����Խ� `@Nullable ` ע����ӵ��ֶλ� `@Optional` ע����ӵ�������

> ע�⣺�κ���Ϊ@Nullable��ע�Ͷ��������ֶΡ� ����ʹ��Android�ġ�support-annotations�����е�@Nullableע�͡�

```
@Nullable @BindView(R.id.might_not_be_there) TextView mightNotBeThere;

@Optional @OnClick(R.id.maybe_missing) void onMaybeMissingClicked() {
  // TODO ...
}
```

## �󶨼������ص��¼�

ÿ����������Ĭ�ϵĻص�������Ҳ����ʹ�� `callback` ������ָ�����Ļص���
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

## ButterKnife zelezny �����װ

File - Settings - Plugins ���� zelezny Ȼ��װ����װ��ɺ����� Android Studio.

ʹ�õ�ʱ�򣬽�������ƶ��� `setContentView(R.layout.activity_main);` �� `R.layout.activity_main`,Ȼ���Ҽ� Generate,�������Ϳ��Կ��� `Generate butterKnife injection`�˵��ˣ�������Ҫ�������ɡ�