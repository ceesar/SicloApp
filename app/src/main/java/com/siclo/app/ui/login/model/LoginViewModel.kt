package com.siclo.app.ui.login.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.siclo.app.core.data.api.models.Resource
import com.siclo.app.core.data.repository.SicloRepository
import com.siclo.app.core.viewmodel.BaseViewModel
import com.siclo.app.core.viewmodel.event.BaseViewEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val repository: SicloRepository
) : BaseViewModel() {

    val loginForm = LoginForm()

    fun performLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            if (loginForm.validateLoginForm()) {
                updateEvent(BaseViewEvent.OnRequestEvent(Resource.loading()))
                val result = repository.login(loginForm.fields)
                updateEvent(BaseViewEvent.OnRequestEvent(result))
            }
        }
    }
}