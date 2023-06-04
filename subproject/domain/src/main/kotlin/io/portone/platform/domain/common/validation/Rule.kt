package io.portone.platform.domain.common.validation

import arrow.core.Either
import arrow.core.left
import arrow.core.right

sealed class Rule<T>(private val condition: (T) -> Boolean) {
    fun validate(value: T): Either<Rule<T>, T> = if (condition(value)) value.right() else this.left()
}
