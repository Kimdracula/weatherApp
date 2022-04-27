package com.homework.weatherapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel(
    private val liveData: MutableLiveData<MainState> = MutableLiveData(),
    private val repository: DetailsRepositoryRoomImpl = DetailsRepositoryRoomImpl()
) :
    ViewModel() {

}
