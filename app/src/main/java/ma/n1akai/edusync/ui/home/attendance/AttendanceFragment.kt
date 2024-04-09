package ma.n1akai.edusync.ui.home.attendance

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Attendance
import ma.n1akai.edusync.databinding.FragmentAttendanceBinding
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class AttendanceFragment : Fragment() {

    private val viewModel: AttendanceViewModel by viewModels()
    private var _binding: FragmentAttendanceBinding? = null
    private val binding: FragmentAttendanceBinding get() = _binding!!
    private val attendanceAdapter: AttendanceAdapter = AttendanceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.attendanceRcAttendance.apply {
            setHasFixedSize(true)
            adapter = attendanceAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
        binding.apply {
            viewModel.attendances.observe(viewLifecycleOwner) {
                when(it) {
                    is UiState.Failure -> {
                        attendanceProgress.hide()
                        root.snackbar(it.error!!)
                    }
                    is UiState.Loading -> attendanceProgress.show()
                    is UiState.Success -> {
                        attendanceProgress.hide()
                        attendanceAdapter.items = it.data
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}