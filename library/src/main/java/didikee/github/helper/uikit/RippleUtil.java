package didikee.github.helper.uikit;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by didikee on 2017/11/2.
 * 使用代码创建水波效果，兼容低版本
 */

public final class RippleUtil {

    public static Drawable createRipple(int rippleColor, int backgroundColor) {
        return createRipple(rippleColor, backgroundColor, null);
    }

    public static Drawable createRipple(Context context, int rippleColorRes, int backgroundColorRes) {
        return createRipple(ContextCompat.getColor(context, rippleColorRes), ContextCompat.getColor(context, backgroundColorRes));
    }

    public static Drawable createRipple(int rippleColor, int backgroundColor, Drawable mask) {
        return createRipple(rippleColor, new ColorDrawable(backgroundColor), mask);
    }

    public static Drawable createTransparentRipple(int rippleColor) {
        GradientDrawable mask = new GradientDrawable();
        mask.setColor(Color.WHITE);
        return RippleUtil.createRipple(rippleColor, Color.TRANSPARENT, mask);
    }

    public static Drawable createRipple(int rippleColor, Drawable background, Drawable mask) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(rippleColor),
                    background,
                    mask);
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(rippleColor));
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, new ColorDrawable(rippleColor));
            stateListDrawable.addState(new int[]{android.R.attr.state_activated}, new ColorDrawable(rippleColor));
            stateListDrawable.addState(new int[]{}, background);
            return stateListDrawable;
        }
    }

    public static Drawable createRipple(int rippleColor, Drawable backgroundDrawable) {
        return createRipple(rippleColor, backgroundDrawable, null);
    }

}
