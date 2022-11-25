package com.bergburg.chatjavamvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bergburg.chatjavamvvm.intefaces.APIListener;
import com.bergburg.chatjavamvvm.model.Contato;
import com.bergburg.chatjavamvvm.model.Dados;
import com.bergburg.chatjavamvvm.model.Mensagem;
import com.bergburg.chatjavamvvm.repositorio.ContatoRepositorio;

import java.util.List;

public class ContatoViewModel extends AndroidViewModel {

    private ContatoRepositorio repositorio = new ContatoRepositorio();

    private MutableLiveData<List<Contato>> _Contatos = new MutableLiveData<>();
    public LiveData<List<Contato>> contatos = _Contatos;

    private  MutableLiveData<String> _Mensagem = new MutableLiveData<>();
    public LiveData<String> mensagem = _Mensagem;

    public ContatoViewModel(@NonNull Application application) {
        super(application);
    }

    public void exibirContatos(){
        APIListener<Dados> listener = new APIListener<Dados>() {
            @Override
            public void onSuccess(Dados result) {
                _Contatos.setValue(result.getContatos());
            }

            @Override
            public void onFailures(String mensagem) {
                _Mensagem.setValue(mensagem);
            }
        };

        repositorio.carregarContatos(listener);
    }
}
