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

import java.lang.Exception

class OverviewViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()
    private val _property = MutableLiveData<MarsProperty>()

    val status: LiveData<String>
        get() = _status

    val property: LiveData<MarsProperty>
        get() = _property

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {

        coroutineScope.launch {
           var getDeferredProperties = MarsApi.retrofitService.getProperties()

            try {

                var listOfResults = getDeferredProperties.await()
                _status.value = "Success: ${listOfResults.size} Mars properties received!"

                if (listOfResults.size > 0) {
                    _property.value = listOfResults[0]
                }

            }catch (e:Exception){
                _status.value = "Failure: ${e.message}"             }
        }
    }
    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}

