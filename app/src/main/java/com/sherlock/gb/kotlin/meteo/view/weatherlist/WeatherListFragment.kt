package com.sherlock.gb.kotlin.meteo.view.weatherlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sherlock.gb.kotlin.meteo.databinding.FragmentWeatherListBinding
import com.sherlock.gb.kotlin.meteo.viewmodel.AppState
import com.sherlock.gb.kotlin.meteo.viewmodel.MainViewModel

class WeatherListFragment : Fragment() {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding
        get() = _binding!!

    val adapter = WeatherListAdapter()


    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.recyclerView.adapter = adapter
        val observer = object: Observer<AppState> {
            override fun onChanged(data: AppState) {
                renderData(data)
            }
        }

        viewModel.getData().observe(viewLifecycleOwner,observer)
        viewModel.getWeather(true)
    }

    private fun renderData(data: AppState){
        when (data){
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                val s = "Do not work"
                Snackbar.make(binding.root,s, Snackbar.LENGTH_LONG).show()

            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setData(data.weatherList)
                val s = "Work"
                Snackbar.make(binding.root,s, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    /*
    private fun setData(weatherData: List<Weather>) {
        /*
        binding.apply {
            cityName.text = weatherData.city.name
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weatherData.city.lat.toString(),
                weatherData.city.lon.toString()
            )
            temperatureValue.text = weatherData.temperature.toString()
            feelsLikeValue.text = weatherData.feelsLike.toString()
        }

         */
    }
*/

}