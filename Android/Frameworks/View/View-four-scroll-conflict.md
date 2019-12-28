## 滑动冲突

当内外两层可以同时滑动时，这个时候就会产生滑动冲突。

#### 滑动冲突的分类

滑动冲突一般可以分为 3 类

- 场景1.外部滑动方向与内部滑动方向不一致
- 场景2.外部滑动冲突与内部滑动方向一致
- 场景3.上述两种情况的嵌套


![view_004](EF7B982518D84405A34039CC084C9BE8)

#### 滑动冲突的处理规则

针对这些滑动冲突，我们是有既定的处理规则：
- 对于场景 1 ，它的处理规则是：当用户左右滑动时，需要外部 View 拦截点击事件，当用户上下滑动时，需要让内部 View 拦截点击事件。至于如何判断是水平滑动还是上下滑动，我们可以根据滑动的距离差。
- 对于场景 2，比较特殊，它无法根据滑动的角度，距离差以及速度差做判断，但是我们可以从业务角度出发，针对某种情况，我们需要外部 View 响应滑动，而另一种情况需要内部 View 来响应滑动。
- 对于场景 3， 它和场景 2  一样，还是以业务角度为突破口。


#### 如何处理滑动冲突

我们需要找出一种通用的处理规则，针对不同场景只需要修改有关滑动的处理规则的逻辑就可以。针对场景 1，我们可以根据滑动的距离差来进行判断，这个距离差就是滑动规则。

针对滑动冲突，我们有两种方案可以解决，**外部拦截法**和**内部拦截法**


**外部拦截法**

外部拦截法是指点击事件都要先经过父容器的拦截处理，如果父容器需要则拦截，否则不拦截，交给内部 View 处理。外部拦截法需要重写父容器的 `onInterceptTouchEvent` 方法

```
public boolean onInterceptTouchEvent(MotionEvent event) {
    boolean intercepted = false;
    int x = (int) event.getX();
    int y = (int) event.getY();
    
    switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            intercepted = false;
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            if(父容器需要当前点击事件) {
                intercepted = true;
            } else {
                intercepted = false;
            }
            break;
        }
        case MotionEvent.ACTION_UP: {
            intercepted = false;
            break;
        }
        default:
            break;
    }
    mLastXIntercept = x;
    mLastYIntercept = y;
    return intercepted;
}
```

针对不同的滑动冲突，只需要修改父容器需要当前点击事件这个条件就可以。 在 `onInterceptTouchEvent` 方法中，首先是 `ACTION_DOWN` 这个事件，父容器必须返回 `false`, 即不拦截 `ACTION_DOWN`，因为一旦拦截，后续的 `ACTION_MOVE` 和 `ACTION_UP` 都会直接交给父容器处理。`ACTION_MOVE` 需要根据条件来拦截，而 `ACTION_UP` 事件这里必须返回 `false`

**内部拦截法**

内部拦截法是指父容器不拦截任何事件，所有的事件都传给子元素，如果子元素需要此事件则直接消耗掉，如果不需要则交给父容器处理，这种方式需要配合 `requestDisallowInterceptTouchEvent` 使用，它的伪代码如下，需要重写子元素的 `dispatchTouchEvent`

```
public boolean dispatchTouchEvent(MotionEvent event) {
    int x = (int) event.getX();
    int y = (int) event.getY();
    
    switch(event.getAction() {
        case MotionEvent.ACTION_DOWN: {
            parent.requestDisallowIntercepTouchEvent(true);
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            if(父容器需要此事件) {
                parent.requestDisallowInterceptTouchEvent(false);
            }
            break;
        }
        case MotionEvent.ACTION_UP: {
            break;
        }
        default:
            break;
    }
    mLastX = x;
    mLastY = y;
    return super.dispatchTouchEvent(event);
}
```

除了子元素需要做处理以外，父元素也要默认拦截除了 `ACTION_DOWN` 以外的其他事件，这样当子元素调用 `parent.requestDisallowInterceptTouchEvent(false)` 方法时，父元素才能继续拦截所需的事件。

为什么父容器不能拦截 `ACTION_DOWN` 事件呢？因为 `ACTION_DOWN` 事件不受 `FLAG_DISALLOW_INTERCEPT` 这个标记位的控制，所以一旦父容器拦截了 `ACTION_DOWN` 事件，那么所有的事件都无法传递到子元素中去，这样内部拦截法就不起作用了。所以需要对父元素做修改

```
public boolean onInterceptTouchEvent(MotionEvent event) {
    int action = event.getAction();
    if (action == MotionEvent.ACTION_DOWN) {
        return false;
    } else {
        return true;
    }
}
```

#### 例1：外部拦截法

这里实现一个类似 `ViewPager` 的效果,定义一个类似于水平的 `LinearLayout` 的布局，它可以水平滑动，初始化时在它的内部添加若干个 `ListView` ，这样由于内部的 `ListView` 可以竖直滑动，而它本身可以水平滑动，所以滑动冲突就出现了。

首先来看 `Activity` 中的代码

```
public class ConflictDemo1Activity extends AppCompatActivity {
    private static final String TAG = "ConflictDemo1Activity";
    private HorizontalScrollViewEx mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conflict_demo1);
        Log.d(TAG, "onCreate");
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);
        // 获取屏幕的宽高
        final int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        final int screenHeight = MyUtils.getScreenMetrics(this).heightPixels;

        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup)inflater.inflate(R.layout.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255/(i + 1), 255 / (i + 1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.content_list_item,R.id.name, datas);
        listView.setAdapter(adapter);
    }
}
```

上述代码很简单，创建了 3 个 `ListView` 并且把 `ListView` 加入到我们自定义的 `HorizontalScrollViewEx` 中。下面是 `HorizontalScrollViewEx` 的代码

```
public class HorizontalScrollViewEx extends ViewGroup {
    private static final String TAG = "HorizontalScrollViewEx";

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }

        Log.d(TAG, "intercepted=" + intercepted);
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-deltaX, 0);
                break;
            }
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize, childView.getMeasuredHeight());
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measuredWidth, heightSpaceSize);
        } else {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth,
                        childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
```

#### 例2：内部拦截法

内部拦截法直需要修改 `ListView` 的 `dispatchTouchEvent` 方法中的父容器拦截逻辑，为了重写这个方法，我们创建一个 `ListViewEx` 继承 `ListView`

```
public class ListViewEx extends ListView {
    private static final String TAG = "ListViewEx";

    private HorizontalScrollViewEx2 mHorizontalScrollViewEx2;

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHorizontalScrollViewEx2(
            HorizontalScrollViewEx2 horizontalScrollViewEx2) {
        mHorizontalScrollViewEx2 = horizontalScrollViewEx2;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
```

然后我们还需要修改父容器的  `onInterceptTouchEvent` 方法

```
@Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mLastX = x;
            mLastY = y;
            if (!mScroller.isFinished()) {
                // 为了优化滑动体验
                mScroller.abortAnimation();
                return true;
            }
            return false;
        } else {
            return true;
        }
    }
```


