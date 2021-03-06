package br.edu.fatec.ifruta_v2.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.ifruta_v2.R;
import br.edu.fatec.ifruta_v2.database.ControllerProduto;
import br.edu.fatec.ifruta_v2.entidades.Produto;

public class AreaCliente extends AppCompatActivity {

    private Button btAddToCart;
    private SearchView searchView;
    private ListView listView;
    private ControllerProduto controllerProduto;
    ArrayList<String> listProductsName = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_area_cliente);

        controllerProduto = new ControllerProduto(getApplicationContext());
        searchView = (SearchView) findViewById(R.id.busca);
        listView = (ListView) findViewById(R.id.list_item);

        List<Produto> listProducts= controllerProduto.listarProdutos();
        for (Produto item : listProducts) {
            listProductsName.add(item.getDescricao());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_product,listProductsName);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

                Intent intent = new Intent (AreaCliente.this,DescriptionProduct.class);
                intent.putExtra("name",listProductsName.get(index));

                startActivity(intent);
            }
        });
        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            finish();
        }


    }
}
