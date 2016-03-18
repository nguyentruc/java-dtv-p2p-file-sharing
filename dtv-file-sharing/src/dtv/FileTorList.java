package dtv;

import java.util.LinkedList;

public class FileTorList {
	
	static volatile LinkedList<String> listFile = new LinkedList<String>();
	
	public static synchronized LinkedList<String> getFileList()
	{	
		return listFile;
	}
	
	public static synchronized int posFile(String hashCode)
	{
		return listFile.indexOf(hashCode);
	}
	
	public static synchronized void addNew (String hashCode)
	{
		listFile.add(hashCode);
	}
	
	public static synchronized String getKey(int index)
	{
		return listFile.get(index);
	}

	public static synchronized void resetList()
	{
		listFile.clear();
	}
}
