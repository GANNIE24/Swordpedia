package com.lastmans.swordpedia.Types;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.lastmans.swordpedia.R;

import java.util.ArrayList;

public class SwordTypeViewPagerAdapter extends PagerAdapter {
    public final Context context;
    public final ArrayList<SwordType> swordTypes;
    public final Handler handler = new Handler();
    public boolean isIndicatorVisible = false;

    public SwordTypeViewPagerAdapter(Context context, ArrayList<SwordType> swordTypes) {
        this.context = context;
        this.swordTypes = swordTypes;
    }

    @Override
    public int getCount() {
        return swordTypes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_sword_type_view_pager_adapter, container, false);

        final ImageView imageView = itemView.findViewById(R.id.imageType);
        final RelativeLayout descriptionLayout = itemView.findViewById(R.id.descriptionType);
        final TextView descriptionTextView = itemView.findViewById(R.id.descriptionTextType);
        final TextView indicatorTextView = itemView.findViewById(R.id.indicatorTextType);

        final SwordType currentRegion = swordTypes.get(position);

        Glide.with(context)
                .load(currentRegion.getImageResourceId())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView);

        // Set the description text
        descriptionTextView.setText(currentRegion.getTypeName());
        // Set a click listener to toggle the visibility of the description and indicator
        imageView.setOnClickListener(new View.OnClickListener() {
            public Runnable indicatorBlinkRunnable;

            @Override
            public void onClick(View v) {
                handler.removeCallbacks(indicatorBlinkRunnable); // Stop the blinking when clicked

                if (descriptionLayout.getVisibility() == View.VISIBLE) {
                    // If description is visible, hide it with fade-out animation
                    descriptionLayout.animate().alpha(0.0f).setDuration(1000).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            descriptionLayout.setVisibility(View.GONE);
                            // If description is hidden, show the indicator with fade-in animation
                            indicatorTextView.setAlpha(0.0f);
                            indicatorTextView.setVisibility(View.VISIBLE);
                            indicatorTextView.animate().alpha(1.0f).setDuration(2000);
                            // Resume the blinking
                            handler.post(indicatorBlinkRunnable);
                        }
                    });
                } else {
                    // If description is hidden, show it with fade-in animation
                    descriptionLayout.setAlpha(0.0f);
                    descriptionLayout.setVisibility(View.VISIBLE);
                    descriptionLayout.animate().alpha(1.0f).setDuration(2000);
                    // Hide the indicator with fade-out animation
                    indicatorTextView.animate().alpha(0.0f).setDuration(2000).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            indicatorTextView.setVisibility(View.GONE);
                            // Resume the blinking
                            handler.post(indicatorBlinkRunnable);
                        }
                    });
                }
            }
        });

        // Set the indicator text
        indicatorTextView.setText("Click the Image");

        // Set a runnable to toggle the visibility of the indicator
        final Runnable indicatorBlinkRunnable = new Runnable() {

            @Override
            public void run() {
                if (isIndicatorVisible) {
                    indicatorTextView.setVisibility(View.INVISIBLE);
                } else {
                    indicatorTextView.setVisibility(View.VISIBLE);
                }
                isIndicatorVisible = !isIndicatorVisible;
                handler.postDelayed(this, 500); // Blink every 500 milliseconds
            }
        };
        handler.post(indicatorBlinkRunnable); // Start the initial blinking

        container.addView(itemView, 0);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}