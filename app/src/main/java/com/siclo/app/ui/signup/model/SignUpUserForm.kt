package com.siclo.app.ui.signup.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.siclo.app.R

class SignUpUserForm : BaseObservable() {
    val fields = UserFields()
    private val errors = UserErrorFields()

    fun validateUserForm(): Boolean {
        val isValid = isNameValid() and isLastNameValid() and isBirthdayValid() and
                isPhoneValid() and isWeightValid() and isHeightValid()

        notifyChange()
        return isValid
    }


    private fun isNameValid(): Boolean {
        val validName = fields.name.isNotEmpty()
        if (validName) {
            errors.name = null
        } else {
            errors.name = R.string.required_data_error
        }

        return validName
    }

    private fun isLastNameValid(): Boolean {
        val validLastName = fields.lastNames.isNotEmpty()
        if (validLastName) {
            errors.lastNames = null
        } else {
            errors.lastNames = R.string.required_data_error
        }

        return validLastName
    }

    private fun isBirthdayValid(): Boolean {
        val validBirthday = fields.birthday.isNotEmpty()
        if (validBirthday) {
            errors.birthday = null
        } else {
            errors.birthday = R.string.required_data_error
        }

        return validBirthday
    }

    private fun isPhoneValid(): Boolean {
        return when {
            fields.phone.isEmpty() -> {
                errors.phone = R.string.required_data_error
                false
            }
            fields.phone.length < 10 -> {
                errors.phone = R.string.login_sign_up_user_phone_length_error
                false
            }
            else -> {
                errors.phone = null
                true
            }
        }
    }

    private fun isWeightValid(): Boolean {
        val weight = fields.weight.isNotEmpty()
        if (weight) {
            errors.weight = null
        } else {
            errors.weight = R.string.required_data_error
        }

        return weight
    }

    private fun isHeightValid(): Boolean {
        return when {
            fields.height.isEmpty() -> {
                errors.height = R.string.required_data_error
                false
            }
            fields.height.toFloat() > 3 -> {
                errors.height = R.string.login_sign_up_user_height_max_error
                false
            }
            else -> {
                errors.height = null
                true
            }
        }
    }

    fun clearForm() {
        fields.clearFormData()
        errors.clearFormData()
        notifyChange()
    }

    @Bindable
    fun getNameError() = errors.name

    @Bindable
    fun getLastNameError() = errors.lastNames

    @Bindable
    fun getBirthdayError() = errors.birthday

    @Bindable
    fun getPhoneError() = errors.phone

    @Bindable
    fun getWeightError() = errors.weight

    @Bindable
    fun getHeightError() = errors.height
}

data class UserFields(
    var name: String = "",
    var lastNames: String = "",
    var birthday: String = "",
    var phone: String = "",
    var weight: String = "",
    var height: String = ""
) {
    fun clearFormData() {
        name = ""
        lastNames = ""
        birthday = ""
        phone = ""
        weight = ""
        height = ""
    }
}

data class UserErrorFields(
    var name: Int? = null,
    var lastNames: Int? = null,
    var birthday: Int? = null,
    var phone: Int? = null,
    var weight: Int? = null,
    var height: Int? = null
) {
    fun clearFormData() {
        name = null
        lastNames = null
        birthday = null
        phone = null
        weight = null
        height = null
    }
}
