package com.fun.prime.solutions.tvremote.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.fun.prime.solutions.tvremote.R;

public class MainMenuAct extends BaseActivity {
    private static boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void tvRemoteClick(View view) {
        gotoActivity(TvRemotesAct.class);
    }

    public void AcRemoteClick(View view) {
        gotoActivity(AcRemotesAct.class);
    }

    @Override
    public void onBackPressed() {
        if(isBackPressed){
            super.onBackPressed();
        }else{
            isBackPressed = true;
            Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBackPressed = false;
                }
            },2000);
        }
    }

}
