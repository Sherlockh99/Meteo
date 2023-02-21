package com.sherlock.gb.kotlin.meteo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sherlock.gb.kotlin.meteo.databinding.FragmentMainBinding
import com.sherlock.gb.kotlin.meteo.viewmodel.AppState
import com.sherlock.gb.kotlin.meteo.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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
                Snackbar.make(binding.mainView,s, Snackbar.LENGTH_LONG).show()

            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                //setData(data.weatherList)
                val s = "Work"
                Snackbar.make(binding.mainView,s, Snackbar.LENGTH_LONG).show()
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