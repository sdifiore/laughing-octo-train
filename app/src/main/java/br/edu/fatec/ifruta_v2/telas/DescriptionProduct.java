package br.edu.fatec.ifruta_v2.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.fatec.ifruta_v2.R;
import br.edu.fatec.ifruta_v2.database.ControllerCategoria;
import br.edu.fatec.ifruta_v2.database.ControllerPedido;
import br.edu.fatec.ifruta_v2.database.ControllerProduto;
import br.edu.fatec.ifruta_v2.entidades.Produto;

public class DescriptionProduct extends AppCompatActivity {

    private TextView name;
    private TextView category;
    private TextView price;
    private TextView amount;
    private Button button;
    private Button btIrSacola;
    private ControllerProduto controllerProduto;
    private ControllerPedido controllerPedido;
    Produto product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_description_product);

        String extra = getIntent().getStringExtra("name");
        controllerProduto = new ControllerProduto(getApplicationContext());
        controllerPedido = new ControllerPedido(getApplicationContext());
        setLayout();
        setInfoProduct(extra);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerPedido.inserirProdutoInSacola(1,product.getIdProduto());
            }
        });

        btIrSacola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DescriptionProduct.this,Sacola.class);
                startActivity(intent);
            }
        });
    }

    private void setLayout(){
        button = (Button) findViewById(R.id.buttonAddSacola);
        btIrSacola = (Button) findViewById(R.id.irParaSacola);
        name = (TextView) findViewById(R.id.name);
        category = (TextView) findViewById(R.id.category);
        price = (TextView) findViewById(R.id.price);
        amount = (TextView) findViewById(R.id.amount);
    }
    private void setInfoProduct(String nameProduct){
        product = controllerProduto.getOneProduct(nameProduct);
        name.setText(product.getDescricao());
        category.setText("Categoria: "+product.getCategoria().getDescricao());
        price.setText("Preco: "+ product.getPreco());
        amount.setText("Estoque (UN): "+ product.getQuantidade());
    }
}