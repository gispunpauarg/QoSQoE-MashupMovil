package com.example.projectmashup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements Buscar.EditNameDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DialogFragment newFragment = new Buscar();
        newFragment.show(getSupportFragmentManager(), "buscar");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        //Log.d("APIREST----main-",inputText);
        //inputText = "pizza";
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("textoAbuscar", inputText);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
