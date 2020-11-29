package com.siclo.app.ui.login.model

import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.siclo.app.R

class LoginForm : BaseObservable() {
    val fields = LoginFields()
    private val errors = LoginErrorFields()


    fun validateLoginForm(): Boolean {
        val isValid = isEmailValid() and isPasswordValid()
        notifyChange()
        return isValid
    }

    private fun isEmailValid(): Boolean {
        val validEmail = fields.email.isNotEmpty()
                && Patterns.EMAIL_ADDRESS.matcher(fields.email).matches()
        if (validEmail) {
            errors.email = null
        } else {
            errors.email = R.string.login_sign_up_email_error
        }

        return validEmail
    }

    private fun isPasswordValid(): Boolean {
        val validPassword = fields.password.length >= 6
        if (validPassword) {
            errors.password = null
        } else {
            errors.password = R.string.login_sign_up_email_password_error
        }

        return validPassword
    }

    fun clearForm() {
        fields.clear()
        errors.clear()
        notifyChange()
    }

    @Bindable
    fun getEmailError() = errors.email

    @Bindable
    fun getPasswordError() = errors.password
}

data class LoginFields(
    var email: String = "",
    var password: String = "",
) {
    fun clear() {
        email = ""
        password = ""
    }
}

data class LoginErrorFields(
    var email: Int? = null,
    var password: Int? = null
) {
    fun clear() {
        email = null
        password = null
    }
}
