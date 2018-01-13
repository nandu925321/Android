package com.sourabhparime.cs478.app1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_RESULT = 1;
    private static boolean AUTH_U;
    private static final String PERM = "edu.uic.cs478.project3";
    private static final String NBA_TEAM = "NBA";
    private static final String MLB_TEAM = "MLB";
    private static final String TOAST_INTENT =
            "edu.uic.cs478.App2.showToast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind UI elements here
        Button nba = (Button) findViewById(R.id.button2);
        Button mlb = (Button) findViewById(R.id.button);

        // Set onlick listners here
        nba.setOnClickListener(nbalistner);
        mlb.setOnClickListener(mlblistner);

    }

    //Listners
    public View.OnClickListener nbalistner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startNBABroadcast();
        }
    };
    public View.OnClickListener mlblistner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startMLBBroadcast();
        }
    };

    //Check or request permissions
    public void auth() {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.project3);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            AUTH_U = true;
        } else if (PackageManager.PERMISSION_DENIED == permissionCheck) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.project3}, PERMISSION_REQUEST_RESULT);


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_RESULT) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.project3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AUTH_U = true;
            } else {

                AUTH_U = false;

            }
        }
    }

    //start or fire intents
    public void startNBABroadcast()

    {
        //Check permissions - No dont
        auth();
        if(AUTH_U) {
            Intent inte = new Intent(TOAST_INTENT);
            inte.putExtra("Team", NBA_TEAM);
            inte.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendOrderedBroadcast(inte, PERM);
            Toast.makeText(getApplicationContext(), "NBA Broadcast Sent",
                    Toast.LENGTH_LONG).show();
        }else
        {Toast.makeText(getApplicationContext(),"Permissions Denied - Enable Permisssions",Toast.LENGTH_LONG).show();}

    }

    public void startMLBBroadcast() {
        auth();
        if (AUTH_U) {
            Intent inte = new Intent(TOAST_INTENT);
            inte.putExtra("Team", MLB_TEAM);
            inte.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendOrderedBroadcast(inte, PERM);
            Toast.makeText(getApplicationContext(), "MLB Broadcast Sent",
                    Toast.LENGTH_LONG).show();
        }else
        {Toast.makeText(getApplicationContext(),"Permissions Denied - Enable Permisssions",Toast.LENGTH_LONG).show();}
    }
}