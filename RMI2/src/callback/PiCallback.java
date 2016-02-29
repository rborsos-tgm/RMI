package callback;

import java.math.BigDecimal;
import java.rmi.RemoteException;

/**
 * Callback wird hier implementiert
 * 
 * @author Robert Borsos
 * @version 23-02-2016
 *
 */
public class PiCallback implements Callback {
	@Override
	/*
	 * Empfangen und ausgeben des Ergebnisses
	 * 
	 * @param pi
	 */
	public void receive(BigDecimal pi) throws RemoteException {
		System.out.println(pi);
	}

}
