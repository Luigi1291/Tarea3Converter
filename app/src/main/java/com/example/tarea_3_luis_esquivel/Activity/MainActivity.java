package com.example.tarea_3_luis_esquivel.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tarea_3_luis_esquivel.Fragments.MainFragment;
import com.example.tarea_3_luis_esquivel.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new MainFragment())
                .commit();
    }
}
