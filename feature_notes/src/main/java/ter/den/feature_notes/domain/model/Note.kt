package ter.den.feature_notes.domain.model

data class Note(
    val title: String,
    val description: String,
    val time: Long,
    val id: Long = 0,
    var isChecked: Boolean = false,
)