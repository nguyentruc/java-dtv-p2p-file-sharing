package XMLDom;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileTorrent {

	String title ;
	String IP;
	String PORT;
	String hashKey;
	String trackerAddress;

	
	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement
	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}
	@XmlElement
	public String getPORT() {
		return PORT;
	}

	public void setPORT(String pORT) {
		PORT = pORT;
	}
	@XmlElement
	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}
	@XmlElement
	public String getTrackerAddress() {
		return trackerAddress;
	}

	public void setTrackerAddress(String trackerAddress) {
		this.trackerAddress = trackerAddress;
	}



	

}
