import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class ThreadManager extends Thread {
	private int possibleThreads = 2;
	private int tasks = 0;
	public static Semaphore ExisitingThreads = new Semaphore(0);
	private FileWriter FileWriter;
	
	public ThreadManager(FileWriter writer)
	{
		this.FileWriter = writer;
	}
	
	public void run()
	{
		List<consumer> threads = new ArrayList<>();
		try {
			
			while(true)
			{
				Storage.Instance().sem.acquire();
				if((Storage.Instance().done == false) || (tasks < Storage.Instance().tasks))
				{
					if(tasks >= possibleThreads)
					{
						ExisitingThreads.acquire();
					}
					
					String url = Storage.Instance().pop();
					consumer t = new consumer(url,FileWriter);
					t.start();
					tasks++;
					threads.add(t);
				}
				else
				{
					break;
				}
			}
			for(int i=0; i<threads.size(); ++i)
			{
				threads.get(i).join();
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
