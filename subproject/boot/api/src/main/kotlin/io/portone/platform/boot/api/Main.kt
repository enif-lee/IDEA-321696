package io.portone.platform.boot.api

import arrow.core.raise.Effect
import arrow.core.raise.effect

object BootApplication {
    fun test(): Effect<Nothing, String> = effect {
        "lsdkjfsdklj"
    }
}


fun main(args: Array<String>) {
    println("*** server shut down")
}
