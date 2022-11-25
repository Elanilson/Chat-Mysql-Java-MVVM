package com.bergburg.chatjavamvvm.repositorio;


import com.bergburg.chatjavamvvm.intefaces.APIListener;
import com.bergburg.chatjavamvvm.model.Dados;
import com.bergburg.chatjavamvvm.repositorio.remoto.RetrofitClient;
import com.bergburg.chatjavamvvm.repositorio.remoto.services.ChatService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversaRepositorio {

    private ChatService service = RetrofitClient.classService(ChatService.class);

    public void carregarConversas(APIListener<Dados> listener ){
        Call<Dados> call = service.getConversas();
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
