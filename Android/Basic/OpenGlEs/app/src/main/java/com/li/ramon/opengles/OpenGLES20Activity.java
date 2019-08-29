package com.li.ramon.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class OpenGLES20Activity extends AppCompatActivity {

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 给 OpenGL ES 提供一个 View 容器
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

    /**
     * GLSurfaceView 的核心代码非常简短，对于一个快速的实现，
     * 我们通常可以在 Activity 中创建一个内部类并使用它
     */
    class MyGLSurfaceView extends GLSurfaceView {
        // 添加一个触摸事件
        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
        private float mPreviousX;
        private float mPreviousY;

        private final MyGLRenderer mRenderer;

        public MyGLSurfaceView(Context context) {
            super(context);

            // 创建 OpenGL ES 2.0 环境
            setEGLContextClientVersion(2);
            mRenderer = new MyGLRenderer();

            // 设置 Render， 使用它在 GLSurfaceView 上绘制
            setRenderer(mRenderer);

            // 将渲染模式设置为：GLSurfaceView.RENDERMODE_WHEN_DIRTY，
            // 其含义是：仅在你的绘制数据发生变化时才在视图中进行绘制操作：
            // 如果选用这一配置选项，那么除非调用了requestRender()，否则
            // GLSurfaceView 不会被重新绘制，这样做可以让应用的性能及效率得到提高。
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            // MotionEvent reports input details from the touch screen
            // and other input controls. In this case, you are only
            // interested in events where the touch position changed.
            float x = e.getX();
            float y = e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    // reverse direction of rotation above the mid-line
                    if (y > getHeight() / 2) {
                        dx = dx * -1;
                    }

                    // reverse direction of rotation to left of the mid-line
                    if (x < getWidth() / 2) {
                        dy = dy * -1;
                    }

                    mRenderer.setAngle(
                            mRenderer.getAngle() +
                                    ((dx + dy) * TOUCH_SCALE_FACTOR));
                    requestRender();
            }

            mPreviousX = x;
            mPreviousY = y;
            return true;
        }
    }
}
