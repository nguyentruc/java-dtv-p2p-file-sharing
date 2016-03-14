package XMLDom;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Marshal {
	public void marshalXML(String url,FileTorrent fileTorrent){
		try {

			File file = new File(url);
			JAXBContext jaxbContext = JAXBContext.newInstance(FileTorrent.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(fileTorrent, file);
			//jaxbMarshaller.marshal(fileTorrent, System.out);

		      } catch (JAXBException e) {
			e.printStackTrace();
		      }
	}
}
