package br.edu.fatec.ifruta_v2.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import br.edu.fatec.ifruta_v2.R;

public class FinalizarPedido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_finalizar_pedido);

        Button btSair = (Button) findViewById(R.id.buttonSair);

        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalizarPedido.this,AreaCliente.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("LOGOUT", true);
                startActivity(intent);
            }
        });
    }
}