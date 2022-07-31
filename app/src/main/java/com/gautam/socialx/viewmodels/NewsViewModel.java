package com.gautam.socialx.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gautam.socialx.activity.HomeActivity;
import com.gautam.socialx.api.APIService;
import com.gautam.socialx.api.OnSuccesfullFetch;
import com.gautam.socialx.api.RetroInstance;
import com.gautam.socialx.model.Articles;
import com.gautam.socialx.model.NewsModel;
import com.gautam.socialx.model.NewsResponse;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {
    private static final String TAG = "APICALL";
    private MutableLiveData<List<NewsModel>> listMutableLiveNewsData;
    private List<Articles> articles;

    public NewsViewModel() {
        listMutableLiveNewsData = new MutableLiveData<>();
        articles = new LinkedList<Articles>() {};
    }

    public MutableLiveData<List<NewsModel>> getListMutableLiveNewsData() {
        return listMutableLiveNewsData;
    }

    public void setListMutableLiveNewsData(MutableLiveData<List<NewsModel>> listMutableLiveNewsData) {
        this.listMutableLiveNewsData = listMutableLiveNewsData;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public void makeApiCall(OnSuccesfullFetch onSuccesfullFetch,String country, String q, String apiKEy){
        APIService apiService= RetroInstance.getRetroClient().create(APIService.class);

        Call<NewsResponse> call = apiService.getNews(q,apiKEy,"popularity","en");

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.d(TAG, "onResponse: "+response.body().getArticles().size());

                onSuccesfullFetch.fetchData(response.body().getArticles(), "Sucessfull Fetching");
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                onSuccesfullFetch.onError("Error Occured : "+t.getLocalizedMessage());
            }
        });
    }


    private void showNews(List<Articles> articles)
    {
        setArticles(articles);
    }
}
