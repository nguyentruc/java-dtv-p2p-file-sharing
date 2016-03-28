package dtv;

import java.util.*;

public class FileDtvList {
	
	static volatile List<DTVParams> listFile = new ArrayList<>();
	
	public static synchronized List<DTVParams> getFileList()
	{	
		return listFile;
	}
	
	public static synchronized int posFile(String hashCode)
	{
		for (int i = 0; i < listFile.size(); i++)
		{
			if (listFile.get(i).getHashCode().equals(hashCode))
				return i;
		}
		return -1;
	}
	
	public static synchronized void addNew (DTVParams dtv_params)
	{
		listFile.add(dtv_params);
	}
	
	public static synchronized DTVParams getDtv(int index)
	{
		return listFile.get(index);
	}

	public static synchronized void resetList()
	{
		listFile.clear();
	}
	
	public static synchronized void remove(String hashCode)
	{
		int i;
		
		for (i = 0; i < listFile.size(); i++)
		{
			if (listFile.get(i).getHashCode().equals(hashCode))
				break;
		}
		
		listFile.remove(i);
	}
	
	public static void printListHash()
	{
		System.out.println("List:");
		for (int i = 0; i < listFile.size(); i++)
		{
			System.out.println(listFile.get(i).getHashCode());
		}
	}
	
	public static synchronized int getSize()
	{
		return listFile.size();
	}
}
