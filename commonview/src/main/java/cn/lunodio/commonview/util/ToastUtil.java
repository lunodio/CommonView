package cn.lunodio.commonview.util;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {

    /**
     * @param context
     * @param msg
     */
    public static void show(final Context context, final String msg) {
        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * @param context
     * @param msgResId
     */
    public static void show(final Context context, final int msgResId) {
        final Toast toast = Toast.makeText(context, msgResId, Toast.LENGTH_SHORT);
        toast.show();
    }
}
