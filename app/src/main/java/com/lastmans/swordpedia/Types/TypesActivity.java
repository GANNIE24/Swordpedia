package com.lastmans.swordpedia.Types;

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

public class TypesActivity extends AppCompatActivity {
    ViewPager viewPager;
    ArrayList<SwordType> swordTypes = new ArrayList<>();
    SwordTypeViewPagerAdapter adapter;
    public DatabaseReference databaseReference;
    private final int[] typesImage  = {
            R.drawable.katana,
            R.drawable.tachi,
            R.drawable.wakizashi,
            R.drawable.nodachi,
            R.drawable.tanto
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("sword_types");

        // Initialize ViewPager
        viewPager = findViewById(R.id.viewPager);
        viewPager.setPadding(50, 0, 50, 0);

        // Initialize ViewPager adapter and set it to the viewPager
        adapter = new SwordTypeViewPagerAdapter(this, swordTypes);
        viewPager.setAdapter(adapter);

        // Fetch data for sword
        fetchSwordTypesData();
    }

    public void fetchSwordTypesData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    swordTypes.clear(); // Clear existing data

                    int index = 0; // Index to loop through regionImages
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        SwordType swordType = snapshot.getValue(SwordType.class);
                        if (swordType != null) {
                            // Set the local drawable resource ID for the image
                            if (index < typesImage.length) {
                                swordType.setImageResourceId(typesImage[index]);
                                index++;
                            } else {
                                // Handle case where regionImages is smaller than the number of sword types
                                Log.e("TypesActivity", "Not enough images in regionImages array.");
                                break;
                            }
                            swordTypes.add(swordType);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching sword types data: " + error.getMessage());
            }
        });
    }

}
