package com.example.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Place

class PlaceViewModel: ViewModel() {
    private val searchLivedata = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = searchLivedata.switchMap { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLivedata.value = query
    }
}