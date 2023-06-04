package io.portone.platform.domain.common.validation

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FunSpec
import io.portone.platform.domain.common.validation.CollectionRules.maxSize
import io.portone.platform.domain.common.validation.CollectionRules.minSize

class CollectionRuleValidatorTest : FunSpec({
    val validator = Validator.collection<Any>()

    test("MinSize") {
        val minSize = validator.minSize(1)
        minSize.validate(listOf(1)).shouldBeRight()
        minSize.validate(emptyList()).shouldBeLeft()
    }

    test("MaxSize") {
        val maxSize = validator.maxSize(2)

        maxSize.validate((1..2).toList()).shouldBeRight()
        maxSize.validate((1..4).toList()).shouldBeLeft()
    }
})
