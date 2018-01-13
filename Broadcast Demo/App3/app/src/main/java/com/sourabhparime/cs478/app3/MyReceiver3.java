package com.sourabhparime.cs478.app3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sourabh on 3/25/2017.
 */

public class MyReceiver3 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        String class_type = intent.getStringExtra("Team");
        if(class_type.equalsIgnoreCase("NBA"))
        {

            Intent i1 = new Intent(context,NBAActivity.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i1);
            Toast.makeText(context,intent.getStringExtra("Team")+" received by App 3 ",Toast.LENGTH_LONG).show();
            Toast.makeText(context,intent.getStringExtra("Team")+" sent to frag ",Toast.LENGTH_SHORT).show();
        }
        else if(class_type.equalsIgnoreCase("MLB"))
        {
            Intent i1 = new Intent(context,MLBActivity.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i1);
            Toast.makeText(context,intent.getStringExtra("Team")+" received by App 3 ",Toast.LENGTH_LONG).show();
            Toast.makeText(context,intent.getStringExtra("Team")+" sent to frag ",Toast.LENGTH_LONG).show();

        }
    }
}
