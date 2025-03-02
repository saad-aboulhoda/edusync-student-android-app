package ma.n1akai.edusync.ui.home.dashboard

import android.content.Context
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.checkbox.MaterialCheckBox
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.HomeworksListItemBinding
import ma.n1akai.edusync.databinding.NotesListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.util.formatToRelativeTime
import ma.n1akai.edusync.util.snackbar

sealed class DashboardHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: TitleItemBinding) :
        DashboardHolder(binding) {
        fun bind(title: Title, onMoreClickListener: DashboardAdapter.OnMoreClickListener) {
            binding.let {
                it.title.text = title.title
                it.title.setCompoundDrawablesWithIntrinsicBounds(title.icon, 0, 0, 0)
                title.navDirections?.let { navDirections ->
                    it.more.isVisible = true
                    it.more.setOnClickListener { view ->
                        onMoreClickListener.onMoreClick(navDirections, view)
                    }
                }
            }
        }
    }

    class TestViewHolder(private val binding: NotesListItemBinding) :
        DashboardHolder(binding) {
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

    class HomeworkViewHolder(private val binding: HomeworksListItemBinding) :
        DashboardHolder(binding) {
        fun bind(
            hw: Homework,
            context: Context,
            listener: DashboardAdapter.OnHomeworkCheckedChangedListener,
            onHomeworkClickListener: DashboardAdapter.OnHomeworkClickListener
        ) {
            binding.apply {
                homework.text = hw.homework
                course.text = hw.course_name
                tvDateDueChip.text = formatToRelativeTime(hw.created_at, context)
                isDone.isChecked = hw.finished == 1
                isDone.setOnClickListener {
                    if (it is MaterialCheckBox) {
                        listener.onHomeworkCheckedChanged(hw, it, it.isChecked)
                        hw.finished = if(it.isChecked) 1 else 0
                    }
                }
                root.setOnClickListener {
                    onHomeworkClickListener.onHomeworkClick(hw, it)
                }
            }
        }
    }

}