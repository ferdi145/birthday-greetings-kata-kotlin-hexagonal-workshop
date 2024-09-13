package it.xpug.kata.birthday_greetings

import it.xpug.kata.birthday_greetings.adapter.outbound.EmployeeFileAdapter
import java.util.Properties
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class BirthdayService {
    fun sendGreetings(fileName: String, xDate: XDate, smtpHost: String, smtpPort: Int) {

        val employeeFileAdapter = EmployeeFileAdapter(fileName)

        val employees = employeeFileAdapter.loadEmployees()

        employees.forEach { employee ->
            if (employee.isBirthday(xDate)) {
                val recipient = employee.email
                val body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.firstName!!)
                val subject = "Happy Birthday!"
                // Create a mail session
                val props = Properties()
                props["mail.smtp.host"] = smtpHost
                props["mail.smtp.port"] = "" + smtpPort
                val session = Session.getInstance(props, null)
                // Construct the message
                val msg: Message = MimeMessage(session)
                msg.setFrom(InternetAddress("sender@here.com"))
                msg.setRecipient(Message.RecipientType.TO, InternetAddress(recipient!!))
                msg.subject = subject
                msg.setText(body)
                // Send the message
                Thread.sleep(2000L) // simulate slow dependency
                Transport.send(msg)
            }
        }

    }

}
