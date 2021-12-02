package br.edu.fatec.ifruta_v2.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.edu.fatec.ifruta_v2.R;
import br.edu.fatec.ifruta_v2.entidades.ItemsPedido;

public class CustomAdapter extends ArrayAdapter<ItemsPedido> {

    private final ArrayList<ItemsPedido> itemsPedidos;
    public CustomAdapter(Context context, ArrayList<ItemsPedido> itemsPedidos) {
        super(context, 0, itemsPedidos);
        this.itemsPedidos = itemsPedidos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemsPedido item = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_pedidos, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.itemName);
        TextView amount = (TextView) convertView.findViewById(R.id.itemAmount);
        TextView price = (TextView) convertView.findViewById(R.id.itemPrice);

        name.setText(item.getName());
        amount.setText(String.valueOf(item.getAmount()));
        price.setText(String.valueOf(item.getPrice()));

        return convertView;
    }

}
