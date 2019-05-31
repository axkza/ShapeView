# ShapeView
总共有4种类型：ShapeTextView、ShapeLinearLayout、ShapeRelativeLayout、ShapeFrameLayout。

主要用于实现宽高比、圆角、纯色背景、线条背景、渐变背景、触摸反馈（触摸切换背景，触摸切换字体颜色）、渐变字体颜色等功能。

**属性**


| 属性名 | 说明 | 
| 字体颜色 | textColor：正常字体颜色 <br> text_touch_color：按下的字体色 <br> text_unable_color：unable的字体色 <br> text_start_color：渐变字体开始颜色 <br> text_end_color：渐变字体结束颜色| 
| drawable背景 | bg_drawable：正常背景图 <br> bg_touch_drawable：按下背景图 <br> bg_unable_drawable：unable背景图 | 
| 纯色背景 |solid_color：纯色背景 <br> solid_touch_color：按下时纯色背景 <br> solid_unable_color：unable时纯色背景 | 
| 线条背景 | stroke_color：边框色 <br> stroke_touch_color：按下时边框色 <br> stroke_unable_color：unable时边框色 <br> stroke_width：线条宽度 <br> stroke_dash_width：虚线宽度 <br> stroke_dash_gap：虚线间隙 |




| bg_default_touch | 是否有默认触摸置灰的效果，以正常背景为基础，添加一个置灰的效果 |

        <!---->
        <attr name=""/>
        <attr name=""/>
        <attr name="gradient_end_color"/>
        <!--按下的渐变颜色-->
        <attr name="gradient_start_touch_color"/>
        <attr name="gradient_center_touch_color"/>
        <attr name="gradient_end_touch_color"/>
        <!--unable的渐变颜色-->
        <attr name="gradient_start_unable_color" />
        <attr name="gradient_center_unable_color"/>
        <attr name="gradient_end_unable_color"/>
        <!--渐变颜色的角度, 当angle=0时，渐变色是从左向右。 然后逆时针方向转，当angle=90时为从下往上。angle必须为45的整数倍-->
        <attr name="gradient_angle"/>
        <attr name="gradient_type" />
        <!-- 渐变色半径, 当 android:type="radial" 时才使用。单独使用android:type="radial"会报错 -->
        <attr name="gradient_radius"/>
        <!-- 中心点(取值范围0-1) -->
        <attr name="gradient_center_x"/>
        <attr name="gradient_center_y"/>
        <!--四角角度-->
        <attr name="corner_radius"/>
        <attr name="top_left_radius"/>
        <attr name="top_right_radius"/>
        <attr name="bottom_left_radius"/>
        <attr name="bottom_right_radius"/>
        <!--shape类型-->
        <attr name="shape_type"/>
        <!--背景类型-->
        <attr name="bg_model"/>
        <!-- 宽高比 -->
        <attr name="wh_ratio"/>
