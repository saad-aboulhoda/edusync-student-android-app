package ma.n1akai.edusync.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.data.models.Mark
import ma.n1akai.edusync.databinding.NotesListItemBinding

class MarksAdapter : RecyclerView.Adapter<MarksAdapter.MarksViewHolder>() {
    inner class MarksViewHolder(val binding: NotesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Mark>() {
        override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var marks: List<Mark>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MarksViewHolder(NotesListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = marks.size

    override fun onBindViewHolder(holder: MarksViewHolder, position: Int) {
        holder.binding.apply {
            val mark = marks[position]
            tvLabel.text = mark.m
            tvDate.text = mark.date
            tvNote.text = mark.note.toString()
        }
    }


}