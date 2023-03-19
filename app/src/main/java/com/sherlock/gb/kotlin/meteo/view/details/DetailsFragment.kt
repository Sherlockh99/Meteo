package com.sherlock.gb.kotlin.meteo.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.sherlock.gb.kotlin.lessons.repository.xdto.WeatherDTO
import com.sherlock.gb.kotlin.meteo.BuildConfig
import com.sherlock.gb.kotlin.meteo.R
import com.sherlock.gb.kotlin.meteo.databinding.FragmentDetailsBinding
import com.sherlock.gb.kotlin.meteo.repository.*
import com.sherlock.gb.kotlin.meteo.repository.weather.*
import com.sherlock.gb.kotlin.meteo.view.extention.ExtentionView
import com.sherlock.gb.kotlin.meteo.view.weatherlist.KEY_BUNDLE_WEATHER
import com.sherlock.gb.kotlin.meteo.viewmodel.ResponseState
import kotlinx.android.synthetic.main.fragment_details.*
import okhttp3.*
import java.io.IOException


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
        requireContext().unregisterReceiver(receiver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER)?.let{
                    onResponse(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().registerReceiver(receiver,
            IntentFilter(KEY_WAVE_SERVICE_BROADCAST)
        )
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            localWeather = it
            /**
            requireActivity().startService(Intent(requireContext(),WeatherLoaderService::class.java).apply {
                putExtra(KEY_BUNDLE_LAT,localWeather.city.lat)
                putExtra(KEY_BUNDLE_LON,localWeather.city.lon)
            })
            */

            getWeather(it.city.lat,it.city.lon)
        }
    }

    private fun getWeather(lat:Double, lon: Double){
        binding.loadingLayout.visibility = View.VISIBLE

        val client = OkHttpClient()
        val builder = Request.Builder()

        builder.addHeader(WEATHER_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("$WEATHER_DOMAIN$WEATHER_ENDPOINT?q=${lat},${lon}&lang=ru")
        val request = builder.build()
        val call = client.newCall(request)

        //*** вариант 1 - асинхронный вызов
        val callback: Callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    val weatherDTO: WeatherDTO = Gson().fromJson(response.body()?.string(),WeatherDTO::class.java)
                    requireActivity().runOnUiThread{
                        renderData(weatherDTO)
                    }
                }
            }
        }

        call.enqueue(callback) //помести в очередь, а ответ верни в callback

        //----------- вариант 1

        /**
        //***************** вариант 2 - синхронный метод
        Thread{
        val response = call.execute() //выполнить здесь и сейчас
        if(response.isSuccessful){
        val weatherDTO: WeatherDTO = Gson().fromJson(response.body()?.string(),WeatherDTO::class.java)
        requireActivity().runOnUiThread{
        renderData(weatherDTO)
        }
        }
        }.start()
        */
         */
        //-------------------- вариант 2



        binding.loadingLayout.visibility = View.GONE
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
        ExtentionView.showToast(mainView,"Получилось")
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
                ExtentionView.showToast(mainView,
                    "Ошибка на стороне сервера: $error. Отображены локальные данные")
            }
            is ResponseState.ClientSide ->
            {
                renderData(localWeather)
                ExtentionView.showToast(mainView,
                    "Ошибка на стороне клиента $error. Отображены локальные данные"
                )
            }
        }
    }
}
