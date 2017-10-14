package com.example.brandon.apiclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.brandon.apiclient.Models.Comment;
import com.example.brandon.apiclient.Models.Post;
import com.example.brandon.apiclient.Utils.CommentHelper;
import com.example.brandon.apiclient.Utils.PostHelper;
import com.example.brandon.apiclient.Views.CommentActivity;
import com.example.brandon.apiclient.Views.PostsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buttons
        Button btnGetPosts = (Button) findViewById(R.id.btnGetPosts);
        Button btnGetComments = (Button) findViewById(R.id.btnGetComments);
        Button btnSeePosts = (Button) findViewById(R.id.btnSeePosts);
        Button btnSeeComments = (Button) findViewById(R.id.btnSeeComments);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String commentsUrl = "https://jsonplaceholder.typicode.com/comments";
        final String postsUrl = "https://jsonplaceholder.typicode.com/posts";

        final PostHelper oPostHelper = new PostHelper(this);
        final CommentHelper oCommentHelper = new CommentHelper(this);
        final Context myContext = this;

        //region OtherMethods
        /*final StringRequest stringRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        //wut
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse (VolleyError error) {
                        //Toast.makeText(this, error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        for (int i = 0; i < response.length(); i++){
                            //magia iteradora
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(this, error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        */
        //endregion

        btnGetPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, postsUrl, null, new Response.Listener<JSONArray>(){
                            @Override
                            public void onResponse(JSONArray response){
                                try {
                                    oPostHelper.open();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject post = response.getJSONObject(i);
                                        int id = post.getInt("id");
                                        if(oPostHelper.exists(id)==false) {
                                            int userId = post.getInt("userId");
                                            String title = post.getString("title");
                                            String body = post.getString("body");
                                            oPostHelper.addPost(userId, id, title, body);
                                        }
                                    }
                                    oPostHelper.close();
                                } catch (JSONException e) {
                                    oPostHelper.close();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(myContext, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(jsonArrayRequest);

                Toast.makeText(myContext, "Posts retrieved successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnGetComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, commentsUrl, null, new Response.Listener<JSONArray>(){
                            @Override
                            public void onResponse(JSONArray response){
                                try {
                                    oCommentHelper.open();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject comment = response.getJSONObject(i);
                                        int id = comment.getInt("id");
                                        if(oCommentHelper.exists(id)==false) {
                                            int postId = comment.getInt("postId");
                                            String name = comment.getString("name");
                                            String email = comment.getString("email");
                                            String body = comment.getString("body");
                                            oCommentHelper.addComment(postId, id, name, email, body);
                                        }
                                    }
                                    oCommentHelper.close();
                                } catch (JSONException e) {
                                    oCommentHelper.close();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(myContext, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(jsonArrayRequest);

                Toast.makeText(myContext, "Comments retrieved successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnSeePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oPostHelper.open();
                ArrayList<Post> postsList = oPostHelper.getAllPosts();
                oPostHelper.close();
                if(postsList.size() == 0)
                    Toast.makeText(getApplicationContext(), "No Posts", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getApplicationContext(), PostsActivity.class);
                    intent.putExtra("data", postsList);
                    startActivity(intent);
                }
            }
        });

        btnSeeComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oCommentHelper.open();
                ArrayList<Comment> commentsList = oCommentHelper.getAllComments();
                oCommentHelper.close();
                if(commentsList.size() == 0)
                    Toast.makeText(getApplicationContext(), "No Comments", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                    intent.putExtra("data", commentsList);
                    startActivity(intent);
                }
            }
        });

    }
}
