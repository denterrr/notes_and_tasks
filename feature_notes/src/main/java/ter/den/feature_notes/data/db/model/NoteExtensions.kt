package ter.den.feature_notes.data.db.model

import ter.den.feature_notes.domain.model.Note

fun NoteDB.toNote(): Note = Note(
    this.id,
    this.title,
    this.description,
    this.time
)

fun Note.toNoteDB(): NoteDB = NoteDB(
    id = this.id,
    title = this.title,
    description = this.description,
    time = this.time
)

fun List<NoteDB>.toNotes() = this.map { it.toNote() }