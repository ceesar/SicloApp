package com.siclo.app.core.data.api

import com.siclo.app.common.constants.SicloEndpoint
import com.siclo.app.core.data.api.models.response.ClassesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SicloServices {

    @GET(SicloEndpoint.CLASSES)
    suspend fun getClasses(
        @Query("location") location: Int = 603967,
        @Query("page_size") pageSize: Int = -1
    ): Response<ClassesResponse>
}