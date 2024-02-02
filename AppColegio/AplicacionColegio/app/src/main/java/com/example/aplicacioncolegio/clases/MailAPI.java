package com.example.aplicacioncolegio.clases;

import android.content.Context;
import android.os.AsyncTask;


import androidx.loader.content.AsyncTaskLoader;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailAPI extends AsyncTask<Void, Void, Void> {
    private Context context;
    private Session session;
    private String main, subject, message;

    public MailAPI(Context context, Session session, String main, String subject, String message) {
        this.context = context;
        this.session = session;
        this.main = main;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties= new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        session= Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(Utils.email,Utils.password);
            }

        });
        MimeMessage mimeMessage= new MimeMessage(session);
        try{
            mimeMessage.setFrom(new InternetAddress(Utils.email));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(main)));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
return null;
    }
}