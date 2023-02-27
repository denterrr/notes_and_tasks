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

            binding.root.context.apply {
                val time: String = getTime(note.time.toDate())
                binding.tvDescription.text =
                    if (note.description.isNotEmpty()) {
                        getString(R.string.note_description)
                            .format(time, note.description.lines().first())
                    } else getString(R.string.note_description_only_time).format(time)
                binding.tvTitle.text = note.title.ifEmpty { getString(R.string.untitled) }

            }

            binding.root.setOnClickListener {
                if (selectIsEnabled) {
                    onCheckListener?.invoke(note.id)
                    draw(note, binding)
                } else {
                    onClickListener?.invoke(note.id)
                }
            }

            binding.root.setOnLongClickListener {
                onCheckListener?.invoke(note.id)
                draw(note, binding)
                true
            }
            binding.root.isChecked = note.isChecked

        }

        private fun getTime(noteTimePair: Pair<Int, String>): String =
            with(binding.root.context) {
                when (noteTimePair.first) {
                    RIGHT_NOW -> {
                        getString(R.string.right_now)
                    }
                    MINUTES -> {
                        val minutes = noteTimePair.second
                        if (minutes.last() == '1' && minutes != "11") getString(R.string.one_minute_ago)
                            .format(minutes)
                        else if (minutes.last().digitToInt() in 2..4
                            && minutes.first() != '1'
                        ) getString(R.string.rus_addition_minutes_ago).format(minutes)
                        else getString(R.string.minutes_ago).format(minutes)
                    }
                    HOURS -> {
                        val hours = noteTimePair.second
                        if (hours.last() == '1' && hours != "11") getString(R.string.one_hour_ago)
                            .format(hours)
                        else if (hours.last().digitToInt() in 2..4
                            && hours.first() != '1'
                        ) getString(R.string.hours_ago).format(hours)
                        else getString(R.string.rus_addition_hours_ago).format(hours)
                    }
                    YESTERDAY -> {
                        getString(R.string.yesterday)
                    }
                    else -> {
                        noteTimePair.second
                    }
                }
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
        const val MINUTES = 1
        const val HOURS = 2
        const val YESTERDAY = 3
        const val DAY_OF_WEEK = 4
        const val FULL_DATE = 5
    }
}