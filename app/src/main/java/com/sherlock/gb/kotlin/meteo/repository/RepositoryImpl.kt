package com.sherlock.gb.kotlin.meteo.repository

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        Thread.sleep(2000L) //эмуляция запроса на сервер
        return Weather() // эмуляция ответа
    }

    override fun getWeatherFromLocalStorage(): Weather {
        Thread.sleep(20L) //эмуляция запроса локального
        return Weather() //эмуляция ответа
    }
}