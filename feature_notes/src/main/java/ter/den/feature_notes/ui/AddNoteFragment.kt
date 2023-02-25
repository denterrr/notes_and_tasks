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
import com.google.android.material.snackbar.Snackbar
import ter.den.core.domain.extensions.showSoftKeyboard
import ter.den.core.domain.model.CustomThrowable
import ter.den.core.presentation.ViewModelFactory
import ter.den.feature_notes.databinding.FragmentAddNoteBinding
import ter.den.feature_notes.di.NoteComponentViewModel
import ter.den.feature_notes.domain.model.Note
import ter.den.feature_notes.presentation.NoteViewModel
import java.util.*
import javax.inject.Inject

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding
        get() = _binding ?: throw CustomThrowable.BindingNull

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: NoteViewModel
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
    }

    private fun parseParams() {
        val noteId = arguments?.getLong(NOTE_ID_KEY)
        if (noteId == null) {
            binding.etTitle.requestFocus()
            binding.etTitle.isFocusableInTouchMode = true
            requireActivity().showSoftKeyboard()
        } else {
//            viewModel.set(noteId)
        }
    }

    private fun initClickListeners() {
        binding.toolBar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.toolBar.setOnMenuItemClickListener {
            viewModel.insert(
                Note(
                    0,
                    binding.etTitle.text.toString().trim(),
                    binding.etDesc.text.toString().trim(),
                    System.currentTimeMillis()
                )
            )
            val calendar = Calendar.getInstance()
            Snackbar.make(
                binding.root,
                calendar.get(Calendar.DAY_OF_WEEK).toString(),
                Snackbar.LENGTH_SHORT
            )
                .show()
            false
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