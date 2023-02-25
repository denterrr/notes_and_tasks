package ter.den.feature_notes.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ter.den.feature_notes.R
import ter.den.feature_notes.databinding.ItemNoteBinding
import ter.den.feature_notes.domain.extensions.toDate
import ter.den.feature_notes.domain.model.Note


class NoteAdapter constructor(
    private val onClickListener: ((Long) -> Unit)? = null,
    private val onCheckListener: ((Long) -> Unit)? = null,
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var list = emptyList<Note>()
    var selectIsEnabled = false

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {

            val noteTimePair = note.time.toDate()
            binding.root.context.apply {
                val time: String = when (noteTimePair.first) {
                    RIGHT_NOW -> {
                        getString(R.string.right_now)
                    }
                    MINUTE_AGO -> {
                        getString(R.string.one_minute_ago)
                    }
                    ANY_MINUTES_AGO -> {
                        getString(R.string.minutes_ago).format(noteTimePair.second)
                    }
                    HOUR_AGO -> {
                        getString(R.string.one_hour_ago)
                    }
                    ANY_HOURS_AGO -> {
                        getString(R.string.hours_ago).format(noteTimePair.second)
                    }
                    YESTERDAY -> {
                        getString(R.string.yesterday)
                    }
                    else -> {
                        noteTimePair.second
                    }
                }
                binding.tvDescription.text =
                    if (note.description.isNotEmpty()) {
                        getString(R.string.note_description)
                            .format(time, note.description.lines().first())
                    } else getString(R.string.note_description_only_time).format(time)
                binding.tvTitle.text = note.title.ifEmpty { getString(R.string.untitled) }

            }

            binding.root.setOnClickListener {
                onClickListener?.invoke(note.id)
                if (selectIsEnabled) {
                    onCheckListener?.invoke(note.id)
                    draw(note, binding)
                }
            }

            binding.root.setOnLongClickListener {
                onCheckListener?.invoke(note.id)
                draw(note, binding)
                true
            }
            binding.root.isChecked = note.isChecked

        }

    }

    private fun draw(note: Note, binding: ItemNoteBinding) {
        binding.root.isChecked = !note.isChecked
        note.isChecked = !note.isChecked
        selectIsEnabled = list.any { it.isChecked }
        if (binding.root.isChecked) binding.tvTitle
            .setPadding(0, 0, 30, 0)
        else binding.tvTitle.setPadding(0, 0, 0, 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = list[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = list.size


    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Note>) {
        val diffResult = DiffUtil.calculateDiff(ItemDiff(list, newList))
        list = newList.apply {
            list.forEach { a -> this.find { it.id == a.id }?.isChecked = a.isChecked }
        }
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    class ItemDiff(private val oldList: List<Note>, private val newList: List<Note>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    companion object {
        const val RIGHT_NOW = 0
        const val MINUTE_AGO = 1
        const val ANY_MINUTES_AGO = 2
        const val HOUR_AGO = 3
        const val ANY_HOURS_AGO = 4
        const val YESTERDAY = 5
        const val DAY_OF_WEEK = 6
        const val JUST_DATE = 7
    }
}