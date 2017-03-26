package shell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class StupidTelnetShell {

	
	
	public static void main(String[] args) {
		try {
			Socket socket= new Socket("192.168.1.11", 4000);
			Scanner output= new Scanner(socket.getInputStream());
			PrintWriter inWriter= new PrintWriter(socket.getOutputStream(),true);
			Scanner inTyped= new Scanner(System.in);
			if(socket.isConnected())
				System.out.println("Connection established!");
			String line;
			String out;
			while(true){
				System.out.print("-> ");
				line=inTyped.nextLine();
//				System.out.println("received command "+line);
				if(line.equals("close")){
					socket.close();
					break;
				}
				inWriter.println(line);
				out=output.nextLine();
				System.out.println(out);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}

}
