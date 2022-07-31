package com.gautam.socialx.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gautam.socialx.R;
import com.gautam.socialx.adapters.NewsAdapter;
import com.gautam.socialx.api.OnSuccesfullFetch;
import com.gautam.socialx.api.RetroInstance;
import com.gautam.socialx.model.Articles;
import com.gautam.socialx.model.NewsResponse;
import com.gautam.socialx.viewmodels.NewsViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView newsRecview;
    EditText searchBox;
    TextView no_news;
    List<Articles> newsModelList;
    NewsAdapter newsAdapter;
    NewsViewModel newsViewModel;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.blue_900));
        }

        initUI();
    }

    private void initUI()
    {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching News...");

        searchBox = findViewById(R.id.search_in_newsapi);

        newsRecview = findViewById(R.id.news_recview);
        no_news = findViewById(R.id.no_news);
        newsRecview.setHasFixedSize(true);
        newsRecview.setLayoutManager(new GridLayoutManager(this,1));

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        initControls();

        callAPI("news");

    }

    private void initControls()
    {
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(searchBox.getText().toString().trim().equals("")){
                    return false;
                }

                String searchKeyword = searchBox.getText().toString().trim();

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(searchKeyword!=null || !searchKeyword.equals("")){
                        callAPI(searchKeyword);
                        searchBox.setText("");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void callAPI(String searchKeyword)
    {
        dialog.show();
        newsViewModel.makeApiCall(onSuccesfullFetch,"in",searchKeyword, RetroInstance.API_KEY);

    }

    private void showNews(List<Articles> articles)
    {
        newsAdapter = new NewsAdapter(getApplicationContext(),articles);
        if(articles.size()==0){
            no_news.setVisibility(View.VISIBLE);
        }else{ no_news.setVisibility(View.GONE);}
        newsRecview.setAdapter(newsAdapter);
    }
    private final OnSuccesfullFetch<NewsResponse> onSuccesfullFetch = new OnSuccesfullFetch<NewsResponse>() {
        @Override
        public void fetchData(List<Articles> articles, String message) {
            showNews(articles);
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {
            Log.d("TAG", "onErrorrr: "+message);
        }
    };

}