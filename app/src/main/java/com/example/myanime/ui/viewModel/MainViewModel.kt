package com.example.myanime.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myanime.core.ApiState
import com.example.myanime.data.models.DataItem
import com.example.myanime.domain.useCase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: UseCase
) : ViewModel() {

    private var _dataAnime = MutableLiveData<MutableList<DataItem?>>()
    var dataAnime: LiveData<MutableList<DataItem?>> = _dataAnime

    private var _apiState = MutableLiveData<ApiState>(ApiState.ApiEmpty)
    var apiState: LiveData<ApiState> = _apiState

    private val TAG = "TAG"

    init {
        animeInfo(true)
    }

    fun animeInfo(json: Boolean) {

        _apiState.value = ApiState.ApiLoading

        viewModelScope.launch(Dispatchers.IO) {
            val getClient = useCase.client(json)

            if (getClient.isSuccessful) {
                getClient.body()?.data.let {
                    _dataAnime.postValue(it)
                }
                _apiState.postValue( ApiState.Success )
            } else {
                _apiState.value = ApiState.Error("error")
            }
        }
    }

    fun delete(position: Int) {
        val data = _dataAnime.value
        data?.removeAt(position)
        _dataAnime.value = data!!
    }

    fun sortASC() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = _dataAnime.value
            data?.sortWith(compareBy { it?.animeName })
            _dataAnime.postValue(data!!)
        }
    }

    fun sortDes() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = _dataAnime.value
            data?.sortWith(compareByDescending { it?.animeName })
            _dataAnime.postValue(data!!)
        }
    }
}