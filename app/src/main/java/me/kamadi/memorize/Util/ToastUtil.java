package me.kamadi.memorize.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Madiyar on 19.04.2016.
 */
public class ToastUtil {
    public static void show(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
