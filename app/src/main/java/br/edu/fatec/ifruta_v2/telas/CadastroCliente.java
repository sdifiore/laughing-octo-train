package br.edu.fatec.ifruta_v2.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.fatec.ifruta_v2.R;
import br.edu.fatec.ifruta_v2.database.ControllerCliente;
import br.edu.fatec.ifruta_v2.database.DBHelper;
import br.edu.fatec.ifruta_v2.entidades.Cliente;

public class CadastroCliente extends AppCompatActivity {

    private EditText nome, cpf, telefone, endereco, login, senha;
    private Button btCadastrar;

    private String stNome, stCpf, stTelefone, stEndereco, stLogin, stSenha;
    private Boolean textoPreenchido;
    SQLiteDatabase sqLiteDB;
    DBHelper dbHelper;
    Cursor cursor;
    String resultado = "NOT_FOUND";
    long idClient;

    ControllerCliente controllerCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cadastro_cliente);

        nome = (EditText) findViewById(R.id.nome);
        cpf = (EditText) findViewById(R.id.cpf);
        telefone = (EditText) findViewById(R.id.telefone);
        endereco = (EditText) findViewById(R.id.endereco);
        login = (EditText) findViewById(R.id.login);
        senha = (EditText) findViewById(R.id.senha);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);

        dbHelper = new DBHelper(this);
        controllerCliente = new ControllerCliente(getApplicationContext());

        btCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                checarTextoVazio();
                checarLoginExistente();
                esvaziarTexto();
            }
        });
    }

    public void checarTextoVazio() {

        stNome = nome.getText().toString();
        stCpf = cpf.getText().toString();
        stTelefone = telefone.getText().toString();
        stEndereco = endereco.getText().toString();
        stLogin = login.getText().toString();
        stSenha = senha.getText().toString();
        if(TextUtils.isEmpty(stNome) || TextUtils.isEmpty(stCpf) || TextUtils.isEmpty(stTelefone) ||
                TextUtils.isEmpty(stEndereco) || TextUtils.isEmpty(stLogin) || TextUtils.isEmpty(stSenha)){
            textoPreenchido = false ;
        } else {
            textoPreenchido = true ;
        }
    }

    public void checarLoginExistente() {

        sqLiteDB = dbHelper.getWritableDatabase();
        cursor = sqLiteDB.query(DBHelper.TABELA1, null, " " +
                        DBHelper.Tabela1_Column_Login + "=?", new String[]{stLogin}, null,
                null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                resultado = "Este login já está sendo usado";
                cursor.close();
            }
        }
        checarResultadoFinal();
    }

    public void checarResultadoFinal() {

        if (resultado.equalsIgnoreCase("Este login já está sendo usado")) {
            Toast.makeText(CadastroCliente.this,"Este login já está sendo usado",
                    Toast.LENGTH_LONG).show();
        } else {
            inserirDadosBD();
        }
        resultado = "NOT_FOUND";
    }

    public void inserirDadosBD() {
        if(textoPreenchido) {
           idClient = controllerCliente.inserirCliente(new Cliente(
                    stNome,
                    stCpf,
                    stTelefone,
                    stEndereco,
                    stLogin,
                    stSenha,
                    1
            ));
            Toast.makeText(CadastroCliente.this,"Cliente registrado com sucesso",
                    Toast.LENGTH_LONG).show();
            setCurrentUser(idClient);
        } else {
            Toast.makeText(CadastroCliente.this,"Por favor preencha todos os campos",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void esvaziarTexto() {

        nome.getText().clear();
        cpf.getText().clear();
        telefone.getText().clear();
        endereco.getText().clear();
        login.getText().clear();
        senha.getText().clear();

        Intent intent = new Intent(CadastroCliente.this, MainActivity.class);
        startActivity(intent);
    }

    private void setCurrentUser(long value) {

        SharedPreferences sharedPreferences = getSharedPreferences("loged", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("currentUser", value);
        editor.apply();
    }
}