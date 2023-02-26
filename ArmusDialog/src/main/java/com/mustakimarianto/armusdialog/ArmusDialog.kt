package com.mustakimarianto.armusdialog

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

class ArmusDialog(private val strategy: ArmusDialogStrategy) {

    fun show() {
        strategy.show()
    }

    fun dismiss() {
        strategy.dismiss()
    }

    // TODO: Need to add support scrollable for long text in dialog
    // TODO: Need to add rounded corner option to dialog
    // TODO: Need to add cancelable option
    /**
     * Show default dialog with title, message, positive button, and negative button.
     */
    class DefaultDialog(
        private val context: Context,
        private val layoutInflater: LayoutInflater,
        private val title: String,
        private val message: String,
        private val positiveBtn: String,
        private val negativeBtn: String,
        private val width: ArmusDialogParams = ArmusDialogParams.WRAP_CONTENT,
        private val listener: ArmusDialogListener
    ) : ArmusDialogStrategy {
        private val dialog: Dialog = Dialog(context)

        override fun show() {
            val root = layoutInflater.inflate(R.layout.layout_default_dialog, null, false)
            dialog.setContentView(root)
            root.findViewById<TextView>(R.id.tv_dialog_title).text = title
            root.findViewById<TextView>(R.id.tv_dialog_message).text = message

            val btnPositive = root.findViewById<Button>(R.id.btn_default_positive)
            val btnNegative = root.findViewById<Button>(R.id.btn_default_negative)
            btnPositive.text = positiveBtn
            btnNegative.text = negativeBtn

            btnPositive.setOnClickListener {
                listener.onPositiveButtonClicked()
            }

            btnNegative.setOnClickListener {
                listener.onNegativeButtonClicked()
            }

            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowManager.currentWindowMetrics.bounds.also { bounds ->
                    displayMetrics.widthPixels = bounds.width()
                    displayMetrics.heightPixels = bounds.height()
                }
            } else {
                @Suppress("DEPRECATION")
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            }


            val dialogWidth = if (width.name == ArmusDialogParams.WRAP_CONTENT.name)
                WindowManager.LayoutParams.WRAP_CONTENT
            else
                WindowManager.LayoutParams.MATCH_PARENT

            val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT

            dialog.window?.setLayout(dialogWidth, dialogHeight)
            dialog.setCancelable(true)
            dialog.show()
        }

        override fun dismiss() {
            dialog.dismiss()
        }
    }
}