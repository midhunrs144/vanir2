package com.vanir.com;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class DialogUtilities {



    public static Dialog showProgressBar(Activity activity){
        Dialog dialog=new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = activity.getLayoutInflater().inflate(R.layout.progress_bar, null);
        ImageView imgview  = view.findViewById(R.id.imgViewpb);
        Glide.with(activity).load(R.drawable.gifloader).into(imgview);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= WindowManager.LayoutParams.DIM_AMOUNT_CHANGED;
        window.setAttributes(wlp);
        //window.getDecorView().setBackgroundColor(AppClass.currentAct.getResources().getColor(R.color.float_transparent));
        return dialog;
    }




}
