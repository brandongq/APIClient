package com.example.brandon.apiclient.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.brandon.apiclient.Models.Post;
import com.example.brandon.apiclient.R;
import com.example.brandon.apiclient.Utils.PostAdapter;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {

    PostAdapter oPostAdapter;
    ListView oListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        oListView = (ListView) findViewById(R.id.lvPosts);
        oPostAdapter = new PostAdapter(this);
        oListView.setAdapter(oPostAdapter);

        ArrayList<Post> posts  = getIntent().getParcelableArrayListExtra("data");

        oPostAdapter.clear();
        for (Post oPost : posts){
            oPostAdapter.add(oPost);
        }
        oPostAdapter.notifyDataSetChanged();

    }
}
