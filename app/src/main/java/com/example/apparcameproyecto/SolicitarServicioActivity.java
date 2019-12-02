package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.CheckBox;

import android.widget.DatePicker;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SolicitarServicioActivity extends AppCompatActivity {

       private  ImageButton fechaEntregaBoton,fechaRecogidaBoton,horaEntregaBoton,horaRecogidaBoton;
       private TextView fechaEntregaText,fechaRecogidaText,horaEntregaText,horaRecogidaText;
       private CheckBox lavadoExterno,lavadoInterno,fundaCoche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_servicio);
        fechaEntregaBoton=findViewById(R.id.calendarioEntradaImagen);
        fechaRecogidaBoton=findViewById(R.id.calendarioSalidaImagen);
        horaEntregaBoton=findViewById(R.id.horaEntradaImagen);
        horaRecogidaBoton=findViewById(R.id.horaSalidaImagen);
        fechaEntregaText=findViewById(R.id.fechaEntregaVehiculo);
        fechaRecogidaText=findViewById(R.id.fechaRecogidaVehiculo);
        horaEntregaText=findViewById(R.id.horaEntregaVehiculo);
        horaRecogidaText=findViewById(R.id.horaRecogidaVehiculo);
        lavadoExterno=findViewById(R.id.lavadoExternoSolicitud);
        lavadoInterno=findViewById(R.id.lavadoInternoSolicitud);
        fundaCoche=findViewById(R.id.fundaSolicitud);


    }
    public void onFechaEntregaAction(View view){
        Calendar calendar= Calendar.getInstance();
        int YEAR= calendar.get(Calendar.YEAR);
        int MONTH=calendar.get(Calendar.MONTH);
        int DATE=calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha=(year+"-"+month+"-"+dayOfMonth);
                fechaEntregaText.setText(fecha);
            }
        },YEAR,MONTH,DATE);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public void onFechaRecogidaAction(View view){

        Calendar calendar= Calendar.getInstance();
        int YEAR= calendar.get(Calendar.YEAR);
        int MONTH=calendar.get(Calendar.MONTH);
        int DATE=calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha=(year+"-"+month+"-"+dayOfMonth);
                fechaRecogidaText.setText(fecha);
            }
        },YEAR,MONTH,DATE);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public void onHoraEntregaAction(View view){
        Calendar calendar= Calendar.getInstance();
        int HOUR=calendar.get(Calendar.HOUR);
        int MINUTE= calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora=(hourOfDay+":"+minute+":"+"00");
                horaEntregaText.setText(hora);

            }
        },HOUR,MINUTE,true);
        timePickerDialog.show();
    }
    public void onHoraRecogidaAction(View view){
        Calendar calendar= Calendar.getInstance();
        int HOUR=calendar.get(Calendar.HOUR);
        int MINUTE= calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora=(hourOfDay+":"+minute+":"+"00");
                horaRecogidaText.setText(hora);

            }
        },HOUR,MINUTE,true);
        timePickerDialog.show();
    }




    public void onVolverMenuAction(View view){
        Intent registro;
        registro = new Intent(this,MenuPrincipal.class);
        startActivity(registro);
    }
}
