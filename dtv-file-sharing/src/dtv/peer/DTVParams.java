/**
 * 
 */
package dtv.peer;

import java.util.*;

/**
 * @author Trung Truc
 *
 */
public class DTVParams {

	private String fileName;
	private String hashCode;
	private long sizeOfFile;
	private String pathToFile;
	private List<String> TrackerList;
	private int type;
	
	/**
	 * 
	 */
	public DTVParams() {
		TrackerList = new ArrayList<String>();
		fileName = hashCode = pathToFile = "";
		sizeOfFile = 0;
	}

	public void setName(String name)
	{
		fileName = name;
	}
	
	public String getName()
	{
		return fileName;
	}
	
	public void setHashCode(String hash)
	{
		hashCode = hash;
	}
	
	public String getHashCode()
	{
		return hashCode;
	}
	
	public void setSize(long size)
	{
		sizeOfFile = size;
	}
	
	public long getSize()
	{
		return sizeOfFile;
	}
	
	public void setPathToFile(String path)
	{
		pathToFile = path;
	}
	
	public String getPathToFile()
	{
		return pathToFile;
	}
	
	public void addTracker(String tracker)
	{
		TrackerList.add(tracker);
	}
	
	public List<String> getTrackerList()
	{
		return TrackerList;
	}
	
	public void setType(int _type)
	{
		type = _type;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void printInfo()
	{
		System.out.println("Type: " + type);
		System.out.println("Name: " + fileName);
		System.out.println("Hash (SHA-512): " + hashCode);
		System.out.println("Size: " + sizeOfFile);
		System.out.println("Path: " + pathToFile);
		System.out.println("Tracker: " + TrackerList);
	}
}
