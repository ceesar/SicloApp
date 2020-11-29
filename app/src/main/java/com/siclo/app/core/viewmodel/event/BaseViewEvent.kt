package com.siclo.app.core.viewmodel.event

import com.siclo.app.core.data.api.models.Resource

open class BaseViewEvent {
    object OnValidationSuccess : BaseViewEvent()
    data class OnRequestEvent(val resource: Resource<*>) : BaseViewEvent()
}