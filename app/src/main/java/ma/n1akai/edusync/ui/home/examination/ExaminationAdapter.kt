package ma.n1akai.edusync.ui.home.examination

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.TestOnline
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.ExamsListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.util.hide
import ma.n1akai.edusync.util.show


class ExaminationAdapter : RecyclerView.Adapter<ExaminationAdapter.ExaminationViewHolder>() {

    lateinit var context: Context
    lateinit var listener: OnStartTestClickListener

    sealed class ExaminationViewHolder(
        binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        class TitleViewHolder(private val binding: TitleItemBinding) :
            ExaminationViewHolder(binding) {
            fun bind(title: Title) {
                binding.let {
                    it.title.text = title.title
                    it.title.setCompoundDrawablesWithIntrinsicBounds(title.icon, 0, 0, 0)
                }
            }
        }

        class TestOnlineViewHolder(private val binding: ExamsListItemBinding) :
            ExaminationViewHolder(binding) {
            fun bind(testOnline: TestOnline, context: Context, listener: OnStartTestClickListener) {
                binding.apply {
                    examTvLabel.text = testOnline.test_online_name
                    examTvDuration.text = context.getString(R.string.duration, testOnline.duration)
                    if (testOnline.score > 0) {
                        examTvStartTest.hide()
                        examTvScore.show()
                        examTvCompleted.show()
                        examTvScore.text = context.getString(R.string.score, testOnline.score)
                    } else {
                        examTvStartTest.show()
                        examTvScore.hide()
                        examTvCompleted.hide()
                    }
                    examTvStartTest.setOnClickListener {
                        listener.onStartTestClick(testOnline, it!!)
                    }
                }
            }
        }
    }

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExaminationViewHolder {
        context = parent.context
        return when (viewType) {
            R.layout.title_item -> ExaminationViewHolder.TitleViewHolder(
                TitleItemBinding
                    .inflate(LayoutInflater.from(context), parent, false)
            )

            R.layout.exams_list_item -> {
                ExaminationViewHolder.TestOnlineViewHolder(
                    ExamsListItemBinding
                        .inflate(LayoutInflater.from(context), parent, false)
                )
            }

            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ExaminationViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is ExaminationViewHolder.TitleViewHolder -> holder.bind(item as Title)
            is ExaminationViewHolder.TestOnlineViewHolder -> holder.bind(item as TestOnline, context, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Title -> R.layout.title_item
            is TestOnline -> R.layout.exams_list_item
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }

    interface OnStartTestClickListener {
        fun onStartTestClick(testOnline: TestOnline, view: View)
    }
}
