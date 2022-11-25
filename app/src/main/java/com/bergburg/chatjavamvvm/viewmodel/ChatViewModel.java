package com.bergburg.chatjavamvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.chatjavamvvm.intefaces.APIListener;
import com.bergburg.chatjavamvvm.model.Dados;
import com.bergburg.chatjavamvvm.model.Mensagem;
import com.bergburg.chatjavamvvm.repositorio.MensagensRepositorio;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    private MensagensRepositorio repositorio = new MensagensRepositorio();

    private MutableLiveData<List<Mensagem>> _Mensagens = new MutableLiveData<>();
    public LiveData<List<Mensagem>> exibirMensagens = _Mensagens;
    private  MutableLiveData<String> _Mensagem = new MutableLiveData<>();
    public LiveData<String> mensagem = _Mensagem;

    public ChatViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMensagens(Long idUsuario){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Mensagens.setValue(result.getMensagens());
            }

            @Override
            public void onFailures(String mensagem) {
                _Mensagem.setValue(mensagem);
            }
        };

        repositorio.getMensagens(listener,idUsuario);
    }

    public void enviarMensagem(Long idUsuario, Long idConversa, String texto){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                if(result.getStatus()){
                    _Mensagem.setValue("Sucesso");
                }else{
                    _Mensagem.setValue("Não foi possível enviar");
                }
            }

            @Override
            public void onFailures(String mensagem) {
                _Mensagem.setValue(mensagem);
            }
        };
        repositorio.enviarMensagem(listener,idUsuario,texto,idConversa);
    }
}
