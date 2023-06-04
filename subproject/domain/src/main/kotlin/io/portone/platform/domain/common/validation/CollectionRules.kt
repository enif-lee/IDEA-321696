package io.portone.platform.domain.common.validation

import io.portone.platform.domain.common.newtype.NewType

typealias ListNewType<T, R> = NewType<List<T>, R>
typealias SetNewType<T, R> = NewType<Set<T>, R>

@JvmName("unsafeMakeList")
fun <T, R> ListNewType<T, R>.unsafeMake(vararg items: T) = unsafeMake(items.toList())

fun <T, R> SetNewType<T, R>.unsafeMake(items: List<T>): R = unsafeMake(items.toSet())

fun <T, R> SetNewType<T, R>.unsafeMake(vararg items: T): R = unsafeMake(items.toSet())

object CollectionRules {
    fun <C : Collection<*>> Validator<C>.minSize(min: Int) = with(MinSizeRule(min))

    data class MinSizeRule<C : Collection<*>>(val min: Int) : Rule<C>({ it.size >= min })

    fun <C : Collection<*>> Validator<C>.maxSize(max: Int) = with(MaxSizeRule(max))

    data class MaxSizeRule<C : Collection<*>>(val max: Int) : Rule<C>({ it.size <= max })
}
