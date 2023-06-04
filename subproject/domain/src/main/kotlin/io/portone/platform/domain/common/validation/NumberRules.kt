package io.portone.platform.domain.common.validation

object NumberRules {
    data class InRange<T>(val from: T, val to: T) : Rule<T>({ it in from..to }) where T : Number, T : Comparable<T>

    fun <T> Validator<T>.inRange(from: T, to: T) where T : Number, T : Comparable<T> = with(InRange(from, to))

    class Negative<T> : Rule<T>({ it.toFloat() < 0 }) where T : Number, T : Comparable<T>

    fun <T> Validator<T>.negative() where T : Number, T : Comparable<T> = with(Negative())

    class Positive<T> : Rule<T>({ it.toFloat() > 0 }) where T : Number, T : Comparable<T>

    fun <T> Validator<T>.positive() where T : Number, T : Comparable<T> = with(Positive())

    data class GreaterThan<T>(val value: T) : Rule<T>({ value < it }) where T : Number, T : Comparable<T>

    fun <T> Validator<T>.greaterThan(value: T) where T : Number, T : Comparable<T> = with(GreaterThan(value))

    data class GreaterThanOrEqual<T>(val value: T) : Rule<T>({ value <= it }) where T : Number, T : Comparable<T>

    fun <T> Validator<T>.greaterThanOrEqual(value: T) where T : Number, T : Comparable<T> =
        with(GreaterThanOrEqual(value))

    data class LessThan<T>(val value: T) : Rule<T>({ value > it }) where T : Number, T : Comparable<T>

    fun <T> Validator<T>.lessThan(value: T) where T : Number, T : Comparable<T> = with(LessThan(value))

    data class LessThanOrEqual<T>(val value: T) : Rule<T>({ value >= it }) where T : Number, T : Comparable<T>

    fun <T> Validator<T>.lessThanOrEqual(value: T) where T : Number, T : Comparable<T> = with(LessThanOrEqual(value))
}
