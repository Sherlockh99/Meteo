package com.sherlock.gb.kotlin.meteo.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.sherlock.gb.kotlin.meteo.R
import com.sherlock.gb.kotlin.meteo.databinding.FragmentDetailsBinding
import com.sherlock.gb.kotlin.meteo.repository.weather.*
import com.sherlock.gb.kotlin.meteo.view.weatherlist.KEY_BUNDLE_WEATHER
import com.sherlock.gb.kotlin.meteo.viewmodel.DetailsState
import com.sherlock.gb.kotlin.meteo.viewmodel.DetailsViewModel

class DetailsFragment : Fragment() {
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

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner,object: Observer<DetailsState> {
            override fun onChanged(t: DetailsState) {
                renderData(t)
            }
        })

        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            localWeather = it
            viewModel.getWeather(localWeather.city)
        }
    }

    private fun renderData(detailsState: DetailsState){
        when(detailsState){
            is DetailsState.Success -> {
                val weather =  detailsState.weather
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

                    val urlIcon = "https:${weather.icon}"

                    /** загрузка иконки с помощью Glide
                    Glide.with(requireContext())
                    .load(urlIcon)
                    .into(icon)
                     */

                    /** загрузка иконки с помощью Picasso
                    Picasso.get()?.load(urlIcon)?.into(icon)
                     */

                    icon.load(urlIcon)
                    headerCityIcon.loadSvg("https://svgsilh.com/svg/1801287.svg")
                }
            }
            is DetailsState.Error -> {

            }
            DetailsState.Loading -> {

            }
        }
    }

    fun ImageView.loadSvg(url: String){

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry{
                add(SvgDecoder(this@loadSvg.context))
            }
            .build()

        val request = ImageRequest.Builder(requireContext())
            .crossfade(500)
            .crossfade(true)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
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
