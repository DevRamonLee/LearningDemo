- [【源码解析】View的事件分发](https://www.jianshu.com/p/5ec375057127) 转载自此文
- 《Android 开发艺术探索》 第三章
- https://github.com/LRH1993/android_interview/blob/master/android/basis/Event-Dispatch.md

## View 的事件分发机制

**事件分发**： 当一个 `MotionEvent` 产生了以后，系统需要把这个事件传递给一个具体的 `View`，这个传递过程就是事件分发。我们先来看一下 `View` 事件分发流程图：

![view_002](2176A08F6E7443BEAB7F6CA91E1877A3)

介绍下上面三个主要方法：

- **dispatchTouchEvent**: 用来进行事件的分发，如果事件能够传递给当前 `View`，那么此方法一定会被调用，返回结果受当前 `View` 的 `onTouchEvent` 和下级 `View` 的 `dispatchTouchEvent` 方法的影响，表示是否消耗当前事件。

- **onInterceptTouchEvent**: 在 `dispatchTouchEvent` 方法内部调用，用来判断是否拦截这个事件，如果当前 `View` 拦截了某个事件，那么在同一个事件序列中，此方法不会被再次调用，返回结果表示是否拦截当前事件。

- **onTouchEvent**: 在 `dispatchTouchEvent` 方法内部调用，用来处理单击事件，返回结果表示是否消耗当前事件，如果不消耗，则在同一个事件序列中，当前 `View` 无法再次接收到事件。

它们三个的关系可以用如下伪代码表示：

```
public boolean dispatchTouchEvent(MotionEvent ev) {
    boolean consume = false;
    if (onInterceptTouchEvent(ev)) {
        consume = onTouchEvent(ev);
    } else {
        consume = child.dispatchTouchEvent(ev)
    }
    return consume;
}
```

通过上面的伪代码，我们可以大致得出点击事件的传递规则：

- 对于一个根 `ViewGroup` 来说，点击事件产生后，首先会传递给它，这时它的 `dispatchTouchEvent` 就会被调用
- 如果这个 `ViewGroup` 的 `onInterceptTouchEvent` 方法返回 `true` 就表示它要拦截当前事件，接着就会调用 `onTouchEvent`
- 如果这个 `ViewGroup` 的 `onIntercepTouchEvent` 方法返回 `false` 就表示它不拦截当前事件，这时就会继续传递给它的子元素，子元素的 `dispatchTouchEvent` 方法会被调用，如此反复，直到事件最终被处理。

#### Activity 对事件进行分发

当一个点击事件发生时，事件最先传递到当前 `View` 所在的 `Activity` 中，由 `Activity` 的 `dispatchTouchEvent` 进行分发。

```
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        onUserInteraction();
    }
    if (getWindow().superDispatchTouchEvent(ev)) {
        return true;
    }
    return onTouchEvent(ev);
}
```

首先调用了`getWindow().superDispatchTouchEvent` 方法，返回 true 表示事件被消耗掉了；返回 false 表示事件交给 `Activity` 的 `onTouchEvent` 方法处理。

`getWindow()` 返回的是 `Window` 类。在 `Android` 中，只有 `PhoneWindow` 是 `Window` 的唯一实现类，所以执行的正是 `PhoneWindow` 里的方法。

```
public boolean superDispatchTouchEvent(MotionEvent event) {
    return mDecor.superDispatchTouchEvent(event);
}
```

在 `PhoneWindow` 中，也没有复杂的处理逻辑，转而调用了变量 `mDecor` 的 `superDispatchTouchEvent` 方法。
这里的 `mDecor` 是 `DecorView` 类型。`DecorView` 是 `Activity` 的顶级 `View` 。在 `Activity` 中，我们通过 `setContentView` 方法设置了自定义 `View`，而 `DecorView` 就是我们自定义 `View` 的父容器。

```
public boolean superDispatchTouchEvent(MotionEvent event) {
    return super.dispatchTouchEvent(event);
}
```

`DecorView` 对于 `superDispatchTouchEvent` 方法的实现逻辑就更奇葩了，直接调用父类的 `dispatchTouchEvent` 方法，而 `DecorView` 的父类是 `ViewGroup`。

> 注意了，事件这里已经传递到了第一个顶级 `ViewGroup` 的 `dispatchTouchEvent`方法了。

#### ViewGroup对事件进行分发

`ViewGroup` 中的 `dispatchTouchEvent` 方法的处理逻辑比较复杂，那我们就化整为零，一段一段的看。

```
public boolean dispatchTouchEvent(MotionEvent ev) {
    ...

    final boolean intercepted;
    if (actionMasked == MotionEvent.ACTION_DOWN
            || mFirstTouchTarget != null) {
        // 由于事件为ACTION_DOWN时，重置了mGroupFlags，所以一定会调用 onInterceptTouchEvent 方法。
        final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
        if (!disallowIntercept) {
            // 调用 onInterceptTouchEvent 方法，判断是否需要拦截该事件
            intercepted = onInterceptTouchEvent(ev); 
            ev.setAction(action); // restore action in case it was changed
        } else {
            intercepted = false;
        }
    } else {
        // 当事件不为 ACTION_DOWN，而且 mFirstTouchTarget == null 时，intercepted 置为 true。
        intercepted = true;
    }
    ...
}
```
`actionMasked == MotionEvent.ACTION_DOWN`，我们比较好理解，表示按下事件。
但是 `mFirstTouchTarget != null`，是什么意思呢？


其实看完下面的代码就会知道，当点击事件被 `ViewGroup` 中的子 `View` 消费后，那么 `mFirstTouchTarget != null`。反过来说，当点击事件被当前 `ViewGroup` 消费了，那么 `mFirstTouchTarget == null`。

我们知道，事件的最开始一定是 `ACTION_DOWN` 事件，然后伴随着一系列的 `ACTION_MOVE` 事件，最后是 `ACTION_UP` 事件。

当事件为 `ACTION_DOWN` 时，`mGroupFlags` 总是被重置了。

```
// Handle an initial down.
if (actionMasked == MotionEvent.ACTION_DOWN) {
    // Throw away all previous state when starting a new touch gesture.
    // The framework may have dropped the up or cancel event for the previous gesture
    // due to an app switch, ANR, or some other state change.
    cancelAndClearTouchTargets(ev);
    resetTouchState();
}

private void resetTouchState() {
    clearTouchTargets();
    resetCancelNextUpFlag(this);
    mGroupFlags &= ~FLAG_DISALLOW_INTERCEPT;
    mNestedScrollAxes = SCROLL_AXIS_NONE;
}
```

当事件为 `ACTION_DOWN` 时，由于 `mGroupFlags` 的重置，所以 `disallowIntercept` 的值为 `false`，也就是说 `onInterceptTouchEvent` 方法一定会执行。

> 注意了，事件这里已经传递到了第一个顶级 `ViewGroup` 的 `onInterceptTouchEvent` 方法


`onInterceptTouchEvent` 方法的返回值决定着事件分发的走向，分为如下两个分支：
- 1、当返回为 `true` 时，表示当前 `ViewGroup` 拦截了该事件。
- 2、当返回为 `false` 时，表示当前 `ViewGroup` 不拦截该事件。该事件转而传递到该 `ViewGroup` 的子 `View`。


##### 第一种： onInterceptTouchEvent 返回 true

这里先看第一个分支，当返回为 `true` 时，`intercepted` 的赋值为 `true`。

```
if (!canceled && !intercepted) {
    // 事件传递到子View才会走下面的逻辑
    ...
}

if (mFirstTouchTarget == null) {
    // No touch targets so treat this as an ordinary view.
    handled = dispatchTransformedTouchEvent(ev, canceled, null,
            TouchTarget.ALL_POINTER_IDS);
} else {
    ...
}
```

`intercepted` 的赋值为 `true`，说明事件被当前 `ViewGroup` 拦截了，`mFirstTouchTarget == null` 条件判断成立，所以调用了 `dispatchTransformedTouchEvent` 方法。**注意，这里的第三个参数为null**。

```
private boolean dispatchTransformedTouchEvent(MotionEvent event, boolean cancel, View child, int desiredPointerIdBits) {
    final boolean handled;
    ...

    // Perform any necessary transformations and dispatch.
    if (child == null) {
        handled = super.dispatchTouchEvent(transformedEvent);
    } else {
        final float offsetX = mScrollX - child.mLeft;
        final float offsetY = mScrollY - child.mTop;
        transformedEvent.offsetLocation(offsetX, offsetY);
        if (! child.hasIdentityMatrix()) {
            transformedEvent.transform(child.getInverseMatrix());
        }

        handled = child.dispatchTouchEvent(transformedEvent);
    }
    transformedEvent.recycle();
    return handled;
}
```

上面提到的第三个参数为 `null`，也就是说 `child == null` 成立，所以这里执行了 `super. dispatchTouchEvent` 方法。`ViewGroup` 的父类是 `View`，所以这里执行的是 `View` 的 `dispatchTouchEvent` 方法。

```
public boolean dispatchTouchEvent(MotionEvent event) {
    ...
    boolean result = false;
    ...

    if (onFilterTouchEventForSecurity(event)) {
        ...
        ListenerInfo li = mListenerInfo;
        if (li != null && li.mOnTouchListener != null
                && (mViewFlags & ENABLED_MASK) == ENABLED
                && li.mOnTouchListener.onTouch(this, event)) {
            result = true;
        }
        if (!result && onTouchEvent(event)) {
            result = true;
        }
    }
    ...
    return result;
}
```

这里判断了当前 `View`（也就是上面的 `ViewGroup`）的状态是否是 `Enable` 的，并且当前 `View` 是否通过 `setOnTouchListener` 方法设置了 `OnTouchListener` 接口方法并在 `onTouch` 方法中是否返回 `true`。

当上面提到的条件都返回 `true` 时，`result` 的赋值为 `true` 。当 `result` 的赋值为 `true` 时，`onTouchEvent` 方法并不会执行。

> **这里得出一个结论**：当 `View` 设置了 `OnTouchListener` 接口，并在接口方法 `onTouch` 中返回 `true` 时，`View` 的 `onTouchEvent` 方法不会执行，也就是说事件不会传递到 `View` 的 `onTouchEvent` 方法中。


这里讨论一般的情况，当 `result` 为 `false` 时，`onTouchEvent` 方法就被执行了。

> 注意了，事件这里已经传递到了第一个顶级 `ViewGroup` 的 `onTouchEvent` 方法了。当 `onTouchEvent` 返回 `true` 时，表示该事件被当前 `View` 消耗了，后续的一系列事件默认都会传递给当前 `View`。


平时我们经常给 `View` 设置的 `OnClickListener` 是在 `onTouchEvent` 中触发的（源码中调用了 `performClick` 方法）。结合上面得出的结论，我们可以得到另外一个结论：`OnTouch` 比 `onTouchEvent` 先执行，`onTouchEvent` 比 `onClick` 先执行。我们平时给 `View` 设置的点击事件是处于最末端执行的


当 `onTouchEvent` 返回 `false` 时，表示当前 `View` 不处理该事件，我们回溯上面的方法，这就相当于最开始的 `dispatchTouchEvent` 方法返回 `false` ，继续回溯到 `Activity` 的 `dispatchTouchEvent` 方法。

```
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        onUserInteraction();
    }
    if (getWindow().superDispatchTouchEvent(ev)) {
        return true;
    }
    return onTouchEvent(ev);
}
```
也就是说，`(getWindow().superDispatchTouchEvent(ev)` 返回 `false`，`Activity` 的 `onTouchEvent` 方法会被执行。


##### 第二种： onInterceptTouchEvent 返回 false

当 `ViewGroup` 的 `onInterceptTouchEvent` 方法返回 `false` 时，则执行如下逻辑。

```
if (!canceled && !intercepted) {
    if (actionMasked == MotionEvent.ACTION_DOWN
            || (split && actionMasked == MotionEvent.ACTION_POINTER_DOWN)
            || actionMasked == MotionEvent.ACTION_HOVER_MOVE) {
        ...
        final int childrenCount = mChildrenCount;
        if (newTouchTarget == null && childrenCount != 0) {
            ...
            final View[] children = mChildren;
            //遍历当前ViewGroup的每一个子View，将该事件分发下去
            for (int i = childrenCount - 1; i >= 0; i--) {
                final int childIndex = getAndVerifyPreorderedIndex(
                        childrenCount, i, customOrder);
                final View child = getAndVerifyPreorderedView(
                        preorderedList, children, childIndex);
                ...
                resetCancelNextUpFlag(child);
                if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAssign)) {
                    // Child wants to receive touch within its bounds.
                    mLastTouchDownTime = ev.getDownTime();
                    if (preorderedList != null) {
                        for (int j = 0; j < childrenCount; j++) {
                            if (children[childIndex] == mChildren[j]) {
                                mLastTouchDownIndex = j;
                                break;
                            }
                        }
                    } else {
                        mLastTouchDownIndex = childIndex;
                    }
                    mLastTouchDownX = ev.getX();
                    mLastTouchDownY = ev.getY();
                    newTouchTarget = addTouchTarget(child, idBitsToAssign);
                    alreadyDispatchedToNewTouchTarget = true;
                    break;
                }
                ev.setTargetAccessibilityFocus(false);
            }
            if (preorderedList != null) preorderedList.clear();
        }
        ...
    }
}
```

遍历了当前 `ViewGroup` 的每一个子 `View`，将事件分发下去。对于 `ViewGroup` 的子 `View`，调用了 `dispatchTransformedTouchEvent` 方法。

前面 `ViewGroup` 拦截事件时也调用了该方法，只不过当时的第三个参数为 `null` 。

```
private boolean dispatchTransformedTouchEvent(MotionEvent event, boolean cancel,
        View child, int desiredPointerIdBits) {
    final boolean handled;
    ...
    // Perform any necessary transformations and dispatch.
    if (child == null) {
        handled = super.dispatchTouchEvent(transformedEvent);
    } else {
        final float offsetX = mScrollX - child.mLeft;
        final float offsetY = mScrollY - child.mTop;
        transformedEvent.offsetLocation(offsetX, offsetY);
        if (! child.hasIdentityMatrix()) {
            transformedEvent.transform(child.getInverseMatrix());
        }

        handled = child.dispatchTouchEvent(transformedEvent);
    }
    transformedEvent.recycle();
    return handled;
}
```

可以看到，当 `child`不为 `null` 时，调用了 `child` 的 `dispatchTouchEvent` 方法。如果 `child` 是一个 `ViewGroup` 的话，那么事件分发的逻辑就和上面分析 `ViewGroup` 一样了

当 `dispatchTransformedTouchEvent` 方法返回 `true` 时，表示事件由该子 `View` 消耗了，所以 `addTouchTarget` 方法就会被执行。

```
private TouchTarget addTouchTarget(@NonNull View child, int pointerIdBits) {
    final TouchTarget target = TouchTarget.obtain(child, pointerIdBits);
    target.next = mFirstTouchTarget;
    mFirstTouchTarget = target;
    return target;
}
```

而 `mFirstTouchTarget` 就是在这里赋值的。这也验证了上面那个结论：
当 `mFirstTouchTarget != null` 时，表示事件由当前 `ViewGroup` 的子 `View` 消耗了。

假设 `ViewGroup`的子 `View` 不是一个容器，而是一个纯粹的 `View` ，那么事件分发逻辑又是怎样的呢？

#### View 对事件的分发

接着上面的逻辑，事件继续传递，在 `dispatchTransformedTouchEvent` 方法中，如果 `child` 是一个 `View`，那么调用的就是 `View` 的 `dispatchTouchEvent` 方法。

```
public boolean dispatchTouchEvent(MotionEvent event) {
    ...
    boolean result = false;
    ...

    if (onFilterTouchEventForSecurity(event)) {
        ...
        ListenerInfo li = mListenerInfo;
        if (li != null && li.mOnTouchListener != null
                && (mViewFlags & ENABLED_MASK) == ENABLED
                && li.mOnTouchListener.onTouch(this, event)) {
            result = true;
        }
        if (!result && onTouchEvent(event)) {
            result = true;
        }
    }
    ...
    return result;
}
```

我们上面也分析了 `View` 的 `dispatchTouchEvent` 方法。这里就只说一下结论：

`dispatchTouchEvent` 方法调用了 `onTouchEvent` 方法，方法的返回值表示事件是否被消耗了。当事件没有被消耗，那么 `mFirstTouchTarget` 没有被赋值，即 `mFirstTouchTarget == null`
此时事件回传到了子 `View` 的父容器。

如果父容器不处理该事件，事件会继续往上传，直到事件被消耗。

当事件被消耗了，后面一系列事件都会传递到该 `View`。当然在传递的过程中，`ACTION_MOVE` 和 `ACTION_UP` 事件默认会经过父容器的 `onInterceptTouchEvent` 方法，父容器可以在该方法中拦截事件。

如果所有 `View` 都不消耗该事件，那么该事件会回传到 `Activity`。`Activity` 如果也不消耗该事件，那么该事件就消失了。