package com.example.brandon.apiclient.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.brandon.apiclient.Models.Comment;
import com.example.brandon.apiclient.R;
import com.example.brandon.apiclient.Utils.CommentAdapter;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    CommentAdapter oCommentAdapter;
    ListView oListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        oListView = (ListView) findViewById(R.id.lvComments);
        oCommentAdapter = new CommentAdapter(this);
        oListView.setAdapter(oCommentAdapter);

        ArrayList<Comment> posts  = getIntent().getParcelableArrayListExtra("data");

        oCommentAdapter.clear();
        for (Comment oPost : posts){
            oCommentAdapter.add(oPost);
        }
        oCommentAdapter.notifyDataSetChanged();
    }
}
