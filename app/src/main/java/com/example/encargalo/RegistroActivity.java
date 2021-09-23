package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    Button btnRegistrar;
    EditText appaterno,apmaterno,nombre,identidad,celular,tienda,ruc,rubro;
    CheckBox che1,che2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Crear Cuenta");
        setSupportActionBar(toolbar);

        appaterno=(EditText)findViewById(R.id.txtappaterno);
        apmaterno=(EditText)findViewById(R.id.txtapmaterno);
        nombre=(EditText)findViewById(R.id.txtnombre);
        identidad=(EditText)findViewById(R.id.txtidentidad);
        celular=(EditText)findViewById(R.id.txtcelular);
        tienda=(EditText)findViewById(R.id.txttienda);
        ruc=(EditText)findViewById(R.id.txtruc);
        rubro=(EditText)findViewById(R.id.txtrubro);

        che1=(CheckBox)findViewById(R.id.check1);
        che2=(CheckBox)findViewById(R.id.check2);

        btnRegistrar=(Button) findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registrar_datos(view);
            }
        });
    }
    public boolean validar_datos(){

        String sappaterno=appaterno.getText().toString();
        String sapmaterno=apmaterno.getText().toString();
        String snombre=nombre.getText().toString();
        String sidentidad=identidad.getText().toString();
        String scelular=celular.getText().toString();
        String stienda=tienda.getText().toString();
        String sruc=ruc.getText().toString();
        String srubro=rubro.getText().toString();
        boolean chek1=che1.isChecked();
        boolean chek2=che2.isChecked();
        if(sappaterno.isEmpty()){
            Toast.makeText(RegistroActivity.this, "Faltan datos!",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(sapmaterno.isEmpty()){
                Toast.makeText(RegistroActivity.this, "Faltan datos!",Toast.LENGTH_SHORT).show();
                return false;
            }else{
                if(snombre.isEmpty()){
                    Toast.makeText(RegistroActivity.this, "Faltan datos!",Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    if(sidentidad.isEmpty()){
                        Toast.makeText(RegistroActivity.this, "Faltan datos!",Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        if(scelular.isEmpty()){
                            Toast.makeText(RegistroActivity.this, "Faltan datos!",Toast.LENGTH_SHORT).show();
                            return false;
                        }else{
                            if(stienda.isEmpty()){
                                Toast.makeText(RegistroActivity.this, "Faltan datos!",Toast.LENGTH_SHORT).show();
                                return false;
                            }else{
                                if(sruc.isEmpty()){
                                    Toast.makeText(RegistroActivity.this, "Faltan datos!",Toast.LENGTH_SHORT).show();
                                    return false;
                                }else{
                                    if(srubro.isEmpty()){
                                        Toast.makeText(RegistroActivity.this, "Faltan datos!",Toast.LENGTH_SHORT).show();
                                        return false;
                                    }else{
                                        if(chek1==false){
                                            Toast.makeText(RegistroActivity.this, "Falta aceptar los terminos!",Toast.LENGTH_SHORT).show();
                                            return false;
                                        }else{
                                            if(chek2==false){
                                                Toast.makeText(RegistroActivity.this, "Falta aceptar la politica!",Toast.LENGTH_SHORT).show();
                                                return false;
                                            }else{
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void registrar_datos(View View){
            Intent i = new Intent(this, VerificarActivity.class);
            i.putExtra("appaterno",appaterno.getText().toString());
            i.putExtra("apmaterno",apmaterno.getText().toString());
            i.putExtra("nombres",nombre.getText().toString());
            i.putExtra("identidad",identidad.getText().toString());
            i.putExtra("celular",celular.getText().toString());
            i.putExtra("tienda",tienda.getText().toString());
            i.putExtra("ruc",ruc.getText().toString());
            i.putExtra("rubro",rubro.getText().toString());
            startActivity(i);
    }

}