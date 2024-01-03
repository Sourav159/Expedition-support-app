package com.example.expeditionsupportapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlogsRecyclerViewFragment extends Fragment {
    RecyclerView blogsRecyclerView;
    List<Blog> blogList;
    String url = "https://www.breakingthecycle.education/wp-json/wp/v2/posts?per_page=100"; // add to strings file
    BlogAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.recyclerview_fragment_view, container, false);

        blogsRecyclerView = root.findViewById(R.id.blogsRecyclerView);
        blogsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        blogList = new ArrayList<>();
        fetchBlogs(url);
        adapter = new BlogAdapter(blogList);
        blogsRecyclerView.setAdapter(adapter);


        return root;
    }

    public static String addCharToString(String str, char c, int pos) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(pos, c);
        return stringBuilder.toString();
    }

    public String extractImageUrl (String text){
        Pattern p = Pattern.compile("(https://|http://)(www.breakingthecycle.education/wp-content/uploads/)(\\d{4}/\\d{2}/)(.+?)(.jpg)");
        Matcher m = p.matcher(text);
        if(m.find()){
            return  addCharToString(m.group(),'s',4);
        }
        else return "https://www.breakingthecycle.education/wp-content/themes/btcycle-edu/images/logo.png"; // return a default image url

    }


    public void fetchBlogs(String URL){
        // use volley to extract the data 62 blogs must be there on extraction
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.d("TAG", "onResponse: "+ response.toString());
                for (int i = 0; i< response.length();i++){
                    // extract the date
                    try {
                        Blog blog = new Blog();

                        //extracting date
                        JSONObject jsonObjectData = response.getJSONObject(i);
                        blog.setDate(jsonObjectData.getString("date"));

                        //extract url
                        JSONObject urlObject = jsonObjectData.getJSONObject("guid");
                        blog.setUrl(urlObject.getString("rendered"));

                        //extracting title
                        JSONObject titleObject = jsonObjectData.getJSONObject("title");
                        blog.setTitle(titleObject.getString("rendered"));

                        //extracting content
                        JSONObject contentObject = jsonObjectData.getJSONObject("content");
                        blog.setContent(contentObject.getString("rendered"));

                        //extracting excerpt
                        JSONObject excerptObject = jsonObjectData.getJSONObject("excerpt");
                        blog.setExcerpt(excerptObject.getString("rendered"));

                        // Extract Image URL
                        blog.setFeature_image(extractImageUrl(blog.getContent()));



                        blogList.add(blog);
                        adapter.notifyDataSetChanged();


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);

    }
}
