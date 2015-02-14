import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;


public class Storage {

	public int tasks = 0;
	public Semaphore sem = new Semaphore(0);
	public Semaphore mutex = new Semaphore(1);
	public boolean done  = false;
	private static Storage ins;
	private Queue<String> q = new LinkedList<>();
	private Storage()
	{
		
	}
	
	public static Storage Instance()
	{
		if(ins == null)
		{
			ins = new Storage();
		}
		return ins;
	}
	
	public void add(String url)
	{
		try {
			mutex.acquire();
			q.add(url);
			tasks++;
			sem.release();
			mutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String pop(){
		String res = null;
		try {
			mutex.acquire();
			res = q.poll();
			mutex.release();
			return res;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public void done()
	{
		done = true;
		sem.release();
	}
}
