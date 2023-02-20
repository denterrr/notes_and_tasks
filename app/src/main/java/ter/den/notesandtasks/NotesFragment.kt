package ter.den.notesandtasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ter.den.notesandtasks.databinding.FragmentNotesBinding
import ter.den.notesandtasks.domain.model.CustomThrowable

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding
        get() = _binding ?: throw CustomThrowable.BindingNull

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentNotesBinding.inflate(layoutInflater)
        binding.tvNotesCount.text = "32 notes"
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