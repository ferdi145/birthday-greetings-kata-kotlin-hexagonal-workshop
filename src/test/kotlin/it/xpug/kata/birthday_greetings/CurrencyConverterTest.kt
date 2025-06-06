package it.xpug.kata.birthday_greetings

import it.xpug.kata.birthday_greetings.Currency.EUR
import it.xpug.kata.birthday_greetings.Currency.USD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class CurrencyConverterTest {
    @Test
    fun `converting EUR to USD`() {
        // given
        val givenAmount = MonetaryAmount(1033, EUR)

        // when
        val result: MonetaryAmount = convert(givenAmount, USD)

        // then
        val expectedAmount = MonetaryAmount(1133, USD)
        assertThat(result).isEqualTo(expectedAmount)
    }

    @Disabled
    @Test
    fun `converting USD to EUR`() {
        // given
        val givenAmount = MonetaryAmount(1000, USD)

        // when
        val result: MonetaryAmount = convert(givenAmount, EUR)

        // then
        val expectedAmount = MonetaryAmount(883, EUR)
        assertThat(result).isEqualTo(expectedAmount)
    }

    private fun convert(
        givenAmount: MonetaryAmount,
        targetCurrency: Currency
    ): MonetaryAmount {
//        val forexRate: ForexRate = ForexRates().findRate(targetCurrency)
        return MonetaryAmount(1133, USD)
    }

}

class ForexRates {
    fun findRate(targetCurrency: Currency): ForexRate {
        TODO("Not yet implemented")
    }

    val rates = mutableMapOf<Currency, ForexRate>(
        USD to ForexRate(1.33),
        EUR to ForexRate(1.33),
    )

}

data class ForexRate(val rate: Double)

enum class Currency {
    EUR,
    USD
}

data class MonetaryAmount(val amountInCents: Int, val currency: Currency) {

}
