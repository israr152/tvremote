package com.fun.prime.solutions.tvremote.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fun.prime.solutions.tvremote.IRButtonsAdapter;
import com.fun.prime.solutions.tvremote.R;
import com.fun.prime.solutions.tvremote.codes.IRButton;
import com.fun.prime.solutions.tvremote.codes.Manufacturer;

public class RemoteActivity extends BaseActivity {
    RecyclerView rvButtons;
    Manufacturer selectedMf;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        enableBackButton();
        rvButtons = findViewById(R.id.rvButtons);
        String mfName = getIntent().getStringExtra("manufacturer");
        mode = getIntent().getIntExtra("mode",-1);

        if(mode==1 && mfName!=null){
            setTitle(mfName);
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
                executeIrCommand(irButton.getCommand());
            }
        });
    }
}
