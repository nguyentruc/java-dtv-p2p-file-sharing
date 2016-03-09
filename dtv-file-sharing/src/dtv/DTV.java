package dtv;

public class DTV {

	public static void main(String[] args) {
		UI.main(null);
		
		new Thread(new Peer()).start();

	}

}
