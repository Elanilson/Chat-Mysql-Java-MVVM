package com.bergburg.chatjavamvvm.intefaces;

public interface APIListener<T>{
    void onSuccess(T result);
    void onFailures(String mensagem);

}