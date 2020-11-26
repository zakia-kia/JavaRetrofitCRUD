package com.zakia.idn.javaretrofitcrud.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zakia.idn.javaretrofitcrud.R;
import com.zakia.idn.javaretrofitcrud.activity.ProductActivity;
import com.zakia.idn.javaretrofitcrud.model.PersonItem;

import java.util.List;

 public class ProductAdapter extends ArrayAdapter<PersonItem> {
    private Context context;
    private List<PersonItem> personItems;
    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<PersonItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.personItems = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.list_item,parent,false);

        TextView txtIdProduct = v.findViewById(R.id.txt_product_id);
        TextView txtNameProduct = v.findViewById(R.id.txt_product_name);
        TextView txtPriceProduct = v.findViewById(R.id.txt_product_price);
        TextView txtDescProduct = v.findViewById(R.id.txt_product_desc);

        txtIdProduct.setText(String.valueOf(personItems.get(position).getId()));
        txtNameProduct.setText(String.valueOf( personItems.get(position).getName()));
        txtPriceProduct.setText(String.valueOf( personItems.get(position).getPrice()));
        txtDescProduct.setText(String.valueOf( personItems.get(position).getDesc()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("id", String.valueOf ( personItems.get(position).getId()));
                intent.putExtra("name", personItems.get(position).getName());
                intent.putExtra("price", personItems.get(position).getPrice());
                intent.putExtra("desc", personItems.get(position).getDesc());
                context.startActivity(intent);
            }
        });

        return v;
    }
}
