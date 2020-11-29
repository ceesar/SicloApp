package com.siclo.app.common.extensions

import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main_activity.view.*

fun Fragment.showLoader() {
    requireActivity().window.run {
        decorView.rootView.rlLoader.isVisible = true
        setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}

fun Fragment.hideLoader() {
    requireActivity().window.run {
        decorView.rootView.rlLoader.isVisible = false
        clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}

fun Fragment.showDialog(message: String, cancelable: Boolean = true, action: (() -> Unit)? = null) {
    val alertDialog = AlertDialog.Builder(requireContext())
        .setMessage(
            HtmlCompat.fromHtml(
                "<font color='#AFAFAF'>$message</font>", HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        )
        .setCancelable(cancelable)
        .setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
            action?.invoke()
            dialog.cancel()
        }
        .create()

    alertDialog.show()
}