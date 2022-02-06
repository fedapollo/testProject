package com.example.testproject.common.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.testproject.R

class FullLoading {

    companion object {
        fun getFullLoading(context: Context?): Dialog? {
            if (context == null) return null

            val dialog = Dialog(context, R.style.FullLoadingDialog)
            if (dialog.window != null) {
                (dialog.window as Window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            dialog.setContentView(R.layout.dialog_full_loading)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)

            return dialog
        }
    }
}