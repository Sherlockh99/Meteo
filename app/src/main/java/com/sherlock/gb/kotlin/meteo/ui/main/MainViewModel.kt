package com.sherlock.gb.kotlin.meteo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sherlock.gb.kotlin.meteo.repository.RepositoryImpl

class MainViewModel(
    private val liveData : MutableLiveData<AppState> = MutableLiveData(),
    private val repository : RepositoryImpl = RepositoryImpl()
) : ViewModel() {
    fun getData() = liveData

    fun getWeather(){
        Thread{
            liveData.postValue(AppState.Loading)

            if((0..10).random()>5) {
                val answer = repository.getWeatherFromServer()
                //TODO HW параметр с переключателем - локально или сервер
                liveData.postValue(AppState.Success(answer))
            }else{
                liveData.postValue(AppState.Error(IllegalAccessException()))
            }
        }.start()
    }
}