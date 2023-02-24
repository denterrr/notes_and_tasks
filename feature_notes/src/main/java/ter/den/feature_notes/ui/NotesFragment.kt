package ter.den.feature_notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ter.den.core.domain.model.CustomThrowable
import ter.den.feature_notes.R
import ter.den.feature_notes.databinding.FragmentNotesBinding
import ter.den.feature_notes.presentation.NoteAdapter


class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding
        get() = _binding ?: throw CustomThrowable.BindingNull

    private val notesAdapter by lazy {
        NoteAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentNotesBinding.inflate(layoutInflater)
        binding.tvNotesCount.text = "32 notes"
        binding.rvNotes.adapter = notesAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}