package com.siclo.app.ui.signup.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.siclo.app.core.data.api.models.Resource
import com.siclo.app.core.data.repository.SicloRepository
import com.siclo.app.core.viewmodel.BaseViewModel
import com.siclo.app.core.viewmodel.event.BaseViewEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpUserViewModel @ViewModelInject constructor(
    private val repository: SicloRepository
) : BaseViewModel() {

    lateinit var emailFields: EmailFields
    val userForm = SignUpUserForm()

    fun performRegistration() {
        viewModelScope.launch(Dispatchers.IO) {
            if (userForm.validateUserForm()) {
                updateEvent(BaseViewEvent.OnRequestEvent(Resource.loading()))
                val result = repository.registryUser(
                    userForm.fields, emailFields
                )
                updateEvent(BaseViewEvent.OnRequestEvent(result))
            }
        }
    }

}