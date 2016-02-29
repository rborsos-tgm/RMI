package callback;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of the Callback
 * 
 * @author Robert Borsos
 * @version 23-02-2016 
 */
public interface Callback extends Remote {
	/*
	 * Ergebnis wird hier übergeben
	 * 
	 * @param pi ergebnis
	 * @throws RemoteException
	 */
	void receive(BigDecimal pi) throws RemoteException;
}
