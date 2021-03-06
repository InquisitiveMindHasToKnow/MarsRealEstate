package org.ohmstheresistance.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.ohmstheresistance.marsrealestate.network.MarsApi
import org.ohmstheresistance.marsrealestate.network.MarsApiFilter
import org.ohmstheresistance.marsrealestate.network.MarsProperty

import java.lang.Exception

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<MarsApiStatus>()
    private val _properties = MutableLiveData<List<MarsProperty>>()
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()

    val status: LiveData<MarsApiStatus>
        get() = _status

    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {

        coroutineScope.launch {
           var getDeferredProperties = MarsApi.retrofitService.getProperties(filter.value)

            try {
                _status.value = MarsApiStatus.LOADING

                var listOfResults = getDeferredProperties.await()
                _status.value = MarsApiStatus.DONE

                _properties.value = listOfResults

            }catch (e:Exception){

                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}

