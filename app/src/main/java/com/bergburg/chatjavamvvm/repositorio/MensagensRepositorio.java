package com.bergburg.chatjavamvvm.repositorio;


import com.bergburg.chatjavamvvm.intefaces.APIListener;
import com.bergburg.chatjavamvvm.model.Dados;
import com.bergburg.chatjavamvvm.repositorio.remoto.RetrofitClient;
import com.bergburg.chatjavamvvm.repositorio.remoto.services.ChatService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensagensRepositorio {
    private ChatService service = RetrofitClient.classService(ChatService.class);

    public void getMensagens(APIListener<Dados> listener, Long idUSuario ){
        Call<Dados> call = service.getMensagens(idUSuario);
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage().toString());
            }
        });

    }

    public void enviarMensagem(APIListener<Dados> listener, Long idUsuario, String texto, Long idConversa ){
        Call<Dados> call = service.enviarMensagem(idUsuario,texto,idConversa);
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                listener.onFailures(t.getMessage().toString());
            }
        });

    }
}
