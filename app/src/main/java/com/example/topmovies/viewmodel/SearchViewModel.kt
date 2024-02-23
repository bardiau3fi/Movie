package com.example.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmovies.models.home.ResponseMoviesList
import com.example.topmovies.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    val moviesList = MutableLiveData<ResponseMoviesList>()
    val loading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()

    fun loadSearchMovies(name: String) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.searchMovie(name)
        if (response.isSuccessful) {
            if (response.body()?.data!!.isNotEmpty()) {
                moviesList.postValue(response.body())
                empty.postValue(false)
            } else {
                empty.postValue(true)
            }
        }
        loading.postValue(false)
    }
}