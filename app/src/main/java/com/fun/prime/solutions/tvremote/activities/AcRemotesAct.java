package com.fun.prime.solutions.tvremote.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fun.prime.solutions.tvremote.R;
import com.fun.prime.solutions.tvremote.adapters.ManufacturerAdapter;

public class AcRemotesAct extends BaseActivity {
    RecyclerView rvMf;
    TextView tvError;
    static String manufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_remote);
        enableBackButton();
        tvError = findViewById(R.id.tvError);
        rvMf = findViewById(R.id.rvMf);
        initManufacturersList();
    }

    private void initManufacturersList() {
        ManufacturerAdapter adapter = new ManufacturerAdapter(this,codesManager.getAcManufacturers());
        rvMf.setLayoutManager(new LinearLayoutManager(this));
        rvMf.setAdapter(adapter);
        adapter.setClickListener(new ManufacturerAdapter.ClickListener() {
            @Override
            public void onManufactureClick(String mf) {
                checkIrAndGotoRemoteAct(mf);
            }
        });
    }

    private void gotoRemoteActivity(String mf) {
        Intent intent = new Intent(this,AcRemoteAct.class);
        intent.putExtra("manufacturer",mf);
        startActivity(intent);
    }

    private void checkIrAndGotoRemoteAct(String mf) {
        if(irManager.hasIrEmitter()){
            gotoRemoteActivity(mf);
        }else{
            Toast.makeText(AcRemotesAct.this, "This device does not support IR Mode!", Toast.LENGTH_SHORT).show();
        }
    }

}
