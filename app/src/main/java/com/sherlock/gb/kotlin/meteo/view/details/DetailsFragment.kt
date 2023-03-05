package com.sherlock.gb.kotlin.meteo.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO
import com.sherlock.gb.kotlin.meteo.R
import com.sherlock.gb.kotlin.meteo.databinding.FragmentDetailsBinding
import com.sherlock.gb.kotlin.meteo.repository.*
import com.sherlock.gb.kotlin.meteo.utils.Extensions
import com.sherlock.gb.kotlin.meteo.view.weatherlist.KEY_BUNDLE_WEATHER
import com.sherlock.gb.kotlin.meteo.viewmodel.ResponseState
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(), OnServerResponse, OnServerResponseListener {
    lateinit var localWeather: Weather
    private var _binding: FragmentDetailsBinding? = null
    private val binding:FragmentDetailsBinding
        get(){
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            localWeather = it
            WeatherLoader(this@DetailsFragment,this@DetailsFragment).loadWeather(it.city.lat,it.city.lon)
        }
    }

    private fun renderData(weather: Weather){
        binding.apply {
            loadingLayout.visibility = View.GONE
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weather.city.lat.toString(),
                weather.city.lon.toString()
            )
        }
    }

    private fun renderData(weather: WeatherDTO){
        binding.apply {
            loadingLayout.visibility = View.GONE
            cityName.text = weather.location.name
            temperatureValue.text = weather.current.tempC.toString()
            feelsLikeValue.text = weather.current.feelslikeC.toString()
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weather.location.lat.toString(),
                weather.location.lon.toString()
            )
        }
        Extensions.showToast(mainView,"Получилось")
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle):DetailsFragment{
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onResponse(weatherDTO: WeatherDTO) {
        renderData(weatherDTO)
    }

    override fun onError(error: ResponseState) {
        when (error){
            is ResponseState.ServerSide ->{
                renderData(localWeather)
                Extensions.showToast(mainView,
                    "Ошибка на стороне сервера: $error. Отображены локальные данные")
            }
            is ResponseState.ClientSide ->
            {
                renderData(localWeather)
                Extensions.showToast(mainView,
                    "Ошибка на стороне клиента $error. Отображены локальные данные"
                )
            }
        }
    }
}
