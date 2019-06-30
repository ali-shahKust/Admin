package com.tool.kustiit.admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tool.kustiit.admin.Model.Orders;

public class AddNewOrder extends AppCompatActivity {

    private RecyclerView orderList;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        orderList = findViewById(R.id.orderList);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<Orders> options = new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(orderRef ,Orders.class)
                .build();

         FirebaseRecyclerAdapter<Orders , AdminOrderHolder> adapter = new FirebaseRecyclerAdapter<Orders, AdminOrderHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrderHolder holder, int position, @NonNull Orders model) {

                holder.userName.setText("Name "+ model.getUser_name());

                holder.adress.setText("Shipping Adress "+ model.getAdress());
                holder.dateNtime.setText("Orders At : "+ model.getDate()+ " , " + model.getTime());
                holder.phone_number.setText("Phone Number "+ model.getPhone_number());
                holder.totalPrice.setText("Total Price "+ model.getTotal_ammount());

            }

            @NonNull
            @Override
            public AdminOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_layout , viewGroup , false);

                return  new AdminOrderHolder(view);
            }
        };

        orderList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class AdminOrderHolder extends RecyclerView.ViewHolder {
        public TextView userName , phone_number , totalPrice , dateNtime , adress;
        public Button showProductBtn;

        public AdminOrderHolder(@NonNull View itemView) {
            super(itemView);
                    userName = itemView.findViewById(R.id.personName);
                    phone_number = itemView.findViewById(R.id.perSonNum);
                    totalPrice =  itemView.findViewById(R.id.tPrice);
                    dateNtime = itemView.findViewById(R.id.dateTime);
                    adress = itemView.findViewById(R.id.PersonAdress);
        }
    }
}
