package ma.n1akai.edusync.ui.home.homework

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.HomeworksListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.ui.home.dashboard.DashboardAdapter

class HomeworkAdapter : RecyclerView.Adapter<HomeworkHolder>() {

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    private lateinit var context: Context
    lateinit var listener: DashboardAdapter.OnHomeworkCheckedChangedListener
    lateinit var onHomeworkClickListener: DashboardAdapter.OnHomeworkClickListener

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Homework -> R.layout.homeworks_list_item
            is Title -> R.layout.title_item
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkHolder {
        if (!this::context.isInitialized) {
            context = parent.context
        }
        return when (viewType) {
            R.layout.title_item -> HomeworkHolder.TitleViewHolder(
                TitleItemBinding.inflate(LayoutInflater.from(context), parent, false)
            )

            R.layout.homeworks_list_item -> HomeworkHolder.HomeworkViewHolder(
                HomeworksListItemBinding.inflate(LayoutInflater.from(context), parent, false)
            )

            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeworkHolder, position: Int) {
        val item = items[position]
        when(holder) {
            is HomeworkHolder.HomeworkViewHolder -> holder.bind(item as Homework, context, listener, onHomeworkClickListener)
            is HomeworkHolder.TitleViewHolder -> holder.bind(item as Title)
        }
    }
}