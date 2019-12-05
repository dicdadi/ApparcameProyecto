package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private EditText emailEditText, contrasenniaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        emailEditText = findViewById(R.id.correoInicio);
        contrasenniaEditText = findViewById(R.id.contrasenniaInicio);

    }

    public void onRegistrarseLoginAction(View view) {
        Intent registro;
        registro = new Intent(this, RegistroActivity.class);
        startActivity(registro);
    }


    public void onLoginAction(View view) {

        String correoLogin, contrasenniaLogin;
        correoLogin = emailEditText.getText().toString();
        contrasenniaLogin = this.contrasenniaEditText.getText().toString();

        if (correoLogin.isEmpty() && contrasenniaLogin.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Introduce un correo y una contraseña.", Toast.LENGTH_SHORT).show();
        } else if (!validarEmail(correoLogin)) {
            this.emailEditText.setError("Este correo no es válido.");

        } else {
            login(correoLogin, contrasenniaLogin);
        }


    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    private void login(String email, String contrasennia) {

        final String emailLogin = email;
        final String psswdLogin = contrasennia;

        String url = "https://apparcame.000webhostapp.com/apparcamebd/login.php";// url donde coges el json

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject user1;
                        JSONObject definitivo;
                        JSONArray array;

                        try {

                            user1 = new JSONObject(response);
                            array = new JSONArray(user1.getString("user"));
                            definitivo = array.getJSONObject(0);
                            String email = definitivo.getString("email");
                            String contrasennia = definitivo.getString("contrasennia");
                            if (email.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "No hay ninguna cuenta asociada a este correo electrónico", Toast.LENGTH_SHORT).show();
                            } else if (!contrasennia.equals(psswdLogin)) {
                                contrasenniaEditText.setError("La contraseña es incorrecta.");
                            } else {
                                irMenu();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailLogin);
                return params;
            }
        };


        mQueue.add(strRequest);// donde se hace la peticion


    }

    private void irMenu() {
        Intent menuPrincipal;
        menuPrincipal = new Intent(this, MenuPrincipal.class);
        menuPrincipal.putExtra("correo", emailEditText.getText().toString());
        startActivity(menuPrincipal);
    }
}
