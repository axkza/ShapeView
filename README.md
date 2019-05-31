# ShapeView
总共有4种类型：ShapeTextView、ShapeLinearLayout、ShapeRelativeLayout、ShapeFrameLayout。

主要用于实现宽高比、圆角、纯色背景、线条背景、渐变背景、触摸反馈（触摸切换背景，触摸切换字体颜色）、渐变字体颜色等功能。

## 属性

|说明|属性字段|
--|--|
字体颜色|textColor：正常字体颜色 <br> text_touch_color：按下的字体色 <br> text_unable_color：unable的字体色 <br> text_start_color：渐变字体开始颜色 <br> text_end_color：渐变字体结束颜色
drawable背景|bg_drawable：正常背景图 <br> bg_touch_drawable：按下背景图 <br> bg_unable_drawable：unable背景图 
纯色背景|solid_color：纯色背景 <br> solid_touch_color：按下时纯色背景 <br> solid_unable_color：unable时纯色背景
线条背景|stroke_color：边框色 <br> stroke_touch_color：按下时边框色 <br> stroke_unable_color：unable时边框色 <br> stroke_width：线条宽度 <br> stroke_dash_width：虚线宽度 <br> stroke_dash_gap：虚线间隙 
渐变颜色 | 正常渐变颜色 <br> gradient_start_color<br>gradient_center_color<br>gradient_end_color<br><br>按下的渐变颜色<br>gradient_start_touch_color<br>gradient_center_touch_color<br>gradient_end_touch_color<br><br>unable的渐变颜色<br>gradient_start_unable_color<br>gradient_center_unable_color<br>gradient_end_unable_color<br><br><br>渐变颜色的角度, 当angle=0时，渐变色是从左向右。 然后逆时针方向转，当angle=90时为从下往上。angle必须为45的整数倍<br>gradient_angle<br>gradient_type<br><br>渐变色半径, 当 android:type="radial" 时才使用。单独使用android:type="radial"会报错<br>gradient_radius<br><br>中心点(取值范围0-1)<br>gradient_center_x<br>gradient_center_y
角度 | corner_radius：四角角度<br>top_left_radius：左上角角度<br>top_right_radius：右上角角度<br>bottom_left_radius：左下角角度<br>bottom_right_radius：右下角角度
背景类型 | bg_model：背景类型，可设置只显示纯色、线条、或渐变背景、或默认为all，即纯色、线条、渐变背景都会显示出来
宽高比 | wh_ratio：宽/高的比例值
默认触摸背景反馈效果 | bg_default_touch：是否增加触摸背景置灰效果<br><br>ShapeTextView默认为true，其他默认false

<br>
<br>

## 代码调用 
所有动态设置ShapeView属性的尽量都用ShapeHelper去处理，比如：ShapeTextView shapeView = new ShapeTextView(context); 
##### 设置字体颜色
ShapeHelper.setTextColor(shapeView, getResources().getColor(R.color.black));
ShapeHelper.setTextColor(shapeView, 0xFFFF9183);
ShapeHelper.setTextColor(shapeView, Color.parseColor("#fae097"));
ShapeHelper.setTextColor(shapeView, 0xFFFF9183, 0xFFF45383, 0xFFF4563d); // 设置正常、触摸、unable字体颜色

##### 设置纯色背景
ShapeHelper.setSolidColor(shapeView, getResources().getColor(R.color.nafio_b));
ShapeHelper.setSolidColor(shapeView, 00xFFFF9183, 0xFFF45383, 0xFFF4563d); // 设置正常、触摸、unable纯色背景

##### 设置线条背景
ShapeHelper.setStrokeColor(shapeView, getResources().getColor(R.color.nafio_b));
ShapeHelper.setStrokeWidth(shapeView, 12, 10, 9);  // 设置线条宽度、虚线宽度、虚线间隔宽度

##### 设置渐变色背景
ShapeHelper.setGradientColor(shapeView, getResources().getColor(R.color.nafio_b), getResources().getColor(R.color.nafio_b));  // 设置起始和结束的渐变颜色

ShapeHelper.setGradientColor(shapeView, getResources().getColor(R.color.nafio_b), getResources().getColor(R.color.nafio_b), getResources().getColor(R.color.nafio_b));  // 设置起始、中间、结束的渐变颜色

##### 设置背景类型 （在线条、纯色、渐变色背景多种配合的情况下）
ShapeHelper.setBgModel(shapeView, ShapeHelper.BG_MODEL.SOLID);    // 设置为纯色背景
ShapeHelper.setBgModel(shapeView, ShapeHelper.BG_MODEL.STROKE);   // 设置为线条背景
ShapeHelper.setBgModel(shapeView, ShapeHelper.BG_MODEL.GRADIENT); // 设置为渐变色背景
ShapeHelper.setBgModel(shapeView, ShapeHelper.BG_MODEL.ALL);      // 设置线条、纯色、渐变色背景均显示

##### 设置增加默认背景置灰效果
ShapeHelper.setBgDefaultTouch(shapeView, true);

##### 设置圆角角度
ShapeHelper.setCornerRadius(shapeView, 10);
<br>
<br>
## 自定义动态修改属性
ShapeHelper如果没有涵盖到的修改属性的方法，可以自行通过ShapeHelper的getShapeModel方法获取属性，修改后通过setShapeModel方法重新设置，比如：
##### 修改宽高比
ShapeModel model = ShapeHelper.getShapeModel(shapeView);
model.whRatio = 2.4f;
ShapeHelper.setShapeModel(shapeView, model);


