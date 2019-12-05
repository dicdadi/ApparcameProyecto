package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {
    private String correo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        correo = getIntent().getStringExtra("correo");
    }

    public void onSolicitarServicioAction(View view) {
        Intent loginInicio;
        loginInicio = new Intent(this, SolicitarServicioActivity.class);
        loginInicio.putExtra("correo", this.correo);
        startActivity(loginInicio);

    }


    public void noImplementado(View view) {
        Toast.makeText(getApplicationContext(), "Estamos trabajando en esta funcionalidad.", Toast.LENGTH_SHORT).show();
    }
    public void irAyuda(View view){
        Intent ayuda;
        ayuda = new Intent(this,Ayuda.class);
        startActivity(ayuda);
    }

    public void onDesconectarseAction(View view) {
        finish();


    }
}
