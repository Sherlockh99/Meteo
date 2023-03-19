package com.sherlock.gb.kotlin.meteo.repository.weather

import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO
import com.sherlock.gb.kotlin.meteo.repository.City

class WeatherUtils {
    companion object {
        fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
            return Weather(
                City(weatherDTO.location.name, weatherDTO.location.lat, weatherDTO.location.lon),
                weatherDTO.current.tempC.toInt(),
                weatherDTO.current.feelslikeC.toInt()
            )
        }
    }
}