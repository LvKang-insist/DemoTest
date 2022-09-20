package com.example.demotest;

/**
 * @author 345 QQ:1831712732
 * @name ImageLoaderUtils
 * @package com.example.demotest
 * @time 2022/02/23 14:36
 * @description
 */

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

public class ImageLoaderUtils {

    public static boolean assertValidRequest(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !isDestroy(activity);
        } else if (context instanceof ContextWrapper){
            ContextWrapper contextWrapper = (ContextWrapper) context;
            if (contextWrapper.getBaseContext() instanceof Activity){
                Activity activity = (Activity) contextWrapper.getBaseContext();
                return !isDestroy(activity);
            }
        }
        return true;
    }

    private static boolean isDestroy(Activity activity) {
        if (activity == null) {
            return true;
        }
        return activity.isFinishing() || activity.isDestroyed();
    }

}
