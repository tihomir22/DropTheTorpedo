package com.example.sportak.torpedodrop.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sportak.torpedodrop.MensajeriaActivity;
import com.example.sportak.torpedodrop.Model.User;
import com.example.sportak.torpedodrop.R;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private Context mcontext;
    private List<User>mUsuarios;


    public UsuarioAdapter(Context mcontext,List<User>mUsuarios){
        this.mcontext=mcontext;
        this.mUsuarios=mUsuarios;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.item_usuario,viewGroup,false);
        return new UsuarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User usuario=mUsuarios.get(i);
        viewHolder.usuario.setText(usuario.getUsername());
        if(usuario.getImageURL().equals("default")){
            viewHolder.perfil.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mcontext).load(usuario.getImageURL()).into(viewHolder.perfil);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,MensajeriaActivity.class);
                intent.putExtra("userid",usuario.getId());
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView usuario;
        public ImageView perfil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usuario=itemView.findViewById(R.id.username);
            perfil  =itemView.findViewById(R.id.profile_image);
        }
    }


}
