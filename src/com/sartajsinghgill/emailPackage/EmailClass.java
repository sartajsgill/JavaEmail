/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sartajsinghgill.emailPackage;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author SSGILL - DEV
 */
public class EmailClass {
    
    String serverAuthG;
    String serverTtlsG;
    String serverHostG;
    String serverPortG;
    
    String usernameG;
    String passwordG;
    
    Properties propsG;
    
    Session sessionG;
    
    Transport transportG;
    
    String fromMailAddressG;
    String toMailAddressG;
    String emailSubjectG;
    String emailMessageG;
    String emailTypeG;
    
    MimeMessage message;
    
    public EmailClass(){
        propsG=new Properties();
    }
    
    public void connectToServer(String serverAuth,String serverTtls,
            String serverHost,String serverPort,String username,String password){
        
        serverAuthG=serverAuth;
        serverTtlsG=serverTtls;
        serverHostG=serverHost;
        serverPortG=serverPort;
        
        usernameG=username;
        passwordG=password;
        
        propsG.put("mail.smtp.auth", serverAuthG);
        propsG.put("mail.smtp.starttls.enable", serverTtlsG);
        propsG.put("mail.smtp.host", serverHostG);
        propsG.put("mail.smtp.port", serverPortG);
        
        sessionG = Session.getInstance(propsG,
              new javax.mail.Authenticator() {
                  @Override
                  protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(usernameG, passwordG);
                  }
              });
        try{
            transportG=sessionG.getTransport("smtp");
        }
        catch(Exception e){
            
        }
    }
    
    public void sendEmail(String fromMailAddress,String toMailAddress, String emailSubject
            ,String emailMessage, String emailType){
        
        fromMailAddressG=fromMailAddress;
        toMailAddressG=toMailAddress;
        emailSubjectG=emailSubject;
        emailMessageG=emailMessage;
        emailTypeG=emailType;
        
        try {
             message = new MimeMessage(sessionG);             
             message.setFrom(new InternetAddress(fromMailAddressG));
             message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toMailAddressG));                       
             message.setSubject(emailSubjectG);
             
             if(!emailTypeG.isEmpty() && emailType.equalsIgnoreCase("html")){
                 message.setContent(emailMessageG, "text/html; charset=utf-8");
             }
             else if(!emailTypeG.isEmpty() && emailType.equalsIgnoreCase("text")){
                 message.setText(emailMessageG, "utf-8", "html");
             }
             else{
                 message.setText(emailMessageG);
             }
             transportG.send(message);
        } 
        catch (MessagingException e) {
            //  e.printStackTrace();
        }
    }
}
