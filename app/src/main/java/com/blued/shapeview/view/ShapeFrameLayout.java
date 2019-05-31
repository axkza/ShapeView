package com.blued.shapeview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.blued.shapeview.R;


/**
 * 带圆角、线条、纯背景和渐变背景的FrameLayout
 * Created by xiekaizhen on 2018/6/4.
 */
public class ShapeFrameLayout extends FrameLayout implements ShapeHelper.ShapeView {
    private ShapeModel model;

    public ShapeFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData(context, attrs);
    }

    public ShapeFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }

    public ShapeFrameLayout(Context context) {
        super(context);
    }

    private void initData(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ShapeFrameLayout);
        model = new ShapeModel();
        model.bgDefaultTouch = a.getBoolean(R.styleable.ShapeFrameLayout_bg_default_touch, false);
        model.bgDrawable = a.getDrawable(R.styleable.ShapeFrameLayout_bg_drawable);
        model.bgTouchDrawable = a.getDrawable(R.styleable.ShapeFrameLayout_bg_touch_drawable);
        model.bgUnableDrawable = a.getDrawable(R.styleable.ShapeFrameLayout_bg_unable_drawable);
        model.solidColor = a.getColor(R.styleable.ShapeFrameLayout_solid_color, Color.TRANSPARENT);
        model.solidTouchColor = a.getColor(R.styleable.ShapeFrameLayout_solid_touch_color, Color.TRANSPARENT);
        model.solidUnableColor = a.getColor(R.styleable.ShapeFrameLayout_solid_unable_color, Color.TRANSPARENT);
        model.strokeColor = a.getColor(R.styleable.ShapeFrameLayout_stroke_color, Color.TRANSPARENT);
        model.strokeTouchColor = a.getColor(R.styleable.ShapeFrameLayout_stroke_touch_color, Color.TRANSPARENT);
        model.strokeUnableColor = a.getColor(R.styleable.ShapeFrameLayout_stroke_unable_color, Color.TRANSPARENT);
        model.strokeWidth = a.getDimension(R.styleable.ShapeFrameLayout_stroke_width, 0f);
        model.strokeDashWidth = a.getDimension(R.styleable.ShapeFrameLayout_stroke_dash_width, 0f);
        model.strokeDashGap = a.getDimension(R.styleable.ShapeFrameLayout_stroke_dash_gap, 0f);
        model.cornerRadius = a.getDimension(R.styleable.ShapeFrameLayout_corner_radius, 0);
        model.topLeftRadius = a.getDimension(R.styleable.ShapeFrameLayout_top_left_radius, 0);
        model.topRightRadius = a.getDimension(R.styleable.ShapeFrameLayout_top_right_radius, 0);
        model.bottomLeftRadius = a.getDimension(R.styleable.ShapeFrameLayout_bottom_left_radius, 0);
        model.bottomRightRadius = a.getDimension(R.styleable.ShapeFrameLayout_bottom_right_radius, 0);
        model.startColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_start_color, Color.TRANSPARENT);
        model.centerColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_center_color, Color.TRANSPARENT);
        model.endColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_end_color, Color.TRANSPARENT);
        model.startTouchColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_start_touch_color, Color.TRANSPARENT);
        model.centerTouchColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_center_touch_color, Color.TRANSPARENT);
        model.endTouchColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_end_touch_color, Color.TRANSPARENT);
        model.startUnableColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_start_unable_color, Color.TRANSPARENT);
        model.centerUnableColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_center_unable_color, Color.TRANSPARENT);
        model.endUnableColor = a.getColor(R.styleable.ShapeFrameLayout_gradient_end_unable_color, Color.TRANSPARENT);
        model.gradientAngle = a.getInt(R.styleable.ShapeFrameLayout_gradient_angle, 0);
        model.gradientType = a.getInt(R.styleable.ShapeFrameLayout_gradient_type, 0);
        model.gradientRadius = a.getDimension(R.styleable.ShapeFrameLayout_gradient_radius, 0);
        model.gradientCenterX = a.getFloat(R.styleable.ShapeFrameLayout_gradient_center_x, 0.5f);
        model.gradientCenterY = a.getFloat(R.styleable.ShapeFrameLayout_gradient_center_y, 0.5f);
        model.bgModel = a.getInt(R.styleable.ShapeFrameLayout_bg_model, 0);
        model.shapeType = a.getInt(R.styleable.ShapeFrameLayout_shape_type, GradientDrawable.RECTANGLE);
        model.whRatio = a.getFloat(R.styleable.ShapeFrameLayout_wh_ratio, 0.0f);

        a.recycle();
        setShapeColor();
    }

    /**
     * 设置比例
     **/
    @SuppressWarnings("unused")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (model != null && model.whRatio != 0) {
            setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
            int childWidthSize = getMeasuredWidth();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize / model.whRatio), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // 设置背景和字体颜色
    private void setShapeColor() {
        ShapeHelper.setShapeColor(this);
    }

    // 获取shapeModel
    @Override
    public ShapeModel getShapeModel() {
        if (model == null)
            model = new ShapeModel();
        return model;
    }

    // 设置shapeModel
    @Override
    public void setShapeModel(ShapeModel shapeModel) {
        model = shapeModel;
        setShapeColor();
    }
}
