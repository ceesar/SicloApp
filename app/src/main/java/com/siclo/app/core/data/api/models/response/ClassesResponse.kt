package com.siclo.app.core.data.api.models.response

import com.squareup.moshi.Json

data class ClassesResponse(
    val calendar: Map<String, List<Class>>,
    val instructors: List<Instructor>
)

data class Class(
    val id: Int,
    @field:Json(name = "fecha")
    val date: String,
    @field:Json(name = "textoEspecial")
    val specialText: String,
    @field:Json(name = "tipo")
    val type: String,
    val instructor: Instructor,
    val hour: String,
    val duration: Int,
    val intensity: Int
)

data class Instructor(
    val id: Int,
    @field:Json(name = "nombre")
    val name: String
)