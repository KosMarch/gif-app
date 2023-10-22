package com.example.giphyapp

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import com.example.giphyapp.GiphyConstant.SEARCH_TITLE

object DialogManager {
    fun searchByQuery(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val editText = EditText(context)
        builder.setView(editText)
        val dialog = builder.create()
        dialog.setTitle(SEARCH_TITLE)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _,_ ->
            listener.onClick(editText.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface Listener {
        fun onClick(query: String?)
    }
}