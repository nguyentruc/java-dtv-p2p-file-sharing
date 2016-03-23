package dtv;

import java.util.*;

public class FileTorList {
	
	static volatile List<TorFileMess> listFile = new ArrayList<>();
	
	public static synchronized List<TorFileMess> getFileList()
	{	
		return listFile;
	}
	
	public static synchronized int posFile(String hashCode)
	{
		for (int i = 0; i < listFile.size(); i++)
		{
			if (listFile.get(i).hashCode == hashCode)
				return i;
		}
		return -1;
	}
	
	public static synchronized void addNew (TorFileMess torMess)
	{
		TorFileMess _torMess = new TorFileMess(torMess.type, torMess.tor, torMess.path, torMess.hashCode);
		listFile.add(_torMess);
	}
	
	public static synchronized TorFileMess getKey(int index)
	{
		return listFile.get(index);
	}

	public static synchronized void resetList()
	{
		listFile.clear();
	}
}
