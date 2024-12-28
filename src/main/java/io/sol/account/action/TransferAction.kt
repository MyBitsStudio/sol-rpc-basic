package io.sol.account.action

abstract class TransferAction(private val BASE: String, private val TYPE: String) : Action {
    override fun execute(): String? {
        return when (BASE) {
            "64" -> when (TYPE) {
                "legacy" -> send64Legacy()
                "raw" -> send64Raw()
                else -> throw IllegalStateException("Unexpected value: $TYPE")
            }

            "58" -> when (TYPE) {
                "legacy" -> send58Legacy()
                "raw" -> send58Raw()
                else -> throw IllegalStateException("Unexpected value: $TYPE")
            }

            else -> throw IllegalStateException("Unexpected value: $BASE")
        }
    }

    abstract fun send64Legacy(): String?
    abstract fun send58Legacy(): String?
    abstract fun send64Raw(): String?
    abstract fun send58Raw(): String?
}
