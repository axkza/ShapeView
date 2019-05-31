package com.blued.shapeview.view;

import android.graphics.drawable.Drawable;

/**
 * ShapeView的参数model
 **/
public class ShapeModel {

    //             是否有默认触摸背景置灰的效果，以正常背景为基础，添加一个置灰的效果
    public boolean bgDefaultTouch;
    //         字体颜色    按下字体颜色     unable字体颜色     字体开始的渐变色   字体结束的渐变色
    public int textColor, textTouchColor, textUnableColor, textStartColor, textEndColor;
    //              正常背景图   按下时的背景图    unable时的背景图
    public Drawable bgDrawable, bgTouchDrawable, bgUnableDrawable;
    //         纯背景颜色   按下时纯背景颜色    unable时的纯背景颜色
    public int solidColor, solidTouchColor, solidUnableColor;
    //         线条颜色      按下线条的颜色      unable时的线条颜色
    public int strokeColor, strokeTouchColor, strokeUnableColor;
    //           线条宽度       虚线宽度          虚线间隙
    public float strokeWidth, strokeDashWidth, strokeDashGap;
    //         渐变颜色                            按下时的渐变颜色                                    unable时的渐变颜色
    public int startColor, centerColor, endColor, startTouchColor, centerTouchColor, endTouchColor, startUnableColor, centerUnableColor, endUnableColor;
    //         渐变角度        渐变类型
    public int gradientAngle, gradientType;
    //           渐变半径         渐变x、y坐标(范围0-1)
    public float gradientRadius, gradientCenterX, gradientCenterY;
    //           四角角度
    public float cornerRadius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius;
    //         背景类型: 线条、纯颜色背景、渐变色背景（注意需要在xml同时设置线条和背景的颜色）
    public int bgModel;
    //         类型（矩形、椭圆形等）
    public int shapeType;
    //         宽高比 = 宽/高
    public float whRatio;
}

