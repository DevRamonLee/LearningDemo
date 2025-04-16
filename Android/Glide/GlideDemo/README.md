- [AndroidͼƬ���ؿ����ȫ�������ˣ�������ȫ���˽�Glide 4���÷�](https://blog.csdn.net/guolin_blog/article/details/78582548)


## ���

Glide ��һ����Դ��ͼƬ���ؿ⣬��ƪ���¡�����ת���Թ��ص�[����](https://blog.csdn.net/guolin_blog/article/details/78582548)����Ҫ���� Glide4 ���¼������������

- �����÷�
- �������
- �ص������
- ͼƬ�任
- �Զ���ģ��


���� Gradle���޸� `app/build.gradle` �ļ�

```
dependencies {
    implementation 'com.github.bumptech.glide:glide:4.4.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.4.0'
}
```

> ����� compiler �Ŀ⣬��������������� Generated API �ģ��������ܵ�����ʹ��

���ǻ���Ҫ����Ȩ��, �޸� `AndroidManifest.xml`

```
<uses-permission android:name="android.permission.INTERNET"/>
```

## ����ͼƬ

������ʹ�� Glide ������ͼƬ������ͼƬ��ַ `http://guolin.tech/book.png`


������дһ�����֣�����ֻ����һ�� ImageView ��һ�� Button���ܼ򵥣����ﲻ���ˣ���Ȼ�������ڴ����е����ť��������ͼƬ,����ǳ��򵥣���

```
String url = "http://guolin.tech/book.png";
Glide.with(MainActivity.this).load(url).into(imageBook);
```

> Glide ����ͼƬ���������� with() �� load() ��� into()�� ��ϸ�۲����ǵ�����Ч������ᷢ�ֵڶ������е�ʱ�򼸺�û�м���ʱ�䣬����� Glide ǿ��Ļ��湦�ܡ�

## ռλͼ

��������׼��һ��ռλͼ `loading.png`, �������޸�һ�����ǵĴ���

```
RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.loading);
Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

���Ǵ�����һ�� RequestOptions ���󣬲�Ϊ��ָ���� placehoder��Ȼ���ڼ���ͼƬ��ʱ��ʹ���� apply ������ RequestOptions �����˽�ȥ��

���д��룬���Ƿ��ֲ�û�п���ռλͼ����Ϊ Glide �Ļ�������ڵ�һ�μ��ص�ʱ��ͽ�ͼƬ��������������һ���ټ��ؾ�ֱ�Ӵӻ����ж�ȡ����Ϊ�����ٶȷǳ��죬���Կ��ܿ�����ռλͼ�������޸��´��룬�������л��ῴ��ռλͼ

```
String url = "http://guolin.tech/book.png";
RequestOptions requestOptions = new RequestOptions()
        .placeholder(R.drawable.loading)
        .diskCacheStrategy(DiskCacheStrategy.NONE);     // ָ���������

Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

> �ҽ����˻��棬����û������Ч�����ڶ��μ��ػ��Ǻܿ�

���˼���ռλͼ�������쳣ռλͼ���쳣ռλͼ��ָ����һЩԭ����ͼƬ����ʧ��ʱ��ʾ��ͼƬ, ͨ�� `error()` ָ��

```
RequestOptions requestOptions = new RequestOptions()
        .placeholder(R.drawable.loading)
        .error(R.drawable.error)
        .diskCacheStrategy(DiskCacheStrategy.NONE);     // ָ���������

Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

> Glide 4 ��ͬ�� Glide3 ������������һ�� RequestOptions ����

Glide 4 ������ `RequestOptions` ���󣬽���һϵ�е� API ���ƶ����� `RequestOptions` ���С��������ĺô��ǿ���ʹ���ǰ����߳��� `Glide` ������䣬���һ��ܽ����Լ��� API ��װ����Ϊ `RequestOptions` �ǿ�����Ϊ�������뵽�����еģ����ǿ��������·�װ

```
public class GlideUtil {
    public static void load(Context context,
                            String url,
                            ImageView imageView,
                            RequestOptions options) {
        Glide.with(context)
             .load(url)
             .apply(options)
             .into(imageView);
    }
}
```

## ָ��ͼƬ��С

ʵ���У�Glide ���������²���Ҫ����ָ��ͼƬ��С����Ϊ Glide ����� ImageView �Ĵ�С������ͼƬ�Ĵ�С����ֹ���� OOM������������Ǳ����ͼƬָ��һ���̶��Ĵ�С����Ҳ�ǿ���������

```
RequestOptions requestOptions = new RequestOptions()
        .override(400, 400);

Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

> ����ͼƬ�ὫͼƬ���س� 400 * 400 �ߴ磬������� ImageView �ĳߴ�

�������Ҫ����һ��ͼƬ��ԭͼ����ʹ�� `Target.SIZE_ORIGINAL`,�������з��յģ����ܻᵼ�� OOM

```
RequestOptions requestOptions = new RequestOptions()
        .override(Target.SIZE_ORIGINAL);

Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

## �������

Glide �Ļ����Ϊ�������֣�

- **�ڴ滺��**���ڴ滺�����Ҫ�����Ƿ�ֹӦ���ظ���ͼƬ���ݶ�ȡ���ڴ浱�С�
- **Ӳ�̻���**��Ӳ�̻������Ҫ�����Ƿ�ֹӦ���ظ�������������ط����غͶ�ȡ���ݡ�


Glide Ĭ���ǿ����ڴ滺��ģ�������ʹ�� Glide ����һ��ͼƬ֮������ͼƬ�ͻỺ�������ڴ滺���У����� RecyleView ʹ�� Glide ������ͼƬ�����Ƿ������»����ͻ�ֱ�Ӵ��ڴ��ж�ȡ���ݣ������������˼���Ч�ʡ�

���ǲ������κβ����������� Glide ���ڴ滺��Ч�������������������������Ҫ�����ڴ滺�棬Glide Ҳ�ṩ�˽ӿ�

```
RequestOptions options = new RequestOptions()
        .skipMemoryCache(true);
```

��������ʹ��ռλͼʱʹ���� `diskCacheStrategy()` ����������������� Glide ��Ӳ�̻��湦�ܣ������Խ������ֲ���

- DisCacheStrategy.NONE: ��ʾ�������κ�����
- DisCacheStrategy.DATA: ��ʾֻ����ԭʼͼƬ
- DisCacheStrategy.RESOUCE: ��ʾֻ����ת�������ͼƬ
- DisCacheStrategy.ALL: ��ʾ������ԭʼͼƬ��Ҳ����ת�������ͼƬ
- DiskCacheStrategy.AUTOMATIC�� ��ʾ��Glide����ͼƬ��Դ���ܵ�ѡ��ʹ����һ�ֻ�����ԣ�Ĭ��ѡ���

> ת�������ͼƬʲô��˼��? ���ǵ�����ʹ�� Glide ȥ����һ��ͼƬ��ʱ��Glide Ĭ�ϲ����ὫԭʼͼƬչʾ���������ǻ��ͼƬ����ѹ����ת��, ������һϵ�к�Ĳ�����ͼƬ����ת��ͼƬ��

## ָ�����ظ�ʽ

Glide ��֧�ּ��� GIF ͼƬ�ģ��� Picasso �ǲ�֧�ֵģ����� Glide ���� GIF ͼƬ�ǲ���Ҫ��д����Ĵ��룬Glide �ڲ����Զ��ж�ͼƬ�ĸ�ʽ�����滻�����ǵ� url Ϊ `"http://guolin.tech/test.gif"` �Ϳ���ֱ����ʾ GIF ͼƬ�ˡ�

������ǲ���Ҫ Glide Ϊ�����Զ��ж�ͼƬ�����ͣ����Ǿ���Ҫ����һ�ž�̬��ͼƬ������ʹ�� `asBitmap()` ������ ���������ʾֻ������ؾ�̬ͼƬ������������һ�� GIF ͼƬ����ֻ����ʾ���ĵ�һ��

```
Glide.with(this)
     .asBitmap()
     .load("http://guolin.tech/test.gif")
     .into(imageView);
```

> ע�⣺�������пӵģ�Glide3 ���� load �� asBitmap��Glide4 ���� asBitmap �� load��д�����ǻᱨ��ġ�`asGif()` ǿ��ָ�����ض�̬ͼƬ����Glide4 ����������`asFile()`������ `asDrawable()` �������ֱ�����ǿ��ָ���ļ���ʽ�ļ��غ�Drawable��ʽ�ļ���


## �ص������

#### Into ����

Glide �� `into()` �����Ǵ���һ�� ImageView �ģ����ǿ��Դ������������𣿴��ǿ϶��ģ�������Ҫ�õ��Զ��� Target ���ܡ�

����ʹ��һ���򵥵� SimpleTarget

```
SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
    @Override
    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
        imageBook.setImageDrawable(resource);
    }
};

String url = "http://guolin.tech/test.gif";

Glide.with(MainActivity.this)
        .load(url)
        .into(simpleTarget);
```

#### Preload ����

���������Ҫ��ͼƬ����һ��Ԥ���أ��ȵ���Ҫʹ�õ�ʱ��ֱ��ȥ�ӻ����ȡ�������Ǵ�������أ�Glide �������ṩ��Ԥ���ؽӿ� preload ����

`preload()` �����������������أ�һ��������������ʾ�������ͼƬ��ԭʼ�ߴ磬��һ������ͨ������ָ������ͼƬ�Ŀ�͸ߡ�

ʹ�÷ǳ��򵥣�ֱ���滻 into �����Ϳ���

```
Glide.with(this)
     .load("http://guolin.tech/book.png")
     .preload();    
```

> ������ preload ����֮���´��ڼ���ͼƬ�ͻ�ӻ�����ȡ��ȡ

#### submit ����

`submit()` ������ʵ���Ƕ�Ӧ�� Glide 3 �е� `downloadOnly()` ����,�� preload ����һ������Ҳ�����滻 into ������submint ������ͼƬ����ͼƬ������֮�����ǿ��Եõ�ͼƬ�Ĵ洢·����submit ����Ҳ��������������

- submit(): ����ԭʼ�ߴ�
- submit(int width, int height)�� ָ������ͼƬ�ĳߴ�

���� ����һ��ͼƬ������ʾ·��

```
 new Thread(
        new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://www.guolin.tech/book.png";
                    final Context context = getApplicationContext();
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(url)
                            .submit();  // submit ��������һ�� FutureTarget ����
                    final File imageFile = target.get();    // ��ȡ�ļ������û�����������������ֱ���������
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e) {

                }
            }
        }
).start();
```

���������Ĵ��룺

- `submit()` ��������Ҫ�������̵߳���, ��Ϊ FutureTarget �� get �����������߳�
- ʹ�� Application Context�����ʱ�������� Activity ��Ϊ Context �ˣ���Ϊ���� Activity �����˵����̻߳�ûִ�������ֿ��ܳ��֡�
- �� `into()` �����滻���� `submit()` ���������һ�ʹ����һ�� `asFile()` ������ָ�����ظ�ʽ


#### listener ����

listener ��������ͼƬ���ص�״̬����������ʹ�� preload �����ͼƬ��������ô֪�����سɹ�����ʧ����?��ʱ�Ϳ���ʹ�� listener�����Ļ����÷����£�

```
Glide.with(this)
     .load("http://www.guolin.tech/book.png")
     .listener(new RequestListener<Drawable>() {
         @Override
         public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
             return false;
         }

         @Override
         public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
             return false;
         }
     })
     .into(imageView);
```

- onResourceReady()����: ͼƬ���سɹ���ص���
- onLoadFailed()����: ͼƬ����ʧ�ܻ�ص���


> ע������ķ��������Ƕ������� false������������¼�û�б���������������´��ݣ���������� true �ʹ����¼���������ˣ����������´������磺������Ǹ� into ָ���� target��������� true����ô target �� onResourceReady �Ͳ���ص��ˡ�


## ͼƬ�任

ͼƬ�任����˼�� Glide �Ӽ���ԭʼͼƬ����ʾ֮ǰ���ֽ�����һЩ������Բ�ǡ�Բ�Ρ�ģ���ȡ�

���ͼƬ�任�ǳ��򵥣�ֻ��Ҫ�� `RequestOptions` �д��� `transforms()` ������������Ҫ���е�ͼƬ�任������Ϊ�������롣

```
RequestOptions options = new RequestOptions()
        .transforms(...);
Glide.with(this)
     .load(url)
     .apply(options)
     .into(imageView);
```

����Ҫ����ʲô���ı任��ͨ��ʹ�������Լ�ʵ�ֵģ�Glide �Ѿ������˼��ֳ����ı任���磺 CenterCrop��FitCenter��CircleCrop �ȡ�

���ǿ���ֱ�ӵ��÷�װ�õ� API ����ʵ�ڲ���װ�� transform��

```
RequestOptions options = new RequestOptions()
        .centerCrop();

RequestOptions options = new RequestOptions()
        .fitCenter();

RequestOptions options = new RequestOptions()
        .circleCrop();
```

���� ʵ��һ��Բ��Ч��

```
RequestOptions options = new RequestOptions()
        .circleCrop();

Glide.with(context)
        .load(url)
        .apply(options)
        .into(imageBook);
```


> [glide-transformations](https://github.com/wasabeef/glide-transformations)����ʵ���˺ܶ�ͨ�õ�ͼƬ�任Ч������ü��任����ɫ�任��ģ���任�ȵȣ�ʹ�����ǿ��Էǳ����ɵؽ��и��ָ�����ͼƬ�任��

## �Զ���ģ��



## ʹ��Generated API

Generated API �� Glide 4 ��ȫ�������һ�����ܣ����Ĺ���ԭ����ʹ��ע�⴦���� (Annotation Processor) �����ɳ�һ��API���� Application ģ���п�ʹ�ø���ʽ API һ���Ե��õ� RequestBuilder��RequestOptions �ͼ��ɿ������е�ѡ�

��ô�����е��ֿڣ��򵥵�˵������ Glide 4��Ȼ�������ṩ��һ�׺� Glide 3 һģһ������ʽ API �ӿڡ�

Generated API ������ Glide3 �÷�һ�£�������Ҫ�� Glide �滻Ϊ GlideApp �ؼ���

```
GlideApp.with(this)
        .load(url)
        .placeholder(R.drawable.loading)
        .error(R.drawable.error)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .override(Target.SIZE_ORIGINAL)
        .circleCrop()
        .into(imageView);
```

GlideApp ������Ǳ������Զ����ɵģ�Ϊ����������࣬����Ҫȷ�����ǵ���һ���Զ����ģ�飬���Ҹ��������� `@GlideModule` ע��, ������֮���� AS �� build rebuild �ͻ����� GlideApp ��

Generated API ���÷���ֹ��һ�㣬�������Զ� API ������չ������������� API

���� ��������Ҫ����Ŀ������ͼƬ�Ļ�����Զ�Ҫʹ��ԭʼͼƬ����ôÿ�μ��ص�ʱ��Ҫָ��  `diskCacheStrategy(DiskCacheStrategy.DATA)` �����Ƚ��鷳�����ǿ���ȥָ��һ���Լ��� api


�����Լ���API��Ҫ����@GlideExtension��@GlideOption������ע�⡣����һ�������Զ������չ��

```
@GlideExtension
public class MyGlideExtension {

    private MyGlideExtension() {

    }

    @GlideOption
    public static void cacheSource(RequestOptions options) {
        options.diskCacheStrategy(DiskCacheStrategy.DATA);
    }

}
```

�����������ںܶ����ƹ������£�

- ��Ҫ���� `@GlideExtension` ע�⣬Ȼ��Ҫ�������Ĺ��캯�������� private
- �Զ��� api ����ʹ�� `@GlideOption` ע�⡣ע���Զ��� API �ķ����������Ǿ�̬���������ҵ�һ������������ `RequestOptions`����������Լ��������������Զ���Ĳ�����
- `cacheSource()` �����У�������Ȼ���ǵ��õ� `diskCacheStrategy(DiskCacheStrategy.DATA)`����������˵ `cacheSource()` ����һ��� API �ķ�װ���ѡ�


����� AS �� build - rebuild project ���ǾͿ���ʹ������Զ���� api ��

```
GlideApp.with(this)
        .load(url)
        .cacheSource()
        .into(imageView);
```