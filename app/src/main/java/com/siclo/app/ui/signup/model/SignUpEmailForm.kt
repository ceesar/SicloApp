package com.siclo.app.ui.signup.model

import android.os.Parcelable
import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.siclo.app.R
import kotlinx.android.parcel.Parcelize

class SignUpEmailForm : BaseObservable() {
    val fields = EmailFields()
    private val errors = EmailErrorFields()

    fun validateEmailForm(): Boolean {
        val isValid = isEmailValid() and isPasswordValid() && isConfirmPasswordValid()
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

    private fun isConfirmPasswordValid(): Boolean {
        with(fields) {
            val passwordsMatch = password == confirmPassword
            if (passwordsMatch) {
                errors.confirmPassword = null
            } else {
                errors.confirmPassword = R.string.login_sign_up_email_repeat_password_error
            }

            return passwordsMatch
        }
    }

    fun clearForm() {
        fields.clearEmailFormData()
        errors.clearEmailForm()
        notifyChange()
    }

    @Bindable
    fun getEmailError() = errors.email

    @Bindable
    fun getPasswordError() = errors.password

    @Bindable
    fun getConfirmPasswordError() = errors.confirmPassword
}

@Parcelize
data class EmailFields(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = ""
) : Parcelable {
    fun clearEmailFormData() {
        email = ""
        password = ""
        confirmPassword = ""
    }
}

data class EmailErrorFields(
    var email: Int? = null,
    var password: Int? = null,
    var confirmPassword: Int? = null
) {
    fun clearEmailForm() {
        email = null
        password = null
        confirmPassword = null
    }
}
