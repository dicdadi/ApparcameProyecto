package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }
public void onSolicitarServicioAction(View view){

}
    public void onDesconectarseAction(View view){
        Intent loginInicio;
        loginInicio = new Intent(this,MainActivity.class);
        startActivity(loginInicio);
    }
}
