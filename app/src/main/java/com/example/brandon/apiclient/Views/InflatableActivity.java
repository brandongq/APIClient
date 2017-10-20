package com.example.brandon.apiclient.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.brandon.apiclient.Models.Comment;
import com.example.brandon.apiclient.Models.Post;
import com.example.brandon.apiclient.R;
import com.example.brandon.apiclient.Utils.ExapndableListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InflatableActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<Post> postsList;
    HashMap<Post, List<Comment>> detailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflatable);

        postsList = getIntent().getParcelableArrayListExtra("data");
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        detailList = getHashMap();
        postsList = new ArrayList<Post>(detailList.keySet());
        expandableListAdapter = new ExapndableListViewAdapter(this, postsList, detailList) ;
        expandableListView.setAdapter(expandableListAdapter);

        //region STUFF I DON'T WANNA USE
        /*expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        postsList.get(groupPosition).getTitle() + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        postsList.get(groupPosition).getTitle() + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        postsList.get(groupPosition).getTitle()
                                + " -> "
                                + detailList.get(
                                postsList.get(groupPosition)).get(
                                childPosition).getName(), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });*/
        //endregion
    }

    private HashMap<Post, List<Comment>> getHashMap (){
        HashMap<Post, List<Comment>> oHashMap = new HashMap<>();
        for (Post oPost : postsList){
            oHashMap.put(oPost, oPost.getComments());
        }
        return oHashMap;
    }
}
