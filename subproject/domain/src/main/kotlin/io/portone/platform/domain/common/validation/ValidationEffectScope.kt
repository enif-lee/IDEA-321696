package io.portone.platform.domain.common.validation

import arrow.core.Nel
import arrow.core.raise.Raise
import arrow.core.identity
import io.portone.platform.domain.common.newtype.NewType
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction0
import kotlin.reflect.KProperty0

abstract class ValidationEffectScope<E, Scope : ValidationEffectScope<E, Scope>>(
    protected val cont: Raise<E>,
    private val paths: List<String>
) : Raise<E> {

    abstract fun Nel<Rule<*>>.toError(fullPath: String): E

    abstract fun withNewPath(paths: List<String>): Scope

    private fun KFunction<*>.path() =
        if (name == "get") {
            name
        } else {
            name.removePrefix("get").let { it[0].lowercase() + it.substring(1) }
        }

    override fun raise(r: E): Nothing = cont.raise(r)

    suspend fun <A, R> KFunction0<A>.validate(f: suspend Scope.(A) -> R): R =
        withNewPath(paths + path()).f(invoke())

    suspend fun <A, R> KFunction0<List<A>>.mapValidate(f: suspend Scope.(A) -> R): List<R> =
        invoke().mapIndexed { index, it ->
            withNewPath(paths + "${path()}[$index]").f(it)
        }

    suspend fun <A, R> KFunction0<List<A>>.mapValidate(newType: NewType<A, R>): List<R> =
        invoke().mapIndexed { index, it -> it.validate(newType, "${path()}[$index]") }

    suspend fun <A, R> KFunction0<List<A>>.mapToSetValidate(newType: NewType<A, R>): Set<R> =
        invoke().mapIndexedTo(mutableSetOf()) { index, it -> it.validate(newType, "${path()}[$index]") }

    suspend fun <A, R> KProperty0<A>.validate(f: suspend Scope.(A) -> R): R =
        withNewPath(paths + name).f(invoke())

    suspend fun <A, R> KProperty0<List<A>>.mapValidate(f: suspend Scope.(A) -> R): List<R> =
        invoke().mapIndexed { index, it ->
            withNewPath(paths + "$name[$index]").f(it)
        }

    suspend fun <A, R> KProperty0<List<A>>.mapValidate(newType: NewType<A, R>): List<R> =
        invoke().mapIndexed { index, it -> it.validate(newType, "$name[$index]") }

    suspend fun <A, R> A.validate(newType: NewType<A, R>, path: String): R =
        newType.new(this)
            .mapLeft { it.toError((paths + path).joinToString(".")) }
            .fold({ raise(it) }, ::identity)

    suspend fun <A, R> KFunction0<A>.validate(newType: NewType<A, R>): R = invoke().validate(newType, path())

    @JvmName("validateList")
    suspend fun <A, R> KFunction0<List<A>>.validate(newType: ListNewType<A, R>): R = invoke().validate(newType, path())

    suspend fun <V, A, R> KFunction0<List<V>>.mapValidate(
        newType: ListNewType<A, R>,
        block: suspend Scope.(V) -> A
    ): R =
        mapValidate(block).validate(newType, path())

    suspend fun <V, A, R> KFunction0<List<V>>.mapValidate(newType: ListNewType<A, R>, itemNewType: NewType<V, A>): R =
        mapValidate(itemNewType).validate(newType, path())

    suspend fun <V, A, R> KFunction0<List<V>>.mapToSetValidate(
        newType: SetNewType<A, R>,
        itemNewType: NewType<V, A>
    ): R =
        mapToSetValidate(itemNewType)
            .validate(newType, path())

    suspend fun <A, R> KProperty0<A>.validate(newType: NewType<A, R>): R = invoke().validate(newType, name)

    suspend fun <V, A, R> KProperty0<List<V>>.mapValidate(
        newType: ListNewType<A, R>,
        block: suspend Scope.(V) -> A
    ): R =
        mapValidate(block).validate(newType, name)

    suspend fun <V, A, R> KProperty0<List<V>>.mapValidate(newType: ListNewType<A, R>, itemNewType: NewType<V, A>): R =
        mapValidate(itemNewType).validate(newType, name)
}
