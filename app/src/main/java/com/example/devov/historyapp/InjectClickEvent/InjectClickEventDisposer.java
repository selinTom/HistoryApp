package com.example.devov.historyapp.InjectClickEvent;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import static com.example.devov.historyapp.utils.xUtilsHelper.XLogE;

/**
 * Created by devov on 2017/3/16.
 */


public class InjectClickEventDisposer {
    private static Object object;

    public static synchronized void injectView(Object o) {
        object = o;
        if (!(object instanceof Activity)) {
            throw new RuntimeException("param must be a Activity!");
        }
        disposeAnnotation();
        object = null;
    }

    private static void disposeAnnotation() {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectClickEvent.class)) {
                InjectClickEvent injectClickEvent = field.getAnnotation(InjectClickEvent.class);
                int id = injectClickEvent.value();
                try {
                    View.OnClickListener listener = (View.OnClickListener) field.get(object);
                    ((Activity) object).findViewById(id).setOnClickListener(listener);
                } catch (Exception e) {
                   XLogE(e);
                }
            }
        }
    }
}
