package it.xpug.kata.birthday_greetings

import it.xpug.kata.birthday_greetings.Currency.EUR
import it.xpug.kata.birthday_greetings.Currency.USD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CurrencyConverterTest {
    @Test
    fun `converting EUR to USD`() {
        // given
        val givenAmount = MonetaryAmount(1000, EUR)

        // when
        val result: MonetaryAmount = givenAmount.convert(USD, ForexRates())

        // then
        val expectedAmount = MonetaryAmount(1133, USD)
        assertThat(result).isEqualTo(expectedAmount)
    }

    @Test
    fun `converting USD to EUR`() {
        // given
        val givenAmount = MonetaryAmount(1000, USD)
        val givenForexRates = ForexRates()

        // when
        val result: MonetaryAmount = givenAmount.convert(EUR, givenForexRates)

        // then
        val expectedAmount = MonetaryAmount(883, EUR)
        assertThat(result).isEqualTo(expectedAmount)
    }

    @Test
    fun `finding correct ForexRate for EUR`() {
        // given
        val forexRates = ForexRates()
        val targetCurrency = EUR

        // when
        val result = forexRates.findRate(targetCurrency)

        // then
        assertThat(result).isEqualTo(ForexRate(0.883))
    }


}

data class MonetaryAmount(val amountInCents: Int, val currency: Currency) {
    fun convert(
        targetCurrency: Currency,
        forexRates: ForexRates
    ): MonetaryAmount {
        val forexRate: ForexRate = forexRates.findRate(targetCurrency)
        val convertedAmountInCents = forexRate.convert(amountInCents)
        
        return MonetaryAmount(convertedAmountInCents, targetCurrency)
    }
}

class ForexRates {
    fun findRate(targetCurrency: Currency): ForexRate {
        return rates.get(targetCurrency)!!
    }

    val rates = mutableMapOf(
        USD to ForexRate(1.133),
        EUR to ForexRate(0.883),
    )

}

data class ForexRate(val rate: Double) {
    fun convert(amountInCents: Int): Int {
        return (amountInCents * rate).toInt()
    }
}

enum class Currency {
    EUR,
    USD
}
