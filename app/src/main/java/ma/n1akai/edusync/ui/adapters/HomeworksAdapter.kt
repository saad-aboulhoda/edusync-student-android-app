package ma.n1akai.edusync.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.databinding.HomeworksListItemBinding

class HomeworksAdapter : RecyclerView.Adapter<HomeworksAdapter.HomeworkViewHolder>() {

    private lateinit var checkedChangeListener: CheckedChangedListener

    inner class HomeworkViewHolder(val binding: HomeworksListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Homework>() {
        override fun areItemsTheSame(oldItem: Homework, newItem: Homework): Boolean {
            return oldItem.homework_id == newItem.homework_id
        }

        override fun areContentsTheSame(oldItem: Homework, newItem: Homework): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var homeworks: List<Homework>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeworkViewHolder(HomeworksListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = homeworks.size

    fun setOnCheckedChangeListener(checkedChangeListener: CheckedChangedListener) {
        this.checkedChangeListener = checkedChangeListener
    }

    override fun onBindViewHolder(holder: HomeworkViewHolder, position: Int) {
        holder.binding.apply {
            val hw = homeworks[position]
            homework.text = hw.homework
            val concatCourseAndDate = "${hw.course_name} - ${hw.created_at}"
            courseAndDate.text = concatCourseAndDate
            isDone.isChecked = hw.finished == 1
            isDone.setOnCheckedChangeListener { buttonView, isChecked ->
                checkedChangeListener.onCheckedChange(hw.homework_id, isChecked)
            }
        }
    }
    @FunctionalInterface
     interface CheckedChangedListener { fun onCheckedChange(homeworkId: String, checked: Boolean)
    }


}