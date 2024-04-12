package ma.n1akai.edusync.ui.home.feedetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Fee
import ma.n1akai.edusync.data.models.TestOnline
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.ExamsListItemBinding
import ma.n1akai.edusync.databinding.FeesListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.ui.home.examination.ExaminationAdapter
import ma.n1akai.edusync.util.hide
import ma.n1akai.edusync.util.show
import ma.n1akai.edusync.util.stringToDate
import java.text.SimpleDateFormat

class FeeAdapter : RecyclerView.Adapter<FeeAdapter.MyFeeViewHolder>() {

    private lateinit var context: Context

    sealed class MyFeeViewHolder(
        binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        class TitleViewHolder(private val binding: TitleItemBinding) :
            MyFeeViewHolder(binding) {
            fun bind(title: Title) {
                binding.let {
                    it.title.text = title.title
                    it.title.setCompoundDrawablesWithIntrinsicBounds(title.icon, 0, 0, 0)
                }
            }
        }

         class FeeViewHolder(private val binding: FeesListItemBinding) :
            MyFeeViewHolder(binding) {
             fun bind(fee: Fee, context: Context) {
                 binding.apply {
                     feeDescription.text = fee.fee_description
                     feeTotalFee.text = context.getString(R.string.total_fee, fee.total_fee.toString())
                     if (fee.is_paid == 0) {
                         feeIsPaid.text = context.getString(R.string.unpaid)
                         feeIsPaid.background = AppCompatResources.getDrawable(context, R.drawable.unpaid_badge)
                     } else {
                         feeIsPaid.text = context.getString(R.string.paid)
                         feeIsPaid.background = AppCompatResources.getDrawable(context, R.drawable.paid_badge)
                     }
                     val date = stringToDate(fee.fee_date,"yyyy-MM-dd")
                     val formater = SimpleDateFormat("dd MMMM")

                     feeDate.text = formater.format(date)
                 }
             }
        }
    }

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeeAdapter.MyFeeViewHolder {
        context = parent.context
        return when (viewType) {
            R.layout.title_item -> MyFeeViewHolder.TitleViewHolder(
                TitleItemBinding
                    .inflate(LayoutInflater.from(context), parent, false)
            )

            R.layout.fees_list_item -> {
                MyFeeViewHolder.FeeViewHolder(
                    FeesListItemBinding
                        .inflate(LayoutInflater.from(context), parent, false)
                )
            }

            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FeeAdapter.MyFeeViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is MyFeeViewHolder.TitleViewHolder -> holder.bind(item as Title)
            is MyFeeViewHolder.FeeViewHolder -> holder.bind(item as Fee, context)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Title -> R.layout.title_item
            is Fee -> R.layout.fees_list_item
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }

}