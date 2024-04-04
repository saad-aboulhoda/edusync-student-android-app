package ma.n1akai.edusync.components.common

import ma.n1akai.edusync.R
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.progressindicator.CircularProgressIndicator
class ButtonWithProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

 private val button: Button
 private val progress: CircularProgressIndicator

 init {
     inflate(context, R.layout.button_with_progress, this) // Inflate the custom layout
     button = findViewById(R.id.button)
     progress = findViewById(R.id.progress)

     // Read custom attribute
     val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonWithProgress)
     val buttonText = typedArray.getString(R.styleable.ButtonWithProgress_buttonText)
     typedArray.recycle()

     button.text = buttonText
 }

 fun setText(text: String) {
  button.text = text
 }

 fun showProgress() {
  button.isEnabled = false
  progress.isVisible = true
 }

 fun hideProgress() {
  button.isEnabled = true
  progress.isVisible = false
 }

 fun setOnButtonClickListener(listener: OnClickListener) {
  button.setOnClickListener(listener)
 }

}