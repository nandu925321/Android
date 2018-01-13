package com.sourabhparime.cs478.app3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;



public class NBAActivity extends AppCompatActivity implements
        NameFragmentNBA.ListSelectionListener {
    public static String[] teamNameArray;
    public static String[] urlArray;
    public int key = -1;
    private static boolean AUTH_U;
    private static final int PERMISSION_REQUEST_RESULT = 1;
    private LinkFragment link_frag;
    private NameFragmentNBA name_frag;
    private FragmentManager manager;
    private FrameLayout name, link;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get arrays from resources
        int orientation = getResources().getConfiguration().orientation;

        teamNameArray = getResources().getStringArray(R.array.NBA_Teams);
        urlArray = getResources().getStringArray(R.array.NBA_URLS);
        //get frame layouts and attach to manager
        name = (FrameLayout) findViewById(R.id.name_fragment_container);
        //link = (FrameLayout) findViewById(R.id.link_fragment_container);
        //get fragment manager
        if (savedInstanceState != null) {
            key = savedInstanceState.getInt("key");
        }
        manager = getFragmentManager();
        if (savedInstanceState != null && key != -1) {

            link_frag = new LinkFragment();
            name_frag = new NameFragmentNBA();
            if (orientation == 1) {

                name.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
                clearBackStack(manager);

                link_frag.setSetStyle(true);
                link_frag.setGoKey(key);
                FragmentTransaction ftrans = manager.beginTransaction();

                ftrans.replace(R.id.name_fragment_container, link_frag);
                ftrans.addToBackStack("link_port");
                ftrans.commit();
                manager.executePendingTransactions();

                /*
                link = (FrameLayout) findViewById(R.id.link_fragment_container);
                FragmentTransaction ftrans2 = manager.beginTransaction();
                clearBackStack(manager);
                name.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                link.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));

                name_frag.setShift(true);
                name_frag.setSett(key);
                link_frag.setGoKey(key);
                link_frag.setSetStyle(true);


                ftrans2.replace(R.id.name_fragment_container, name_frag);
                ftrans2.replace(R.id.link_fragment_container, link_frag);
                ftrans2.addToBackStack("link");
                ftrans2.commit();
                manager.executePendingTransactions();
                //name_frag.setShift(false);
*/
            } else if (orientation == 2) {
                //backstack issue - fix it!
                link = (FrameLayout) findViewById(R.id.link_fragment_container);
                FragmentTransaction ftrans2 = manager.beginTransaction();
                clearBackStack(manager);
                name.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                link.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));

                name_frag.setShift(true);
                name_frag.setSett(key);
                link_frag.setGoKey(key);
                link_frag.setSetStyle(true);


                ftrans2.replace(R.id.name_fragment_container, name_frag);
                ftrans2.replace(R.id.link_fragment_container, link_frag);
                ftrans2.addToBackStack("link");
                ftrans2.commit();
                manager.executePendingTransactions();
                //name_frag.setShift(false);

            }
            manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                                      @Override
                                                      public void onBackStackChanged() {
                                                          setLayout(true);
                                                      }
                                                  }
            );

        } else {
            clearBackStack(manager);
            link_frag = new LinkFragment();

            name_frag = new NameFragmentNBA();
            name_frag.setShift(false);

            FragmentTransaction transaction = manager.beginTransaction();
            //add names fragment

            transaction.replace(R.id.name_fragment_container, name_frag);
            //commit!
            transaction.commit();
            manager.executePendingTransactions();

            manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                                                      @Override
                                                      public void onBackStackChanged() {
                                                          setLayout(false);
                                                      }
                                                  }
            );

        }
    }


    public void setLayout(boolean b) {
        int orientation = getResources().getConfiguration().orientation;
        // add the fragments here and divide into ratio 1:2
        if (orientation == 1) {
            name.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            //link.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT));

        } else if (orientation == 2) {
            // make names to occupy only 1/3
            if (!link_frag.isAdded()) {
                link = (FrameLayout) findViewById(R.id.link_fragment_container);
                name.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
                link.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));

            } else {
                link = (FrameLayout) findViewById(R.id.link_fragment_container);
                name.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                link.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
            }


        }
    }

    // Called when the user selects an item in the names
    @Override
    public void onListSelection(int index) {
        auth();
        if(AUTH_U) {
            clearBackStack(manager);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == 1) {
                FragmentTransaction ftrans = manager.beginTransaction();
                link_frag = new LinkFragment();
                ftrans.replace(R.id.name_fragment_container, link_frag);
                ftrans.addToBackStack("Link_port");
                ftrans.commit();
                manager.executePendingTransactions();

            } else if (orientation == 2) {

                FragmentTransaction ftrans = manager.beginTransaction();
                link_frag = new LinkFragment();
               /* name_frag.setSett(index);
                name_frag.setShift(false);*/
                ftrans.replace(R.id.name_fragment_container, name_frag);
                ftrans.replace(R.id.link_fragment_container, link_frag);
                ftrans.addToBackStack("link");
                ftrans.commit();
               // name_frag.setShift(false);
                manager.executePendingTransactions();
            }
            key = index;
            if (link_frag.getShownIndex() != index) {
                link_frag.showTeamUrl(index);
            }
        }else
        {Toast.makeText(getApplicationContext(),"Permission Denied - Enable Permissions",Toast.LENGTH_SHORT).show();}
    }

    public void clearBackStack(FragmentManager manager) {

        //FragmentManager fm = manager.getgetSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); ++i) {
            manager.popBackStack();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("key", key);

    }

    // Main menu related stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle menu item id's here
        switch (item.getItemId()) {
            case R.id.mlbmenu:

                return createAndShootIntent();
            case R.id.nbamenu:
                Toast.makeText(getApplicationContext(), "You are already viewing NBA teams", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public boolean createAndShootIntent() {
        Intent inte = new Intent(NBAActivity.this, MLBActivity.class);
        startActivity(inte);
        return true;
    }


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

    @Override
    public void onBackPressed()
    {
          clearBackStack(manager);
    }
}