package com.leonjojo.autoscaleandfittextviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.leonjojo.autoscaleandfittextviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LQ
 */
public class AutoScaleAndFitTextView extends View implements View.OnTouchListener {
    protected int parentViewWidth;
    protected int parentViewHeight;
    protected int lastX;
    protected int lastY;
    private int oriLeft;
    private int oriRight;
    private int oriTop;
    private int oriBottom;
    private int dragDirection;
    private static final int TOP = 0x15;
    private static final int LEFT = 0x16;
    private static final int BOTTOM = 0x17;
    private static final int RIGHT = 0x18;
    private static final int LEFT_TOP = 0x11;
    private static final int RIGHT_TOP = 0x12;
    private static final int LEFT_BOTTOM = 0x13;
    private static final int RIGHT_BOTTOM = 0x14;
    private static final int CENTER = 0x19;
    private int offset = 20;
    protected Paint paint = new Paint();
    protected Paint paintText = new Paint();


    private static final String TAG = AutoScaleAndFitTextView.class.getSimpleName();
    //可拖拉的图片
    private Bitmap bitmap;
    Rect rectImg;
    Rect rectArea;
    private int imgWidth;
    private int imgHeight;


    private String text = AutoScaleAndFitTextView.class.getSimpleName();
     final int TEXT_SIZE = 50;

     final float TEXT_HEIGHT_POUND =1.1f;
    /**
     * 初始化获取屏幕宽高
     */
    protected void initScreenW_H() {
        parentViewHeight = getResources().getDisplayMetrics().heightPixels - 40;
        parentViewWidth = getResources().getDisplayMetrics().widthPixels;
        rectImg = new Rect();
        rectArea = new Rect();

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_scale);
        imgWidth = bitmap.getWidth();
        imgHeight = bitmap.getHeight();
        intPaint();

    }

    public void setParentViewSize(int width, int height) {
        parentViewHeight = height;
        parentViewWidth = width;
    }

    private void intPaint() {
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4.0f);
        paint.setStyle(Paint.Style.STROKE);

        paintText.setColor(Color.BLACK);
        paintText.setStrokeWidth(2);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(TEXT_SIZE);
    }

    public AutoScaleAndFitTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnTouchListener(this);
        initScreenW_H();
    }

    public AutoScaleAndFitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        initScreenW_H();
    }

    public AutoScaleAndFitTextView(Context context) {
        super(context);
        setOnTouchListener(this);
        initScreenW_H();
    }

    private int[] getCenter() {
        int[] center = new int[2];
        center[0] = getWidth() / 2;
        center[1] = getHeight() / 2;
        return center;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画边缘
        canvas.drawRect(offset, offset, getWidth() - offset, getHeight()
                - offset, paint);


        rectImg.set(getWidth() - imgWidth, getHeight() - imgHeight, getWidth(), getHeight());
        canvas.drawBitmap(bitmap, getWidth() - imgWidth, getHeight() - imgWidth, paint);

        //画文字
        if (!emptyText) {
            drawText(canvas);
        }
    }

    private List<String> resultStr;

    private void drawText(Canvas canvas) {
        drawText(canvas,false);
    }
    private void drawText(Canvas canvas, boolean isOutput) {
        try {
            //平移的时候不用调整
//            resultStr = adjustTextLength(text, paintText);
            resultStr = adjustTextSize(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        float textHeight = paintText.getTextSize();
        for (int i = 0; i < resultStr.size(); i++) {
            String displayText = resultStr.get(i);
            float textLength = paintText.measureText(displayText, 0, displayText.length());
            int yoff = (int) ((textHeight) * (i + 1));
            if (isOutput){
                //添加x y轴的偏移量
                canvas.drawText(displayText, getLeft()+getCenter()[0] - textLength / 2, getTop()+yoff+padding, paintText);
            }else{
                canvas.drawText(displayText, getCenter()[0] - textLength / 2, yoff+padding, paintText);

            }
        }
    }


    public void setDisplayText(String text) {
        if (text.length() > 0) {
            emptyText = false;
        }
        this.text = text;
        invalidate();
    }

    public void setTextColor(int color) {
        paintText.setColor(color);
        invalidate();
    }

    private boolean emptyText = false;

    public void clearText() {
        emptyText = true;
    }

    public Bitmap getBitmap(String bmPath) {

        Bitmap bitMap = BitmapFactory.decodeFile(bmPath);
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
    // 设置想要的大小
        int newWidth = parentViewWidth;
        int newHeight = parentViewHeight;
    // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
    // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
    // 得到新的图片
        bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);

        Canvas c = new Canvas(bitMap);
        c.drawBitmap(bitMap, 0, 0, paint);
        drawText(c,true);
        return bitMap;
    }
    public Bitmap getBitmap(Bitmap bitmapFrame) {

        Bitmap bitMap = bitmapFrame;
        int newWidth = parentViewWidth;
        int newHeight = parentViewHeight;
        // 设置想要的大小
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);

        Canvas c = new Canvas(bitMap);
        c.drawBitmap(bitMap, 0, 0, paint);
        drawText(c,true);
        return bitMap;
    }

    private float padding=10;
    private List<String> adjustTextSize(String text) throws Exception {
        List<String> resultStr = new ArrayList<>();
        float width=getWidth()-padding*2;
        float height=getHeight()-padding*2;
        int textMaxSize=(int)height;
        if (height>width){
            textMaxSize=(int)width;
        }
        int lineCount;
        int rowCount;
        do {
            textMaxSize--;
            lineCount=(int) Math.floor(width/(textMaxSize* TEXT_HEIGHT_POUND));
            rowCount=(int) Math.floor(height/(textMaxSize* TEXT_HEIGHT_POUND));
        }while (lineCount*rowCount<text.length());

        //计算完后返回字符
        paintText.setTextSize(textMaxSize);

        for (int i = 0; i < rowCount; i++) {
            int start = lineCount * i;
            int end = lineCount * (i + 1);
            end = end > text.length() ? text.length() : end;
            String lineString = text.substring(start, end);
            resultStr.add(lineString);
        }
        return  resultStr;
    }


    /*
    只改变行数不改变大小
     */
    private List<String> adjustTextLength(String text, Paint paint) throws Exception {
        float charwidth = paint.measureText(text, 0, 1);
        //每行的字符数量
        float textAreaWidth = getWidth() - charwidth * 2;
        int charsPerLine = Math.round(textAreaWidth / charwidth);

        List<String> resultStr = new ArrayList<>();
        //第一行的字符
        if (text.length() <= charsPerLine) {
            resultStr.add(text);
            return resultStr;
        }
        String firstlinetext = text.substring(0, charsPerLine);
        float textLength = paint.measureText(firstlinetext, 0, firstlinetext.length());

        //检测是否超过最大值
        while (textLength >= textAreaWidth) {
            firstlinetext = text.substring(0, --charsPerLine);
            textLength = paint.measureText(firstlinetext, 0, firstlinetext.length());
        }
        int lines = Math.round(text.length() / charsPerLine + 0.0f);
        for (int i = 0; i <= lines; i++) {
            int start = charsPerLine * i;
            int end = charsPerLine * (i + 1);
            end = end > text.length() ? text.length() : end;
            String lineString = text.substring(start, end);
            resultStr.add(lineString);
        }
        return resultStr;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            oriLeft = v.getLeft();
            oriRight = v.getRight();
            oriTop = v.getTop();
            oriBottom = v.getBottom();
            lastY = (int) event.getRawY();
            lastX = (int) event.getRawX();
//            dragDirection = getDirection(v, (int) event.getX(),
//                    (int) event.getY());
            Log.d(TAG, "onTouch: lastX=" + lastX + ",lastY=" + lastY + ",touchX=" + event.getX() + ",touchY=" + event.getY());
            if (rectImg.contains((int) event.getX(), (int) event.getY())) {
                //拖拽放大
                dragDirection = RIGHT_BOTTOM;
            } else {
                dragDirection = CENTER;
            }
        }
        // 处理拖动事件
        delDrag(v, event, action);
        invalidate();
        return false;
    }

    /**
     * 处理拖动事件
     *
     * @param v
     * @param event
     * @param action
     */
    protected void delDrag(View v, MotionEvent event, int action) {
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                switch (dragDirection) {
                    case LEFT: // 左边缘
                        left(v, dx);
                        break;
                    case RIGHT: // 右边缘
                        right(v, dx);
                        break;
                    case BOTTOM: // 下边缘
                        bottom(v, dy);
                        break;
                    case TOP: // 上边缘
                        top(v, dy);
                        break;
                    case CENTER: // 点击中心-->>移动
                        center(v, dx, dy);
                        break;
                    case LEFT_BOTTOM: // 左下
                        bottom(v, dy);
                        left(v, dx);
                        break;
                    case LEFT_TOP: // 左上
                        left(v, dx);
                        top(v, dy);
                        break;
                    case RIGHT_BOTTOM: // 右下
                        right(v, dx);
                        bottom(v, dy);
                        break;
                    case RIGHT_TOP: // 右上
                        right(v, dx);
                        top(v, dy);
                        break;
                }
                if (dragDirection != CENTER) {
                    v.layout(oriLeft, oriTop, oriRight, oriBottom);
                }
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                dragDirection = 0;
                break;
        }
    }

    /**
     * 触摸点为中心->>移动
     *
     * @param v
     * @param dx
     * @param dy
     */
    private void center(View v, int dx, int dy) {
        int left = v.getLeft() + dx;
        int top = v.getTop() + dy;
        int right = v.getRight() + dx;
        int bottom = v.getBottom() + dy;
        if (left < -offset) {
            left = -offset;
            right = left + v.getWidth();
        }
        if (right > parentViewWidth + offset) {
            right = parentViewWidth + offset;
            left = right - v.getWidth();
        }
        if (top < -offset) {
            top = -offset;
            bottom = top + v.getHeight();
        }
        if (bottom > parentViewHeight + offset) {
            bottom = parentViewHeight + offset;
            top = bottom - v.getHeight();
        }
        v.layout(left, top, right, bottom);
    }

    /**
     * 触摸点为上边缘
     *
     * @param v
     * @param dy
     */
    private void top(View v, int dy) {
        oriTop += dy;
        if (oriTop < -offset) {
            oriTop = -offset;
        }
        if (oriBottom - oriTop - 2 * offset < 100) {
            oriTop = oriBottom - 2 * offset - 100;
        }
    }

    /**
     * 触摸点为下边缘
     *
     * @param v
     * @param dy
     */
    private void bottom(View v, int dy) {
        oriBottom += dy;
        if (oriBottom > parentViewHeight + offset) {
            oriBottom = parentViewHeight + offset;
        }
        if (oriBottom - oriTop - 2 * offset < 100) {
            oriBottom = 100 + oriTop + 2 * offset;
        }
    }

    /**
     * 触摸点为右边缘
     *
     * @param v
     * @param dx
     */
    private void right(View v, int dx) {
        oriRight += dx;
        if (oriRight > parentViewWidth + offset) {
            oriRight = parentViewWidth + offset;
        }
        if (oriRight - oriLeft - 2 * offset < 100) {
            oriRight = oriLeft + 2 * offset + 100;
        }
    }

    /**
     * 触摸点为左边缘
     *
     * @param v
     * @param dx
     */
    private void left(View v, int dx) {
        oriLeft += dx;
        if (oriLeft < -offset) {
            oriLeft = -offset;
        }
        if (oriRight - oriLeft - 2 * offset < 200) {
            oriLeft = oriRight - 2 * offset - 200;
        }
    }

    /**
     * 获取触摸点flag
     *
     * @param v
     * @param x
     * @param y
     * @return
     */
    protected int getDirection(View v, int x, int y) {
        int left = v.getLeft();
        int right = v.getRight();
        int bottom = v.getBottom();
        int top = v.getTop();
        if (x < 40 && y < 40) {
            return LEFT_TOP;
        }
        if (y < 40 && right - left - x < 40) {
            return RIGHT_TOP;
        }
        if (x < 40 && bottom - top - y < 40) {
            return LEFT_BOTTOM;
        }
        if (right - left - x < 40 && bottom - top - y < 40) {
            return RIGHT_BOTTOM;
        }
        if (x < 40) {
            return LEFT;
        }
        if (y < 40) {
            return TOP;
        }
        if (right - left - x < 40) {
            return RIGHT;
        }
        if (bottom - top - y < 40) {
            return BOTTOM;
        }
        return CENTER;
    }

    /**
     * 获取截取宽度
     *
     * @return
     */
    public int getCutWidth() {
        return getWidth() - 2 * offset;
    }

    /**
     * 获取截取高度
     *
     * @return
     */
    public int getCutHeight() {
        return getHeight() - 2 * offset;
    }

}
