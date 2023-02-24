package ter.den.feature_tasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ter.den.core.domain.model.CustomThrowable
import ter.den.feature_tasks.R
import ter.den.feature_tasks.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding
        get() = _binding ?: throw CustomThrowable.BindingNull

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentTasksBinding.inflate(layoutInflater)
        binding.tvNotesCount.text = getString(R.string.tasks_count).format(5)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }

    private fun initClickListeners() {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}