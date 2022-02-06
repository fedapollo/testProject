package com.example.testproject.common

import android.app.Dialog
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.testproject.common.custom.FullLoading

abstract class BaseFragment : Fragment() {

    private val fullLoadingDialog: Dialog? by lazy {
        FullLoading.getFullLoading(context)
    }

    protected fun showFullLoadingDialog() {
        try {
            fullLoadingDialog?.show()
        } catch (exception: WindowManager.BadTokenException) {
            // This crash is usually caused by app trying to display a dialog using a previously-finished Activity as a context
        }
    }

    protected fun hideFullLoadingDialog() {
        try {
            fullLoadingDialog?.dismiss()
        } catch (exception: Exception) {
            // view not attached to activity
        }
    }
}