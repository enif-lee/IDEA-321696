package io.portone.platform.domain.common.util

import java.time.Instant
import java.time.ZoneOffset

fun Instant.toUtcOffset() = this.atOffset(ZoneOffset.UTC)!!
