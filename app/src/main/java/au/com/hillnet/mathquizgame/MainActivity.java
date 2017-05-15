package au.com.hillnet.mathquizgame;

//import android.support.v7.app.AppCompatActivity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements QuizSizeDialogPrompt.DialogListener, View.OnSystemUiVisibilityChangeListener {

    //    MediaPlayer mediaPlayer;
    private View decorView;
    private ActionBar actionBar;
    private GestureDetectorCompat gestureDetector;

    public Button onBtnScoreClick;

    // Enables full screen
    private static final int NO_CONTROLS =
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

    private static final int FULLSCREEN =
            View.SYSTEM_UI_FLAG_IMMERSIVE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    public void init() {
        onBtnScoreClick = (Button) findViewById(R.id.button);
        onBtnScoreClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent highScore = new Intent(MainActivity.this, HighscoreActivity.class);
                startActivity(highScore);
                finish();

            }
        });
    }


    public final static String EXTRA_LENGTH = "au.com.hillnet.mathquizgame.LENGTH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minimal_menu);

        init();


        actionBar = getSupportActionBar();
        gestureDetector = new GestureDetectorCompat(this, new GestureHandler());
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(FULLSCREEN);
        decorView.setOnSystemUiVisibilityChangeListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Starts background music
        startService(new Intent(this, MusicPlayer.class));
    }


    //Enables fullscreen
    private void toggleControls() {
        int flags = decorView.getSystemUiVisibility();
        flags ^= NO_CONTROLS;
        decorView.setSystemUiVisibility(flags);
    }


    // Inflates the menu; adding items to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void settingButtonHandler(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
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

    // Determine quiz length
    public void onBtnStartClick(View view) {
        DialogFragment dialog = new QuizSizeDialogPrompt();
        dialog.show(getFragmentManager(), getString(R.string.tag_qlength));

    }


    public void onGoClick(int choice) {
        Intent intent = new Intent(this, QuizActivity.class);
        int num;
        switch (choice) {
            case 0:
                num = 10;
                break;
            case 1:
                num = 15;
                break;
            case 2:
                num = 20;
                break;
            default:
                num = 10;
                break;
        }
        intent.putExtra(EXTRA_LENGTH, num);
        startActivity(intent);
    }
}


