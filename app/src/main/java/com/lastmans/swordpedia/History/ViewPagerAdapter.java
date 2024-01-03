package com.lastmans.swordpedia.History;

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

public class ViewPagerAdapter extends PagerAdapter {
    public Context context;
    public ArrayList<Sword> swords;
    public Handler handler = new Handler();
    public boolean isIndicatorVisible = false;

    public ViewPagerAdapter(Context context, ArrayList<Sword> swords) {
        this.context = context;
        this.swords = swords;
    }

    @Override
    public int getCount() {
        return swords.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_viewpager, container, false);

        final ImageView imageView = view.findViewById(R.id.imageView);
        final RelativeLayout descriptionLayout = view.findViewById(R.id.descriptionLayout);
        final TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        final TextView indicatorTextView = view.findViewById(R.id.indicatorTextView);

        final Sword sword = swords.get(position);

        Glide.with(context)
                .load(sword.getImageResourceId())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView);

        descriptionTextView.setText(sword.getDescription());
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

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}