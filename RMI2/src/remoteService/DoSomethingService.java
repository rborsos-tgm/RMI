package remoteService;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

import server.commands.Command;

/**
 * Interface als Vorlage fuer serverseitige Taskabfrage
 * 
 * @author Robert Borsos
 * @version 23-02-2016
 *
 */
public interface DoSomethingService extends Remote {
	/*
	 * Durchführen der Aufgabe
	 * 
	 * @param c 
	 * @throws RemoteException
	 */
	void executeTask(Command<BigDecimal> c) throws RemoteException;
}
