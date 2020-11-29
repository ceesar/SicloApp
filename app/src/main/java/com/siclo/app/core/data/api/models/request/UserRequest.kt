package com.siclo.app.core.data.api.models.request

import com.siclo.app.ui.signup.model.EmailFields
import com.siclo.app.ui.signup.model.UserFields
import com.squareup.moshi.Json

data class UserRequest(
    @field:Json(name = "firstname")
    val name: String,
    @field:Json(name = "lastname")
    val lastName: String,
    val email: String,
    val birthday: String,
    val phone: String,
    val weight: Float,
    val height: Float,
    val password: String,
    @field:Json(name = "repeat_password")
    val repeatPassword: String
) {
    val gender: String = "male"
    val country: String = "Mex"
    val lada: String = "+52"

    companion object {
        fun build(userFields: UserFields, emailFields: EmailFields): UserRequest {
            return UserRequest(
                name = userFields.name,
                lastName = userFields.lastNames,
                birthday = userFields.birthday,
                phone = userFields.phone,
                weight = userFields.weight.toFloat(),
                height = userFields.height.toFloat(),
                email = emailFields.email,
                password = emailFields.password,
                repeatPassword = emailFields.confirmPassword
            )
        }
    }
}
