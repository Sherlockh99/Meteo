package com.sherlock.gb.kotlin.meteo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sherlock.gb.kotlin.meteo.repository.RepositoryImpl

class MainViewModel(
    private val liveData : MutableLiveData<AppState> = MutableLiveData(),
    private val repository : RepositoryImpl = RepositoryImpl(),
) : ViewModel() {
    fun getData() = liveData

    fun getWeather(isRussian: Boolean){
        Thread{
            liveData.postValue(AppState.Loading)

            //if((0..10).random()>5) {
            if(true) {
                val answer =
                    if(isRussian) repository.getRussianWeatherFromLocalStorage()
                    else repository.getWorldWeatherFromLocalStorage()
                liveData.postValue(AppState.Success(answer))
            }else{
                liveData.postValue(AppState.Error(IllegalAccessException()))
            }
        }.start()
    }
}