package com.sourabhparime.cs478.tmm;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    // Declare variables to hold player stats, positions and thread tags
    public static final int THREAD_ONE_TAG = 0;
    public static final int THREAD_TWO_TAG = 1;
    public static final int DO_NOT_MAKE_A_MOVE = 0;
    public static final int MAKE_MOVE_ONE = 5;
    public static final int MAKE_MOVE_TWO = 6;
    public static ArrayList grid = new ArrayList();
    // public static int playertwo_stat[] = new int[9];
    public static final int DUMMY_FROM = 27;
    Thread p1 = new Thread(new Player1());
    Thread p2 = new Thread(new Player2());
    private ImageView tzero;
    private ImageView tone;
    private ImageView ttwo;
    private ImageView tthree;
    private ImageView tfour;
    private ImageView tfive;
    private ImageView tsix;
    private ImageView tseven;
    private ImageView teight;
    private static Handler player_one_handler;
    private static Handler player_two_handler;

    // override main handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int w = msg.what;
            switch (w) {
                case THREAD_ONE_TAG:

                    updateGrid(msg.arg1, msg.arg2, 1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");}

                    updateBoard(msg.arg1, msg.arg2, 1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");}
                    int result = check(1);
                    int result_2 = check(2);
                    if (result == 0 && result_2==0) {
                        System.out.println("Thread in one states"+p1.getState().name()+p2.getState().name());
                        Message msgToTwo = player_two_handler.obtainMessage(MainActivity.MAKE_MOVE_TWO);
                        player_two_handler.sendMessage(msgToTwo);
                    } else if (result == 1|| result_2==1) {
                        p1.interrupt();
                        p2.interrupt();
                        Toast.makeText(getApplicationContext(), "Player one has won the game", Toast.LENGTH_LONG).show();

                    }


                    break;
                case THREAD_TWO_TAG:

                    updateGrid(msg.arg1, msg.arg2, 2);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");}
                    updateBoard(msg.arg1, msg.arg2, 2);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");}
                    int result1_2 = check(1);
                    int resul2_2 = check(2);
                    if (resul2_2 == 0 && result1_2==0) {
                        System.out.println("Thread in two states"+p1.getState().name()+p2.getState().name());
                        Message msgToTwo = player_one_handler.obtainMessage(MainActivity.MAKE_MOVE_ONE);
                        player_one_handler.sendMessage(msgToTwo);
                    } else if (resul2_2 == 1 || result1_2==1) {
                        p1.interrupt();
                        p2.interrupt();
                        Toast.makeText(getApplicationContext(), "Player  two won the game", Toast.LENGTH_LONG).show();

                    }

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button) findViewById(R.id.button);
        tzero = (ImageView) findViewById(R.id.zero);
        tone = (ImageView) findViewById(R.id.one);
        ttwo = (ImageView) findViewById(R.id.two);
        tthree = (ImageView) findViewById(R.id.three);
        tfour = (ImageView) findViewById(R.id.four);
        tfive = (ImageView) findViewById(R.id.five);
        tsix = (ImageView) findViewById(R.id.six);
        tseven = (ImageView) findViewById(R.id.seven);
        teight = (ImageView) findViewById(R.id.eight);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireItUp();
            }
        });


    }

    public void fireItUp() {

        if (p1.getState().name().equalsIgnoreCase("RUNNABLE") || p2.getState().name().equalsIgnoreCase("RUNNABLE")) {

            mHandler.removeCallbacksAndMessages(null);
            //mHandler.removeMessages(THREAD_ONE_TAG);
            grid.set(0, 0);
            grid.set(1, 0);
            grid.set(2, 0);
            grid.set(3, 0);
            grid.set(4, 0);
            grid.set(5, 0);
            grid.set(6, 0);
            grid.set(7, 0);
            grid.set(8, 0);


            player_one_handler.removeCallbacksAndMessages(null);

            player_two_handler.removeCallbacksAndMessages(null);

            resetUI();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");}


            p1.interrupt();
            p2.interrupt();
            p1 = new Thread(new Player1());
            p2 = new Thread(new Player2());
            p1.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
            }
            p2.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
            }

            Message msgToOne = player_one_handler.obtainMessage(MainActivity.MAKE_MOVE_ONE);
                player_one_handler.sendMessage(msgToOne);
            }else{

                grid.add(0, 0);
                grid.add(1, 0);
                grid.add(2, 0);
                grid.add(3, 0);
                grid.add(4, 0);
                grid.add(5, 0);
                grid.add(6, 0);
                grid.add(7, 0);
                grid.add(8, 0);
                p1.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted!");
                }
                p2.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted!");
                }
                Message msgToOne = player_one_handler.obtainMessage(MainActivity.MAKE_MOVE_ONE);
                player_one_handler.sendMessage(msgToOne);

            }

        }



    public class Player1 extends Thread {


        @Override
        public void run() {

            while(!p1.isInterrupted()) {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                player_one_handler = new Handler() {
                    public void handleMessage(Message msg) {
                        // Act on the message
                        int w = msg.what;
                        switch (w) {
                            case MAKE_MOVE_ONE:
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    System.out.println("Thread interrupted!");
                                }
                                String move = makeMove(1);
                                char start = move.charAt(0);
                                char end = move.charAt(1);
                                int from = 0;
                                int to = 0;
                                if (String.valueOf(start).equalsIgnoreCase("z")) {
                                    from = DUMMY_FROM;
                                } else {
                                    from = Integer.parseInt(String.valueOf(start));
                                }
                                to = Integer.parseInt(String.valueOf(end));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    System.out.println("Thread interrupted!");
                                }
                                ;
                                Message msgToMain = mHandler.obtainMessage(MainActivity.THREAD_ONE_TAG);
                                msgToMain.arg1 = from;
                                msgToMain.arg2 = to;
                                mHandler.sendMessage(msgToMain);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Player one made a move", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;


                            case DO_NOT_MAKE_A_MOVE:
                                mHandler.removeMessages(w);
                                player_one_handler.removeMessages(w);

                                break;
                        }

                    }
                };
                Looper.loop();

            }
        }


    }

    public class Player2 extends Thread {

        @Override
        public void run() {
            while (!p2.isInterrupted()) {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                player_two_handler = new Handler() {
                    public void handleMessage(Message msg) {
                        // Act on the message
                        int w = msg.what;
                        switch (w) {
                            case MAKE_MOVE_TWO:
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    System.out.println("Thread interrupted!");
                                }
                                String move = makeMove(2);
                                char start = move.charAt(0);
                                char end = move.charAt(1);
                                int from = 0;
                                int to = 0;
                                if (String.valueOf(start).equalsIgnoreCase("z")) {
                                    from = DUMMY_FROM;
                                } else {
                                    from = Integer.parseInt(String.valueOf(start));
                                }
                                to = Integer.parseInt(String.valueOf(end));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    System.out.println("Thread interrupted!");
                                }
                                Message msgToMain = mHandler.obtainMessage(MainActivity.THREAD_TWO_TAG);
                                msgToMain.arg1 = from;
                                msgToMain.arg2 = to;
                                mHandler.sendMessage(msgToMain);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Player two made a move", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case DO_NOT_MAKE_A_MOVE:
                                mHandler.removeMessages(w);
                                player_two_handler.removeMessages(w);

                                break;
                        }

                    }
                };
                Looper.loop();


            }
        }
    }


    public void updateBoard(int from, int to, int player) {

        System.out.println("received f rom main " +from+to+player);
        if (from != DUMMY_FROM) {
            //3 is for emptying not a player number
            resolveUI(from, 3);
        }
        resolveUI(to, player);

    }

    public void resetUI() {
        for (int i = 0; i < 9; i++) {
            resolveUI(i, 3);
        }
    }

    public void resolveUI(int i, int player) {
        if (player == 3) {
            switch (i) {
                case 0:
                    tzero.setImageResource(R.drawable.empty);
                    break;
                case 1:
                    tone.setImageResource(R.drawable.empty);
                    break;
                case 2:
                    ttwo.setImageResource(R.drawable.empty);
                    break;
                case 3:
                    tthree.setImageResource(R.drawable.empty);
                    break;
                case 4:
                    tfour.setImageResource(R.drawable.empty);
                    break;
                case 5:
                    tfive.setImageResource(R.drawable.empty);
                    break;
                case 6:
                    tsix.setImageResource(R.drawable.empty);
                    break;
                case 7:
                    tseven.setImageResource(R.drawable.empty);
                    break;
                case 8:
                    teight.setImageResource(R.drawable.empty);
                    break;

            }
            ;
        } else if (player == 2) {


            switch (i) {
                case 0:
                    tzero.setImageResource(R.drawable.blue);
                    break;
                case 1:
                    tone.setImageResource(R.drawable.blue);
                    break;
                case 2:
                    ttwo.setImageResource(R.drawable.blue);
                    break;
                case 3:
                    tthree.setImageResource(R.drawable.blue);
                    break;
                case 4:
                    tfour.setImageResource(R.drawable.blue);
                    break;
                case 5:
                    tfive.setImageResource(R.drawable.blue);
                    break;
                case 6:
                    tsix.setImageResource(R.drawable.blue);
                    break;
                case 7:
                    tseven.setImageResource(R.drawable.blue);
                    break;
                case 8:
                    teight.setImageResource(R.drawable.blue);
                    break;

            }
            ;
        } else if (player == 1) {

            switch (i) {
                case 0:
                    tzero.setImageResource(R.drawable.red);
                    break;
                case 1:
                    tone.setImageResource(R.drawable.red);
                    break;
                case 2:
                    ttwo.setImageResource(R.drawable.red);
                    break;
                case 3:
                    tthree.setImageResource(R.drawable.red);
                    break;
                case 4:
                    tfour.setImageResource(R.drawable.red);
                    break;
                case 5:
                    tfive.setImageResource(R.drawable.red);
                    break;
                case 6:
                    tsix.setImageResource(R.drawable.red);
                    break;
                case 7:
                    tseven.setImageResource(R.drawable.red);
                    break;
                case 8:
                    teight.setImageResource(R.drawable.red);
                    break;

            }
            ;

        }

    }

    public void updateGrid(int from, int to, int player) {
        System.out.println("received f rom main " +from+to+player);

        if (from != DUMMY_FROM) {
            grid.set(from, Integer.valueOf(0));
        }
        grid.set(to, Integer.valueOf(player));
    }

    public String makeMove(int player) {
        // player one - vertical player 2 - horizontal
        String move = "";
        if (player == 1) {
            int from = 0;
            int to = 0;
            //get counts!
            ArrayList<Integer> list1 = new ArrayList<Integer>();
            ArrayList<Integer> list2 = new ArrayList<Integer>();
            ArrayList<Integer> list3 = new ArrayList<Integer>();
            list1.add(0);
            list1.add(3);
            list1.add(6);

            list2.add(1);
            list2.add(4);
            list2.add(7);

            list3.add(2);
            list3.add(5);
            list3.add(8);


            int group1_count = 0;
            int group2_count = 0;
            int group3_count = 0;

            for (Integer i : list1) {
                if (grid.get(i)==Integer.valueOf(1)) {
                    group1_count = group1_count + 1;
                }
            }
            for (Integer i : list2) {
                if (grid.get(i)==Integer.valueOf(1)) {
                    group2_count = group2_count + 1;
                }
            }
            for (Integer i : list3) {
                if (grid.get(i)==Integer.valueOf(1)) {
                    group3_count = group3_count + 1;
                }
            }

            // if counts are zero - that is no move yet on the board
            if (group1_count == group2_count && group2_count == group3_count && group3_count == 0) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 9);
                move = "z" + String.valueOf(randomNum);
                return move;
            }

            if (group1_count > group2_count && group1_count > group3_count) {
                ArrayList<Integer> combined = new ArrayList<Integer>();
                combined.addAll(list2);
                combined.addAll(list3);

                for (Integer i : combined) {
                    if (grid.get(i) == Integer.valueOf(1)) {
                        from = i;
                        break;
                    }

                }
                for (Integer i : list1) {
                    if (grid.get(i) == Integer.valueOf(0)) {
                        to = i;
                        break;
                    }
                }

            } else if (group2_count > group1_count && group2_count > group3_count) {
                ArrayList<Integer> combined = new ArrayList<Integer>();
                combined.addAll(list1);
                combined.addAll(list3);

                for (Integer i : combined) {
                    if (grid.get(i) == Integer.valueOf(1)) {
                        from = i;
                        break;
                    }

                }
                for (Integer i : list2) {
                    if (grid.get(i) == Integer.valueOf(0)) {
                        to = i;
                        break;
                    }
                }


            } else if (group3_count > group1_count && group3_count > group2_count) {

                ArrayList<Integer> combined = new ArrayList<Integer>();
                combined.addAll(list1);
                combined.addAll(list2);
                for (Integer i : combined) {
                    if (grid.get(i) == Integer.valueOf(1)) {
                        from = i;
                        break;
                    }

                }
                for (Integer i : list3) {
                    if (grid.get(i) == Integer.valueOf(0)) {
                        to = i;
                        break;
                    }
                }


            }


            // check for adding new pieces
            if(to==0&&grid.get(to)!=Integer.valueOf(0))
            {
                for(int i =0 ; i<9 ; i++)
                {
                    int pos = (Integer) grid.get(i);
                    if(pos==0)
                    {
                        to = i;
                        break;
                    }


                }
            }
            System.out.println("In make Move player 1 "+ from+to);
            if (from == 0 ) {
                move = "z" + String.valueOf(to);
            } else if (from != 0 ) {
                move = String.valueOf(from) + String.valueOf(to);
            }

        }

        if (player == 2) {
            int from = 0;
            int to = 0;
            //get counts!
            ArrayList<Integer> list1 = new ArrayList<Integer>();
            ArrayList<Integer> list2 = new ArrayList<Integer>();
            ArrayList<Integer> list3 = new ArrayList<Integer>();
            list1.add(0);
            list1.add(1);
            list1.add(2);

            list2.add(3);
            list2.add(4);
            list2.add(5);

            list3.add(6);
            list3.add(7);
            list3.add(8);


            int group1_count = 0;
            int group2_count = 0;
            int group3_count = 0;

            for (Integer i : list1) {
                if (grid.get(i)==Integer.valueOf(2)) {
                    group1_count = group1_count + 1;
                }
            }
            for (Integer i : list2) {
                if (grid.get(i)==Integer.valueOf(2)) {
                    group2_count = group2_count + 1;
                }
            }
            for (Integer i : list3) {
                if (grid.get(i)==Integer.valueOf(2)) {
                    group3_count = group3_count + 1;
                }
            }

            // if counts are zero - that is no move yet on the board
            if (group1_count == group2_count && group2_count == group3_count && group3_count == 0) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 9);
                move = "z" + String.valueOf(randomNum);
                return move;
            }

            if (group1_count > group2_count && group1_count > group3_count) {
                ArrayList<Integer> combined = new ArrayList<Integer>();
                combined.addAll(list2);
                combined.addAll(list3);

                for (Integer i : combined) {
                    if (grid.get(i) == Integer.valueOf(2)) {
                        from = i;
                        break;
                    }

                }
                for (Integer i : list1) {
                    if (grid.get(i) == Integer.valueOf(0)) {
                        to = i;
                        break;
                    }
                }

            } else if (group2_count > group1_count && group2_count > group3_count) {
                ArrayList<Integer> combined = new ArrayList<Integer>();
                combined.addAll(list1);
                combined.addAll(list3);

                for (Integer i : combined) {
                    if (grid.get(i) == Integer.valueOf(2)) {
                        from = i;
                        break;
                    }

                }
                for (Integer i : list2) {
                    if (grid.get(i) == Integer.valueOf(0)) {
                        to = i;
                        break;
                    }
                }


            } else if (group3_count > group1_count && group3_count > group2_count) {

                ArrayList<Integer> combined = new ArrayList<Integer>();
                combined.addAll(list1);
                combined.addAll(list2);
                for (Integer i : combined) {
                    if (grid.get(i) == Integer.valueOf(2)) {
                        from = i;
                        break;
                    }

                }
                for (Integer i : list3) {
                    if (grid.get(i) == Integer.valueOf(0)) {
                        to = i;
                        break;
                    }
                }


            }


            if(to==0&&grid.get(to)!=Integer.valueOf(0))
            {
                for(int i =0 ; i<9 ; i++)
                {
                    int pos = (Integer) grid.get(i);
                    if(pos==0)
                    {
                        to = i;
                        break;
                    }


                }
            }
            // check for adding new pieces
            System.out.println("In make Move player 2 "+ from+to);
            if (from == 0 ) {
                move = "z" + String.valueOf(to);
            } else if (from != 0 ) {
                move = String.valueOf(from) + String.valueOf(to);
            }

        }

        return move;

    }

    public int check(int p) {
        //check horizontal
        Integer player = Integer.valueOf(p);
        if ((grid.get(0) == grid.get(1) && grid.get(1) == grid.get(2) && grid.get(2) == player) ||
                (grid.get(3) == grid.get(4) && grid.get(4) == grid.get(5) && grid.get(5) == player) ||
                (grid.get(7) == grid.get(7) && grid.get(7) == grid.get(8) && grid.get(8) == player)
                ) {
            return 1;
        }
        if ((grid.get(0) == grid.get(3) && grid.get(3) == grid.get(6) && grid.get(6) == player) ||
                (grid.get(1) == grid.get(4) && grid.get(4) == grid.get(7) && grid.get(7) == player) ||
                (grid.get(2) == grid.get(5) && grid.get(5) == grid.get(8) && grid.get(8) == player)
                ) {
            return 1;
        }
        return 0;
    }






















































































































































































































































































































































































/*
// Write methods to check winning condition or a tie

    public String checkWinTie() {
        //Check if one has won
        String result = "";
        int result_player_one = check(1, playerone_stat);
        int result_player_two = check(2, playertwo_stat);
        if (result_player_one == 1) {
            result = "one";
        } else if (result_player_two == 1) {
            result = "two";
        } else {
            result = "undecided";
        }

        return result;
    }

    public int check(int player, int stat[]) {
        //check horizontal
        if ((stat[0] == stat[1] && stat[1] == stat[2] && stat[2] == player) ||
                (stat[3] == stat[4] && stat[4] == stat[5] && stat[5] == player) ||
                (stat[6] == stat[7] && stat[7] == stat[8] && stat[8] == player)
                ) {
            return 1;
        }
        if ((stat[0] == stat[3] && stat[3] == stat[6] && stat[6] == player) ||
                (stat[1] == stat[4] && stat[4] == stat[7] && stat[7] == player) ||
                (stat[2] == stat[5] && stat[5] == stat[8] && stat[8] == player)
                ) {
            return 1;
        }
        return 0;
    }

    public void updateStats(int player, int from, int to) {
        if (player == 1) {
            if(from!=DUMMY_FROM)
            {playerone_stat[from] = 0;}
            playerone_stat[to] = 1;
        } else if (player == 2) {
            if(from!=DUMMY_FROM)
            {playerone_stat[from] = 0;}
            playertwo_stat[to] = 2;
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int w = msg.what;
            switch (w) {
                case THREAD_ONE_TAG:
                    updatedmsg = "updated from t1" + msg.arg1;
                    updateStats(1,msg.arg1,msg.arg2);
                    updateBoard( 1, playerone_stat);
                    String say = checkWinTie();
                    if(say.equalsIgnoreCase("one")||say.equalsIgnoreCase("two"))
                    {
                        Toast.makeText(getApplicationContext(),"Player "+say+" has won",Toast.LENGTH_LONG).show();

                        p1.interrupt();
                        p2.interrupt();
                    }else
                    {
                        if(p2.isInterrupted())
                        {
                            p2.start();
                        }
                        else
                        {
                            p2.run();
                        }
                    }
                    Toast.makeText(getApplicationContext(), updatedmsg, Toast.LENGTH_SHORT).show();
                    break;
                case THREAD_TWO_TAG:
                    updatedmsg = "not updated from t1 but t2";
                    updateStats(2,msg.arg1,msg.arg2);
                    updateBoard( 2, playertwo_stat);
                    String says = checkWinTie();
                    if(says.equalsIgnoreCase("one")||says.equalsIgnoreCase("two"))
                    {
                        Toast.makeText(getApplicationContext(),"Player "+says+" has won",Toast.LENGTH_LONG).show();

                        p1.interrupt();
                        p2.interrupt();
                    }else
                    {
                        if(p1.isInterrupted())
                        {
                            p1.start();
                        }
                        else
                        {
                            p1.run();
                        }
                    }
                    Toast.makeText(getApplicationContext(), updatedmsg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public Looper l = Looper.getMainLooper();
    public String updatedmsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button) findViewById(R.id.button);
        tzero = (TextView) findViewById(R.id.zero);
        tone =  (TextView) findViewById(R.id.one);
        ttwo = (TextView) findViewById(R.id.two);
        tthree = (TextView) findViewById(R.id.three);
        tfour = (TextView) findViewById(R.id.four);
        tfive = (TextView) findViewById(R.id.five);
        tsix = (TextView) findViewById(R.id.six);
        tseven = (TextView) findViewById(R.id.seven);
        teight = (TextView) findViewById(R.id.eight);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireItUp();
            }
        });


    }

    public void fireItUp() {


        resetUI();
        if(!p1.isInterrupted())
        {
            p1.interrupt();
            java.util.Arrays.fill(playerone_stat,0);
            updateBoard(1,playerone_stat);
        }
        if(!p2.isInterrupted())
        {
            p2.interrupt();
            java.util.Arrays.fill(playertwo_stat,0);
            updateBoard(2,playertwo_stat);
        }
        p1.run();


    }

    // Make Move fucntion---------------------------------------------------------------------------


    // Thread one - Player 1------------------------------------------------------------------------
    public class Player1 extends Thread {

        @Override
        public void run() {
            try { Thread.sleep(2000); }
            catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;
            String move = makeMove(1, playerone_stat);
            char from = move.charAt(0);
            char to = move.charAt(1);
            int start = 0;
            int end = 0;
            if(from=='z' || from =='t')
            {
                start = DUMMY_FROM;
            }else {
                start = Integer.parseInt(String.valueOf(from));
            }
            end = Integer.parseInt(String.valueOf(to));


            Message msg = mHandler.obtainMessage(MainActivity.THREAD_ONE_TAG);
            msg.arg1 = start;
            msg.arg2 = end;
            mHandler.sendMessage(msg);



        }

    }

    //Thread two - Player 2-------------------------------------------------------------------------
    public class Player2 extends Thread {

        //public Handler p2Handler = new Handler();


        @Override
        public void run() {

            try { Thread.sleep(2000); }
            catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;
            String move = makeMove(2, playertwo_stat);
            char from = move.charAt(0);
            char to = move.charAt(1);
            int start = 0;
            int end = 0;
            if(from=='z' || from =='t')
            {
                start = DUMMY_FROM;
            }else {
                start = Integer.parseInt(String.valueOf(from));
            }
            end = Integer.parseInt(String.valueOf(to));


            Message msg = mHandler.obtainMessage(MainActivity.THREAD_TWO_TAG);
            msg.arg1 = start;
            msg.arg2 = end;
            mHandler.sendMessage(msg);
        }
    }

    public void updateBoard(int player, int [] position)
    {
        for(int i  =0 ; i<9 ;i++)
        {
            if(position[i]==player)
            {
                resolveUI(i,"Player "+Integer.toString(player));
            }else
            {
                if(position[i]!=0)
                {resolveUI(i,"Empty");}
            }


        }

    }
    public void resetUI()
    {
        for(int i =0 ; i<9 ;i++)
        {
            playerone_stat[i]=0;
            playertwo_stat[i]=0;
            resolveUI(i,"Empty");
        }
    }
    public void resolveUI(int i , String text)
    {
        switch (i)
        {
            case 0: tzero.setText(text);
                break;
            case 1 : tone.setText(text);
                break;
            case 2 : ttwo.setText(text);
                break;
            case 3 : tthree.setText(text);
                break;
            case 4 :tfour.setText(text);
                break;
            case 5 :tfive.setText(text);
                break;
            case 6 : tsix.setText(text);
                break;
            case 7 : tseven.setText(text);
                break;
            case 8 : teight.setText(text);
                break;

    };
    }

    public String makeMove(int player, int[] stat) {
        //int move = 0;
        String move = "0";
        //go vertically
        if (player == 1) {
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            //check  0 3 6
            if (stat[0] == 1 || stat[3] == 1 || stat[6] == 1) {
                if (stat[0] == 1) {
                    count1++;
                }
                if (stat[3] == 1) {
                    count1++;
                }
                if (stat[6] == 1) {
                    count1++;
                }
            }
            //check  1 4 7
            if (stat[1] == 1 || stat[4] == 1 || stat[7] == 1) {
                if (stat[1] == 1) {
                    count2++;
                }
                if (stat[4] == 1) {
                    count2++;
                }
                if (stat[7] == 1) {
                    count2++;
                }
            }
            //check  2 5 8
            if (stat[2] == 1 || stat[5] == 1 || stat[8] == 1) {
                if (stat[2] == 1) {
                    count3++;
                }
                if (stat[5] == 1) {
                    count3++;
                }
                if (stat[8] == 1) {
                    count3++;
                }
            }
            //first move only
            if (count1 == count2 && count2 == count3 && count3 == 0) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 9);
                move = "z" + String.valueOf(randomNum);
            }

            //normal move
            if (count1 > count2 && count1 > count3) {
                int from = 0;
                int to = 0;
                int others[] = {1, 4, 7, 2, 5, 8};
                int current[] = {0, 3, 6};
                for (Integer i : others) {
                    if (playerone_stat[i] == 1) {
                        from = i;
                        break;
                    }
                }
                for (Integer i : current) {
                    if (playertwo_stat[i] == 0 && playerone_stat[i]==0) {
                        to = i;
                        break;
                    }
                }

                move = String.valueOf(from) + String.valueOf(to);
                if (from == 0) {
                    move = "t" + String.valueOf(to);
                }
                System.out.println("First number is largest.");
            } else if (count2 > count1 && count2 > count3) {


                int from = 0;
                int to = 0;
                int others[] = {0, 3, 6, 2, 5, 8};
                int current[] = {1, 4, 7};
                for (Integer i : others) {
                    if (playerone_stat[i] == 1) {
                        from = i;
                        break;
                    }
                }
                for (Integer i : current) {
                    if (playertwo_stat[i] == 0 && playerone_stat[i]==0) {
                        to = i;
                        break;
                    }
                }

                move = String.valueOf(from) + String.valueOf(to);
                if (from == 0) {
                    move = "t" + String.valueOf(to);
                }


                System.out.println("Second number is largest.");
            } else if (count3 > count1 && count3 > count2) {


                int from = 0;
                int to = 0;
                int others[] = {0, 3, 6, 1, 4, 7};
                int current[] = {2, 5, 8};
                for (Integer i : others) {
                    if (playerone_stat[i] == 1) {
                        from = i;
                        break;
                    }
                }
                for (Integer i : current) {
                    if (playertwo_stat[i] == 0 && playerone_stat[i]==0) {
                        to = i;
                        break;
                    }
                }

                move = String.valueOf(from) + String.valueOf(to);
                if (from == 0) {
                    move = "t" + String.valueOf(to);
                }

                System.out.println("Third number is largest.");
            }


        }
        //go horizontally
        else if (player == 2) {


            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            //check  0 1 2
            if (stat[0] == 2 || stat[1] == 2 || stat[2] == 2) {
                if (stat[0] == 2) {
                    count1++;
                }
                if (stat[1] == 2) {
                    count1++;
                }
                if (stat[2] == 2) {
                    count1++;
                }
            }
            //check  3 4 5
            if (stat[3] == 2 || stat[4] == 2 || stat[5] == 2) {
                if (stat[3] == 2) {
                    count2++;
                }
                if (stat[4] == 2) {
                    count2++;
                }
                if (stat[5] == 2) {
                    count2++;
                }
            }
            //check  6 7 8
            if (stat[6] == 2 || stat[7] == 2 || stat[8] == 2) {
                if (stat[6] == 2) {
                    count3++;
                }
                if (stat[7] == 2) {
                    count3++;
                }
                if (stat[8] == 2) {
                    count3++;
                }
            }
            //first move only
            if (count1 == count2 && count2 == count3 && count3 == 0) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 9);
                if (playertwo_stat[randomNum] != 0) {
                    randomNum = ThreadLocalRandom.current().nextInt(0, 9);
                }
                move = "z" + String.valueOf(randomNum);
            }

            //normal move
            if (count1 > count2 && count1 > count3) {
                int from = 0;
                int to = 0;
                int others[] = {3, 4, 5, 6, 7, 8};
                int current[] = {0, 1, 2};
                for (Integer i : others) {
                    if (playertwo_stat[i] == 2) {
                        from = i;
                        break;
                    }
                }

                for (Integer i : current) {
                    if (playertwo_stat[i] == 0 && playerone_stat[i]==0) {
                        to = i;
                        break;
                    }
                }

                move = String.valueOf(from) + String.valueOf(to);
                if (from == 0) {
                    move = "t" + String.valueOf(to);
                }
                System.out.println("First number is largest.");
            } else if (count2 > count1 && count2 > count3) {


                int from = 0;
                int to = 0;
                int others[] = {0, 1, 2, 6, 7, 8};
                int current[] = {3, 4, 5};
                for (Integer i : others) {
                    if (playertwo_stat[i] == 2) {
                        from = i;
                        break;
                    }
                }
                for (Integer i : current) {
                    if (playertwo_stat[i] == 0 && playerone_stat[i]==0) {
                        to = i;
                        break;
                    }
                }

                move = String.valueOf(from) + String.valueOf(to);
                if (from == 0) {
                    move = "t" + String.valueOf(to);
                }


                System.out.println("Second number is largest.");
            } else if (count3 > count1 && count3 > count2) {


                int from = 0;
                int to = 0;
                int others[] = {0, 1, 2, 3, 4, 5};
                int current[] = {6, 7, 8};
                for (Integer i : others) {
                    if (playertwo_stat[i] == 2) {
                        from = i;
                        break;
                    }
                }
                for (Integer i : current) {
                    if (playertwo_stat[i] == 0 && playerone_stat[i]==0) {
                        to = i;
                        break;
                    }
                }

                move = String.valueOf(from) + String.valueOf(to);
                if (from == 0) {
                    move = "t" + String.valueOf(to);
                }

                System.out.println("Third number is largest.");
            }


        }

        return move;
    }
*/
}

