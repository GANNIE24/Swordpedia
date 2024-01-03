// RegionsActivity.java
package com.lastmans.swordpedia.Regions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lastmans.swordpedia.R;
import java.util.ArrayList;

public class RegionsActivity extends AppCompatActivity {
    ViewPager viewPager;
    ArrayList<Region> regions = new ArrayList<>();
    RegionViewPagerAdapter adapter;
    public DatabaseReference databaseReference;
    private final int[] regionImages = {
            R.drawable.saif,
            R.drawable.kilij,
            R.drawable.zulfikar,
            R.drawable.nimcha,
            R.drawable.shamshir
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);
        viewPager = findViewById(R.id.viewPager);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("regions");

        // Initialize adapter and set it to the viewPager
        adapter = new RegionViewPagerAdapter(this, regions);
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setAdapter(adapter);

        // Fetch data from Firebase
        fetchRegionsData();
    }

    public void fetchRegionsData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    regions.clear(); // Clear existing data

                    int index = 0; // Index for selecting regionImages
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Region region = snapshot.getValue(Region.class);
                        if (region != null) {
                            // Set the description from the database
                            region.setDescription(snapshot.child("description").getValue(String.class));

                            // Set the drawable resource ID from the array
                            if (index < regionImages.length) {
                                region.setImageResourceId(regionImages[index]);
                                index++;
                            } else {
                                // Handle the case where there are more regions in the database than images
                                Log.e("RegionsActivity", "Not enough regionImages for all regions");
                            }

                            regions.add(region);
                        }
                    }

                    if (adapter != null) {
                        adapter.notifyDataSetChanged(); // Notify the adapter that data has changed
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Log.e("Firebase", "Error fetching regions data: " + error.getMessage());
            }
        });
    }
}
