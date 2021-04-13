import java.util.Vector;


public class ConnectThread implements Runnable {

	private String servidor,acao;
	private Vector vetorResultado;
	private String nomeServidor,dirATF,dirATFLocal;
	
	public ConnectThread(String nomeServer,String server) {
		// TODO Auto-generated constructor stub
	this.servidor = server;
	this.nomeServidor = nomeServer;
	
	}

	public void getInfoServer(String diretorioATF ,String diretorioATFLocalStr ) {
		
		this.acao = "<getInfoServer>";
		this.dirATF = diretorioATF;
		this.dirATFLocal = diretorioATFLocalStr;
		
	}
	
	public Vector returnVector() {
	
		return vetorResultado;
		
	}
	
	@Override
	
	public void run() {
		
		Connect net = new Connect(servidor);
		net.setNome(nomeServidor);
		
		if( acao.equals("<getInfoServer>")) {
		
			vetorResultado = net.getInfoServer(nomeServidor,dirATF,dirATFLocal);
			
		}
		
		
	}

}
