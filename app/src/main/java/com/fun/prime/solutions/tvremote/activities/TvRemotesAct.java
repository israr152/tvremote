package com.fun.prime.solutions.tvremote.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fun.prime.solutions.tvremote.adapters.ManufacturerAdapter;
import com.fun.prime.solutions.tvremote.R;

public class TvRemotesAct extends BaseActivity {
    RecyclerView rvMf;
    TextView tvError;
    static String manufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enableBackButton();
        tvError = findViewById(R.id.tvError);
        rvMf = findViewById(R.id.rvMf);
        initManufacturersList();
    }

    private void initManufacturersList() {
        ManufacturerAdapter adapter = new ManufacturerAdapter(this,codesManager.getManufacturers());
        rvMf.setLayoutManager(new LinearLayoutManager(this));
        rvMf.setAdapter(adapter);
        adapter.setClickListener(new ManufacturerAdapter.ClickListener() {
            @Override
            public void onManufactureClick(String mf) {
                //showModeOptions(mf);
                checkIrAndGotoRemoteAct(mf);
            }
        });
    }

    private void showModeOptions(final String mf) {
        String[] options = new String[]{"IR Mode","Wifi Mode"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Mode");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
//                        if(checkIrPerm()){
//                            checkIrAndGotoRemoteAct(mf);
//                        }else{
//                            manufacturer = mf;
//                            requestIrPerm();
//                        }
                        checkIrAndGotoRemoteAct(mf);
                        break;
                    case 1:
                        gotoRemoteActivity(mf,2);
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void checkIrAndGotoRemoteAct(String mf) {
        if(irManager.hasIrEmitter()){
            gotoRemoteActivity(mf,1);
        }else{
            Toast.makeText(TvRemotesAct.this, "This device does not support IR Mode!", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoRemoteActivity(String mf, int mode) {
        Intent intent = new Intent(this, TvRemoteActivity.class);
        intent.putExtra("manufacturer",mf);
        intent.putExtra("mode",mode);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==IR_PERM_REQ_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(manufacturer!=null){
                    checkIrAndGotoRemoteAct(manufacturer);
                }
            }else{
                Toast.makeText(this, "Permission required to control TV!", Toast.LENGTH_SHORT).show();
            }
            manufacturer = null;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
