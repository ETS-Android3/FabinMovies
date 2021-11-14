package com.fmoreno.fabinmovies.ui.fragments;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.fmoreno.fabinmovies.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragmentViewPagerActivity extends AppCompatActivity {
    // tab titles
    private String[] titles = new String[]{"Popular", "Top Rated"};

    ViewPager2 view_pager;
    TabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //setContentView(R.layout.curriculum_vitae_activity);
        setContentView(R.layout.activity_fragment_view_pager);
        view_pager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
        init();
    }

    private void init() {
        // removing toolbar elevation
        getSupportActionBar().setElevation(0);

        view_pager.setAdapter(new ViewPagerFragmentAdapter(this));

        // attaching tab mediator
        new TabLayoutMediator(tab_layout, view_pager,
                (tab, position) -> tab.setText(titles[position])).attach();
    }

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new PopularFragment();
                case 1:
                    return new TopRatedFragment();
            }
            return new PopularFragment();
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}
