package br.com.timer.mk.mktimer;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnStop;
    private Button btnReset;

    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updateTimer = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView) findViewById(R.id.timer_label);
        btnStart = (Button) findViewById(R.id.btn_timer_start);
        btnStop = (Button) findViewById(R.id.btn_timer_stop);
        btnReset = (Button) findViewById(R.id.btn_timer_reset);

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updateTimer = 0L;
                startTime = 0L;

                customHandler.removeCallbacks(updateTimerThread);

                timerValue.setText("00:00:000");
            }
        });
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTimer = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updateTimer / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updateTimer % 1000);
            String text = String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds);

            timerValue.setText(text);

            customHandler.postDelayed(this, 0);
        }
    };
}
