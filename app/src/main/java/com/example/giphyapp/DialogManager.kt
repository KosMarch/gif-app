package com.example.giphyapp

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

class DialogManager {
    fun searchByQuery(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val editText = EditText(context)
        builder.setView(editText)
        val dialog = builder.create()
        dialog.setTitle(context.resources.getString(R.string.dialog_search))
        dialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            context.resources.getString(R.string.dialog_positive)
        ) { _, _ ->
            listener.onClick(editText.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            context.resources.getString(R.string.dialog_negative)
        ) { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun errorMessage(context: Context, e: String) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(context.resources.getString(R.string.dialog_exception))
            .setMessage(e)
            .setPositiveButton(
                context.resources.getString(R.string.dialog_positive)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }
}