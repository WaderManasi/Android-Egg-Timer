package com.example.twirktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive=  false;
    CountDownTimer countDownTimer;

    public void updateTimer(int secl)
    {
        int min=(int) secl/60;
        int sec=secl-min*60;
        String zeroString = Integer.toString(sec);
        if(sec<=9)

        {
            zeroString="0"+zeroString;
        }

        timerTextView.setText(Integer.toString(min) + ":" + zeroString);

    }
    public void resetTime()
    {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go !");
        timerSeekBar.setEnabled(true);
        counterIsActive=false;

    }

    public void controlTimer(View view)
    {
       if(counterIsActive==false) {
           counterIsActive = true;
           timerSeekBar.setEnabled(false);
           controllerButton.setText("Stop");
           countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
               @Override
               public void onTick(long millisUntilFinished) {
                   updateTimer((int) millisUntilFinished / 1000);
               }

               @Override
               public void onFinish() {

                   ImageView egg = (ImageView) findViewById(R.id.egg);
                   egg.animate().alpha(0f);
                   egg.animate().rotation(360).setDuration(2000);
                   ImageView chick = (ImageView) findViewById(R.id.chick);
                   chick.animate().alpha(1f);
                    /*TextView controllerButton = (TextView) findViewById(R.id.controllerButton);
                    controllerButton.animate().alpha(0f);*/
                   final MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.happysound);
                   mplayer.start();

                   Handler handler=new Handler();
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           mplayer.stop();
                       }
                   }, 10 * 1000);
                   Log.i("finished", "Tine done");
                   resetTime();
                   controllerButton.setText("Stop");
               }

           }.start();
       }
       else {
           resetTime();
           ImageView egg = (ImageView) findViewById(R.id.egg);
           egg.animate().alpha(1f);
           ImageView chick = (ImageView) findViewById(R.id.chick);
           chick.animate().alpha(0f);
                    /*TextView controllerButton = (TextView) findViewById(R.id.controllerButton);
                    controllerButton.animate().alpha(0f);*/

           Log.i("reset", "Tine done");
       }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView egg = (ImageView) findViewById(R.id.egg);
        egg.animate().rotation(360).setDuration(2000);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        timerSeekBar.setMax(600);   //60min i.e 600sec
        timerSeekBar.setProgress(30); //30sec
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}