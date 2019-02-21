package com.example.sportak.torpedodrop.Notificaciones;

import com.example.sportak.torpedodrop.Notificaciones.MyResponse;
import com.example.sportak.torpedodrop.Notificaciones.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAepfNFAA:APA91bGXQaR_dN6WCl81if8qfkaKljDJ_Y7X9VB2Yr17gR-mVbL2w3LaaRLD14SMf27AdATeprHbbuF4wioyec82-zaaMJEnylr4dphJiipBqNNNrXGCarebj8xkJo3__qg0JTElyiOx"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
