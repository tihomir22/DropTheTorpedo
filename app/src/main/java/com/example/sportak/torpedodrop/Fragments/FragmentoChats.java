package com.example.sportak.torpedodrop.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.sportak.torpedodrop.Adaptadores.UsuarioAdapter;
import com.example.sportak.torpedodrop.Model.Chat;
import com.example.sportak.torpedodrop.Model.Chatlist;
import com.example.sportak.torpedodrop.Model.User;
import com.example.sportak.torpedodrop.Notificaciones.Token;
import com.example.sportak.torpedodrop.R;
import com.example.sportak.torpedodrop.Threads.CambiadorDeImagen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class FragmentoChats extends Fragment {

    private RecyclerView recyclerView;
    private UsuarioAdapter usuarioAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;
    CambiadorDeImagen thread;

    private List<Chatlist> chatlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fragmento_chats, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        chatlist=new ArrayList<>();

        cambiarFondoDinamicamente(view);

        reference=FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatlist.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chatlist chatlistn=snapshot.getValue(Chatlist.class);
                    chatlist.add(chatlistn);
                }
                listaChat();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }

    private void listaChat(){
        mUsers=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot:  dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                    for (Chatlist chat:chatlist){
                        if(user.getId()!=null) {
                            if (user.getId().equals(chat.getId())) {
                                mUsers.add(user);
                            }
                        }
                    }
                }
                usuarioAdapter=new UsuarioAdapter(getContext(),mUsers,true);
                recyclerView.setAdapter(usuarioAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cambiarFondoDinamicamente(View view)  {
        RelativeLayout rLayout = (RelativeLayout) view.findViewById(R.id.layout_rela_frag);
        Resources res = getResources(); //resource handle
        Drawable drawable = res.getDrawable(R.drawable.photo1); //new Image that was added to the res folder
        //rLayout.setBackground(drawable);
        int[]listaDrawablesIds=new int[]{R.drawable.photo1,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4,R.drawable.photo5,R.drawable.photo6,R.drawable.photo7};
        Drawable[]listaDrawable=new Drawable[listaDrawablesIds.length];
        for (int i=0;i<listaDrawablesIds.length;i++){
            listaDrawable[i]=getResources().getDrawable(listaDrawablesIds[i]);
        }
        thread=new CambiadorDeImagen(getContext(),rLayout,listaDrawable);
        thread.start();
    }




}




