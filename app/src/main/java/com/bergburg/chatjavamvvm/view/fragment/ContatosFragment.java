package com.bergburg.chatjavamvvm.view.fragment;

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
import com.bergburg.chatjavamvvm.adapter.ContatoAdapter;
import com.bergburg.chatjavamvvm.databinding.FragmentContatosBinding;
import com.bergburg.chatjavamvvm.model.Contato;
import com.bergburg.chatjavamvvm.viewmodel.ContatoViewModel;

import java.util.List;

public class ContatosFragment extends Fragment {
    private FragmentContatosBinding binding;
    private ContatoViewModel viewModel;
    private ContatoAdapter adapter = new ContatoAdapter();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContatosBinding.inflate(getLayoutInflater(),container, false);
        viewModel = new ViewModelProvider(this).get(ContatoViewModel.class);


        configurarRecycleview();
        observe();
        return binding.getRoot();
    }

    private void observe() {
        viewModel.contatos.observe(getViewLifecycleOwner(), new Observer<List<Contato>>() {
            @Override
            public void onChanged(List<Contato> contatoes) {
                if(contatoes != null){
                    adapter.attackContatos(contatoes);
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
        binding.recyclerViewContatos.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewContatos.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.exibirContatos();
    }
}