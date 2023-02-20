package ter.den.notesandtasks.domain.model

sealed class CustomThrowable : Throwable() {

    object BindingNull : CustomThrowable()

}