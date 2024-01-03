package com.example.expeditionsupportapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class SingleBlogViewFragment extends Fragment {

    WebView webView;
    Blog blog;
    String temp = "\n<p>Blog 6</p>\n\n\n\n<p>12th &#8211; 16th March</p>\n\n\n\n<p>Total Distance &#8211; 1106km</p>\n\n\n\n<p>This is the last blog for the time being. It has been a long time coming because I have had to postpone the last two-thirds of the expedition until the COVID-19 virus is under control.</p>\n\n\n\n<p>La Paz</p>\n\n\n\n<p>Days 13 and 14</p>\n\n\n\n<p>One of the best things Chris and I did in La Paz was take a ride on the amazing public cable car transport system. It has only been up and running for about two years, but what a fantastic way to get to work &#8211; beats the tram, bus and underground systems around the world! The steep, narrow, cobbled streets are very congested with traffic and the cable car system is designed to reduce the number of cars and small taxi busses on the roads. The flip side is that when the cable cars were introduced, many taxi drivers were out of a job because the transition to the new system happened too fast.</p>\n\n\n\n<figure class=\"wp-block-image\"><img src=\"https://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_221641.jpg\" alt=\"\" class=\"wp-image-1839\" srcset=\"httpss://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_221641.jpg 882w, httpss://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_221641-600x450.jpg 600w\" sizes=\"(max-width: 882px) 100vw, 882px\" /><figcaption>On the way up to the suburbs on the purple line</figcaption></figure>\n\n\n\n<figure class=\"wp-block-image\"><img src=\"https://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_154952.jpg\" alt=\"\" class=\"wp-image-1837\" srcset=\"httpss://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_154952.jpg 848w, httpss://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_154952-600x450.jpg 600w\" sizes=\"(max-width: 848px) 100vw, 848px\" /><figcaption>Travelling across the lip of the valley</figcaption></figure>\n\n\n\n<figure class=\"wp-block-image\"><img src=\"https://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_170934.jpg\" alt=\"\" class=\"wp-image-1838\" srcset=\"httpss://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_170934.jpg 848w, httpss://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200313_170934-600x388.jpg 600w\" sizes=\"(max-width: 848px) 100vw, 848px\" /><figcaption>Houses on the edge of the fast-eroding cliffs, perhaps not much longer in this world (the reflection is from inside the cable car)</figcaption></figure>\n\n\n\n<p>For the rest of the time, Chris, myself and our new driver, Rolando, organised ourselves for the next stage of the journey, a loop into the rainforest through the South Yungas.</p>\n\n\n\n<p>Day 15</p>\n\n\n\n<p>La Paz &#8211; Corioco</p>\n\n\n\n<p>Distance &#8211; 108km</p>\n\n\n\n<p>Pass &#8211; 4769m</p>\n\n\n\n<p>I rejoined the line of my journey, setting off along the airport road. It was great to be back on the road again, feeling rested from the two-day break. The traffic in the outer suburbs of La Paz was still very hectic and the air horribly polluted, but I just had to ride assertively with the flow.&nbsp;</p>\n\n\n\n<p>I decided to take a slightly different course out of town than the usual way to Corioco. Once free of the sprawling city limits, the route proved to be a good choice. Cars were few and far between and I was able to enjoy the high, undulating landscape. After crossing a valley,&nbsp; the road, which was under construction, started to ascend sharply. There were several places where small landslides had blocked or partially blocked the road. It was easy for me to carry my bike over the mud but at one stage, Rolando had to pull the shovel out and dig a path for his vehicle.</p>\n\n\n\n<p>The pass was concealed in the clouds at 4769m. It was cold, rainy and the air was very low on oxygen. From there I descended about 150m to the busy Coroico &#8211; La Paz road. The road is the main artery dropping away from the Altiplano and into the rainforest. Over the next 30km, I lost 1400m of elevation. Initially, the road and mountains were totally encased in cloud. It was a virtual white-out and I switched my powerful bike headlight on for improved visibility. Rolando drove behind to give me some protection from the heavy traffic. I kept intense concentration to maintain control of my bike in the slippery conditions. Breaking through the mist, the breathtaking scale of the valley and mountains was revealed. I gradually thawed out as I descended into tropical climes.&nbsp;</p>\n\n\n\n<p>The main objective for the day was to cycle down Death Road. Prior to 2006, the La Paz &#8211; Coroico Road was dubbed the World’s Most Dangerous Road. It is a single-lane stoney, muddy road, that, at times is only about three metres wide. It winds its way from the 4650m Le Cumbre pass (I cycled higher on the first road I followed that morning, before it hit the Coroico Road at the pass) down to the Amazon rainforest. The North Yungas Road, as is its correct name, is cut into a deep canyon with vertical drops of about 600 metres and no side barriers. Normally in Bolivia, drivers and cyclists keep to the right-hand side of the road, but down Death Road, they must stay left, near to the edge of the cliff and give way to vehicles ascending the road.&nbsp;</p>\n\n\n\n<div class=\"wp-block-image\"><figure class=\"aligncenter\"><img src=\"https://www.breakingthecycle.education/wp-content/uploads/2020/03/IMG_20200314_160618.jpg";

    // TODO: Rename and change types of parameters

    public SingleBlogViewFragment(Blog blog)
    {
        this.blog = blog;

        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_single_blog_view, container, false);
        webView = v.findViewById(R.id.single_blog_view_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);


        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.loadUrl(this.blog.getUrl());
        //webView.loadData(this.blog.getContent(), "text/html", "UTF-8");
        //webView.loadData(String.valueOf(Html.fromHtml(this.blog.getContent())), "text/html", "UTF-8");


        //webView.loadData(String.valueOf(Html.fromHtml(temp)), "text/html", "UTF-8");
        Log.d("TAG", "Blog Data: "+this.blog.getContent());


        return v;
    }
}