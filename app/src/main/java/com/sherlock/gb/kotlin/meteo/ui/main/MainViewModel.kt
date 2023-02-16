package com.sherlock.gb.kotlin.meteo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sherlock.gb.kotlin.meteo.repository.RepositoryImpl

class MainViewModel(
    private val liveData : MutableLiveData<AppState> = MutableLiveData(),
    private val repository : RepositoryImpl = RepositoryImpl(),
    private var isLocal: Boolean = false
) : ViewModel() {
    fun getData() = liveData

    fun getWeather(){
        Thread{
            liveData.postValue(AppState.Loading)

            if((0..10).random()>5) {
                val answer = if(getIsLocal()){
                    repository.getWeatherFromLocalStorage()
                }else {
                    repository.getWeatherFromServer()
                }
                liveData.postValue(AppState.Success(answer))
            }else{
                liveData.postValue(AppState.Error(IllegalAccessException()))
            }
        }.start()
    }

    fun setIsLocal(isLocal: Boolean){
        this.isLocal = isLocal
    }

    fun getIsLocal():Boolean{
        return isLocal
    }
}