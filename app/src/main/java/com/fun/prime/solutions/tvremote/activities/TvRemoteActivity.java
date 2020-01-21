package com.fun.prime.solutions.tvremote.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fun.prime.solutions.tvremote.R;
import com.fun.prime.solutions.tvremote.adapters.IRButtonsAdapter;
import com.fun.prime.solutions.tvremote.codes.IRButton;
import com.fun.prime.solutions.tvremote.codes.Manufacturer;

public class TvRemoteActivity extends BaseActivity {
    RecyclerView rvButtons;
    Manufacturer selectedMf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        enableBackButton();
        rvButtons = findViewById(R.id.rvButtons);
        String mfName = getIntent().getStringExtra("manufacturer");
        setTitle(mfName);

        int mode = getIntent().getIntExtra("mode",-1);

        if(mode==1 && mfName!=null){
            selectedMf = codesManager.getManufacturer(mfName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(selectedMf!=null){
            initButtons();
        }
    }

    private void initButtons() {
        rvButtons.setLayoutManager(new GridLayoutManager(this,3));
        IRButtonsAdapter adapter = new IRButtonsAdapter(selectedMf.getButtons(),this);
        rvButtons.setAdapter(adapter);
        adapter.setClickListener(new IRButtonsAdapter.ClickListener() {
            @Override
            public void onButtonClick(IRButton irButton) {
//                Toast.makeText(TvRemoteActivity.this, ""+irButton.getCommand().getOnOffs()[1], Toast.LENGTH_SHORT).show();
                executeIrCommand(irButton.getCommand());
            }
        });
    }

}
