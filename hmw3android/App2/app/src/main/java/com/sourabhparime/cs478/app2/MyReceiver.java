package com.sourabhparime.cs478.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sourabh on 3/24/2017.
 */

  public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    // pop up the toast here

        Toast.makeText(context,intent.getStringExtra("Team")+" received by App 2 ",Toast.LENGTH_LONG).show();
    }
}
