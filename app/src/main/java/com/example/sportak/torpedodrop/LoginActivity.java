package com.example.sportak.torpedodrop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {






    MaterialEditText email,password;
    Button btn_login;

    FirebaseAuth auth;
    TextView contrasenya_olvidada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        email.setHint(ResourcesLocale.getResoruces(this).getString(R.string.input_email));
        email.setFloatingLabelText(ResourcesLocale.getResoruces(this).getString(R.string.input_email));

        password=findViewById(R.id.password);
        password.setFloatingLabelText(ResourcesLocale.getResoruces(this).getString(R.string.simple_pass));
        password.setHint(ResourcesLocale.getResoruces(this).getString(R.string.simple_pass));
        btn_login=findViewById(R.id.btn_login);
        btn_login.setText(ResourcesLocale.getResoruces(this).getString(R.string.simple_login));
        contrasenya_olvidada=findViewById(R.id.forgot_password);
        contrasenya_olvidada.setText(ResourcesLocale.getResoruces(this).getString(R.string.forgotten_pass));

        contrasenya_olvidada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ContraseaOlvidadaActivity.class));
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_contraseña=password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_contraseña)){
                    Toast.makeText(LoginActivity.this,getString(R.string.all_required),Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(txt_email,txt_contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(LoginActivity.this,PostLoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this,getString(R.string.aut_failed),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }





}
