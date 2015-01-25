package netfetch;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sadpanda
 */
public class FetchWEB {
    
    public static StringBuilder getasstring(String webhost) throws MalformedURLException, IOException
    {
        HttpURLConnection urlc = null; 
        StringBuilder s=new StringBuilder();
        URL url = new URL(webhost);
        urlc = (HttpURLConnection)url.openConnection();
        urlc.setUseCaches(false);
        urlc.setInstanceFollowRedirects(false);
        urlc.setConnectTimeout(30000);
        urlc.setRequestProperty("Cache-Control", "no-cache");
        urlc.setRequestProperty("Pragma", "no-cache");
        urlc.setRequestProperty("Accept-Charset", "UTF-8");
        urlc.connect();
        Object o=null;
        o=urlc.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) o,"UTF-8"));
        String line;
         while ((line = reader.readLine()) != null) {
              // ...
        	  s.append(line);
        	  s.append("\r\n");
        	  
          }
         reader.close();
         return s;
    }
    
    public static void main(String args[])
    {
        try {
            //"http://77.78.12.91:40961/"+"api/iphone/"
            StringBuilder sd=FetchWEB.getasstring("http://77.78.12.91:40961/"+"api/iphone/");
            System.out.println(sd);
        } catch (IOException ex) {
            Logger.getLogger(FetchWEB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
