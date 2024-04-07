package ma.n1akai.edusync.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.databinding.NotesListItemBinding

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
        holder.binding.apply {
            val test = tests[position]
            tvLabel.text = test.course_name
            tvDate.text = test.created_at
            tvNote.text = test.mark.toString()
        }
    }


}