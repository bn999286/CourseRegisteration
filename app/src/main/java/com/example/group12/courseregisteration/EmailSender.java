package com.example.group12.courseregisteration;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;


public class EmailSender {


    private static Message msg;

    // constructor
    public EmailSender(){

    }

    // send email method
    public static void sendEmail(String receiver_email, String subject, String message){

        //set sender's information
        final String sender_username = "course.register2018@gmail.com";
        final String sender_password ="XAB-6QG-kFV-Kwa";
        String sender_email = "course.register2018@gmail.com";

        //set properities
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // set the session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            //verify sender's username and password
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(sender_username, sender_password);

            }

        });


        try {

            // set  default MimeMessage object.
            msg = new MimeMessage(session);

            // header field of the header.
            msg.setFrom(new InternetAddress(sender_email));

            // set to: header field of the header.
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver_email));

            // set Subject: header field
            msg.setSubject(subject);

            // create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // set the actual message
            messageBodyPart.setContent(message, "text/html");

            // create a multipart message
            Multipart multipart = new MimeMultipart();
            // set text message part
            multipart.addBodyPart(messageBodyPart);


            // send the final combined message parts
            msg.setContent(multipart);

            //run the sending process
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    try  {

                        //Send the message
                        Transport.send(msg);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }});
                    thread.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}




