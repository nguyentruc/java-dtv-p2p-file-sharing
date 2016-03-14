package XMLDom;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Unmarshal {

	public FileTorrent unmarshalXML(File file){
		JAXBContext jaxbContext;
		FileTorrent result = null; 
		try {
			jaxbContext = JAXBContext.newInstance(FileTorrent.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			result = (FileTorrent) jaxbUnmarshaller.unmarshal(file);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
