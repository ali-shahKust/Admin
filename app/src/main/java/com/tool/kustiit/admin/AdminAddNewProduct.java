package com.tool.kustiit.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminAddNewProduct extends AppCompatActivity {

    private Button addProductbtn;
    private ImageView selectProductImg;
    private EditText ProductName, ProductDescription, ProductPrice;


    private  String CategoryName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);


        addProductbtn = (Button)findViewById(R.id.addProductBtn);
        ProductName = (EditText)findViewById(R.id.productName);
        ProductPrice = (EditText)findViewById(R.id.producPrice);
        ProductDescription = (EditText)findViewById(R.id.productDescription);

        CategoryName = getIntent().getExtras().get("Catergory").toString();
        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
    }
}
