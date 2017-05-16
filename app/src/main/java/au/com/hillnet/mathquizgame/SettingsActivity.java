package au.com.hillnet.mathquizgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

/**
 * Created by matt- on 11/05/2017. ;)
 */

public class SettingsActivity extends AppCompatActivity implements View.OnSystemUiVisibilityChangeListener {

    private View decorView;
    private ActionBar actionBar;
    private GestureDetectorCompat gestureDetector;
    public Button backMenuClick1;
    CheckBox checkbox1;
    public static int bkgdchecked = 0;
    int checkedPasser, bkgdcheckedHandler;
    SharedPreferences prefs;


    // Enables full screen
    private static final int NO_CONTROLS =
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

    private static final int FULLSCREEN =
            View.SYSTEM_UI_FLAG_IMMERSIVE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


    public void back() {
        backMenuClick1 = (Button) findViewById(R.id.btn_menu1);
        backMenuClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent back = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(back);
                finish();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        prefs = getSharedPreferences("musicPositionPref", MODE_PRIVATE);

        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    checkbox1.setChecked(true);
                    // The check box is checked
                    bkgdchecked = 1;
                    checkedPasser = bkgdchecked;
                    SharedPreferences prefs = getSharedPreferences("musicPositionPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("musicPosition", checkedPasser);
                    editor.apply();
                } else {
                    checkbox1.setChecked(false);
                    // The check box is unchecked
                    bkgdchecked = 0;
                    checkedPasser = bkgdchecked;
                    SharedPreferences prefs = getSharedPreferences("musicPositionPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("musicPosition", checkedPasser);
                    editor.apply();
                }
            }
        });


        musicPositionHandler();
    }

    // Background music
    private void musicPositionHandler() {
        prefs = getSharedPreferences("musicPositionPref", MODE_PRIVATE);
        checkedPasser = prefs.getInt("musicPosition", 0);

        // if 0 == 0 music is on
        if (checkedPasser == 0) {
            checkbox1.setChecked(false);
            bkgdcheckedHandler = 0;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("setMusicPosition", bkgdcheckedHandler);
            editor.apply();

            // else if 1 == 1 music is off
        } else if (checkedPasser == 1) {
            checkbox1.setChecked(true);
            bkgdcheckedHandler = 1;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("setMusicPosition", bkgdcheckedHandler);
            editor.apply();


        } else {
            checkbox1.setChecked(false);
            bkgdcheckedHandler = 0;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("setMusicPosition", bkgdcheckedHandler);
            editor.apply();
            Toast.makeText(this, "bkgdchecked failed return music: playing.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        back();


        actionBar = getSupportActionBar();
        gestureDetector = new GestureDetectorCompat(this, new GestureHandler());
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(FULLSCREEN);
        decorView.setOnSystemUiVisibilityChangeListener(this);
        checkbox1 = (CheckBox) findViewById(R.id.gameMusicCheck);

    }

    //Enables fullscreen
    private void toggleControls() {
        int flags = decorView.getSystemUiVisibility();
        flags ^= NO_CONTROLS;
        decorView.setSystemUiVisibility(flags);

    }

    private class GestureHandler extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            toggleControls();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        if (actionBar == null) return;
        switch (visibility) {
            case NO_CONTROLS:
                actionBar.hide();
                break;
            default:
                actionBar.show();
        }
    }

    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
    }

    public void onPause() {
        super.onPause();

        // Stops played when on exit
        stopService(new Intent(this, MusicPlayer.class));

        if (bkgdchecked == 0) {

            // save state as on (0)
            bkgdchecked = 0;

        } else {

            // save state as off (1)
            bkgdchecked = 1;
        }
    }


}