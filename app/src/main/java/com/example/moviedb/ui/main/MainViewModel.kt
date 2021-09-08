package com.example.moviedb.ui.main

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep


class MainViewModel(private val liveDataToObserve: MutableLiveData<AppAction> = MutableLiveData()) :
        ViewModel() {

        fun getLiveData() = liveDataToObserve

    fun getMovieFromLocalSource() = getDataFromLocalSource()
    fun getMovieFromRemote() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppAction.Loading
        Thread {
            sleep(2000)
            liveDataToObserve.postValue(AppAction.Success(MovieData()))
        }.start()
    }
}
