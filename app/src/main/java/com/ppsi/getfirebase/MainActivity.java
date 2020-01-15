package com.ppsi.getfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ppsi.getfirebase.adapter.MobilAdapter;
import com.ppsi.getfirebase.adapter.MotorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> blockMobil = new ArrayList<>();
    private List<String> blockMotor = new ArrayList<>();
    private RecyclerView recyclerViewMobil, recyclerViewMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMobil = findViewById(R.id.blok_mobil);
        recyclerViewMotor = findViewById(R.id.blok_motor);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("block");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blockMobil.clear();
                blockMotor.clear();
                String dataMobil = String.valueOf(dataSnapshot.child("block_mobil").getChildrenCount());
//                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                int jumlahBlokMobil = Integer.valueOf(dataMobil);
                for (int i = 1; i <= jumlahBlokMobil; i++){
                    String child = "block"+i;
                    blockMobil.add(String.valueOf(dataSnapshot.child("block_mobil").child(child).getValue()));
                }

                GridLayoutManager layoutManagerMobil = new GridLayoutManager(MainActivity.this, 4);
                recyclerViewMobil.setLayoutManager(layoutManagerMobil);
                MobilAdapter mobilAdapter = new MobilAdapter(MainActivity.this, blockMobil);
                recyclerViewMobil.setAdapter(mobilAdapter);

                String dataMotor = String.valueOf(dataSnapshot.child("block_motor").getChildrenCount());
//                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                int jumlahBlokMotor = Integer.valueOf(dataMotor);
                for (int i = 1; i <= jumlahBlokMotor; i++){
                    String child = "block"+i;
                    blockMotor.add(String.valueOf(dataSnapshot.child("block_motor").child(child).getValue()));
                }

//                GridLayoutManager layoutManagerMotor = new GridLayoutManager(MainActivity.this, 10);
//                recyclerViewMotor.setLayoutManager(layoutManagerMotor);
//                MotorAdapter motorAdapter = new MotorAdapter(MainActivity.this, blockMotor);
//                recyclerViewMotor.setAdapter(motorAdapter);

                LinearLayoutManager layoutmanagerMotor = new LinearLayoutManager(MainActivity.this);
                recyclerViewMotor.setLayoutManager(layoutmanagerMotor);
                MotorAdapter motorAdapter = new MotorAdapter(MainActivity.this,blockMotor);
                recyclerViewMotor.setAdapter(motorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
