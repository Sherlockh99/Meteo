package com.sherlock.gb.kotlin.meteo.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.sherlock.gb.kotlin.meteo.R

class Extensions {
    fun showSnackbar(
        view: View,
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {

        Snackbar.make(view, text, length).setAction(actionText, action).show()

    }

    fun showToast(
        view: View,
        text: String?,
        length: Int = Toast.LENGTH_LONG
    ) {

        Toast.makeText(
            view.context,
            view.resources.getString(R.string.looking_for) + " " + text, length
        ).show()

    }

    fun showAlertDialog(
        fragment: Fragment,
        title: Int,
        message: Int,
        icon: Int,
        positive_button: Int
    ) {
        val builder = AlertDialog.Builder(fragment.requireContext())

        builder.run {
            setTitle(title)
            //set message for alert dialog
            setMessage(message)
            setIcon(icon)
            setPositiveButton(positive_button) { dialogInterface, which ->
                builder
            }
            show()
        }

    }

}