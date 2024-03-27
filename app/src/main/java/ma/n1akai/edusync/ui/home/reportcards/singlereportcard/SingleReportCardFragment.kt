package ma.n1akai.edusync.ui.home.reportcards.singlereportcard

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ma.n1akai.edusync.R

class SingleReportCardFragment : Fragment() {

    companion object {
        fun newInstance() = SingleReportCardFragment()
    }

    private val viewModel: SingleReportCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_single_report_card, container, false)
    }
}