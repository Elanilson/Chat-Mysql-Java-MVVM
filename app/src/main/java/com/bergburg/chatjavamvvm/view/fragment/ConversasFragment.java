package com.bergburg.chatjavamvvm.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bergburg.chatjavamvvm.R;
import com.bergburg.chatjavamvvm.adapter.ConversaAdapter;
import com.bergburg.chatjavamvvm.databinding.FragmentConversasBinding;
import com.bergburg.chatjavamvvm.intefaces.AcaoListener;
import com.bergburg.chatjavamvvm.model.Conversa;
import com.bergburg.chatjavamvvm.view.activity.ChatActivity;
import com.bergburg.chatjavamvvm.viewmodel.ConversaViewModel;

import java.util.List;


public class ConversasFragment extends Fragment {

    private FragmentConversasBinding binding;
    private ConversaViewModel viewModel;
    private ConversaAdapter adapter = new ConversaAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConversasBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(ConversaViewModel.class);

        AcaoListener<Conversa> listener = new AcaoListener<Conversa>() {
            @Override
            public void onClick(Conversa objeto) {
                Bundle bundle = new  Bundle();
                bundle.putLong("idUsuario",objeto.getIdUsuario());
                bundle.putLong("idConversa",objeto.getId());
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        adapter.attackListener(listener);

        configurarRecycleview();
        observe();

        return binding.getRoot();
    }

    private void observe() {
        viewModel.conversas.observe(getViewLifecycleOwner(), new Observer<List<Conversa>>() {
            @Override
            public void onChanged(List<Conversa> conversas) {
                if(conversas != null){
                    adapter.attackConversas(conversas);
                }
            }
        });

        viewModel.mensagem.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mensagem) {
                Toast.makeText(getActivity(), mensagem, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void  configurarRecycleview(){
        binding.recyclerViewConversa.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewConversa.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.exibirConversas();
    }
}