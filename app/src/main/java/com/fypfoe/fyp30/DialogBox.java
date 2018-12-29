package com.fypfoe.fyp30;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DialogBox {
    public static Context context;
    public DialogBox(Context context2){
        context=context2;
    }
    public DialogBox(){
    }
    public void getDialog(){

        Toast.makeText(context,"chaonima",Toast.LENGTH_SHORT).show();

        Log.d("niama", "no0o");
    }
}
