package ma.n1akai.edusync.ui.home.examination.exam

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.TestQuestion
import ma.n1akai.edusync.databinding.QuestionsListItemBinding

class ExamAdapter : RecyclerView.Adapter<ExamAdapter.ExamViewHolder>() {

    private var context: Context? = null

    var items = listOf<TestQuestion>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var data = mutableMapOf<String, Int>()

    inner class ExamViewHolder(
        private val binding: QuestionsListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(testQuestion: TestQuestion, context: Context, index: Int) {
            binding.apply {
                val indexAndMark = "${index} (${testQuestion.mark})"
                tvQuestionTitle.text = context.getString(R.string.question_num, indexAndMark)
                tvQuestion.text = testQuestion.question

                radiogroupQuestions.removeAllViews()

                testQuestion.answers.forEach {
                    val radioBtn = RadioButton(context)
                    radioBtn.text = it.answer

                    if (data[testQuestion.question_id.toString()] == it.answer_id) {
                        radioBtn.isChecked = true
                    }

                    radioBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            data[testQuestion.question_id.toString()] = it.answer_id
                        }
                    }
                    radiogroupQuestions.addView(radioBtn)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        if (context == null) {
            context = parent.context
        }
        return ExamViewHolder(QuestionsListItemBinding.inflate(LayoutInflater.from(context),
            parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, context!!, position+1)
    }

}