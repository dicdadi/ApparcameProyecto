package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.CheckBox;

import android.widget.DatePicker;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolicitarServicioActivity extends AppCompatActivity {

    private ImageButton fechaEntregaBoton, fechaRecogidaBoton, horaEntregaBoton, horaRecogidaBoton;
    private TextView fechaEntregaText, fechaRecogidaText, horaEntregaText, horaRecogidaText;
    private CheckBox lavadoExterno, lavadoInterno, fundaCoche;
    private EditText marcaEdit, modeloEdit, matriculaEdit;
    private String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_servicio);
        correo = getIntent().getStringExtra("correo");
        fechaEntregaBoton = findViewById(R.id.calendarioEntradaImagen);
        fechaRecogidaBoton = findViewById(R.id.calendarioSalidaImagen);
        horaEntregaBoton = findViewById(R.id.horaEntradaImagen);
        horaRecogidaBoton = findViewById(R.id.horaSalidaImagen);
        fechaEntregaText = findViewById(R.id.fechaEntregaVehiculo);
        fechaRecogidaText = findViewById(R.id.fechaRecogidaVehiculo);
        horaEntregaText = findViewById(R.id.horaEntregaVehiculo);
        horaRecogidaText = findViewById(R.id.horaRecogidaVehiculo);
        marcaEdit = findViewById(R.id.marcaCocheSolicitud);
        modeloEdit = findViewById(R.id.modeloCocheSolicitud);
        matriculaEdit = findViewById(R.id.matriculaSolicitud);
        lavadoExterno = findViewById(R.id.lavadoExternoSolicitud);
        lavadoInterno = findViewById(R.id.lavadoInternoSolicitud);
        fundaCoche = findViewById(R.id.fundaSolicitud);


    }

    public void onFechaEntregaAction(View view) {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = (year + "-" + month + "-" + dayOfMonth);
                fechaEntregaText.setText(fecha);
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void onFechaRecogidaAction(View view) {

        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = (year + "-" + month + "-" + dayOfMonth);
                fechaRecogidaText.setText(fecha);
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void onHoraEntregaAction(View view) {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = (hourOfDay + ":" + minute + ":" + "00");
                horaEntregaText.setText(hora);

            }
        }, HOUR, MINUTE, true);
        timePickerDialog.show();
    }

    public void onHoraRecogidaAction(View view) {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = (hourOfDay + ":" + minute + ":" + "00");
                horaRecogidaText.setText(hora);

            }
        }, HOUR, MINUTE, true);
        timePickerDialog.show();
    }

    public void onSolicitarServicioAction(View view) {

        String dias;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Pattern p = Pattern.compile("[0-9]");

        try {
            Date fecha = dateFormat.parse(fechaEntregaText.getText().toString());
            Date fecha2 = dateFormat.parse(fechaRecogidaText.getText().toString());
            Matcher compruebaHoraEntrega = p.matcher(horaEntregaText.getText().toString());
            Matcher compruebaHoraRecogida = p.matcher(horaRecogidaText.getText().toString());
            if (!compruebaHoraEntrega.find() || !compruebaHoraRecogida.find()) {
                Toast.makeText(getApplicationContext(), "Debes introducir una hora de entrega y reocogida", Toast.LENGTH_SHORT).show();
            } else if (marcaEdit.getText().length() < 1) {
                marcaEdit.setError("Intorduce la marca del vehiculo");
            } else if (modeloEdit.getText().length() < 1) {
                modeloEdit.setError("Introduce el model del vehiculo");
            } else if (matriculaEdit.getText().length() < 1) {
                matriculaEdit.setError("Introduce la matrícula del vehículo");
            } else {
                int limpiezaExterna = lavadoExterno.isChecked() ? 1 : 0;
                int limpiezaInterna = lavadoInterno.isChecked() ? 1 : 0;
                int funda = fundaCoche.isChecked() ? 1 : 0;
                confirmarSolicitud(limpiezaExterna, limpiezaInterna, funda);
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Debes introducir una fecha de entrada y de recogida del vehículo.", Toast.LENGTH_SHORT).show();
        }



    }

    public void confirmarSolicitud(int extraExterno, int extraInterno, int extraFunda) {
        Intent factura;
        factura = new Intent(this, facturaServicio.class);
        factura.putExtra("correo", correo);
        factura.putExtra("fechaEntrega", fechaEntregaText.getText().toString());
        factura.putExtra("fechaRecogida", fechaRecogidaText.getText().toString());
        factura.putExtra("horaEntrega", horaEntregaText.getText().toString());
        factura.putExtra("horaRecogida", horaRecogidaText.getText().toString());
        factura.putExtra("marca", marcaEdit.getText().toString());
        factura.putExtra("modelo", modeloEdit.getText().toString());
        factura.putExtra("matricula", matriculaEdit.getText().toString());
        factura.putExtra("extraExterno", extraExterno);
        factura.putExtra("extraInterno", extraInterno);
        factura.putExtra("extraFunda", extraFunda);
        startActivity(factura);
    }

    public void onVolverMenuAction(View view) {
        finish();
        //Intent menu;
        //menu = new Intent(this,MenuPrincipal.class);
        //menu.putExtra("correo",correo);
        //startActivity(menu);
    }
}
