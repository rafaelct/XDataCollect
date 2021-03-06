import java.net.Socket;
import java.net.UnknownHostException;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.OutputStream;
//import java.io.ClassNotFoundException;
import java.util.Vector;
import java.util.Date;

public class ConnectAdm {

	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private OutputStream out;
	private InputStream in;
	
	private String server;
	
	private String nomeServidor;
	private String dirATF;
	private int codRetorno = 0;
	
	public ConnectAdm(String servidor) {
		
		this.server = servidor;
		System.out.println("Connect a " + this.server);
	}
	public int open() {
		
		try{
		socket = new Socket(this.server,4001);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {
			//e.printStackTrace();
			return 1;
		}catch(Exception e) {
			return 2;
		}
		return 0;
		
	}

	public void close() throws IOException {
		
		output.close();
		input.close();
		socket.close();
		
		output = null;
		socket = null;
		input = null;
		
	}
	
	public void setNome(String nomeServer) {
		
		nomeServidor = nomeServer;
		
	}
	
	public void gravaConfigServer(Vector vetDadosInfo) {

		if( open() == 1) {
			this.codRetorno = 1;
			return;
		}
		
		try {
			output.writeObject("<gravaConfig>");
			output.flush();

			output.writeObject( vetDadosInfo );
			output.flush();
		
			this.codRetorno = (Integer) input.readObject();
			close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			try {
				close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	public Vector lerConfigServer() {

		if( open() == 1) {
			this.codRetorno = 1;
			return null;
		}
		
		Vector vetorInfoServer = null;
		
		try {
			
			output.writeObject("<lerConfig>");
			output.flush();
			
			try {
				codRetorno = (Integer) input.readObject();
				
				if ( codRetorno != 0 ) {
					close();
					return null;
				}
				
				vetorInfoServer = (Vector) input.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				close();
				e.printStackTrace();
			}
			
			System.out.println("Fechando....");
			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		return vetorInfoServer;

	}
	
	public int codRetorno() {
		return this.codRetorno;
	}
	public void atualizaServer(String strCaminhoArquivo) {
		// TODO Auto-generated method stub

		if( open() == 1) {
			this.codRetorno = 1;
			return;
		}
		
		try {
			output.writeObject("<atualizaServer>");
			output.flush();
			CopiarArquivo copiarArquivo = new CopiarArquivo();
			copiarArquivo.copiar( new File(strCaminhoArquivo), socket);
			close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public String versaoServidor() {
		// TODO Auto-generated method stub
		
		if( open() == 1) {
			this.codRetorno = 1;
			return null;
		}
		
		String versao = null;
		
		try {
			
		
			output.writeObject("<versao>");
			output.flush();
			
			versao = (String) input.readObject();
								
		} catch( ClassNotFoundException e) {
				//close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			return versao;
	}

}
