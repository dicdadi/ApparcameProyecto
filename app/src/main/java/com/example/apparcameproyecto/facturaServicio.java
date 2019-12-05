package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class facturaServicio extends AppCompatActivity {
    private String correo, fechaEntrega, fechaRecogida, horaEntrega, horaRecogida, marca, modelo, matricula;
    private int extraExterno, extraInterno, extraFunda;
    private TextView diasTotales, precioDia, conExtraExterno, conExtraInterno, conExtraFunda, precioTotalDias, precioExtra1, precioExtra2, precioExtra3, precioTotal;
    private RequestQueue mQueue;
    private String precioDiaDevuelto;
    private Float totalApagar;
    private Button confirmar;
    boolean ex1 = false, ex2 = false, ex3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura_servicio);
        mQueue = Volley.newRequestQueue(this);


        correo = getIntent().getStringExtra("correo");
        fechaEntrega = getIntent().getStringExtra("fechaEntrega");
        fechaRecogida = getIntent().getStringExtra("fechaRecogida");
        horaEntrega = getIntent().getStringExtra("horaEntrega");
        horaRecogida = getIntent().getStringExtra("horaRecogida");
        marca = getIntent().getStringExtra("marca");
        modelo = getIntent().getStringExtra("modelo");
        matricula = getIntent().getStringExtra("matricula");
        extraExterno = getIntent().getIntExtra("extraExterno", 0);
        extraInterno = getIntent().getIntExtra("extraInterno", 0);
        extraFunda = getIntent().getIntExtra("extraFunda", 0);
        diasTotales = findViewById(R.id.diasFactura);
        precioDia = findViewById(R.id.preciopordia);
        precioTotalDias = findViewById(R.id.datoDias);
        conExtraExterno = findViewById(R.id.extra1);
        confirmar = findViewById(R.id.confirmarBoton);
        conExtraInterno = findViewById(R.id.extra2);
        conExtraFunda = findViewById(R.id.extra3);
        precioExtra1 = findViewById(R.id.datoExterno);
        precioExtra2 = findViewById(R.id.datoInterno);
        precioExtra3 = findViewById(R.id.datoFunda);
        precioTotal = findViewById(R.id.datoTotal);

        procesaDatos();


    }

    public void procesaDatos() {
        String dias;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fecha = dateFormat.parse(fechaEntrega);
            Date fecha2 = dateFormat.parse(fechaRecogida);
            long prueba = fecha2.getTime() - fecha.getTime();
            long seconds = prueba / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            dias = String.valueOf(days);
            if (dias.equals("0")) {
                dias = "1";
            }
            if (Integer.parseInt(dias) < 15) {
                getPrecioDia(1);
            } else if (Integer.parseInt(dias) >= 15 && Integer.parseInt(dias) < 30) {
                getPrecioDia(2);
            } else {
                getPrecioDia(3);
            }
            diasTotales.setText(dias);


        } catch (Exception e) {

        }


    }

    private void getPrecioDia(int codigo) {
        final String codigoPrecio = String.valueOf(codigo);

        String url = "https://apparcame.000webhostapp.com/apparcamebd/consultaPrecioDia.php";// url donde coges el json

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject user1;
                        JSONObject definitivo;
                        JSONArray array;

                        try {
                            DecimalFormat df = new DecimalFormat();
                            df.setMaximumFractionDigits(2);
                            user1 = new JSONObject(response);
                            array = new JSONArray(user1.getString("cambios"));
                            definitivo = array.getJSONObject(0);
                            String precioEncontrado = definitivo.getString("precio");
                            precioDiaDevuelto = precioEncontrado;
                            precioDia.setText(precioDiaDevuelto);
                            Float aux = Float.valueOf(precioDiaDevuelto) * Float.valueOf(diasTotales.getText().toString());
                            precioTotalDias.setText(df.format(aux));


                            if (extraExterno == 1) {
                                conExtraExterno.setText("6.95");
                                precioExtra1.setText("6.95");
                                ex1 = true;
                            }
                            if (extraInterno == 1) {
                                conExtraInterno.setText("16.95");
                                precioExtra2.setText("16.95");
                                ex2 = true;
                            }
                            if (extraFunda == 1) {
                                conExtraFunda.setText("9.95");
                                precioExtra3.setText("9.95");
                                ex3 = true;
                            }
                            Float aux2 = aux + Float.valueOf(precioExtra1.getText().toString()) + Float.valueOf(precioExtra2.getText().toString()) + Float.valueOf(precioExtra3.getText().toString());

                            totalApagar = aux2;
                            precioTotal.setText(df.format(aux2) + "€");
                            //Toast.makeText(getApplicationContext(),precioDiaDevuelto, Toast.LENGTH_SHORT).show();
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
                params.put("codigoPrecio", codigoPrecio);
                return params;
            }
        };


        mQueue.add(strRequest);


    }

    private void insertaRegistro() {

        final String emailDato = correo;
        final String fechaEntregaDato = fechaEntrega;
        final String fechaRecogidaDato = fechaRecogida;
        final String horaEntregaDato = horaEntrega;
        final String horaRecogidaDato = horaRecogida;
        final String marcaDato = marca;
        final String modeloDato = modelo;
        final String matriculaDato = matricula;
        final String ex1dato = String.valueOf(ex1);
        final String ex2dato = String.valueOf(ex2);
        final String ex3dato = String.valueOf(ex3);
        final String precioTotalDato = String.valueOf(totalApagar);

        String urlInserta = "https://apparcame.000webhostapp.com/apparcamebd/nuevoServicio.php";
        StringRequest insertaRequest = new StringRequest(Request.Method.POST, urlInserta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Se ha creado el servicio correctamente.", Toast.LENGTH_SHORT).show();
                        confirmar.setEnabled(false);
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

                params.put("email", emailDato);
                params.put("fechaEntrega", fechaEntregaDato);
                params.put("fechaRecogida", fechaRecogidaDato);
                params.put("horaEntrega", horaEntregaDato);
                params.put("horaRecogida", horaRecogidaDato);
                params.put("marca", marcaDato);
                params.put("modelo", modeloDato);
                params.put("matricula", matriculaDato);
                params.put("ex1", ex1dato);
                params.put("ex2", ex2dato);
                params.put("ex3", ex3dato);
                params.put("totalApagar", precioTotalDato);


                return params;
            }
        };

        mQueue.add(insertaRequest);

    }

    public void onConfirmarFacturaAction(View view) {

        insertaRegistro();

    }

    public void onVolverSolictarFacturaAction(View view) {
        finish();

    }
   public void onVolverMenuAction(View view){
       NavUtils.navigateUpFromSameTask(this);
       finish();
   }
}
