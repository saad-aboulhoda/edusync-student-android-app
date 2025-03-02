package ma.n1akai.edusync.ui.home.homework

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.checkbox.MaterialCheckBox
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.HomeworksListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.ui.home.dashboard.DashboardAdapter
import ma.n1akai.edusync.util.formatToRelativeTime
import ma.n1akai.edusync.util.snackbar

sealed class HomeworkHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(
        private val binding: TitleItemBinding
    ) : HomeworkHolder(binding) {
        fun bind(title: Title) {
            binding.let {
                it.title.text = title.title
                it.title.setCompoundDrawablesWithIntrinsicBounds(title.icon, 0, 0, 0)
            }
        }
    }

    class HomeworkViewHolder(private val binding: HomeworksListItemBinding) :
        HomeworkHolder(binding) {
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
                    }
                }
                root.setOnClickListener {
                    onHomeworkClickListener.onHomeworkClick(hw, it)
                }
            }
        }
    }

}