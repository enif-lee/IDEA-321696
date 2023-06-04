package io.portone.platform.domain.common

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

inline infix fun <A> Boolean.then(f: () -> A): Option<A> = if (this) Some(f()) else None

infix fun <A> Boolean.then(value: A): Option<A> = if (this) Some(value) else None
