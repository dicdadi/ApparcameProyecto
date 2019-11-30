package com.example.apparcameproyecto;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;
import android.view.View;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


public class RegistroActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private EditText email,nombre,apellidos,contrasennia,repiteContrasennia,direccion,telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue= Volley.newRequestQueue(this);
        setContentView(R.layout.activity_registro);
        email=findViewById(R.id.correcRegistro);
        nombre=findViewById(R.id.nombreRegistro);
        apellidos=findViewById(R.id.apellidosRegistro);
        contrasennia=findViewById(R.id.contrasenniaRegistro);
        repiteContrasennia=findViewById(R.id.repiteContrasenniaRegistro);
        telefono=findViewById(R.id.telefenoRegistro);
        direccion=findViewById(R.id.direccionRegistro);
    }

public void onRegistrarseRegistroAction(View view){
String datoEmail,datoNombre,datoApellidos,datoContrasennia,datoRepiteContrasennia,datoTelefono,datoDireccion;

    datoEmail=email.getText().toString();
    datoNombre=nombre.getText().toString();
    datoApellidos=apellidos.getText().toString();
    datoContrasennia=contrasennia.getText().toString();
    datoRepiteContrasennia=repiteContrasennia.getText().toString();
    datoDireccion=direccion.getText().toString();
    datoTelefono=telefono.getText().toString();



    if(datoEmail.length()==0){

        email.setError("Campo email obligatorio.");

    }else if(!validarEmail(datoEmail)){

    email.setError("El formato del correo no es valido.");

    } else if(datoNombre.length()==0){

    nombre.setError("Campo nombre obligatorio.");

    }else if(datoApellidos.length()==0){

    apellidos.setError("Dato apellidos obligatorio.");

    }else if( datoContrasennia.length()==0){

    contrasennia.setError("Debe introducir un contraseña.");

    }else if(datoRepiteContrasennia.length()==0){

    repiteContrasennia.setError("Vuelva a repetir la contraseña.");

    }else if(!datoContrasennia.equals(datoRepiteContrasennia)){

    contrasennia.setError("Las contraseñas no coinciden.");
    repiteContrasennia.setError("Las contraseñas no coinciden.");
    contrasennia.setText("");
    repiteContrasennia.setText("");

    }else if(datoDireccion.length()==0){

    direccion.setError("Debe introducir una dirección.");


    }else if( telefono.length()==0){

    telefono.setError("Debe introducir un número de teléfono.");


    }else if(!validarMobil(datoTelefono)){

  telefono.setError("Formato de teléfono incorrecto.");

    }else {
    validarRegistro(
            datoEmail,
            datoNombre,
            datoApellidos,
            datoContrasennia,
            datoDireccion,
            datoTelefono);



}




   // Toast.makeText(this, email.getText()+" "+nombre.getText()+" "+apellidos.getText()+" "+ contrasennia.getText()+ " "+ repiteContrasennia.getText() +" "+ telefono.getText(), Toast.LENGTH_SHORT).show();

}

private void validarRegistro( String email, String nombre, String apellidos, String contrasennia, String direccion, String telefono) {
    final String emailValida=email;
    final String validaNombre=nombre;
    final String validaApellidos=apellidos;
    final String validaContrasennia=contrasennia;
    final String validaDireccion=direccion;
    final String validaTelefono=telefono;

    String url="https://apparcame.000webhostapp.com/apparcamebd/compruebaUsuario.php";// url donde coges el json

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
                        array=new JSONArray(user1.getString("user"));
                        definitivo=array.getJSONObject(0);
                        String emailEncontrado=definitivo.getString("email");
                        existeUsuario(emailEncontrado,emailValida,validaNombre,validaApellidos,validaContrasennia,validaDireccion,validaTelefono);

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
            params.put("email",emailValida);
            return params;
        }
    };



    mQueue.add(strRequest);



}
private void existeUsuario(String emailExiste,String email,String nombre, String apellidos, String contrasennia, String direccion, String telefono){
        if(!emailExiste.isEmpty()) {
            Toast.makeText(getApplicationContext(), email + "Ya existe un usuario con este correo electrónico." + nombre, Toast.LENGTH_SHORT).show();
        }else{
           insertaRegistro(email,nombre,apellidos,contrasennia,direccion,telefono);
        }

}
private void insertaRegistro(  String email, String nombre,  String apellidos,  String contrasennia, String direccion, String telefono){
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    final String emailInsertar=email;
    final String nombreInsertar=nombre;
    final String apellidosInsertar=apellidos;
    final String contrasenniaInsertar=contrasennia;
    final String direccionInsertar=direccion;
    final String telefonoInsertar=telefono;
    final String fechaInsertar = df.format(Calendar.getInstance().getTime());

        String urlInserta="https://apparcame.000webhostapp.com/apparcamebd/AltaRegistro.php";
        StringRequest insertaRequest = new StringRequest(Request.Method.POST, urlInserta,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), "Se ha creado el usuario correctamente.", Toast.LENGTH_SHORT).show();

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
                params.put("email",emailInsertar);
                params.put("nombreCliente",nombreInsertar);
                params.put("apellidosCliente",apellidosInsertar);
                params.put("contrasennia",contrasenniaInsertar);
                params.put("telefonoCliente",telefonoInsertar);
                params.put("direccion",direccionInsertar);
                params.put("fechaRegistro",fechaInsertar);
                return params;
            }
        };

        mQueue.add(insertaRequest);

    }

//VALIDACIÓN REGISTRO
private boolean validarEmail(String email) {
    Pattern pattern = Patterns.EMAIL_ADDRESS;
    return pattern.matcher(email).matches();
}
    private boolean validarMobil(String phone) {

      return  PhoneNumberUtils.isGlobalPhoneNumber(phone);
    }

    public void onVolverRegistroAction(View view){
        Intent registro;
        registro = new Intent(this,MainActivity.class);
        startActivity(registro);
    }






}
