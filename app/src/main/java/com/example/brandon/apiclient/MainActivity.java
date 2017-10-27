package com.example.brandon.apiclient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.brandon.apiclient.Models.Comment;
import com.example.brandon.apiclient.Models.Post;
import com.example.brandon.apiclient.Utils.CommentHelper;
import com.example.brandon.apiclient.Utils.PostHelper;
import com.example.brandon.apiclient.Views.CommentActivity;
import com.example.brandon.apiclient.Views.InflatableActivity;
import com.example.brandon.apiclient.Views.PostsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buttons
        Button btnGetPosts = (Button) findViewById(R.id.btnGetPosts);
        Button btnGetComments = (Button) findViewById(R.id.btnGetComments);
        Button btnSeePosts = (Button) findViewById(R.id.btnSeePosts);
        Button btnSeeComments = (Button) findViewById(R.id.btnSeeComments);
        Button btnPostPosts = (Button) findViewById(R.id.btnPostPosts);
        Button btnPostComments = (Button) findViewById(R.id.btnPostComments);
        Button btnSeePostsWithComments = (Button) findViewById(R.id.btnSeePostsWithComments);
        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        Button btnGetPostsOldWay = (Button) findViewById(R.id.btnGetPostsOldWay);
        Button btnGetCommentsOldWay = (Button) findViewById(R.id.btnGetCommentsOldWay);


        final RequestQueue queue = Volley.newRequestQueue(this);
        final String commentsUrl = "https://jsonplaceholder.typicode.com/comments";
        final String postsUrl = "https://jsonplaceholder.typicode.com/posts";
        final String postPostsURL = "http://107.170.247.123:2403/posts";
        final String postCommentsURL = "http://107.170.247.123:2403/comments";

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
                progressBar.setVisibility(View.VISIBLE);
                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, postsUrl, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    oPostHelper.open();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject post = response.getJSONObject(i);
                                        int id = post.getInt("id");
                                        if (oPostHelper.exists(id) == false) {
                                            int userId = post.getInt("userId");
                                            String title = post.getString("title");
                                            String body = post.getString("body");
                                            oPostHelper.addPost(userId, id, title, body);
                                        }
                                    }
                                    oPostHelper.close();
                                    progressBar.setVisibility(View.GONE);
                                } catch (JSONException e) {
                                    progressBar.setVisibility(View.GONE);
                                    oPostHelper.close();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.GONE);
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
                progressBar.setVisibility(View.VISIBLE);
                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, commentsUrl, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    oCommentHelper.open();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject comment = response.getJSONObject(i);
                                        int id = comment.getInt("id");
                                        if (!oCommentHelper.exists(id)) {
                                            int postId = comment.getInt("postId");
                                            String name = comment.getString("name");
                                            String email = comment.getString("email");
                                            String body = comment.getString("body");
                                            oCommentHelper.addComment(postId, id, name, email, body);
                                        }
                                    }
                                    oCommentHelper.close();
                                    progressBar.setVisibility(View.GONE);
                                } catch (JSONException e) {
                                    progressBar.setVisibility(View.GONE);
                                    oCommentHelper.close();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.GONE);
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
                if (postsList.size() == 0)
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
                if (commentsList.size() == 0)
                    Toast.makeText(getApplicationContext(), "No Comments", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                    intent.putExtra("data", commentsList);
                    startActivity(intent);
                }
            }
        });

        btnPostPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oPostHelper.open();
                final ArrayList<Post> posts = oPostHelper.getAllPosts();
                oPostHelper.close();
                for (int i = 0; i < posts.size(); i++) {

                    final JSONObject postJSONObject = parsePostToJSONObject(posts.get(i));

                    final JsonObjectRequest jsonPostRequest = new JsonObjectRequest
                            (Request.Method.POST, postPostsURL, postJSONObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(myContext, response.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(myContext, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonPostRequest);
                }
            }
        });

        btnPostComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oCommentHelper.open();
                final ArrayList<Comment> comments = oCommentHelper.getAllComments();
                oCommentHelper.close();
                for (int i = 0; i < comments.size(); i++) {

                    final JSONObject commentJSONObject = parseCommentToJSONObject(comments.get(i));

                    final JsonObjectRequest jsonPostRequest = new JsonObjectRequest
                            (Request.Method.POST, postCommentsURL, commentJSONObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(myContext, response.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(myContext, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonPostRequest);
                }

            }
        });

        btnSeePostsWithComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oPostHelper.open();
                ArrayList<Post> postsWithComments = oPostHelper.getAllPostsWithComments(oCommentHelper);
                oPostHelper.close();
                Intent intent = new Intent(getApplicationContext(), InflatableActivity.class);
                intent.putExtra("data", postsWithComments);
                startActivity(intent);
            }
        });

        btnGetPostsOldWay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new HttpAsyncTask().execute("http://jsonplaceholder.typicode.com/posts");
            }
        });

        btnGetCommentsOldWay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new HttpAsyncTask().execute("http://jsonplaceholder.typicode.com/comments");
            }
        });
    }

    private JSONObject parsePostToJSONObject (Post p) {
        JSONObject jObject = new JSONObject();
        try {
            jObject.put("userId", p.getUserId());
            jObject.put("title", p.getTitle());
            jObject.put("body", p.getBody());
        }catch (Exception e){

        }
        return jObject;
    }

    private JSONObject parseCommentToJSONObject (Comment c) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("postId", c.getPostId());
            jsonObject.put("name", c.getName());
            jsonObject.put("email", c.getEmail());
            jsonObject.put("body", c.getBody());
        }catch (Exception e){

        }
        return jsonObject;
    }

    public static String getHTTPRequest(String url) {

        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return "Request did not work.";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            return getHTTPRequest(urls[0]);
        }

        @Override
        protected void onPostExecute(String resultado) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Received! " + resultado, Toast.LENGTH_LONG).show();
        }

    }

}