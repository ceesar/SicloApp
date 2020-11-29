package com.siclo.app.core.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siclo.app.core.viewmodel.event.BaseViewEvent
import com.siclo.app.core.viewmodel.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel @ViewModelInject constructor() : ViewModel() {

    val viewEvent: LiveData<Event<BaseViewEvent>>
        get() = innerViewEvent

    private val innerViewEvent = MutableLiveData<Event<BaseViewEvent>>()

    fun updateEvent(state: BaseViewEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            innerViewEvent.value = Event(state)
        }
    }
}