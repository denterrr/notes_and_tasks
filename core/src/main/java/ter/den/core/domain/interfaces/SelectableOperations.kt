package ter.den.core.domain.interfaces

interface SelectableOperations {
    fun hide()
    fun show()
    fun onClickDelete(callBack: (() -> Unit))
    fun onClickSelectAll(callBack: (() -> Unit))
}