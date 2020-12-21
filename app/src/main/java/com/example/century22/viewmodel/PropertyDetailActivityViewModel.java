package com.example.century22.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;

import com.example.century22.api.currencyAPI;
import com.example.century22.bo.CResponse;
import com.example.century22.bo.Property;
import com.example.century22.bo.Rates;
import com.example.century22.preferences.AppPreferences;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.PropertyDetailActivity;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class PropertyDetailActivityViewModel extends AndroidViewModel{

    public enum Event{
        Ok, Ko
    }

    public class Exchange{
        String currency;

        String converted_price;

        public Exchange(String currency, String converted_price)
        {
            this.currency = currency;
            this.converted_price = converted_price;
        }

        public String getCurrency() {
            return this.currency;
        }

        public String getConverted_price(){
            return this.converted_price;
        }
    }

    public MutableLiveData<Event> event = new MutableLiveData<>();

    public MutableLiveData<Property> property = new MutableLiveData<>();

    public MutableLiveData<Exchange> currency = new MutableLiveData<>();

    private final Property propertyExtra;

    private Rates rate;


    public PropertyDetailActivityViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        propertyExtra = savedStateHandle.get(PropertyDetailActivity.PROPERTY_EXTRA);
        property.postValue(propertyExtra);
    }

    /* Method to check if we can delete property or not */
    public void deleteProperty()
    {
        //If agent is the agent that added the property then it can be deleted
        if(AppPreferences.getAgentName(getApplication()).equals(propertyExtra.agent)){
            AppRepository.getInstance(getApplication()).deleteProperty(propertyExtra);
            event.postValue(Event.Ok);
        }
        //Else we post the value to inform that we cannot delete property
        else
        {
            event.postValue(Event.Ko);
        }
    }

    /* Method to refresh the attributes if property has been edited */
    public void loadProperty()
    {
        if(property.getValue() != null)
            property.postValue(AppRepository.getInstance(getApplication()).getProperty(property.getValue().id));
    }

    //Call the api
    private void currencyFromAPI(String current_currency){
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor() ;
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //city = checkCity(city);
        final OkHttpClient okHttp = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.exchangeratesapi.io/")
                .addConverterFactory(MoshiConverterFactory.create())//.build();
                .client(okHttp).build();
        currencyAPI service = retrofit.create(currencyAPI.class);
        Call<CResponse> call = service.getCurrency("USD");
        call.enqueue(new Callback<CResponse>()
        {
            @Override
            public void onResponse(Call<CResponse> call, Response<CResponse> response) {
                //if(!(response.isSuccessful()))
                Log.d(PropertyDetailActivityViewModel.class.getSimpleName(), String.valueOf(response.code()));
                CResponse data = response.body();//We get the data from the api
                rate = data.getRates();
                setExchange(current_currency);
            }
            @Override
            public void onFailure(Call<CResponse> call, Throwable t) {
                Log.d(PropertyDetailActivityViewModel.class.getSimpleName(), "ERROR");
                t.printStackTrace();
            } });
    }

    private void setExchange(String current_currency){
        int new_price;
        new_price = (int) (Integer.parseInt(property.getValue().price)*rate.getUSD());
        Exchange ex = new Exchange("$", Integer.toString(new_price));
        currency.postValue(ex);
    }


    public void convert(String current_currency)
    {
        if(current_currency.equals("€"))
            currencyFromAPI(current_currency);
        else{
            Exchange ex = new Exchange("€", property.getValue().price);
            currency.postValue(ex);
        }
    }

}
