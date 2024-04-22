package ma.n1akai.edusync.ui.home.mark

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.HomeworksListItemBinding
import ma.n1akai.edusync.databinding.NotesListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.ui.home.dashboard.DashboardAdapter

class MarkAdapter : RecyclerView.Adapter<MarkHolder>() {

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private lateinit var context: Context

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Test -> R.layout.notes_list_item
            is Title -> R.layout.title_item
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkHolder {
        if (!this::context.isInitialized) {
            context = parent.context
        }
        return when (viewType) {
            R.layout.title_item -> MarkHolder.TitleViewHolder(
                TitleItemBinding.inflate(LayoutInflater.from(context), parent, false)
            )

            R.layout.notes_list_item -> MarkHolder.MarkViewHolder(
                NotesListItemBinding.inflate(LayoutInflater.from(context), parent, false)
            )

            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MarkHolder, position: Int) {
        val item = items[position]
        when(holder) {
            is MarkHolder.MarkViewHolder -> holder.bind(item as Test, context)
            is MarkHolder.TitleViewHolder -> holder.bind(item as Title)
        }
    }
}