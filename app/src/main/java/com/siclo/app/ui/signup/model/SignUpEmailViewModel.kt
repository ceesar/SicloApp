package com.siclo.app.ui.signup.model

import androidx.hilt.lifecycle.ViewModelInject
import com.siclo.app.core.viewmodel.BaseViewModel
import com.siclo.app.core.viewmodel.event.BaseViewEvent

class SignUpEmailViewModel @ViewModelInject constructor() : BaseViewModel() {

    val emailForm = SignUpEmailForm()

    fun validateEmailForm() {
        if (emailForm.validateEmailForm()) {
            updateEvent(BaseViewEvent.OnValidationSuccess)
        }
    }
}