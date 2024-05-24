package com.example.foodorderapp.viewmodal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.NotiDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.NotiResponsive;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotiViewModel extends ViewModel {
    private MutableLiveData<List<NotiDTO>> notiList;
    int userId = UserManager.getInstance().getUserId();
    private MutableLiveData<Boolean> errorLiveData = new MutableLiveData<>();


    public LiveData<List<NotiDTO>> getNotiList(){
        if(notiList == null ){
            notiList = new MutableLiveData<>();
            loadNotiList(userId);
        }
        return notiList;
    }

    public LiveData<Boolean> getErrorLiveData() {
        return errorLiveData;
    }

    private void loadNotiList( int userId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<NotiResponsive> call = apiService.getNotis(userId);

        call.enqueue(new Callback<NotiResponsive>() {
            @Override
            public void onResponse(Call<NotiResponsive> call, Response<NotiResponsive> response) {
                if(response.isSuccessful()){
                    NotiResponsive notiResponsive =response.body();
                    if(notiResponsive != null && notiResponsive.isStatus()){
                        notiList.setValue(notiResponsive.getData());
                        Log.d("NotiViewModel","API call successful.");
                        errorLiveData.setValue(false);
                    } else{
                        Log.e("NotiViewModel","API call failed");
                    }
                } else{
                    Log.e("NotiViewModel", "API call failed: " + response.message()); // Log error message
                    errorLiveData.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<NotiResponsive> call, Throwable t) {
                Log.e("NotiViewModel", "API call failed: " + t.getMessage()); // Log failure message
            }
        });
    }
}
