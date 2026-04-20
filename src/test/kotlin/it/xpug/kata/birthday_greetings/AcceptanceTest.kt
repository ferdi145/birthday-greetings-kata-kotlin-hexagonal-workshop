package it.xpug.kata.birthday_greetings

import com.icegreen.greenmail.junit5.GreenMailExtension
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetupTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class AcceptanceTest {
    companion object {

        @RegisterExtension
        @JvmField
        val mailServer: GreenMailExtension = GreenMailExtension(ServerSetupTest.SMTP)
    }

    private val birthdayService: BirthdayService = BirthdayService()

    @Test
    fun willSendGreetings_whenItsSomebodysBirthday() {
        birthdayService.sendGreetings("employee_data.txt", XDate("2008/10/08"), "localhost", ServerSetupTest.SMTP.port)

        assertThat(mailServer.receivedMessages.size)
            .`as`("message not sent?")
            .isEqualTo(1)

        val message = mailServer.receivedMessages[0]
        assertThat(GreenMailUtil.getBody(message))
            .isEqualTo("Happy Birthday, dear John!")
        assertThat(message.subject)
            .isEqualTo("Happy Birthday!")
        assertThat(message.allRecipients)
            .hasSize(1)
        assertThat(message.allRecipients[0].toString())
            .isEqualTo("john.doe@foobar.com")
    }

    @Test
    fun willNotSendEmailsWhenNobodysBirthday() {
        birthdayService.sendGreetings("employee_data.txt", XDate("2008/01/01"), "localhost",
            ServerSetupTest.SMTP.port)

        assertThat(mailServer.receivedMessages.size)
            .`as`("what? messages?")
            .isZero()
    }
}
