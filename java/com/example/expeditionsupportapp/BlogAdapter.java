package com.example.expeditionsupportapp;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    List<Blog> allBlogs;
    public BlogAdapter(List<Blog> allBlogs) {
        this.allBlogs = allBlogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_blog_view_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.blogTitle.setText(Html.fromHtml(allBlogs.get(position).getTitle().trim()) );
        holder.blogExcerpt.setText(Html.fromHtml(allBlogs.get(position).getExcerpt().trim()));
        Picasso.get().load(allBlogs.get(position).getFeature_image()).resize(100,100).into(holder.blogImage);
        //Log.d("TAG", position+": "+allBlogs.get(position).getFeature_image());
        //Picasso.get().load("https://futurestud.io/images/books/picasso.png").into(holder.blogImage);
        //Picasso.get().load("https://futurestud.io/images/books/picasso.png").into(temp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                SingleBlogViewFragment singleBlogViewFragment = new SingleBlogViewFragment(allBlogs.get(position));
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_page, singleBlogViewFragment).addToBackStack(null) .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return allBlogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView blogImage;
        TextView blogTitle, blogExcerpt;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            blogImage = itemView.findViewById(R.id.blog_display_image);
            blogTitle = itemView.findViewById(R.id.blog_title_textview);
            blogExcerpt = itemView.findViewById(R.id.excerpt_textview);
        }
    }

}
