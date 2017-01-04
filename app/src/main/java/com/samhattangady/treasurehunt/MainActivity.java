package com.samhattangady.treasurehunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewActive;
    private RecyclerView mRecyclerViewNew;
    private List<Hunt> mActiveHunts = new ArrayList<Hunt>();
    private List<Hunt> mNewHunts = new ArrayList<Hunt>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewActive = (RecyclerView) findViewById(R.id.active_hunts_recycler_view);
        mRecyclerViewNew = (RecyclerView) findViewById(R.id.start_hunts_recycler_view);

        MainRecyclerAdapter mAdapterActive = new MainRecyclerAdapter(mActiveHunts, new MainRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Hunt hunt) {
                Intent intent = new Intent(mRecyclerViewActive.getContext(), HuntDetailsActivity.class);
                startActivity(intent);
            }});
        mRecyclerViewActive.setAdapter(mAdapterActive);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewActive.setLayoutManager(mLayoutManager);

        MainRecyclerAdapter mAdapterNew = new MainRecyclerAdapter(mNewHunts, new MainRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Hunt hunt) {
                Toast.makeText(mRecyclerViewNew.getContext(), "New Hunt", Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerViewNew.setAdapter(mAdapterNew);
        RecyclerView.LayoutManager mLayoutManagerNew = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewNew.setLayoutManager(mLayoutManagerNew);

        Button searchButton = (Button) findViewById(R.id.search_hunt_button);
        if (searchButton != null) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mRecyclerViewNew.getContext(), "To search. Not yet implemented", Toast.LENGTH_LONG).show();
                }});
        }

        Button createButton = (Button) findViewById(R.id.create_button);
        if (createButton != null) {
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CreateHuntActivity.class);
                    startActivity(intent);
                }});
        }

        prepareData();
    }

    private void prepareData() {
        mActiveHunts.add(new Hunt(0, "BEL Road", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mActiveHunts.add(new Hunt(0, "Indiranagar", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mActiveHunts.add(new Hunt(0, "Malleswaram", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mActiveHunts.add(new Hunt(0, "Rajajinagar", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mActiveHunts.add(new Hunt(0, "MG Road", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mActiveHunts.add(new Hunt(0, "Church Street", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));

        mNewHunts.add(new Hunt(0, "Koramangala", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mNewHunts.add(new Hunt(0, "Whitefield", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mNewHunts.add(new Hunt(0, "Brigade Road", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mNewHunts.add(new Hunt(0, "BEL Road", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mNewHunts.add(new Hunt(0, "Indiranagar", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));
        mNewHunts.add(new Hunt(0, "Malleswaram", "https://www.drupal.org/files/project-images/Stamen%20Watercolor.png"));

    }
}
