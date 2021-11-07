package sleepchild.flashiex4;

import android.view.*;
import android.graphics.drawable.*;
import android.graphics.*;

public class Styler
{
    public static void roundCorners(View v, int color){
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(20);
        v.setBackground(gd);
    }

    public static void roundCorners(View v, String color){
        roundCorners(v, Color.parseColor(color));
    }
}
