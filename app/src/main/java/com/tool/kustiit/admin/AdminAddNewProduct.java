package com.tool.kustiit.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProduct extends AppCompatActivity {

    private Button addProductbtn;
    private ImageView selectProductImg;
    private EditText ProductName, ProductDescription, ProductPrice;

    private static final int GallaryPick =  1;
    private  String CategoryName , Description , Price , Name , saveCurrentDate , saveCurrentTime, ProductRandomKey;
    private String downloadImgUrl;
    private Uri imageUri;
    private StorageReference productImagesRef;

    private DatabaseReference mRootRef;

    private ProgressDialog mprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );


        addProductbtn = (Button)findViewById(R.id.addProductBtn);
        ProductName = (EditText)findViewById(R.id.productName);
        ProductPrice = (EditText)findViewById(R.id.producPrice);
        ProductDescription = (EditText)findViewById(R.id.productDescription);
        selectProductImg = (ImageView)findViewById(R.id.selectProductImage);

        mprogressBar = new ProgressDialog(this);

        CategoryName = getIntent().getExtras().get("Catergory").toString();
        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();

        productImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        mRootRef = FirebaseDatabase.getInstance().getReference().child("Products");

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        selectProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               OpenGalleryImage();
            }
        });

        addProductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProducts();
            }
        });
    }

    private void ValidateProducts() {

        Description = ProductDescription.getText().toString();
        Price = ProductPrice.getText().toString();
        Name = ProductName.getText().toString();
        
        if(imageUri==null){
            Toast.makeText(this, "Product Image is Mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)){

            Toast.makeText(this, "Please Write Product Description", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(Name)){

            Toast.makeText(this, "Please Write  Product Name", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(Price)){

            Toast.makeText(this, "Please Write Product Price", Toast.LENGTH_SHORT).show();

        }
        else {
            StoreProductInformation();
        }
    }




    private void OpenGalleryImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),GallaryPick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GallaryPick && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            selectProductImg.setImageURI(imageUri);

        }
    }

    private void StoreProductInformation() {


        mprogressBar.setTitle("Adding Product Information");
        mprogressBar.setMessage("Please Wait while we get Product Information");
        mprogressBar.setCanceledOnTouchOutside(false);
        mprogressBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd , yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //Image link for Product
        ProductRandomKey = saveCurrentDate+saveCurrentTime;

        final StorageReference filepath = productImagesRef.child(imageUri.getLastPathSegment()+ProductRandomKey + ".jpg");

        final UploadTask uploadTask = filepath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(AdminAddNewProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                mprogressBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProduct.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImgUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }



                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            downloadImgUrl= task.getResult().toString();

                            Toast.makeText(AdminAddNewProduct.this, "Getting Url of Image", Toast.LENGTH_SHORT).show();

                            SaveProductInfo();
                        }
                    }
                });
            }
        });
    }
    private void SaveProductInfo() {

        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("pid" , ProductRandomKey);
        hashMap.put("date" , saveCurrentDate);
        hashMap.put("time", saveCurrentTime);
        hashMap.put("description" , Description);
        hashMap.put("image", downloadImgUrl);
        hashMap.put("catergory", CategoryName);
        hashMap.put("price", Price);
        hashMap.put("productName" , Name);

        mRootRef.child(ProductRandomKey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Intent intent = new Intent(AdminAddNewProduct.this, AdminCategory.class);
                    startActivity(intent);
                    mprogressBar.dismiss();

                    Toast.makeText(AdminAddNewProduct.this, "Product Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    mprogressBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminAddNewProduct.this, "Error "+ message , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
