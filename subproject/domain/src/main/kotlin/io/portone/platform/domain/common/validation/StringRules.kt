package io.portone.platform.domain.common.validation

typealias StringValidator = Validator<String>
typealias StringRule = Rule<String>

object StringRules {
    object NonEmpty : StringRule({ it.isNotEmpty() })

    fun StringValidator.nonEmpty() = with(NonEmpty)

    data class MinLength(val min: Int) : StringRule({ it.length >= min })

    fun StringValidator.minLength(min: Int) = with(MinLength(min))

    data class MaxLength(val max: Int) : StringRule({ it.length <= max })

    fun StringValidator.maxLength(max: Int) = with(MaxLength(max))

    data class Allow(val options: AllowOptions) : StringRule({ value ->
        value.all { options.characters.contains(it) }
    })

    data class AllowOptions(
        val lower: Boolean,
        val upper: Boolean,
        val number: Boolean,
        val specials: String = ""
    ) {

        val characters = listOf(
            lower to 'a'..'z',
            upper to 'A'..'Z',
            number to '0'..'9'
        )
            .filter { it.first }
            .flatMap { it.second.toList() }
            .toList() + specials.toList()

        companion object {
            val UpperAlphabets = AllowOptions(lower = false, upper = true, number = false)

            val Alphabets = AllowOptions(lower = true, upper = true, number = false)

            val AlphabetsDigits = AllowOptions(lower = true, upper = true, number = true)

            val Digits = AllowOptions(lower = false, upper = false, number = true)
        }
    }

    fun StringValidator.allow(options: AllowOptions) = with(Allow(options))

    data class RegexPattern(val pattern: String) : StringRule({ Regex(pattern).matches(it) })

    fun StringValidator.regex(pattern: String) = with(RegexPattern(pattern))

    data class Prefix(val prefix: String) : StringRule({ it.startsWith(prefix) })

    fun StringValidator.prefix(prefix: String) = with(Prefix(prefix))

    data class Suffix(val suffix: String) : StringRule({ it.endsWith(suffix) })

    fun StringValidator.suffix(suffix: String) = with(Suffix(suffix))
}
