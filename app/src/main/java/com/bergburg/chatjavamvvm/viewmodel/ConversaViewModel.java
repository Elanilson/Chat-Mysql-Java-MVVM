package com.bergburg.chatjavamvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.chatjavamvvm.intefaces.APIListener;
import com.bergburg.chatjavamvvm.model.Contato;
import com.bergburg.chatjavamvvm.model.Conversa;
import com.bergburg.chatjavamvvm.model.Dados;
import com.bergburg.chatjavamvvm.repositorio.ConversaRepositorio;

import java.util.List;

public class ConversaViewModel extends AndroidViewModel {

    private ConversaRepositorio repositorio = new ConversaRepositorio();

    private MutableLiveData<List<Conversa>> _Conversas = new MutableLiveData<>();
    public LiveData<List<Conversa>> conversas = _Conversas;

    private  MutableLiveData<String> _Mensagem = new MutableLiveData<>();
    public LiveData<String> mensagem = _Mensagem;

    public ConversaViewModel(@NonNull Application application) {
        super(application);
    }

    public void exibirConversas(){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Conversas.setValue(result.getConversas());
            }

            @Override
            public void onFailures(String mensagem) {
                _Mensagem.setValue(mensagem);
            }
        };
        repositorio.carregarConversas(listener);

    }
}
