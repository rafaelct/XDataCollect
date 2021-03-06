import java.util.Vector;


public class ConnectThreadAdm implements Runnable {

	private String servidor,acao;
	private Vector vetorResultado;
	private String strCmd,strResultado,nomeServidor,dirATF,dirATFLocal;
	private int intResultado;
	
	public ConnectThreadAdm(String nomeServer,String server) {
		// TODO Auto-generated constructor stub
	this.servidor = server;
	this.nomeServidor = nomeServer;
	
	}

	public void lerConfigServer() {
		
		this.acao = "<lerConfig>";
		
	}
	
	public void gravaConfigServer(Vector vetDados) {
		this.acao = "<gravaConfig>";
		this.vetorResultado = vetDados;
		
	}

	public void AtualizaServer(String strCaminhoArquivoJar ) {
		// TODO Auto-generated method stub
		this.acao = "<atualizaServer>";
		//this.servidor = ipHostServer;
		this.strCmd = strCaminhoArquivoJar;
		
	}

	public void versaoServidor() {
		
		this.acao = "<versao>";
		
	}
	
	public Vector returnVector() {
	
		return vetorResultado;
		
	}
	
	@Override
	
	public void run() {
		
		ConnectAdm net = new ConnectAdm(servidor);
		net.setNome(nomeServidor);
		
		if( acao.equals("<lerConfig>")) {
			
			vetorResultado = net.lerConfigServer();
			intResultado = net.codRetorno();
			
		}

		if( acao.equals("<gravaConfig>")) {
			
			net.gravaConfigServer(vetorResultado);
			intResultado = net.codRetorno();
			
		}
		
		if( acao.equals("<atualizaServer>") ) {
			net.atualizaServer(strCmd);
			intResultado = net.codRetorno();
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			strResultado = net.versaoServidor();
			
		}
		
		if( acao.equals("<versao>") ) {
			
			strResultado = net.versaoServidor();
			intResultado = net.codRetorno();
				
		}
		
	}

	public String getNomeServidor() {
		// TODO Auto-generated method stub
		return this.nomeServidor;
	}

	public String getVersaoServidor() {
		
		if( strResultado == null ) {
			strResultado = "";
		}
		return strResultado;
	}
	
	public int returnCod() {
		// TODO Auto-generated method stub
		return this.intResultado;
	}


}
