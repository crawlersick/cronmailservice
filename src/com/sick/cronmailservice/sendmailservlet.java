package com.sick.cronmailservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService.Attachment;
import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;

import netfetch.ChapterItem;
import netfetch.FetchAnyWeb;
import netfetch.FetchWEB;



public class sendmailservlet extends HttpServlet {
	
	
	private static final Logger log = Logger.getLogger(sendmailservlet.class.getName());
	//private static String reg="<td class='vg_table_row_[0-9]+'.+?([0-9a-zA-Z]+).opengw.net.+?UDP: ([0-9]+).+?</span></b></td></tr>";
	private static String reg="<td class='vg_table_row_[0-9]+'><b><span style='font-size: 9pt;'>([0-9a-zA-Z]+).opengw.net.+?UDP: ([0-9]+).+?</span></b></td></tr>";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		  Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);

	        String msgBody = "This is testing";

	        try {
	        	
	        	StringBuilder sd=FetchWEB.getasstring("http://www.vpngate.net/"+"api/iphone/");
	        	
	        	sd.append("sickjohnsisick1122356l112355iaaaoss");
	        	
	        	 FetchAnyWeb faw=new FetchAnyWeb("http://www.vpngate.net/"+"en/",reg);
	        	
	        	 faw.initreq();
                 ArrayList<ChapterItem> mappingforudp=faw.getIndex();

                 for(int i=0;i<mappingforudp.size();i++)
					{
					
					sd.append(mappingforudp.get(i).geturl());
					sd.append(",");
					sd.append(mappingforudp.get(i).getdesc());
					sd.append(",");
					
					}
                // log.log(Level.INFO,"UDP parse list: "+sd.toString());
                  //System.out.println(s);
                 
                byte attachmentbinary[] = compress(sd.toString());
                 
                /*
                String htmlBody="This is a Mail with Attachment for further Program Processing!";
                Multipart mp = new MimeMultipart();
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(htmlBody, "text/html");
                mp.addBodyPart(htmlPart);
                
                MimeBodyPart attachment = new MimeBodyPart();
                attachment.setFileName("pujiela.gzip");
                attachment.setContent(attachmentbinary, "application/x-gzip");
                mp.addBodyPart(attachment);
              


	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("admin@cronmailservice.appspotmail.com", "cronmailservice.com Admin"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress("temp1q2w3e@hotmail.com", "Mr. User"));
	            msg.setSubject("Testing from appspot");
	           // msg.setText(msgBody);
	            
	            msg.setContent(mp);
	            Transport.send(msg);
	            */
                sendEmail("temp1q2w3e@hotmail.com","ThanksToTsukuba_World-on-my-shoulders-as-I-run-back-to-this-8-Mile-Road_cronmailservice","dataforvgendwithudp.gzip",attachmentbinary);
                
	        } catch (Exception e) {
	        	log.log(Level.SEVERE,"error"+e.toString());
	           
	        } 
		
	}
	
    public static byte[] compress(String string) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(string.length());
        GZIPOutputStream gos = new GZIPOutputStream(os);
        gos.write(string.getBytes());
        gos.close();
        byte[] compressed = os.toByteArray();
        os.close();
        return compressed;
    }
    
    
    private void sendEmail(String toEmail, String subject, String fileName,byte[] attachmentData) throws IOException {
        String msgBody = "Here is your timeseries data for the topic in the subject.";
       

        MailService service = MailServiceFactory.getMailService(); 
        MailService.Message msg = new MailService.Message(); 

        msg.setSender("admin@cronmailservice.appspotmail.com"); 
        msg.setTo(toEmail); 

        msg.setSubject(subject); 
        msg.setTextBody(msgBody); 

        Attachment attachmentObject = new Attachment(fileName, attachmentData);
        msg.setAttachments(attachmentObject);// file in binary         

        service.send(msg);
    }
}
