package com.sick.cronmailservice;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import Datatool.CheckEmpty;
import Datatool.PMF;
import dataobjects.EmailAddress;
import javax.jdo.Query;

@SuppressWarnings("serial")
public class CronmailserviceServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();

		String temp;
		if(CheckEmpty.docheck("dataobjects.EmailAddress"))
		{
			
			temp="empty!!!";
			EmailAddress ea=new EmailAddress("temp1q2w3e@hotmail.com");
			pm.makePersistent(ea);
			pm.close();
		}else
		{
			temp="";
			Query q = pm.newQuery(EmailAddress.class);
			try {
				  
				  List<EmailAddress> results = (List<EmailAddress>) q.execute();
				  if (!results.isEmpty()) {
				    for (EmailAddress p : results) {
				    	temp=temp+p.getMail()+",";
				      // Process result p
				    }
				    temp=temp.substring(0,temp.length()-1);
				  } else {
				    // Handle "no results" case
				  }
				} finally {
				  q.closeAll();
				}
		}
		
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world!   "+temp);
	}
}
