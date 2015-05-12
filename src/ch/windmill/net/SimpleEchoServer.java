package ch.windmill.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleEchoServer {
	private int port;
	private ServerSocket serverSocket;
	
	public SimpleEchoServer(final int port) throws Exception{
		this.port = port;
		
		serverSocket = new ServerSocket(port);
	}
	
	public void Listen() throws Exception{
		System.out.println("I listen to "+port);
		Socket client = serverSocket.accept();
		DataInputStream reader = new DataInputStream(client.getInputStream());
		DataOutputStream writer = new DataOutputStream(client.getOutputStream());
		
		while(true) {
			byte[] clientRequest = new byte[20];
			reader.read(clientRequest);
			String s = new String(clientRequest);
			writer.write(("Echo: "+s).getBytes());
			writer.flush();
			System.out.println("Echo: "+s);
		}
	}
	
	public int getPort() {
		return port;
	}
}
