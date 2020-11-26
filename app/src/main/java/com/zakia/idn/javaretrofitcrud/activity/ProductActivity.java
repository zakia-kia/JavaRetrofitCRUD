package com.zakia.idn.javaretrofitcrud.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zakia.idn.javaretrofitcrud.R;
import com.zakia.idn.javaretrofitcrud.model.PersonItem;
import com.zakia.idn.javaretrofitcrud.remote.APIUtils;
import com.zakia.idn.javaretrofitcrud.remote.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    ProductService productService;
    EditText edtName,edtPrice,edtDesc, edtId;
    Button btnSave,btnDelete;
    TextView txtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtName = findViewById(R.id.et_name);
        edtPrice = findViewById(R.id.et_price);
        edtDesc = findViewById(R.id.et_desc);
        edtId = findViewById(R.id.et_id);
        txtId = findViewById(R.id.txt_id);

        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);

        productService = APIUtils.getProductService();

        Bundle extras = getIntent().getExtras();
        final String productId = extras.getString("id");
        String productName = extras.getString("name");
        String productPrice = extras.getString("price");
        String productDesc = extras.getString("desc");

        edtId.setText(productId);
        edtName.setText(productName);
        edtPrice.setText(productPrice);
        edtDesc.setText(productDesc);

        if (productId !=null && productId.trim().length() > 0 ) {
            edtId.setFocusable(false);
        } else {
            txtId.setVisibility(View.INVISIBLE);
            edtId.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String price = edtPrice.getText().toString();
                String desc = edtDesc.getText().toString();

                if (productId !=null && productId.trim().length() > 0 ) {
                    updateProduct(Integer.parseInt(productId),name,price,desc);
                } else {
                    addProduct(name, price, desc);
                }
            }
        });
        
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(Integer.parseInt(productId));
                Intent intent = new Intent(ProductActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addProduct(String name, String price, String desc) {
        Call<PersonItem> call = productService.addProduct(name,price,desc);
        call.enqueue(new Callback<PersonItem>() {
            @Override
            public void onResponse(Call<PersonItem> call, Response<PersonItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductActivity.this,"Product added",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonItem> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void updateProduct(int id, String name, String price, String desc) {
        Call<PersonItem> call = productService.updateProduct(id,name,price,desc);
        call.enqueue(new Callback<PersonItem>() {
            @Override
            public void onResponse(Call<PersonItem> call, Response<PersonItem> response) {
                Toast.makeText(ProductActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PersonItem> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
    private void deleteProduct(int id) {
        Call<PersonItem> call = productService.deleteProduct(id);
        call.enqueue(new Callback<PersonItem>() {
            @Override
            public void onResponse(Call<PersonItem> call, Response<PersonItem> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ProductActivity.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonItem> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
}