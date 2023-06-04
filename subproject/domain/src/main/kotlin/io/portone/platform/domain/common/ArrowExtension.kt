package io.portone.platform.domain.common

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.raise.EagerEffect
import arrow.core.raise.Effect
import arrow.core.raise.Raise
import arrow.core.raise.effect
import arrow.core.raise.getOrNull
import arrow.core.raise.recover
import arrow.core.toOption

context(Raise<R2>)
suspend fun <R, A, R2> Effect<R, A>.bind(transformError: (R) -> R2): A = mapError(transformError).bind()

fun <R, A, R2> Effect<R, A>.mapError(transform: (R) -> R2): Effect<R2, A> =
    recover { raise(transform(it)) }

fun <R, A, R2> EagerEffect<R, A>.mapError(transform: (R) -> R2): EagerEffect<R2, A> =
    recover { raise(transform(it)) }

fun <R, A, A2> Effect<R, A>.map(transform: (A) -> A2): Effect<R, A2> = effect {
    transform(this@map.bind())
}

fun <R, A> Effect<R, A>.dieOnError(die: (R) -> A): Effect<Nothing, A> =
    recover { die(it) }

suspend fun <R, A> Effect<R, A>.getOrNone(): Option<A> = getOrNull().toOption()

fun <A> Option<A>.getOrThrow(): A = this.getOrNull()!!

fun <A> Option<List<A>>.getOrEmpty(): List<A> = getOrElse { emptyList() }

fun <A> Option<Set<A>>.getOrEmpty(): Set<A> = getOrElse { emptySet() }

fun <K, A> Option<Map<K, A>>.getOrEmpty(): Map<K, A> = getOrElse { emptyMap() }
