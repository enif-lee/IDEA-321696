package io.portone.platform.domain.common.newtype

import io.portone.platform.domain.common.validation.Validator

abstract class NewType<Value, T>(val unsafeMake: (Value) -> T, setRules: Validator<Value>.() -> Validator<Value>) {
    private val validator: Validator<Value> = setRules(Validator<Value>())

    fun new(value: Value) = validator.validate(value).map(unsafeMake)
}

typealias IntNewType<T> = NewType<Int, T>

typealias LongNewType<T> = NewType<Long, T>

typealias StringNewType<T> = NewType<String, T>
