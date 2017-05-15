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

/**
 * Created by matt- on 11/05/2017. ;)
 */

public class SettingsActivity extends AppCompatActivity implements View.OnSystemUiVisibilityChangeListener {

    private View decorView;
    private ActionBar actionBar;
    private GestureDetectorCompat gestureDetector;
    public Button backMenuClick1;
    CheckBox checkbox1;
    private SharedPreferences prefs;
    int bkgdcheckedPlaceHolder;
    public static int bkgdchecked = 0;


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

    private void bkgdMusicOff() {
        checkbox1 = (CheckBox) findViewById(R.id.gameMusicCheck);
        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    // The check box is checked
                    bkgdchecked = 1;
                } else {
                    bkgdchecked = 0;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        prefs = getSharedPreferences("musicPositionPref", MODE_PRIVATE);
        prefs = getSharedPreferences("btnPositionPrefs", MODE_PRIVATE);

        bkgdMusicOff();
        musicPositionHandler();

    }

    private void musicPositionHandler() {
        prefs = getSharedPreferences("musicPositionPref", MODE_PRIVATE);
        bkgdcheckedPlaceHolder = prefs.getInt("musicPosition", 0);

        if (bkgdchecked == bkgdcheckedPlaceHolder) {
            checkbox1.setChecked(false);
            bkgdchecked = 0;
            bkgdcheckedPlaceHolder = bkgdchecked;
            SharedPreferences prefs = getSharedPreferences("musicPositionPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("musicPosition", bkgdcheckedPlaceHolder);
            editor.apply();
        } else {
            checkbox1.setChecked(true);
            bkgdchecked = 1;
            bkgdcheckedPlaceHolder = bkgdchecked;
            SharedPreferences prefs = getSharedPreferences("musicPositionPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("musicPosition", bkgdcheckedPlaceHolder);
            editor.apply();
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
        stopService(new Intent(this, MusicPlayer.class));

        if (bkgdchecked == 0) {
            // save state as on (0)
            checkbox1.setChecked(false);
            bkgdchecked = 0;

        } else {
            // save state as off (1)
            checkbox1.setChecked(true);
            bkgdchecked = 1;
            bkgdcheckedPlaceHolder = bkgdchecked;
        }
    }
}