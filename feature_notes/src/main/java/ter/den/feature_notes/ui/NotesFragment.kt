package ter.den.feature_notes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import ter.den.core.domain.extensions.repeatOnCreatedLifecycle
import ter.den.core.domain.extensions.toInvisible
import ter.den.core.domain.extensions.toVisible
import ter.den.core.domain.interfaces.SelectableOperations
import ter.den.core.domain.model.CustomThrowable
import ter.den.core.presentation.ViewModelFactory
import ter.den.feature_notes.R
import ter.den.feature_notes.databinding.FragmentNotesBinding
import ter.den.feature_notes.di.NoteComponentViewModel
import ter.den.feature_notes.presentation.NoteAdapter
import ter.den.feature_notes.presentation.NotesViewModel
import javax.inject.Inject


class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding
        get() = _binding ?: throw CustomThrowable.BindingNull

    private val notesAdapter by lazy {
        NoteAdapter(onClickListener = { id ->
            findNavController().navigate(
                R.id.action_notesFragment_to_addNoteFragment, bundleOf(
                    AddNoteFragment.NOTE_ID_KEY to id
                )
            )
        }, onCheckListener = {
            viewModel.onSelect(it)
        })
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: NotesViewModel
            by viewModels(factoryProducer = { viewModelFactory })


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ViewModelProvider(this).get<NoteComponentViewModel>()
            .noteComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentNotesBinding.inflate(layoutInflater)
        binding.rvNotes.adapter = notesAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        initObservers()
        setUpSelectableOperations()
    }

    private fun setUpSelectableOperations() {
        (requireActivity() as SelectableOperations).onClickDelete {
            showDeleteDialog()
        }

        (requireActivity() as SelectableOperations).onClickSelectAll {
            viewModel.selectAll()
            notesAdapter.selectAll()
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_chosen_notes))
            .setNegativeButton(R.string.no) { _, _ -> }
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteSelected()
            }
            .show()
    }

    private fun initObservers() {
        repeatOnCreatedLifecycle {
            viewModel.notesFlow.collectLatest {
                notesAdapter.submitList(it)
                binding.tvNotesCount.text = getString(R.string.notes_count).format(it.size)
                binding.tvEmpty.isVisible = it.isEmpty()
                binding.rvNotes.isVisible = it.isNotEmpty()
            }
        }
        repeatOnCreatedLifecycle {
            viewModel.selectedListFlow.collectLatest {
                if (it == 0) {
                    binding.fab.toVisible()
                    (requireActivity() as SelectableOperations).hide()
                } else {
                    binding.fab.toInvisible()
                    (requireActivity() as SelectableOperations).show()
                }
            }
        }
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