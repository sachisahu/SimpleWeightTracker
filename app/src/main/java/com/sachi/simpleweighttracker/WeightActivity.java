package com.sachi.simpleweighttracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class WeightActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference mDatabase;
    EditText txtWeight;
    ListView listView;


    ListAdapter adapter;
    List<WeightClass> weightLists;

    ArrayList<String> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtWeight = findViewById(R.id.txtWeight);
        listView = findViewById(R.id.weightlistview);

        weightLists = new ArrayList<>();


        weightLists.clear();
        adapter = new ListAdapter(WeightActivity.this,weightLists);
        listView.setAdapter(adapter);

        updateList();


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();

        adapter.notifyDataSetChanged();




    }

    private void updateList() {

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                String weight = snapshot.child("weight").getValue().toString();
                String time = snapshot.child("time").getValue().toString();

                Log.d("Login", weight+" " + time);

                weightLists.add(new WeightClass(weight,time));

                adapter.notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void signOut(View view) {
        mAuth.signOut();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addWeight(View view) {

        if(txtWeight.getText().length()>0){

            String time = java.time.LocalTime.now().toString()+"";
            WeightClass weightClass = new WeightClass(txtWeight.getText().toString(),time);

            mDatabase.push().setValue(weightClass);
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, "Enter Valid Weight", Toast.LENGTH_SHORT).show();
        }

    }
}