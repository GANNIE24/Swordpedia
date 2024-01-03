package com.lastmans.swordpedia;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.lastmans.swordpedia.History.HistoryActivity;
import com.lastmans.swordpedia.Regions.RegionsActivity;
import com.lastmans.swordpedia.Types.TypesActivity;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    CardView historyCard, regionsCard, typesCard, infoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyCard = findViewById(R.id.historyCard);
        regionsCard = findViewById(R.id.regionsCard);
        typesCard = findViewById(R.id.typesCard);
        infoCard = findViewById(R.id.infoCard);

        // Set up click listeners with animation
        setClickListenerWithAnimation(historyCard, HistoryActivity.class);
        setClickListenerWithAnimation(regionsCard, RegionsActivity.class);
        setClickListenerWithAnimation(typesCard, TypesActivity.class);
        setClickListenerWithAnimation(infoCard, GameActivity.class);
    }

    public void setClickListenerWithAnimation(CardView cardView, Class<?> targetActivity) {
        cardView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, targetActivity);
                startActivity(intent);
    });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
