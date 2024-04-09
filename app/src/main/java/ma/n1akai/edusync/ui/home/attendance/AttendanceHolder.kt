package ma.n1akai.edusync.ui.home.attendance

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ma.n1akai.edusync.data.models.Attendance
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.AttendancePerMonthListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.util.monthName

sealed class AttendanceHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(
        private val binding: TitleItemBinding
    ) : AttendanceHolder(binding) {
        fun bind(item: Title) {
            binding.title.apply {
                text = item.title
                setCompoundDrawablesWithIntrinsicBounds(item.icon, 0, 0, 0)
            }
        }
    }

    class AttendanceViewHolder(
        private val binding: AttendancePerMonthListItemBinding
    ) : AttendanceHolder(binding) {
        fun bind(attendance: Attendance) {
            binding.apply {
                month.text = monthName(attendance.month)
                totalPresent.text = attendance.total_present.toString()
                totalAbsent.text = attendance.total_absent.toString()
                totalLeave.text = attendance.total_leave.toString()
            }
        }
    }

}