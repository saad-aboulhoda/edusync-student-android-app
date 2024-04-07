package ma.n1akai.edusync.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.databinding.NotesListItemBinding
import ma.n1akai.edusync.util.formatToRelativeTime
import ma.n1akai.edusync.util.snackbar

class TestsAdapter : RecyclerView.Adapter<TestsAdapter.MarksViewHolder>() {
    inner class MarksViewHolder(val binding: NotesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Test>() {
        override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem.test_id == newItem.test_id
        }

        override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var tests: List<Test>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MarksViewHolder(NotesListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = tests.size

    override fun onBindViewHolder(holder: MarksViewHolder, position: Int) {
        val test = tests[position]
        holder.binding.apply {
            tvLabel.text = test.course_name
            tvDate.text = formatToRelativeTime(test.created_at)
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