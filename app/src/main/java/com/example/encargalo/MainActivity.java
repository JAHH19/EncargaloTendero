package com.example.encargalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.encargalo.InicioMenuActivity;
import com.example.encargalo.LoginActivity;
import com.example.encargalo.R;
import com.example.encargalo.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private Button authBtn;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    String usuario,contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        final SharedPreferences sharpref = getSharedPreferences("ARCHIVOBIO",context.MODE_PRIVATE);
        SharedPreferences preferencias = getSharedPreferences("ARCHIVOREG",Context.MODE_PRIVATE);
        usuario = preferencias.getString("usuario","");
        contraseña = preferencias.getString("pass","");
        authBtn = findViewById(R.id.btn_inicia);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                iralogin();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "¡Autenticación exitosa!", Toast.LENGTH_SHORT).show();
                validarUsuario(Valores.getIP_SERVER()+"/APIS/tienda/validarusuario.php");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Ups!, ha ocurrido un fallo en la autenticacion...", Toast.LENGTH_SHORT).show();

            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion Biometrica")
                .setSubtitle("Inicia usando tu huella")
                .setNegativeButtonText("Iniciar con contraseña")
                .build();

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor=sharpref.getString("BIO","0");
                if (valor.equals("1")){
                    biometricPrompt.authenticate(promptInfo);
                }else {
                    iralogin();
                }

            }
        });

        notifcationSend();
    }

    public void notifcationSend()
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("error", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        SharedPreferences.Editor editor = getSharedPreferences("data_user", MODE_PRIVATE).edit();
                        editor.putString("token_app", token);
                        editor.apply();
                    }
                });
    }

    public void iralogin (){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void iraregistro (View View){

        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }
    public void iraMenu (){
        Intent i = new Intent(this, InicioMenuActivity.class);
        startActivity(i);}

    private  void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    iraMenu();
                    Toast.makeText(MainActivity.this,"¡Bienvenido!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",usuario);
                parametros.put("password",contraseña);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}