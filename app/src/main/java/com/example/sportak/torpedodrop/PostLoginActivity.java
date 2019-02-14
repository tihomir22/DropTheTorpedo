package com.example.sportak.torpedodrop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sportak.torpedodrop.Adaptadores.ViewPagerAdaptador;
import com.example.sportak.torpedodrop.Fragments.FragmentoChats;
import com.example.sportak.torpedodrop.Fragments.FragmentoProfile;
import com.example.sportak.torpedodrop.Fragments.FragmentoUsuarios;
import com.example.sportak.torpedodrop.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostLoginActivity extends AppCompatActivity {


    CircleImageView imagen_perfil;
    TextView usuario;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        imagen_perfil=findViewById(R.id.profile_image);
        usuario=findViewById(R.id.username);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");



        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();



        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=new User();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    System.out.println(childSnapshot.getKey()+"\t"+
                          childSnapshot.getValue(String.class));

                    switch(childSnapshot.getKey().toString()){
                        case "id":
                            user.setId(childSnapshot.getValue(String.class));
                            break;
                        case "imageURL":
                            user.setImageURL(childSnapshot.getValue(String.class));
                            break;

                        case "username":
                            user.setUsername(childSnapshot.getValue(String.class));
                            break;

                    }



                }
                if(user!=null) {
                    usuario.setText(user.getUsername());
                    if (user.getImageURL().equalsIgnoreCase("default")) {
                        imagen_perfil.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        Glide.with(getApplicationContext()).load(user.getImageURL()).into(imagen_perfil);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TabLayout tabLayout=findViewById(R.id.tab_layout);
        ViewPager viewPager=findViewById(R.id.view_pager);

        ViewPagerAdaptador viewPageradaptador=new ViewPagerAdaptador(getSupportFragmentManager());
        viewPageradaptador.addFragment(new FragmentoChats(),"Chats");
        viewPageradaptador.addFragment(new FragmentoUsuarios(),"Usuarios");
        viewPageradaptador.addFragment(new FragmentoProfile(),"Perfil");

        viewPager.setAdapter(viewPageradaptador);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PostLoginActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));


                return true;
        }
        return false;
    }

    //Funcion usada para guardar el estado de un usuario o actualizarlo
    private void status(String status){
        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("status",status);
        reference.updateChildren(hashMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
