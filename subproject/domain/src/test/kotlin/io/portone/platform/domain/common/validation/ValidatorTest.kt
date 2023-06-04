package io.portone.platform.domain.common.validation

import arrow.core.nonEmptyListOf
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FunSpec
import io.portone.platform.domain.common.validation.StringRules.minLength
import io.portone.platform.domain.common.validation.StringRules.nonEmpty

class ValidatorTest : FunSpec({

    test("Validator multiple invalid rules test") {
        val stringValidator = Validator.String.nonEmpty().minLength(1)

        stringValidator.validate("").shouldBeLeft(
            nonEmptyListOf(
                StringRules.NonEmpty,
                StringRules.MinLength(1)
            )
        )
    }

    test("Validator partial invalid rule test") {
        val stringValidator = Validator.String.nonEmpty().minLength(10)

        stringValidator
            .validate("123")
            .shouldBeLeft(nonEmptyListOf(StringRules.MinLength(10)))
    }

    test("Validator valid result test") {
        val stringValidator = Validator.String.nonEmpty()

        stringValidator.validate("123").shouldBeRight("123")
    }
})
