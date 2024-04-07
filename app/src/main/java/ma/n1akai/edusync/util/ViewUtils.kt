package ma.n1akai.edusync.util

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ma.n1akai.edusync.components.common.ButtonWithProgress

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
}
fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show();
}

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun Button.navigate(directions: NavDirections) {
    setOnClickListener {
        findNavController().navigate(directions)
    }
}

fun ButtonWithProgress.navigate(directions: NavDirections) {
    setOnButtonClickListener {
        findNavController().navigate(directions)
    }
}

fun Button.popBackStack() {
    setOnClickListener {
        findNavController().popBackStack()
    }
}