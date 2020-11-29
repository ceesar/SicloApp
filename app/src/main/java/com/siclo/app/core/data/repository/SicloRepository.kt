package com.siclo.app.core.data.repository

import android.util.Log
import com.siclo.app.core.data.api.SicloServices
import com.siclo.app.core.data.api.SicloStagingServices
import com.siclo.app.core.data.api.models.Resource
import com.siclo.app.core.data.api.models.request.LoginRequest
import com.siclo.app.core.data.api.models.request.UserRequest
import com.siclo.app.core.data.api.models.response.ClassesResponse
import com.siclo.app.core.data.api.models.response.LoginResponse
import com.siclo.app.core.data.api.models.response.UserResponse
import com.siclo.app.ui.login.model.LoginFields
import com.siclo.app.ui.signup.model.EmailFields
import com.siclo.app.ui.signup.model.UserFields
import retrofit2.Response
import javax.inject.Inject

class SicloRepository @Inject constructor(
    private val services: SicloServices,
    private val stagingServices: SicloStagingServices
) {
    private suspend fun <R> makeRequest(
        request: suspend () -> Response<R>
    ): Resource<R> {
        try {
            with(request.invoke()) {
                return if (isSuccessful) {
                    Resource.success(body())
                } else {
                    Resource.error("Code: ${code()}, ${errorBody()} ")
                }
            }
        } catch (exception: Exception) {
            Log.e(SicloRepository::class.simpleName, "Error executing service", exception)
            return Resource.error("Ha ocurrido un error inesperado.")
        }

    }

    suspend fun login(loginFields: LoginFields): Resource<LoginResponse> {
        with(loginFields) {
            return makeRequest { stagingServices.login(LoginRequest(email, password)) }
        }
    }

    suspend fun registryUser(
        userFields: UserFields,
        emailFields: EmailFields
    ): Resource<UserResponse> {
        return makeRequest {
            stagingServices.registryUser(UserRequest.build(userFields, emailFields))
        }
    }

    suspend fun getClasses(): Resource<ClassesResponse> {
        return makeRequest { services.getClasses() }
    }

}