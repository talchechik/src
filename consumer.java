import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;


public class consumer extends Thread{
	
	private String urll;
	private FileWriter writer;
	public consumer(String url, FileWriter writer)
	{
		this.urll= url;
		this.writer = writer;
	}
	 public void run()
	 {
		 try {
			 try {
				main.lock.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  URL url = new URL(urll);
	      URLConnection connection = url.openConnection();
	      InputStream is = connection.getInputStream();
	      InputStreamReader isr = new InputStreamReader(is);
	      BufferedReader br = new BufferedReader(isr);

	      HTMLEditorKit htmlKit = new HTMLEditorKit();
	      HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
	      HTMLEditorKit.Parser parser = new ParserDelegator();
	      HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
	    
	      

	    	  parser.parse(br, callback, true);
			writer.write(urll);
	         
	      for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.IMG); iterator.isValid(); iterator.next()) {
	             AttributeSet attributes = iterator.getAttributes();
	             String imgSrc = (String) attributes.getAttribute(HTML.Attribute.SRC);
	             writer.write(imgSrc);
			}
	      writer.write(System.lineSeparator());
	      } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 main.lock.release();
		 ThreadManager.ExisitingThreads.release();
	 }
}
