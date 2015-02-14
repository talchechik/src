import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;


public class main {

	public static Semaphore lock = new Semaphore(1);
	
	
	public static void main(String[] args) throws IOException {
		try {
			File file = new File("C:\\Users\\tal\\Desktop\\test55.txt");
	         FileWriter writer = new FileWriter(file,false); 
	         ThreadManager manger = new ThreadManager(writer);
			manger.start();
			
			BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\tal\\Desktop\\test.txt"));
			String str;
			
			while ((str = in.readLine()) != null)
			{
				Storage.Instance().add(str);
	        }
			
			Storage.Instance().done();
			try {
				manger.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			   // Writes the content to the file 
	         writer.flush();
	         writer.close();
	        }
		catch (IOException e) {
			
		}
         
	
	}

	}
