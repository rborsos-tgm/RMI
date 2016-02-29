package client;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import callback.Callback;
import callback.PiCallback;
import remoteService.DoSomethingService;
import server.commands.Command;
import server.commands.Pi;

/** 
 * Verbindung zum Server und Berechnung durchführen auf Userseite
 * 
 * @author Robert Borsos
 * @version 23-02-2016
 *
 */
public class Client {

	/**
	 * Main Methode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Es wird ein neue SecurityManager erstellt wenn keiner vorhanden ist
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry registry = LocateRegistry.getRegistry(1234); // Registry wird abgefragt
			Callback cb = new PiCallback();
			Callback cbStub = (Callback) UnicastRemoteObject.exportObject(cb, 0); // Stub erstellen mit Callback Objekt
			DoSomethingService uRemoteObject = (DoSomethingService) registry.lookup("Service"); // ServerService Objekt unter dem Namen Service aus Registry auslesen
			System.out.println("Service found");		
			Command<BigDecimal> rc = new Pi(Integer.parseInt(args[0]), cbStub); // Neues Objekt von Pi
			uRemoteObject.executeTask(rc); // Pi Obejekt übergeben und auf Serverseite berechnen

		} catch (RemoteException re) {
			System.err.println("Service not found?" + " Check your RMI-Registry!");
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Service exception:");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
