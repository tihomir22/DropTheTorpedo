package com.example.sportak.torpedodrop.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String username;
    private String imageURL;
    //indicara si esta online o offline
    private String status;
    //para la busqueda en el edittext
    private String search;
    //para saber si han visto mi mensaje



    public User(String id, String username, String imageURL, String status,String search) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.status = status;
        this.search=search;
    }

    public User(){

    }

    public String getStatus() {
        return status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
