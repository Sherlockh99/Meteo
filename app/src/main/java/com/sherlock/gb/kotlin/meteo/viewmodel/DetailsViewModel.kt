package com.sherlock.gb.kotlin.meteo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sherlock.gb.kotlin.meteo.repository.DetailsRepositoryRetrofit2Impl
import com.sherlock.gb.kotlin.meteo.repository.City
import com.sherlock.gb.kotlin.meteo.repository.DetailsRepository
import com.sherlock.gb.kotlin.meteo.repository.weather.Weather

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    //private var repository: DetailsRepository = DetailsRepositoryOkHttpImpl()
    private val repository: DetailsRepository = DetailsRepositoryRetrofit2Impl()
): ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(city: City){
        liveData.postValue(DetailsState.Loading)
        repository.getWeatherDetails(city, object : Callback {
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
            }

            override fun onFail() {
                TODO("Not yet implemented")
            }

        })
    }

    interface Callback {
        fun onResponse(weather: Weather)
        fun onFail()
    }
}