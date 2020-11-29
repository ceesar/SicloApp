package com.siclo.app.common.bindingadapter

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object EditTextBindingAdapter {
    @BindingAdapter("error")
    @JvmStatic
    fun setError(inputLayout: TextInputLayout, @StringRes error: Int? = null) {
        inputLayout.run {
            if (error == null) setError(null)
            else setError(context.getString(error))
        }
    }
}