package com.tool.kustiit.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminCategory extends AppCompatActivity {

    private ImageView tshirts,sports , fdresses , sweater;
    private  ImageView glasses, hats, shoes , purse ;
    private ImageView headphone , laptop , mobile, watches;
    private FirebaseAuth mAuth;

    //Buttons

    private Button logoutBtn , checkOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        mAuth = FirebaseAuth.getInstance();
//first Linear
        tshirts = (ImageView)findViewById(R.id.tShirts);
        sports = (ImageView)findViewById(R.id.sports);
        fdresses = (ImageView)findViewById(R.id.fDresses);
        sweater = (ImageView)findViewById(R.id.sweater);

        //second Linear


        glasses = (ImageView)findViewById(R.id.glasses);
        hats = (ImageView)findViewById(R.id.hats);
        shoes = (ImageView)findViewById(R.id.shoes);
        purse = (ImageView)findViewById(R.id.purse);

        //third Linear


        headphone = (ImageView)findViewById(R.id.headphone);
        laptop = (ImageView)findViewById(R.id.laptop);
        mobile = (ImageView)findViewById(R.id.mobiles);
        watches = (ImageView)findViewById(R.id.watches);


        //Button Calling

        logoutBtn = (Button)findViewById(R.id.adminLogout);
        checkOrderBtn = (Button)findViewById(R.id.checkOrder);

        //Logout Click Listener

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this , MainActivity.class);
                startActivity(intent);
                mAuth.signOut();
                finish();

            }
        });


        //Onclick Listen Tshirt


       tshirts.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
               intent.putExtra("Catergory" , "Tshirts");
               startActivity(intent);
           }
       });

       //Onclick Sports
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Sports Tshirts");
                startActivity(intent);
            }
        });

        //Female Dresses

        fdresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Female Dresses");
                startActivity(intent);
            }
        });

        //Sweater

        sweater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Sweaters");
                startActivity(intent);
            }
        });

        //glasses

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Glasses");
                startActivity(intent);
            }
        });

        //Hats
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Hats");
                startActivity(intent);
            }
        });

        //Purse

        purse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Purse");
                startActivity(intent);
            }
        });


        //Shoes

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Shoes");
                startActivity(intent);
            }
        });


        //Mobile

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Mobiles");
                startActivity(intent);
            }
        });

        //Laptop

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Laptops");
                startActivity(intent);
            }
        });

        //Headphone

        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Headphones");
                startActivity(intent);
            }
        });

        //watches

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategory.this , AdminAddNewProduct.class);
                intent.putExtra("Catergory" , "Watches");
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent intent = new Intent(AdminCategory.this , MainActivity.class);
            startActivity(intent);
            finish();

        };
    }
}
