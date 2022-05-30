package org.pchack.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

// define remote interface
public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}
