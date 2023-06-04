package io.portone.platform.domain.common.validation

import arrow.core.EitherNel
import arrow.core.filterOption
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import arrow.core.toNonEmptyListOrNone

data class Validator<T>(val rules: List<Rule<T>> = emptyList()) {

    fun with(rule: Rule<T>): Validator<T> = this.copy(rules = rules + rule)

    fun validate(value: T): EitherNel<Rule<T>, T> = rules
        .map { it.validate(value).swap().getOrNone() }
        .filterOption()
        .toNonEmptyListOrNone()
        .map { it.left() }
        .getOrElse { value.right() }

    companion object {
        val Int = Validator<Int>()
        val Double = Validator<Double>()
        val Float = Validator<Float>()
        val Long = Validator<Long>()
        val Byte = Validator<Byte>()
        val String = Validator<String>()
        val Short = Validator<Short>()
        fun <T> collection() = Validator<Collection<T>>()

        // TODO support object validator
    }
}
