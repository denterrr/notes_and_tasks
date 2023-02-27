package ter.den.core.domain.model

sealed class CustomThrowable : Throwable() {

    object BindingNull : CustomThrowable()
    object NoteNull : CustomThrowable()

}