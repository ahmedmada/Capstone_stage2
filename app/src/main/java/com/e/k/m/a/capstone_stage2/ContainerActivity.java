package com.e.k.m.a.capstone_stage2;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.e.k.m.a.capstone_stage2.constants.Constants;
import com.e.k.m.a.capstone_stage2.fragment.TrailerFragment;

import static com.e.k.m.a.capstone_stage2.constants.Constants.MOVIE_ID;

public class ContainerActivity extends AppCompatActivity {


    public static Fragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        getSupportFragmentManager().beginTransaction().add(R.id.container,myFragment).commit();
    }
}