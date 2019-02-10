package com.example.sportak.torpedodrop.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.sportak.torpedodrop.Adaptadores.UsuarioAdapter;
import com.example.sportak.torpedodrop.Model.User;
import com.example.sportak.torpedodrop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentoUsuarios extends Fragment {


    private RecyclerView recyclerview;
    private UsuarioAdapter usuarioadapter;
    private List<User> aUsers;

    EditText busqueda_usuarios;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragmento_usuarios,container,false);
        recyclerview=view.findViewById(R.id.recycler_view);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        aUsers=new ArrayList<>();

        usuarioadapter=new UsuarioAdapter(getContext(),aUsers,false);
        recyclerview.setAdapter(usuarioadapter);
        leerUsuarios();

        busqueda_usuarios=view.findViewById(R.id.search_users);
        busqueda_usuarios.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarUsuarios(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return view;
    }

    private void buscarUsuarios(String s) {
        final FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        Query query=FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
                .startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                aUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(!user.getId().equals(fuser.getUid())){
                        aUsers.add(user);
                    }
                }
                usuarioadapter=new UsuarioAdapter(getContext(),aUsers,false);
                recyclerview.setAdapter(usuarioadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void leerUsuarios(){
        final FirebaseUser firebaseusuario=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(busqueda_usuarios.getText().toString().equalsIgnoreCase("")) {
                    aUsers.clear();
                    System.out.println("******************");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        User usuario = snapshot.getValue(User.class);
                        System.out.println(usuario.getUsername());


                        assert usuario != null;
                        assert firebaseusuario != null;

                        //if(usuario.getId().equals(firebaseusuario.getUid())){
                        aUsers.add(usuario);
                        //}
                        System.out.println(aUsers.size());
                    }
                    usuarioadapter = new UsuarioAdapter(getContext(), aUsers, false);
                    recyclerview.setAdapter(usuarioadapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
