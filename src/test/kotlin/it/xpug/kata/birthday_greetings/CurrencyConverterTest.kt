package it.xpug.kata.birthday_greetings

import it.xpug.kata.birthday_greetings.Currency.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyConverterTest {
    @Test
    fun `converting EUR to USD`() {
        // given
        val givenAmount = MonetaryAmount(10.toBigDecimal(), EUR)

        // when
        val result: MonetaryAmount = givenAmount.convert(USD, ForexRates())

        // then
        val expectedAmount = MonetaryAmount(11.33.toBigDecimal(), USD)
        assertThat(result).isEqualTo(expectedAmount)
    }

    @Test
    fun `converting USD to EUR`() {
        // given
        val givenAmount = MonetaryAmount(10.toBigDecimal(), USD)
        val givenForexRates = ForexRates()

        // when
        val result: MonetaryAmount = givenAmount.convert(EUR, givenForexRates)

        // then
        val expectedAmount = MonetaryAmount(8.83.toBigDecimal(), EUR)
        assertThat(result).isEqualTo(expectedAmount)
    }

    @Test
    fun `converting USD to JPY`() {
        // given
        val givenAmount = MonetaryAmount(1.toBigDecimal(), USD)
        val givenForexRates = ForexRates()

        // when
        val result: MonetaryAmount = givenAmount.convert(JPY, givenForexRates)

        // then
        val expectedAmount = MonetaryAmount(165.toBigDecimal(), JPY)
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
        assertThat(result).isEqualTo(ForexRate(0.883, 2))
    }


}

data class MonetaryAmount(val amount: BigDecimal, val currency: Currency) {
    fun convert(
        targetCurrency: Currency,
        forexRates: ForexRates
    ): MonetaryAmount {
        val forexRate: ForexRate = forexRates.findRate(targetCurrency)
        val convertedAmountInCents = forexRate
            .convert(amount)

        return MonetaryAmount(convertedAmountInCents, targetCurrency)
    }
}

class ForexRates {
    fun findRate(targetCurrency: Currency): ForexRate {
        return rates.get(targetCurrency)!!
    }

    val rates = mutableMapOf(
        USD to ForexRate(1.133, 2),
        EUR to ForexRate(0.883, 2),
        JPY to ForexRate(165.21, 0),
    )

}

data class ForexRate(val rate: Double, val scale: Int) {
    fun convert(amountInCents: BigDecimal): BigDecimal {
        return amountInCents.times(BigDecimal(rate))
            .setScale(scale, RoundingMode.HALF_UP)
    }
}

enum class Currency {
    EUR,
    USD,
    JPY
}
