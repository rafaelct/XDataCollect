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

public class Connect {

	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private OutputStream out;
	private InputStream in;
	
	private String server;
	
	private String nomeServidor;
	private String dirATF;
	
	public Connect(String servidor) {
		
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
	public Vector getInfoServer(String nomeServidor,String diretorioATF,String diretorioATFLocalStr ) {
		
		Vector vetor = new Vector();
		try{
			
		//while(true) { // comeco do while de teste
		//Vector vetor;
		if( open() == 1) {
			return null;
		}
		output.writeObject("<getInfoServer>");
		output.flush();
		
		output.writeObject(nomeServidor);
		output.flush();
		
		output.writeObject(diretorioATF);
		output.flush();
		
		this.setDirATF(diretorioATF);
		
		// colocar aqui em baixo o retorno de status ok ou erro.
		
		Integer codigoErro = new Integer( (Integer) input.readObject() );
		
		if(codigoErro.intValue() == 1 ) {
			
			vetor.add("<ERRO-1>");
			close();
			return vetor;
		}
		
		if(codigoErro.intValue() == 2 ) {
			
			vetor.add("<ERRO-2>");
			close();
			return vetor;
		}

		if(codigoErro.intValue() == 3 ) {
			
			vetor.add("<ERRO-3>");
			close();
			return vetor;
			
		}

		if(codigoErro.intValue() == 4 ) {
			
			vetor.add("<ERRO-4>");
			close();
			return vetor;
		}

		vetor = (Vector) input.readObject();

		if( ! vetor.get(0).equals("<ERRO-4>") ) {
		
			Vector vetListaArquivos = (Vector) input.readObject();
			
			//while(true) {
			
			// Fecha Conexão
			close();
			
			CompactadorZip.compactarParaZip(diretorioATFLocalStr + nomeServidor+".zip", vetListaArquivos,this);
			
			return vetor;
		
		}
		
		
		// Segunda verificação de erros
		
		
		if(codigoErro.intValue() == 1 ) {
			
			vetor.add("<ERRO-1>");
			close();
			return vetor;
		}
		
		if(codigoErro.intValue() == 2 ) {
			
			vetor.add("<ERRO-2>");
			close();
			return vetor;
		}

		if(codigoErro.intValue() == 3 ) {
			
			vetor.add("<ERRO-3>");
			close();
			return vetor;
			
		}

		if(codigoErro.intValue() == 4 ) {
			
			vetor.add("<ERRO-4>");
			close();
			return vetor;
		}


		
		close();

		}catch(IOException e) {
			
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return vetor;
	}
	public Socket getSocket() {
		// TODO Auto-generated method stub
		return socket;
	}
	public ObjectOutputStream getObjOutputStream() {
		// TODO Auto-generated method stub
		return output;
	}
	public String getDirATF() {
		// TODO Auto-generated method stub
		return dirATF;
	}
	
	public void setDirATF( String dATF) {
		dirATF = dATF;
	}
		
}
