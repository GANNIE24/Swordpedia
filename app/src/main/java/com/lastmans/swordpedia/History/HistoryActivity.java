package com.lastmans.swordpedia.History;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lastmans.swordpedia.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public final ArrayList<Sword> swords = new ArrayList<>();
    public ViewPagerAdapter adapter;
    public DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(this, swords);
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setAdapter(adapter);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("swords");

        // Fetch data from Firebase
        fetchSwordsData();
    }

    public void fetchSwordsData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("HistoryActivity", "Data changed event received");
                if (dataSnapshot.exists()) {
                    swords.clear(); // Clear existing data

                    // Array of drawable resource IDs
                    int[] imageResourceIds = {
                            R.drawable.viking,
                            R.drawable.arming,
                            R.drawable.longsword,
                            R.drawable.bastard,
                            R.drawable.scimitar
                    };

                    int index = 0; // Index for looping through imageResourceIds

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Sword sword = snapshot.getValue(Sword.class);
                        if (sword != null) {
                            // Set the description from the database and use drawable resource IDs from the array
                            sword.setDescription(snapshot.child("description").getValue(String.class));
                            sword.setImageResourceId(imageResourceIds[index % imageResourceIds.length]);
                            swords.add(sword);

                            index++; // Move to the next image resource ID
                        } else {
                            Log.e("HistoryActivity", "Sword is null for snapshot: " + snapshot.toString());
                        }
                    }

                    adapter.notifyDataSetChanged(); // Notify the adapter that data has changed
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HistoryActivity", "Data retrieval canceled: " + error.getMessage());
            }
        });
    }

}