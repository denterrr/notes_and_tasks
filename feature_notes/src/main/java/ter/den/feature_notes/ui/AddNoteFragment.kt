package ter.den.feature_notes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import ter.den.core.domain.extensions.repeatOnCreatedLifecycle
import ter.den.core.domain.extensions.showSoftKeyboard
import ter.den.core.domain.model.CustomThrowable
import ter.den.core.presentation.ViewModelFactory
import ter.den.feature_notes.databinding.FragmentAddNoteBinding
import ter.den.feature_notes.di.NoteComponentViewModel
import ter.den.feature_notes.presentation.AddNoteViewModel
import javax.inject.Inject

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding
        get() = _binding ?: throw CustomThrowable.BindingNull

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: AddNoteViewModel
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
        _binding = FragmentAddNoteBinding.inflate(layoutInflater)
        parseParams()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        initObservers()
    }

    private fun parseParams() {
        val noteId = arguments?.getLong(NOTE_ID_KEY)
        viewModel.setNote(noteId)
    }

    private fun initClickListeners() {
        binding.etTitle.setOnFocusChangeListener { _, _ ->
            binding.toolBar.menu.getItem(0).isVisible = true
        }
        binding.etDesc.setOnFocusChangeListener { _, _ ->
            binding.toolBar.menu.getItem(0).isVisible = true
        }
        binding.toolBar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.toolBar.setOnMenuItemClickListener {
            viewModel.insertNote(
                binding.etTitle.text.toString().trim(),
                binding.etDesc.text.toString().trim(),
                System.currentTimeMillis()
            )
            findNavController().popBackStack()
            false
        }
    }

    private fun initObservers() {
        repeatOnCreatedLifecycle {
            viewModel.note.collectLatest {
                if (it == null) {
                    binding.etTitle.requestFocus()
                    binding.etTitle.isFocusableInTouchMode = true
                    requireActivity().showSoftKeyboard()
                } else {
                    binding.etTitle.setText(it.title)
                    binding.etDesc.setText(it.description)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    companion object {
        const val NOTE_ID_KEY = "NOTE_ID_KEY"
    }
}