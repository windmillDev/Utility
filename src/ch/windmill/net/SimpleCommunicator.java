package ch.windmill.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SimpleCommunicator {
	private int timeout;
	private int remotePort;
	private InetAddress remoteAdress;
	private Socket client;
	private DataInputStream reader;
	private DataOutputStream writer;
	
	public SimpleCommunicator(final String ipAdress) throws Exception {
		this(ipAdress, 12345, 5);
	}
	
	public SimpleCommunicator(final String ipAdress, final int remotePort, final int timeout) throws Exception {
		remoteAdress = InetAddress.getByName(ipAdress);
		this.remotePort = remotePort;
		this.timeout = timeout;
	}
	
	public void connect() throws Exception {
		client = new Socket(remoteAdress, remotePort);
		reader = new DataInputStream(client.getInputStream());
		writer = new DataOutputStream(client.getOutputStream());
	}
	
	public void writeToStream(final byte[] request) throws Exception {
		if(client.isConnected()) {
			writer.write(request);
		} else {
			throw new Exception("Socket is not connected.");
		}
	}
	
	public byte[] readFromStream() throws Exception {
		byte[] request = null;
		
		if(reader.read() != -1) {
			for(int i = 0; i < timeout && reader.available() == 0; i++) {
				System.out.println("wait for answer...");
				Thread.sleep(1000);
			}
			
			request = new byte[reader.available()];
			
			if((reader.read(request)) == 0) {
				throw new Exception("Connection timeout");
			}
		} else {
			shutdown();
			throw new Exception("The server closed the connection");
		}
		return request;
	}
	
	public void setRemoteAddress(String ipAddress) throws Exception {
		remoteAdress = InetAddress.getByName(ipAddress);
	}
	
	public void setRemotePort(int port) {
		remotePort = port;
	}
	
	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public Socket getSocket() {
		return client;
	}
	
	public void shutdown() {
		try {
			reader.close();
			writer.flush();
			writer.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
