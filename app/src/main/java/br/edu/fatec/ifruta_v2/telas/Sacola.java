package br.edu.fatec.ifruta_v2.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.ifruta_v2.R;
import br.edu.fatec.ifruta_v2.database.ControllerPedido;
import br.edu.fatec.ifruta_v2.database.DBHelper;
import br.edu.fatec.ifruta_v2.entidades.ItemsPedido;
import br.edu.fatec.ifruta_v2.entidades.Produto;
import br.edu.fatec.ifruta_v2.entidades.ProdutoInSacola;
import br.edu.fatec.ifruta_v2.utils.CustomAdapter;

public class Sacola extends AppCompatActivity {

    private TextView name;
    private TextView price;
    private TextView amount;
    private TextView valorTotal;
    private Button searchMore;
    private Button finalize;
    private Button esvaziarSacola;
    private ControllerPedido controllerPedido;
    ArrayList<ItemsPedido> listProductsName = new ArrayList<>();
    //ArrayList<ItemsPedido> listProductsPrecos = new ArrayList<>();
    private ListView listView;

    private float valorCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sacola);

        controllerPedido = new ControllerPedido(getApplicationContext());
        setLayout();
        setListView();
        setValorTotal();

        searchMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sacola.this,AreaCliente.class);
                startActivity(intent);

            }
        });

        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sacola.this,FinalizarPedido.class);
                startActivity(intent);
            }
        });

        esvaziarSacola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esvaziaSacola();
            }
        });
    }

    private void setLayout() {

        searchMore = (Button) findViewById(R.id.searchMore);
        finalize = (Button) findViewById(R.id.finalize);
        esvaziarSacola = (Button) findViewById(R.id.buttonLimpar);
        name = (TextView) findViewById(R.id.itemName);
        price = (TextView) findViewById(R.id.itemPrice);
        amount = (TextView) findViewById(R.id.itemAmount);
        valorTotal = (TextView) findViewById(R.id.valorTotal);
        listView = (ListView) findViewById(R.id.list_pedido);
    }

    public void setListView() {

        ArrayList<ProdutoInSacola> listProducts = controllerPedido.getProdutosInSacola(1);
        for( ProdutoInSacola item : listProducts){
            listProductsName.add(new ItemsPedido(
                    item.getDescricao(),
                    item.getQuantidade(),
                    item.getPreco()
            ));
            valorCompra = valorCompra + item.getPreco();
            valorTotal.setText(String.valueOf(valorCompra));
        }

        for ( int i = 0 ; i< listProductsName.size() -1; i++){
            Log.d("PRODUCT", String.valueOf(listProductsName.get(i).getName()));
        }
        CustomAdapter adapter = new CustomAdapter(this,listProductsName);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setValorTotal() {


    }

    public void esvaziaSacola() {

        listProductsName.removeAll(listProductsName);
        //CustomAdapter adapter = new CustomAdapter(this,listProductsName);
        listView.setAdapter(null);
    }
}