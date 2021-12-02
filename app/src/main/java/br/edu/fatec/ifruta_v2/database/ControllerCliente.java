package br.edu.fatec.ifruta_v2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.ifruta_v2.entidades.Cliente;

public class ControllerCliente {

    private static DBHelper dbHelper = null;
    private static SQLiteDatabase db = null;

    public ControllerCliente(Context context) {
        if (dbHelper == null ) {
            dbHelper = new DBHelper(context);
        }
    }

    public long inserirCliente (Cliente cli) {
        db = dbHelper.getWritableDatabase();
        ContentValues valores;
        long resultado;
        valores = new ContentValues();
        valores.put("NOME", cli.getNome());
        valores.put("CPF", cli.getCpf());
        valores.put("TELEFONE", cli.getTelefone());
        valores.put("ENDERECO", cli.getEndereco());
        valores.put("LOGIN", cli.getLogin());
        valores.put("SENHA", cli.getSenha());
        resultado = db.insert(DBHelper.TABELA1, null, valores);
        db.close();
        return resultado;
    }

    public String excluirCliente (Cliente cli) {
        String retorno = "Resgistro excluído com Sucesso";
        String where = "ID = " + cli.getIdCliente();
        db = dbHelper.getReadableDatabase();
        db.delete(DBHelper.TABELA1,where,null);
        db.close();
        return retorno;
    }

    public String alterarCliente (Cliente cli) {
        db = dbHelper.getWritableDatabase();
        ContentValues valores;
        String retorno = "Registro alterado com sucesso";
        String where = "ID = " + cli.getIdCliente();
        valores = new ContentValues();
        valores.put("NOME", cli.getNome());
        valores.put("CPF", cli.getCpf());
        valores.put("TELEFONE", cli.getTelefone());
        valores.put("ENDERECO", cli.getEndereco());
        valores.put("LOGIN", cli.getLogin());
        valores.put("SENHA", cli.getSenha());
        db.update(DBHelper.TABELA1, valores, where,null);
        db.close();
        return retorno;
    }

    public List<Cliente> listarClientes() {
        List<Cliente> listaClientes = new ArrayList<Cliente>();
        String selectQuery = "SELECT * FROM CLIENTES" ;
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Cliente cli = new Cliente();
                cli.setIdCliente(Integer.parseInt("" + cursor.getInt(0)));
                cli.setNome(cursor.getString(1));
                cli.setCpf(cursor.getString(2));
                cli.setTelefone(cursor.getString(3));
                cli.setEndereco(cursor.getString(4));
                cli.setLogin(cursor.getString(5));
                cli.setSenha(cursor.getString(6));
                listaClientes.add(cli);
            } while (cursor.moveToNext());
        }
        return listaClientes;
    }
}