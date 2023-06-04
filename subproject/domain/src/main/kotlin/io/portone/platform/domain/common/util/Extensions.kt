package io.portone.platform.domain.common.util

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

inline fun <T> T.runIf(condition: Boolean, block: T.() -> T): T = if (condition) block() else this
inline fun <T, R> T.runIfSome(option: Option<R>, block: T.(R) -> T): T = when (option) {
    None -> this
    is Some -> block(option.value)
}
