package com.example.sportak.torpedodrop.Model;

public class Chat {

    private String sender;
    private String receiver;
    private String message;
    private boolean visto;
    private String photoEnviada;

    public Chat(String sender, String receiver, String message,Boolean visto) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.visto=visto;
    }

    public Chat() {

    }

    @Override
    public String toString() {
        return "Chat{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getPhotoEnviada() {
        return photoEnviada;
    }

    public void setPhotoEnviada(String photoEnviada) {
        this.photoEnviada = photoEnviada;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}
