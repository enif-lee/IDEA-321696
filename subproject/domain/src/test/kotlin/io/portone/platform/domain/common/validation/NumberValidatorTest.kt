package io.portone.platform.domain.common.validation

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FunSpec
import io.portone.platform.domain.common.validation.NumberRules.greaterThan
import io.portone.platform.domain.common.validation.NumberRules.greaterThanOrEqual
import io.portone.platform.domain.common.validation.NumberRules.inRange
import io.portone.platform.domain.common.validation.NumberRules.lessThan
import io.portone.platform.domain.common.validation.NumberRules.lessThanOrEqual
import io.portone.platform.domain.common.validation.NumberRules.negative
import io.portone.platform.domain.common.validation.NumberRules.positive

class NumberValidatorTest : FunSpec({
    val validator = Validator.Int

    test("InRange") {
        val inRange = validator.inRange(1, 2)
        inRange.validate(0).shouldBeLeft()
        inRange.validate(1).shouldBeRight()
        inRange.validate(1).shouldBeRight()
    }

    test("Negative") {
        val negative = validator.negative()

        negative.validate(-1).shouldBeRight()
        negative.validate(1).shouldBeLeft()
    }

    test("Positive") {
        val positive = validator.positive()

        positive.validate(1).shouldBeRight()
        positive.validate(-1).shouldBeLeft()
    }

    test("GreaterThan") {
        val greaterThan = validator.greaterThan(10)

        greaterThan.validate(15).shouldBeRight()
        greaterThan.validate(5).shouldBeLeft()
    }

    test("LessThan") {
        val lessThan = validator.lessThan(10)
        lessThan.validate(5).shouldBeRight()
        lessThan.validate(15).shouldBeLeft()
    }

    test("GreaterThanOrEqual") {
        val greaterThanOrEqual = validator.greaterThanOrEqual(10)

        greaterThanOrEqual.validate(15).shouldBeRight()
        greaterThanOrEqual.validate(10).shouldBeRight()
        greaterThanOrEqual.validate(5).shouldBeLeft()
    }

    test("LessThanOrEqual") {
        val lessThanOrEqual = validator.lessThanOrEqual(10)

        lessThanOrEqual.validate(5).shouldBeRight()
        lessThanOrEqual.validate(10).shouldBeRight()
        lessThanOrEqual.validate(15).shouldBeLeft()
    }
})
