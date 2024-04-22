package ma.n1akai.edusync.ui.home.mark

import android.content.Context
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.HomeworksListItemBinding
import ma.n1akai.edusync.databinding.NotesListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.ui.home.dashboard.DashboardAdapter
import ma.n1akai.edusync.util.formatToRelativeTime
import ma.n1akai.edusync.util.snackbar

sealed class MarkHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(
        private val binding: TitleItemBinding
    ) : MarkHolder(binding) {
        fun bind(title: Title) {
            binding.let {
                it.title.text = title.title
                it.title.setCompoundDrawablesWithIntrinsicBounds(title.icon, 0, 0, 0)
            }
        }
    }

    class MarkViewHolder(private val binding: NotesListItemBinding) :
        MarkHolder(binding) {
        fun bind(test: Test, context: Context) {
            binding.apply {
                tvLabel.text = test.course_name
                tvDate.text = formatToRelativeTime(test.created_at, context)
                tvNote.text = test.mark.toString()
                val concatModuleAndCc = "${test.course_code} - ${test.test_code}"
                tvModuleCc.text = concatModuleAndCc

                root.setOnLongClickListener {
                    val message =
                        "${test.course_code}: ${test.course_name} - ${test.test_code}: ${test.mark}"
                    it.snackbar(message)
                    true
                }
            }
        }
    }

}