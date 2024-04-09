package ma.n1akai.edusync.ui.home.attendance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Attendance
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.AttendancePerMonthListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding

class AttendanceAdapter : RecyclerView.Adapter<AttendanceHolder>() {

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceHolder {
        return when (viewType) {
            R.layout.title_item -> AttendanceHolder
                .TitleViewHolder(
                    TitleItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )

            R.layout.attendance_per_month_list_item -> {
                AttendanceHolder
                    .AttendanceViewHolder(
                        AttendancePerMonthListItemBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                        )
                    )

            }

            else -> throw IllegalArgumentException("Invalid Type")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: AttendanceHolder, position: Int) {
        val item = items[position]
        when(holder) {
            is AttendanceHolder.AttendanceViewHolder -> holder.bind(item as Attendance)
            is AttendanceHolder.TitleViewHolder -> holder.bind(item as Title)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Title -> R.layout.title_item
            is Attendance -> R.layout.attendance_per_month_list_item
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }
}