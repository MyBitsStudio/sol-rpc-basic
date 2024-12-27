package io.sol.types

import lombok.Getter

@Getter
enum class Commitment(private val value: String) {
    FINALIZED("finalized"),
    CONFIRMED("confirmed"),
    PROCESSED("processed")
}
