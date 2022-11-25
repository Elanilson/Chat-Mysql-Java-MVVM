package com.bergburg.chatjavamvvm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.bergburg.chatjavamvvm.R;
import com.bergburg.chatjavamvvm.adapter.MensagemAdapter;
import com.bergburg.chatjavamvvm.databinding.ActivityChatBinding;
import com.bergburg.chatjavamvvm.model.Mensagem;
import com.bergburg.chatjavamvvm.viewmodel.ChatViewModel;

import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private MensagemAdapter adapter = new MensagemAdapter();
    private ChatViewModel viewModel;
    private Long idUsuario = 1L;
    private Long idRemetente = 5L;
    private Long idConversa = 0L;
    private String texto;


    private Runnable runnable;
    private Handler handler = new Handler();
    private Boolean ticker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        binding.imageButtonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto = binding.editTextMensagem.getText().toString();
                viewModel.enviarMensagem(idRemetente,idConversa,texto);
                binding.editTextMensagem.setText("");
            }
        });
        configurarRecycleview();

        observe();
    }

    private void observe() {
        viewModel.exibirMensagens.observe(this, new Observer<List<Mensagem>>() {
            @Override
            public void onChanged(List<Mensagem> mensagems) {
                if(mensagems != null){
                    adapter.attackMensagens(mensagems);
                }
            }
        });

        viewModel.mensagem.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mensagem) {
                Toast.makeText(ChatActivity.this, mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startClock(){
        final Calendar calendar = Calendar.getInstance();
        this.runnable = new Runnable() {
            @Override
            public void run() {
                if(!ticker){
                    return;
                }

                viewModel.getMensagens(idUsuario);

                Long now = SystemClock.uptimeMillis();
                Long next = now + (1000 - (now % 1000));
                handler.postAtTime(runnable,next);



            }
        };
        this.runnable.run();
    }

    private void  configurarRecycleview(){
        binding.recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewChat.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idUsuario =  bundle.getLong("idUsuario");
            idConversa =  bundle.getLong("idConversa");
            viewModel.getMensagens(idUsuario);
            ticker = true;
            startClock();
        }





    }

    @Override
    protected void onStop() {
        super.onStop();
        ticker = false;
    }

}