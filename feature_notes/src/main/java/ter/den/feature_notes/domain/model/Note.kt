package ter.den.feature_notes.domain.model

data class Note(
    val id: Long = 0,
    val title: String,
    val description: String,
    val time: Long,
    var isChecked: Boolean = false,
)