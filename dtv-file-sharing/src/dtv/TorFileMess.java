package dtv;

import java.io.*;

public class TorFileMess {
	int type;
	File tor = null;
	String path = null;
	String hashCode = null;
	
	public TorFileMess()
	{
		type = 0;
		tor = null;
		path = null;
	}
	
	public TorFileMess(int type, File tor, String path, String hash)
	{
		this.type = type;
		this.tor = tor;
		this.path = path;
		this.hashCode = hash;
	}
}
