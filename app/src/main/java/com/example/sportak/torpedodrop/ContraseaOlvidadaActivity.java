package com.example.sportak.torpedodrop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class ContraseaOlvidadaActivity extends AppCompatActivity {

    EditText enviar_email;
    Button btn_reiniciar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasea_olvidada);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enviar_email=findViewById(R.id.send_email);
        btn_reiniciar=findViewById(R.id.btn_reset);

        firebaseAuth=FirebaseAuth.getInstance();

        btn_reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=enviar_email.getText().toString();
                if(email.equals("")){

                }
            }
        });

    }
}