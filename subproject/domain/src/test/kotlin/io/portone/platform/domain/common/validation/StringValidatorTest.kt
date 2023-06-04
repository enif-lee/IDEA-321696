package io.portone.platform.domain.common.validation

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FunSpec
import io.portone.platform.domain.common.validation.StringRules.allow
import io.portone.platform.domain.common.validation.StringRules.maxLength
import io.portone.platform.domain.common.validation.StringRules.minLength
import io.portone.platform.domain.common.validation.StringRules.nonEmpty
import io.portone.platform.domain.common.validation.StringRules.regex

class StringValidatorTest : FunSpec({

    val validator = StringValidator()

    test("NonEmpty") {
        val nonEmpty = validator.nonEmpty()
        nonEmpty.validate("").shouldBeLeft()
        nonEmpty.validate("NON_EMPTY").shouldBeRight()
    }

    test("MinLength") {
        val minLength = validator.minLength(10)
        minLength.validate("12345").shouldBeLeft()
        minLength.validate("1234512345").shouldBeRight()
    }

    test("MaxLength") {
        val maxLength = validator.maxLength(5)
        maxLength.validate("123456").shouldBeLeft()
        maxLength.validate("12345").shouldBeRight()
    }

    test("Complex condition") {
        val complex = validator.nonEmpty().maxLength(5)

        complex.validate("").shouldBeLeft()
        complex.validate("123456").shouldBeLeft()
        complex.validate("VALID").shouldBeRight()
    }

    test("Allow - Alphabets") {
        val alphabetAllow = validator.allow(StringRules.AllowOptions.Alphabets)

        alphabetAllow.validate("AaBbCc").shouldBeRight()
        alphabetAllow.validate("123AaBbCc123").shouldBeLeft()
        alphabetAllow.validate("!").shouldBeLeft()
    }

    test("Allow - AlphabetsDigits") {
        val alphabetAllow = validator.allow(StringRules.AllowOptions.AlphabetsDigits)

        alphabetAllow.validate("AaBbCc").shouldBeRight()
        alphabetAllow.validate("AaBbCc123").shouldBeRight()
        alphabetAllow.validate("!").shouldBeLeft()
    }

    test("Allow - Special") {
        val special = validator.allow(
            StringRules.AllowOptions(
                lower = false,
                upper = false,
                number = false,
                specials = "!\\_-+."
            )
        )

        special.validate("AaBbCc").shouldBeLeft()
        special.validate("AaBbCc123").shouldBeLeft()
        special.validate("$").shouldBeLeft()
        special.validate("!").shouldBeRight()
        special.validate("\\").shouldBeRight()
        special.validate("_-+").shouldBeRight()
    }

    test("Regex") {
        val regex = validator.regex("^[!@#]+$")

        regex.validate("!!@#").shouldBeRight()
        regex.validate("hello!").shouldBeLeft()
    }
})
