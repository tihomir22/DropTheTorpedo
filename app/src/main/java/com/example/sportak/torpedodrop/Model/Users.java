package com.example.sportak.torpedodrop.Model;

public class Users {

    private String id;
    private String usuario;
    private String image_url;


    public Users(String id, String usuario, String image_url) {
        this.id = id;
        this.usuario = usuario;
        this.image_url = image_url;
    }

    public Users() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", usuario='" + usuario + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
