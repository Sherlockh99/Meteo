package com.sherlock.gb.kotlin.meteo.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sherlock.gb.kotlin.meteo.R
import com.sherlock.gb.kotlin.meteo.databinding.FragmentDetailsBinding
import com.sherlock.gb.kotlin.meteo.repository.Weather
import com.sherlock.gb.kotlin.meteo.utils.Extensions
import com.sherlock.gb.kotlin.meteo.view.weatherlist.KEY_BUNDLE_WEATHER
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

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
            renderData(it)
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

}
