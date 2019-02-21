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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    MaterialEditText username,email,password;
    Button btn_registro;
    TextView titulo;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titulo=findViewById(R.id.titulo);
        titulo.setText(ResourcesLocale.getResoruces(this).getString(R.string.create_account));

        username=findViewById(R.id.username);
        username.setHint(ResourcesLocale.getResoruces(this).getString(R.string.username_register));
        username.setFloatingLabelText(ResourcesLocale.getResoruces(this).getString(R.string.username_register));

        email=findViewById(R.id.email);
        email.setHint(ResourcesLocale.getResoruces(this).getString(R.string.input_email));
        email.setFloatingLabelText(ResourcesLocale.getResoruces(this).getString(R.string.input_email));

        password=findViewById(R.id.password);
        password.setHint(ResourcesLocale.getResoruces(this).getString(R.string.simple_pass));
        password.setFloatingLabelText(ResourcesLocale.getResoruces(this).getString(R.string.simple_pass));

        btn_registro=findViewById(R.id.btn_register);
        btn_registro.setText(ResourcesLocale.getResoruces(this).getString(R.string.simple_register));

        auth=FirebaseAuth.getInstance();
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_usuario=username.getText().toString();
                String txt_contra=password.getText().toString();
                String txt_email=email.getText().toString();

                if(TextUtils.isEmpty(txt_usuario) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_contra)){
                    Toast.makeText(RegisterActivity.this,getString(R.string.all_required),Toast.LENGTH_SHORT).show();

                }else if(txt_contra.length()<6){
                    Toast.makeText(RegisterActivity.this,getString(R.string.pass_six),Toast.LENGTH_SHORT).show();
                }else{
                    register(txt_usuario,txt_email,txt_contra);
                }
            }
        });
    }

    private void register(final String user, String email, String pass){
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid=firebaseUser.getUid();

                    reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username",user);
                    hashMap.put("status","offline");
                    hashMap.put("imageURL","default");
                    hashMap.put("search",user.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(RegisterActivity.this,PostLoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this,getString(R.string.aut_failed),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
            }
        });

    }

}
