package server.commands;

import java.io.Serializable;

/**
 * Interface für die Berechnung
 * 
 * @author Robert Borsos
 * @version 23-02-2016
 * @param <T>
 */
public interface Command<T> extends Serializable {
	/**
	 * start der Berechnung
	 */
	void execute();
}
