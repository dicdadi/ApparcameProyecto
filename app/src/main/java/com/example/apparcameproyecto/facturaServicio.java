package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class facturaServicio extends AppCompatActivity {
    private String correo,fechaEntrega,fechaRecogida,horaEntrega,horaRecogida,marca,modelo,matricula;
    private int extraExterno,extraInterno,extraFunda;
    private TextView diasTotales,precioDia,conExtraExterno,conExtraInterno,conExtraFunda,precioTotalDias,precioExtra1,precioExtra2,precioExtra3,precioTotal;
    private RequestQueue mQueue;
    private String precioDiaDevuelto,pExtra1="0",pExtra2="0",pExtra3="0";
    private Float totalApagar;
    private Button confirmar;
    //boolean cola1=false,cola2=false,cola3=false;
    boolean ex1=false,ex2=false,ex3=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura_servicio);
        mQueue= Volley.newRequestQueue(this);



        correo=getIntent().getStringExtra("correo");
        fechaEntrega=getIntent().getStringExtra("fechaEntrega");
        fechaRecogida=getIntent().getStringExtra("fechaRecogida");
        horaEntrega=getIntent().getStringExtra("horaEntrega");
        horaRecogida=getIntent().getStringExtra("horaRecogida");
        marca=getIntent().getStringExtra("marca");
        modelo=getIntent().getStringExtra("modelo");
        matricula=getIntent().getStringExtra("matricula");
        extraExterno=getIntent().getIntExtra("extraExterno",0);
        extraInterno=getIntent().getIntExtra("extraInterno",0);
        extraFunda=getIntent().getIntExtra("extraFunda",0);
        diasTotales=findViewById(R.id.diasFactura);
        precioDia=findViewById(R.id.preciopordia);
        precioTotalDias=findViewById(R.id.datoDias);
        conExtraExterno=findViewById(R.id.extra1);
        confirmar=findViewById(R.id.confirmarBoton);
        conExtraInterno=findViewById(R.id.extra2);
        conExtraFunda=findViewById(R.id.extra3);
        precioExtra1=findViewById(R.id.datoExterno);
        precioExtra2=findViewById(R.id.datoInterno);
        precioExtra3=findViewById(R.id.datoFunda);
        precioTotal=findViewById(R.id.datoTotal);

        procesaDatos();



    }

    public void procesaDatos(){
        String dias;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date fecha= dateFormat.parse(fechaEntrega);
            Date fecha2=dateFormat.parse(fechaRecogida);
            long prueba=fecha2.getTime()-fecha.getTime();
            long seconds = prueba / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            dias=String.valueOf(days);
            if(dias.equals("0")){
                dias="1";
            }
            if(Integer.parseInt(dias)<15) {
                getPrecioDia(1);
            }else if(Integer.parseInt(dias)>=15 && Integer.parseInt(dias)<30){
                getPrecioDia(2);
            }else{
                getPrecioDia(3);
            }
            diasTotales.setText(dias);


        }catch (Exception e){

        }

/*
        if(extraExterno==1){
            getPrecioExtra(1);

        }
        if(extraInterno==1){
            getPrecioExtra(2);
        }
        if(extraFunda==1){
            getPrecioExtra(3);
        }

        conExtraExterno.setText(pExtra1);
        precioExtra1.setText(pExtra1+"€");
        conExtraInterno.setText(pExtra2);
        precioExtra2.setText(pExtra2+"€");
        conExtraFunda.setText(pExtra3);
        precioExtra3.setText(pExtra3+"€");
        Float aux=Float.valueOf(precioDiaDevuelto)*Float.valueOf(diasTotales.getText().toString());
        precioTotalDias.setText(aux.toString()+"€");
        Float aux2=aux+Float.valueOf(pExtra1)+Float.valueOf(pExtra2)+Float.valueOf(pExtra3);
        precioTotal.setText(aux2+"€");

   */

    }
    private void getPrecioDia(int codigo) {
        final String codigoPrecio=String.valueOf(codigo);

        String url="https://apparcame.000webhostapp.com/apparcamebd/consultaPrecioDia.php";// url donde coges el json

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JSONObject user1;
                        JSONObject definitivo;
                        JSONArray array;

                        try{

                            user1 = new JSONObject(response);
                            array=new JSONArray(user1.getString("cambios"));
                            definitivo=array.getJSONObject(0);
                            String precioEncontrado=definitivo.getString("precio");
                            precioDiaDevuelto=precioEncontrado;
                            precioDia.setText(precioDiaDevuelto);
                            Float aux=Float.valueOf(precioDiaDevuelto)*Float.valueOf(diasTotales.getText().toString());
                            precioTotalDias.setText(aux.toString());


                            if(extraExterno==1){
                                conExtraExterno.setText("6.95");
                                precioExtra1.setText("6.95");
                                ex1=true;
                            }
                            if(extraInterno==1){
                                conExtraInterno.setText("16.95");
                                precioExtra2.setText("16.95");
                                ex2=true;
                            }
                            if(extraFunda==1){
                                conExtraFunda.setText("9.95");
                                precioExtra3.setText("9.95");
                                ex3=true;
                            }
                            Float aux2=aux+Float.valueOf(precioExtra1.getText().toString())+Float.valueOf(precioExtra2.getText().toString())+Float.valueOf(precioExtra3.getText().toString());
                            totalApagar=aux2;
                            precioTotal.setText(aux2+"€");
                            //Toast.makeText(getApplicationContext(),precioDiaDevuelto, Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("codigoPrecio",codigoPrecio);
                return params;
            }
        };



        mQueue.add(strRequest);


    }
    /*
    private void getPrecioExtra(int codigo) {
        final String codigoExtra=String.valueOf(codigo);

        String url="https://apparcame.000webhostapp.com/apparcamebd/preciosExtra.php";// url donde coges el json

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JSONObject user1;
                        JSONObject definitivo;
                        JSONArray array;

                        try{

                            user1 = new JSONObject(response);
                            array=new JSONArray(user1.getString("extras"));
                            definitivo=array.getJSONObject(0);
                            String precioEncontrado=definitivo.getString("precioExtra");
                            if(codigoExtra.equals("1")){
                                pExtra1=precioEncontrado;
                                conExtraExterno.setText(pExtra1);
                                precioExtra1.setText(pExtra1);
                            }else if (codigoExtra.equals("2")){
                                pExtra2=precioEncontrado;
                                conExtraInterno.setText(pExtra2);
                                precioExtra2.setText(pExtra2);
                            }else if(codigoExtra.equals("3")){
                                pExtra3=precioEncontrado;

                                conExtraFunda.setText(pExtra3);
                                precioExtra3.setText(pExtra3);
                            }else{ }
                            //Toast.makeText(getApplicationContext(),precioDiaDevuelto, Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("codigoExtra",codigoExtra);
                return params;
            }
        };




        if(cola1==false) {
            mQueueExtra1.add(strRequest);
            cola1=true;
        }else if(cola2==false){
            mQueueExtra2.add(strRequest);
            cola2=true;
        }else{
            mQueueExtra3.add(strRequest);
        }

    }
    */

    private void insertaRegistro(){

    final String emailDato=correo;
    final String fechaEntregaDato=fechaEntrega;
    final String fechaRecogidaDato=fechaRecogida;
    final String horaEntregaDato=horaEntrega;
    final String horaRecogidaDato=horaRecogida;
    final String marcaDato=marca;
    final String modeloDato=modelo;
    final String matriculaDato=matricula;
    final String ex1dato=String.valueOf(ex1);
    final String ex2dato=String.valueOf(ex2);
    final String ex3dato=String.valueOf(ex3);
    final String precioTotalDato=String.valueOf(totalApagar);

        String urlInserta="https://apparcame.000webhostapp.com/apparcamebd/nuevoServicio.php";
        StringRequest insertaRequest = new StringRequest(Request.Method.POST, urlInserta,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), "Se ha creado el servicio correctamente.", Toast.LENGTH_SHORT).show();
                        confirmar.setEnabled(false);
                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<>();

                params.put("email",emailDato);
                params.put("fechaEntrega",fechaEntregaDato);
                params.put("fechaRecogida",fechaRecogidaDato);
                params.put("horaEntrega",horaEntregaDato);
                params.put("horaRecogida",horaRecogidaDato);
                params.put("marca",marcaDato);
                params.put("modelo",modeloDato);
                params.put("matricula",matriculaDato);
                params.put("ex1",ex1dato);
                params.put("ex2",ex2dato);
                params.put("ex3",ex3dato);
                params.put("totalApagar",precioTotalDato);


                return params;
            }
        };

        mQueue.add(insertaRequest);

    }
    public void onConfirmarFacturaAction(View view){

        //Toast.makeText(getApplicationContext(),"Guardando solicitud.", Toast.LENGTH_SHORT).show();

                insertaRegistro();

        //Toast.makeText(getApplicationContext(),precioDiaDevuelto, Toast.LENGTH_SHORT).show();


        //Toast.makeText(getApplicationContext(),correo+ " "+ fechaEntrega +" "+fechaRecogida+" "+horaEntrega+" "+horaRecogida+" "+marca+" "+modelo+" "+matricula+" "+ex1+" "+ex2+" "+ex3+" "+totalApagar, Toast.LENGTH_SHORT).show();
    }
    public void onVolverSolictarFacturaAction(View view){
        Intent registro;
        registro = new Intent(this,SolicitarServicioActivity.class);
        startActivity(registro);
    }
}
