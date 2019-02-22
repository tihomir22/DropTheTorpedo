package com.example.sportak.torpedodrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sportak.torpedodrop.Adaptadores.ViewPagerAdaptador;
import com.example.sportak.torpedodrop.Fragments.FragmentoChats;
import com.example.sportak.torpedodrop.Fragments.FragmentoProfile;
import com.example.sportak.torpedodrop.Fragments.FragmentoUsuarios;
import com.example.sportak.torpedodrop.Model.Chat;
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
    ProgressDialog progress;
    Menu menuglobal;

    static Boolean tasca1=false;
    static Boolean tasca2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        imagen_perfil=findViewById(R.id.profile_image);
        usuario=findViewById(R.id.username);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        iniciarCarga();



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
                tasca1=true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TabLayout tabLayout=findViewById(R.id.tab_layout);
        final ViewPager viewPager=findViewById(R.id.view_pager);



        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ViewPagerAdaptador viewPageradaptador=new ViewPagerAdaptador(getSupportFragmentManager());
                int sinLeer=0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat= snapshot.getValue(Chat.class);
                    if(chat.getMessage().equals(firebaseUser.getUid()) && !chat.isVisto()){
                        sinLeer++;
                    }
                }
                if(sinLeer==0){
                    viewPageradaptador.addFragment(new FragmentoChats(),ResourcesLocale.getResoruces(PostLoginActivity.this).getString(R.string.bar_layout_chats));
                }else{
                    viewPageradaptador.addFragment(new FragmentoChats(),"("+sinLeer+") "+ResourcesLocale.getResoruces(PostLoginActivity.this).getString(R.string.bar_layout_chats)+"");
                }

                viewPageradaptador.addFragment(new FragmentoUsuarios(),ResourcesLocale.getResoruces(PostLoginActivity.this).getString(R.string.bar_layout_users));
                viewPageradaptador.addFragment(new FragmentoProfile(),ResourcesLocale.getResoruces(PostLoginActivity.this).getString(R.string.bar_layout_profiles));

                viewPager.setAdapter(viewPageradaptador);
                tabLayout.setupWithViewPager(viewPager);

                tasca2=true;

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ThreadProgreso thread=new ThreadProgreso(progress);
        thread.start();

    }

    private void iniciarCarga() {
        progress = new ProgressDialog(this);
        progress.setTitle(ResourcesLocale.getResoruces(PostLoginActivity.this).getString(R.string.simple_loading));
        progress.setMessage(ResourcesLocale.getResoruces(PostLoginActivity.this).getString(R.string.torpedo_loading));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }


    public static synchronized boolean getTasca1(){
        return tasca1;
    }
    public static synchronized boolean getTasca2(){
        return tasca2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        this.menuglobal=menu;
        modificarTextosMenu();
        return true;
    }
    public void modificarTextosMenu(){
        this.menuglobal.getItem(0).setTitle(ResourcesLocale.getResoruces(PostLoginActivity.this).getString(R.string.logout));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PostLoginActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

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

class ThreadProgreso extends Thread{
    ProgressDialog progress;

    public ThreadProgreso(ProgressDialog progress) {
        this.progress = progress;
    }

    @Override
    public void run(){
        comprobarFinalizacion();
    }

    private void comprobarFinalizacion(){
        while(!PostLoginActivity.getTasca1() || !PostLoginActivity.getTasca2()){
            //do nothing
        }
        progress.dismiss();
    }
}
