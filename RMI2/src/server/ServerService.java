package server;

import java.rmi.RemoteException;

import remoteService.DoSomethingService;
import server.commands.Command;

/**
 * Interface DoSomethingService implementation
 * 
 * @author Robert Borsos
 * @version 23-02-2016
 *
 */
public class ServerService implements DoSomethingService {

	@Override
	/*
	 * 
	 */
	public void executeTask(Command c) throws RemoteException {
		c.execute();

	}

}
