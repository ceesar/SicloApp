package com.siclo.app.core.data.api

import com.siclo.app.common.constants.SicloEndpoint
import com.siclo.app.core.data.api.models.request.LoginRequest
import com.siclo.app.core.data.api.models.request.UserRequest
import com.siclo.app.core.data.api.models.response.LoginResponse
import com.siclo.app.core.data.api.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SicloStagingServices {

    @POST(SicloEndpoint.USER)
    suspend fun registryUser(@Body request: UserRequest): Response<UserResponse>

    @POST(SicloEndpoint.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}