package com.blued.shapeview.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

/**
 * 处理背景的类
 *
 * 注意：
 * 1.改变bgModel，需要同时设置线条和背景颜色，否则背景会空白
 * 2.代码设置color，可通过Color.parseColor来获取，比如：Color.parseColor("#ffffff");
 * 3.设置defaultTouch=true后，需要为其设置点击事件，或者设置clickable=true才有效果
 *
 * Created by xiekaizhen on 2018/10/9.
 */
public class ShapeHelper {

    // 背景模式（默认所有设置均显示，纯线条，纯实体背景，纯渐变背景）
    public interface BG_MODEL {
        int DEFAULT = 0;
        int STROKE = 1;
        int SOLID = 2;
        int GRADIENT = 3;
    }

    // 背景触摸状态（未触摸、触摸、不可用）
    public interface BG_STATE {
        int NORMAL = 0;
        int PRESS = 1;
        int UNABLE = 2;
    }

    public interface ShapeView {
        ShapeModel getShapeModel();

        void setShapeModel(ShapeModel shapeModel);
    }

    public static StateListDrawable createStateListDrawable(ShapeModel model) {
        Drawable normalDrawable = model.bgDrawable;   // 正常背景
        Drawable pressDrawable = model.bgTouchDrawable;   // 触摸背景
        Drawable unableDrawable = model.bgUnableDrawable;   // unable背景

        if (normalDrawable == null) {
            normalDrawable = createGradientDrawable(BG_STATE.NORMAL, model);
        }

        if (pressDrawable == null) {
            if ((model.bgModel == BG_MODEL.DEFAULT && (model.solidTouchColor != 0 || model.solidTouchColor != 0 || model.startTouchColor != 0)) ||
                    (model.bgModel == BG_MODEL.SOLID && model.solidTouchColor != 0) ||
                    (model.bgModel == BG_MODEL.STROKE && model.strokeTouchColor != 0) ||
                    (model.bgModel == BG_MODEL.GRADIENT && model.startTouchColor != 0)) {
                pressDrawable = createGradientDrawable(BG_STATE.PRESS, model);
            } else if (model.defaultTouch && normalDrawable != null) {
                // 复制正常状态下的Drawable，然后setColorFilter增加灰色效果
                Drawable.ConstantState state = normalDrawable.getConstantState();
                if (state != null) {
                    pressDrawable = DrawableCompat.wrap(state.newDrawable()).mutate();
                    pressDrawable.setColorFilter(Color.parseColor("#d1d1d1"), PorterDuff.Mode.MULTIPLY);
                }
            }
        }

        if (unableDrawable == null) {
            if (model.solidUnableColor != 0 || model.strokeUnableColor != 0 || model.startUnableColor != 0) {
                unableDrawable = createGradientDrawable(BG_STATE.UNABLE, model);
            }
        }

        int enabled = android.R.attr.state_enabled;
        int pressed = android.R.attr.state_pressed;
        int focused = android.R.attr.state_focused;
        StateListDrawable bgDrawable = new StateListDrawable();
        if (pressDrawable != null) {
            bgDrawable.addState(new int[]{focused, pressed}, pressDrawable);
            bgDrawable.addState(new int[]{-focused, pressed}, pressDrawable);
        }
        if (unableDrawable != null) {
            bgDrawable.addState(new int[]{-enabled}, unableDrawable);
        }
        bgDrawable.addState(new int[]{}, normalDrawable);

        return bgDrawable;
    }

    /**
     * 初始化正常、触摸、unable状态下的Drawable
     *
     * @param bgState
     * @param model
     */
    private static GradientDrawable createGradientDrawable(int bgState, ShapeModel model) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        int solidColor, strokeColor, startColor, centerColor, endColor;
        switch (bgState) {
            case BG_STATE.NORMAL:
            default:
                solidColor = model.solidColor;
                strokeColor = model.strokeColor;
                startColor = model.startColor;
                centerColor = model.centerColor;
                endColor = model.endColor;
                break;
            case BG_STATE.PRESS:
                solidColor = model.solidTouchColor;
                strokeColor = model.strokeTouchColor;
                startColor = model.startTouchColor;
                centerColor = model.centerTouchColor;
                endColor = model.endTouchColor;
                break;
            case BG_STATE.UNABLE:
                solidColor = model.solidUnableColor;
                strokeColor = model.strokeUnableColor;
                startColor = model.startUnableColor;
                centerColor = model.centerUnableColor;
                endColor = model.endUnableColor;
                break;
        }

        switch (model.bgModel) {
            // 指定只显示线条
            case BG_MODEL.STROKE:
                gradientDrawable.setStroke(model.strokeWidth, strokeColor, model.strokeDashWidth, model.strokeDashGap);
                gradientDrawable.setColor(0);
                break;
            // 指定只显示背景, 纯背景
            case BG_MODEL.SOLID:
                gradientDrawable.setStroke(0, 0, 0, 0);
                gradientDrawable.setColor(solidColor);
                break;
            // 指定只显示背景, 渐变色
            case BG_MODEL.GRADIENT:
                gradientDrawable.setStroke(0, 0, 0, 0);
                setGradientColors(gradientDrawable, model, startColor, centerColor, endColor);
                break;
            // 默认情况下，线条、纯背景、渐变背景均会显示，渐变色优先于纯背景
            case BG_MODEL.DEFAULT:
                gradientDrawable.setStroke(model.strokeWidth, strokeColor, model.strokeDashWidth, model.strokeDashGap);
                gradientDrawable.setColor(solidColor);
                setGradientColors(gradientDrawable, model, startColor, centerColor, endColor);
                break;
        }

        if (model.shapeType != GradientDrawable.RECTANGLE) {
            gradientDrawable.setShape(model.shapeType);
        }

        // 设置圆角
        if (model.shapeType != GradientDrawable.OVAL) {
            if (model.cornerRadius != 0) {
                gradientDrawable.setCornerRadius(model.cornerRadius);
            } else {
                //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
                gradientDrawable.setCornerRadii(new float[]{model.topLeftRadius,
                        model.topLeftRadius, model.topRightRadius, model.topRightRadius,
                        model.bottomRightRadius, model.bottomRightRadius, model.bottomLeftRadius,
                        model.bottomLeftRadius});
            }
        }

        return gradientDrawable;
    }

    /**
     * 设置渐变颜色
     *
     * @param gradientDrawable
     * @param model
     * @param startColor
     * @param centerColor
     * @param endColor
     */
    private static void setGradientColors(GradientDrawable gradientDrawable, ShapeModel model, int startColor, int centerColor, int endColor) {
        if (startColor != 0) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                if (centerColor != 0) {
                    gradientDrawable.setColors(new int[]{startColor, centerColor, endColor});
                } else {
                    gradientDrawable.setColors(new int[]{startColor, endColor});
                }

                GradientDrawable.Orientation orientation;
                switch (model.gradientAngle) {
                    case 45:
                        orientation = GradientDrawable.Orientation.BL_TR;
                        break;
                    case 90:
                        orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                        break;
                    case 135:
                        orientation = GradientDrawable.Orientation.BR_TL;
                        break;
                    case 180:
                        orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                        break;
                    case 225:
                        orientation = GradientDrawable.Orientation.TR_BL;
                        break;
                    case 270:
                        orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                        break;
                    case 315:
                        orientation = GradientDrawable.Orientation.TL_BR;
                        break;
                    default:
                        orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                        break;
                }
                gradientDrawable.setOrientation(orientation);

                gradientDrawable.setGradientType(model.gradientType);
                if (model.gradientType == GradientDrawable.RADIAL_GRADIENT) {
                    gradientDrawable.setGradientRadius(model.gradientRadius);
                }
                gradientDrawable.setGradientCenter(model.gradientCenterX, model.gradientCenterY);
            } else {
                gradientDrawable.setColor(startColor);
            }
        }
    }


    /**
     * 创建字体selector
     *
     * @return
     */
    public static ColorStateList createColorStateList(ShapeModel model) {
        int[] colors = new int[]{model.textTouchColor, model.textTouchColor, model.textUnableColor, model.textColor};
        int[][] states = new int[4][];
        int enabled = android.R.attr.state_enabled;
        int pressed = android.R.attr.state_pressed;
        int focused = android.R.attr.state_focused;
        states[0] = new int[]{focused, pressed};
        states[1] = new int[]{-focused, pressed};
        states[2] = new int[]{-enabled};
        states[3] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);

        return colorList;
    }

    // 设置背景和字体颜色总入口
    public static void setShapeColor(ShapeView shapeView) {
        ShapeModel model = shapeView.getShapeModel();
        ((View) shapeView).setBackgroundDrawable(ShapeHelper.createStateListDrawable(model));
        if (shapeView instanceof AppCompatTextView || shapeView instanceof TextView) {
            ((TextView) shapeView).setTextColor(ShapeHelper.createColorStateList(model));
        }
    }

    // 设置shapeModel
    public static void setShapeModel(ShapeView shapeView, ShapeModel shapeModel) {
        shapeView.setShapeModel(shapeModel);
    }

    // 获取shapeModel
    public static ShapeModel getShapeModel(ShapeView shapeView) {
        ShapeModel model = shapeView.getShapeModel();
        return model;
    }

    // 切换模式，默认字体颜色为线条颜色或者白色
    public static void setBgModel(ShapeView shapeView, int bgModel) {
        if (bgModel == ShapeHelper.BG_MODEL.STROKE) {
            ShapeModel model = getShapeModel(shapeView);
            setBgModel(shapeView, bgModel, model.strokeColor, model.strokeColor);
        } else {
            setBgModel(shapeView, bgModel, Color.WHITE, Color.WHITE);
        }
    }

    // 切换模式，同时设置字体颜色、触摸字体颜色
    public static void setBgModel(ShapeView shapeView, int bgModel, int textColor, int textTouchColor) {
        setBgModel(shapeView, bgModel, textColor, textTouchColor, textColor);
    }

    // 切换模式，同时设置字体颜色、触摸字体颜色、unable字体颜色
    public static void setBgModel(ShapeView shapeView, int bgModel, int textColor, int textTouchColor, int textUnableColor) {
        ShapeModel model = getShapeModel(shapeView);
        if (model.bgModel == bgModel) return;
        model.bgModel = bgModel;
        model.textColor = textColor;
        model.textTouchColor = textTouchColor;
        model.textUnableColor = textUnableColor;
        setShapeModel(shapeView, model);
    }

    // 设置触摸字体
    public static void setTextColor(ShapeView shapeView, int textColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.textColor = textColor;
        setShapeModel(shapeView, model);
    }

    // 设置触摸字体
    public static void setTextTouchColor(ShapeView shapeView, int textTouchColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.textTouchColor = textTouchColor;
        setShapeModel(shapeView, model);
    }

    // 设置unable字体颜色
    public static void setTextUnableColor(ShapeView shapeView, int textUnableColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.textUnableColor = textUnableColor;
        setShapeModel(shapeView, model);
    }

    // 设置纯背景颜色
    public static void setSolidColor(ShapeView shapeView, int solidColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.solidColor = solidColor;
        setShapeModel(shapeView, model);
    }

    // 设置纯背景、触摸颜色
    public static void setSolidColor(ShapeView shapeView, int solidColor, int solidTouchColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.solidColor = solidColor;
        model.solidTouchColor = solidTouchColor;
        setShapeModel(shapeView, model);
    }

    // 设置纯背景、触摸颜色、unable时的纯背景颜色
    public static void setSolidColor(ShapeView shapeView, int solidColor, int solidTouchColor, int solidUnableColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.solidColor = solidColor;
        model.solidTouchColor = solidTouchColor;
        model.solidUnableColor = solidUnableColor;
        setShapeModel(shapeView, model);
    }

    // 设置纯背景触摸颜色
    public static void setSolidTouchColor(ShapeView shapeView, int solidTouchColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.solidTouchColor = solidTouchColor;
        setShapeModel(shapeView, model);
    }

    // 设置线条颜色
    public static void setStrokeColor(ShapeView shapeView, int strokeColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.strokeColor = strokeColor;
        setShapeModel(shapeView, model);
    }

    // 设置触摸线条颜色
    public static void setStrokeTouchColor(ShapeView shapeView, int startTouchColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.startTouchColor = startTouchColor;
        setShapeModel(shapeView, model);
    }

    // 设置unable线条颜色
    public static void setStrokeUnableColor(ShapeView shapeView, int strokeUnableColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.strokeUnableColor = strokeUnableColor;
        setShapeModel(shapeView, model);
    }

    // 设置线条颜色、线条大小、间隙大小
    public static void setStrokeColor(ShapeView shapeView, int strokeColor, int strokeDashWidth, int strokeDashGap) {
        ShapeModel model = getShapeModel(shapeView);
        model.strokeColor = strokeColor;
        model.strokeDashWidth = strokeDashWidth;
        model.strokeDashGap = strokeDashGap;
        setShapeModel(shapeView, model);
    }

    // 设置渐变颜色（start、end）
    public static void setGradientColor(ShapeView shapeView, int startColor, int endColor) {
        setGradientColor(shapeView, startColor, Color.TRANSPARENT, endColor);
    }

    // 设置渐变颜色（start、center、end）
    public static void setGradientColor(ShapeView shapeView, int startColor, int centerColor, int endColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.startColor = startColor;
        model.centerColor = centerColor;
        model.endColor = endColor;
        setShapeModel(shapeView, model);
    }

    // 设置触摸渐变颜色（start、end）
    public static void setGradientTouchColor(ShapeView shapeView, int startTouchColor, int endTouchColor) {
        setGradientTouchColor(shapeView, startTouchColor, Color.TRANSPARENT, endTouchColor);
    }

    // 设置触摸渐变颜色（start、center、end）
    public static void setGradientTouchColor(ShapeView shapeView, int startTouchColor, int centerTouchColor, int endTouchColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.startTouchColor = startTouchColor;
        model.centerTouchColor = centerTouchColor;
        model.endTouchColor = endTouchColor;
        setShapeModel(shapeView, model);
    }

    // 设置unable渐变颜色（start、end）
    public static void setGradientUnableColor(ShapeView shapeView, int startUnableColor, int endUnableColor) {
        setGradientUnableColor(shapeView, startUnableColor, Color.TRANSPARENT, endUnableColor);
    }

    // 设置unable渐变颜色（start、center、end）
    public static void setGradientUnableColor(ShapeView shapeView, int startUnableColor, int centerUnableColor, int endUnableColor) {
        ShapeModel model = getShapeModel(shapeView);
        model.startUnableColor = startUnableColor;
        model.centerUnableColor = centerUnableColor;
        model.endUnableColor = endUnableColor;
        setShapeModel(shapeView, model);
    }

    // 设置四角角度
    public static void setCornerRadius(ShapeView shapeView, float cornerRadius) {
        ShapeModel model = getShapeModel(shapeView);
        model.cornerRadius = cornerRadius;
        setShapeModel(shapeView, model);
    }

    // 设置四角角度
    public static void setCornerRadius(ShapeView shapeView, float topLeftRadius, float topRightRadius, float bottomLeftRadius, float bottomRightRadius) {
        ShapeModel model = getShapeModel(shapeView);
        model.topLeftRadius = topLeftRadius;
        model.topRightRadius = topRightRadius;
        model.bottomLeftRadius = bottomLeftRadius;
        model.bottomRightRadius = bottomRightRadius;
        setShapeModel(shapeView, model);
    }

}

