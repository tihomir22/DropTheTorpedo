package com.example.sportak.torpedodrop.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sportak.torpedodrop.MensajeriaActivity;
import com.example.sportak.torpedodrop.Model.Chat;
import com.example.sportak.torpedodrop.Model.User;
import com.example.sportak.torpedodrop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.List;

public class MensajeAdaptador extends RecyclerView.Adapter<MensajeAdaptador.ViewHolder> {


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGH = 1;
    private Context mcontext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fuser;

    public MensajeAdaptador(Context mcontext, List<Chat> mChat,String imageurl) {
        this.mcontext = mcontext;
        this.mChat = mChat;
        this.imageurl=imageurl;
    }

    @NonNull
    @Override
    public MensajeAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==MSG_TYPE_RIGH){
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MensajeAdaptador.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MensajeAdaptador.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MensajeAdaptador.ViewHolder viewHolder, int i) {
        Chat chat=mChat.get(i);
        viewHolder.show_message.setText(chat.getMessage());
        if(imageurl.equals("default")){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mcontext).load(imageurl).into(viewHolder.profile_image);
        }

        if(i==mChat.size()-1){ // comprobar el ultimo mensaje
            if(chat.isVisto()){
                viewHolder.txt_visto.setText("Visto");
            }else{
                viewHolder.txt_visto.setText("Enviado");
            }
        }else{
            viewHolder.txt_visto.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_visto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_visto=itemView.findViewById(R.id.visto);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGH;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}


