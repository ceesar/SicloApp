package com.siclo.app.ui.dashboard.model

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.siclo.app.core.data.api.models.Resource
import com.siclo.app.core.data.api.models.response.Class
import com.siclo.app.core.data.api.models.response.ClassesResponse
import com.siclo.app.core.data.repository.SicloRepository
import com.siclo.app.core.viewmodel.BaseViewModel
import com.siclo.app.core.viewmodel.event.BaseViewEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel @ViewModelInject constructor(
    private val repository: SicloRepository
) : BaseViewModel() {

    val classes = MutableLiveData(emptyList<Any>())

    fun retrieveClasses() {
        viewModelScope.launch(Dispatchers.IO) {
            updateEvent(BaseViewEvent.OnRequestEvent(Resource.loading()))
            val result = repository.getClasses()
            if (result.status == Resource.SUCCESS) {
                handleClassesResponse(result)
            } else {
                updateEvent(BaseViewEvent.OnRequestEvent(result))
            }
        }
    }

    @SuppressLint("SimpleDateFormat", "DefaultLocale")
    private fun handleClassesResponse(resource: Resource<ClassesResponse>) {
        val classesList = arrayListOf<Any>()
        var currentDayOfYear = -1
        val calendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("EEEE dd LLLL", Locale.forLanguageTag("es-MX"))

        resource.data!!.calendar
            .flatMap { entry: Map.Entry<String, List<Class>> -> entry.value }
            .sortedBy { it.date }
            .forEach {
                val date = dateFormatter.parse(it.date)
                if (date != null) {
                    calendar.time = date
                    if (currentDayOfYear != calendar[Calendar.DAY_OF_YEAR]) {
                        classesList.add(formatter.format(date).capitalize())
                        currentDayOfYear = calendar[Calendar.DAY_OF_YEAR]
                    }
                    classesList.add(it)
                }
            }

        viewModelScope.launch(Dispatchers.Main) { classes.value = classesList.toList() }
    }
}