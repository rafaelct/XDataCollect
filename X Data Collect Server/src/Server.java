import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* X Data Collect Server v3.0
 * Compativel com a versão X Data Collect Cliente v3.0
 * Atualizado em: 26/09/2012 - v2.2
 * Atualizado em: 21/05/2013 - v3.0
 * 
 * Desenvolvedor: Rafael Costa Teixeira
 * 
 */

public class Server {

	private ServerSocket serverSocket;
	private Socket socket;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	public Server() {
		
		// TODO Auto-generated constructor stub
		try{
			
			try {
		serverSocket = new ServerSocket(4001);
			} catch( BindException e) {
				e.printStackTrace();
				System.exit(1);
				
			}
		 
		// ATENÇÃO NÃO TIRAR O PRIMEIRO # (JOGO DA VELHA) É UTILIZADO PELO X DATA COLLECT SERVER START NA EXECUÇÃO DO X DATA COLLECT SERVER.
			
		System.out.println("###################################");
		System.out.println("##    X DATA COLLECT SERVER " + Versao.versao()+ " ##");
		System.out.println("###################################");
		 
		
		while(true) {
			
			socket = serverSocket.accept();
			System.out.println("Conectou !!!");
			
				ServerSession serverSession = new ServerSession(socket);
				
				Thread tarefaSession = new Thread(serverSession);
				tarefaSession.start();
			
		}
		
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
