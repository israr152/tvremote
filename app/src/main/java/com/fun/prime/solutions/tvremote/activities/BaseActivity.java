package com.fun.prime.solutions.tvremote.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.fun.prime.solutions.tvremote.IrCommand;
import com.fun.prime.solutions.tvremote.R;
import com.fun.prime.solutions.tvremote.codes.CodesManager;
import com.fun.prime.solutions.tvremote.codes.IRButton;
import com.fun.prime.solutions.tvremote.codes.IRCommand;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class BaseActivity extends AppCompatActivity {
    protected CodesManager codesManager;
    protected ConsumerIrManager irManager;
    protected static final int IR_PERM_REQ_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            codesManager = CodesManager.getInstance(this);
            irManager = (ConsumerIrManager)getSystemService(CONSUMER_IR_SERVICE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Toast.makeText(this, "error parsing: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout parentLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base,null);
        getLayoutInflater().inflate(layoutResID,parentLayout,true);
        super.setContentView(parentLayout);
    }

    protected void enableBackButton(){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    protected void gotoActivity(Class actClass) {
        startActivity(new Intent(this,actClass));
    }

    protected boolean checkIrPerm(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.TRANSMIT_IR) == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestIrPerm(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.TRANSMIT_IR},IR_PERM_REQ_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void executeIrCommand(IRCommand c){
        if(irManager!=null){
            irManager.transmit(c.getFrequency(),c.getOnOffs());
        }
    }

    protected void execNecCommand(IrCommand c){
        IrCommand necCommand = IrCommand.NEC.buildNEC(24, 0x0000000000FF46B9);
        irManager.transmit(c.frequency,c.pattern);
    }

}
