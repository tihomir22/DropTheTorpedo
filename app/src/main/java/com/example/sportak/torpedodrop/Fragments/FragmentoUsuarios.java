package com.example.sportak.torpedodrop.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportak.torpedodrop.Adaptadores.UsuarioAdapter;
import com.example.sportak.torpedodrop.Model.User;
import com.example.sportak.torpedodrop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentoUsuarios extends Fragment {


    private RecyclerView recyclerview;
    private UsuarioAdapter usuarioadapter;
    private List<User> aUsers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragmento_usuarios,container,false);
        recyclerview=view.findViewById(R.id.recycler_view);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        aUsers=new ArrayList<>();

        usuarioadapter=new UsuarioAdapter(getContext(),aUsers);
        recyclerview.setAdapter(usuarioadapter);
        leerUsuarios();





        return view;
    }

    private void leerUsuarios(){
        final FirebaseUser firebaseusuario=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                aUsers.clear();
                System.out.println("******************");
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    User usuario=snapshot.getValue(User.class);
                    System.out.println(usuario.getUsername());




                    assert usuario!=null;
                    assert firebaseusuario!=null;

                    //if(usuario.getId().equals(firebaseusuario.getUid())){
                        aUsers.add(usuario);
                    //}
                    System.out.println(aUsers.size());
                }
                usuarioadapter=new UsuarioAdapter(getContext(),aUsers);
                recyclerview.setAdapter(usuarioadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
