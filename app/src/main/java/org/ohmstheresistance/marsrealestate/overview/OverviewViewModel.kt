package org.ohmstheresistance.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.ohmstheresistance.marsrealestate.network.MarsApi
import org.ohmstheresistance.marsrealestate.network.MarsProperty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverviewViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val response: LiveData<String>
        get() = _response

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {

        coroutineScope.launch {
           var getDeferredProperties = MarsApi.retrofitService.getProperties()

            try {
                var listOfResults = getDeferredProperties.await()
                _response.value = "Success: ${listOfResults.size} Mars properties received!"

            }catch (t:Throwable){
                _response.value = "Failure: " + t.message
            }
        }
    }
    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}

