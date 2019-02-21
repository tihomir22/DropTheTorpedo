package com.example.sportak.torpedodrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ContraseaOlvidadaActivity extends AppCompatActivity {

    MaterialEditText enviar_email;
    Button btn_reiniciar;

    FirebaseAuth firebaseAuth;
    ProgressDialog progress;

    static boolean  envioCorreo=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasea_olvidada);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(ResourcesLocale.getResoruces(this).getString(R.string.restart_email));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enviar_email=findViewById(R.id.send_email);
        enviar_email.setHint(ResourcesLocale.getResoruces(this).getString(R.string.input_email));
        enviar_email.setFloatingLabelText(ResourcesLocale.getResoruces(this).getString(R.string.input_email));

        btn_reiniciar=findViewById(R.id.btn_reset);
        btn_reiniciar.setText(ResourcesLocale.getResoruces(this).getString(R.string.restart_email));
        ((TextView)findViewById(R.id.textoinformativo)).setText(ResourcesLocale.getResoruces(this).getString(R.string.hint_restart_email));

        firebaseAuth=FirebaseAuth.getInstance();

        btn_reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarCarga();

                String email=enviar_email.getText().toString();
                if(email.equals("")){
                    Toast.makeText(ContraseaOlvidadaActivity.this,getString(R.string.all_required),Toast.LENGTH_SHORT).show();
                }else{
                    progress.show();
                    ThreadProgresoEmail thread=new ThreadProgresoEmail(progress);
                    thread.start();
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                envioCorreo=false;
                                Toast.makeText(ContraseaOlvidadaActivity.this,ResourcesLocale.getResoruces(ContraseaOlvidadaActivity.this).getString(R.string.check_email_pass),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ContraseaOlvidadaActivity.this,LoginActivity.class));

                            }else{
                                envioCorreo=false;
                                String error = task.getException().getMessage();
                                Toast.makeText(ContraseaOlvidadaActivity.this,error,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });




    }
    private void iniciarCarga() {
        envioCorreo=true;
        System.out.println("activando progress bar?");
        progress = new ProgressDialog(ContraseaOlvidadaActivity.this);
        progress.setTitle(getString(R.string.simple_loading));
        progress.setMessage(getString(R.string.torpedo_loading));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

    }

    public static synchronized boolean getEnvioEmail(){
        return envioCorreo;
    }


}

class ThreadProgresoEmail extends Thread{
    ProgressDialog progress;

    public ThreadProgresoEmail(ProgressDialog progress) {
        this.progress = progress;
    }

    @Override
    public void run(){
        comprobarFinalizacion();
    }

    private void comprobarFinalizacion(){
        System.out.println("entro...");
        while(ContraseaOlvidadaActivity.getEnvioEmail()){
            //do nothingee
        }
        progress.dismiss();
    }
}
