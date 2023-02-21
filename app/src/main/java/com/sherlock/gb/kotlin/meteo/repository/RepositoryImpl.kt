package com.sherlock.gb.kotlin.meteo.repository

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        Thread.sleep(2000L) //эмуляция запроса на сервер
        return Weather() // эмуляция ответа
    }

    override fun getWorldWeatherFromLocalStorage(): List<Weather> {
        return getWorldCities() //эмуляция ответа
    }

    override fun getRussianWeatherFromLocalStorage(): List<Weather> {
        return getRussianCities()
    }
}