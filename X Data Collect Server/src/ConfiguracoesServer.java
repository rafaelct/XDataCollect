import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JOptionPane;


public class ConfiguracoesServer {

	private FileOutputStream arquivoConf;
	private FileInputStream arquivoConfLer;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String diretorioATF;
	private String servidor1 = "localhost";
	private String servidor2 = "d4003s199";
	private Vector dadosConfig;
	private Vector usuariosOcultos;
	
	public ConfiguracoesServer() {
		
		dadosConfig = new Vector();
		
	}
	
	public void criaConf() throws IOException {
		
			
			dadosConfig.clear();
			
			/*
			dadosConfig.add("Resultado comando NetStat");
			//dadosServidores.add("Maquina Local");
			dadosConfig.add("netstat");
			*/
			
			//dadosServidores.add("localhost");
			
			arquivoConf = new FileOutputStream("configuracoesServer.dat");
			output = new ObjectOutputStream(arquivoConf);
			
			output.writeObject(dadosConfig);
			output.flush();
			
			/*
			output.writeObject("d:\\Trace");
			output.flush();
			*/
			
			output.close();
			output.close();
			
			arquivoConf.close();
			
	}

	public void gravaConf( Vector infoConfig) throws IOException {
		
			arquivoConf = new FileOutputStream("configuracoesServer.dat");
			output = new ObjectOutputStream(arquivoConf);
			
			output.writeObject(infoConfig);
			output.flush();
			
			/*
			output.writeObject(infoDiretorioATF);
			output.flush();
			*/
			
			output.close();
			output.close();
			
			arquivoConf.close();
			
			
	}

	public void lerConf() throws IOException, ClassNotFoundException {
			
			File arquivo = new File("configuracoesServer.dat");
			
			if(! arquivo.exists() ) {
				this.criaConf();
			}
		
			arquivoConfLer = new FileInputStream("configuracoesServer.dat");
			input = new ObjectInputStream(arquivoConfLer);
			
			dadosConfig = (Vector) input.readObject();
			//diretorioATF = (String) input.readObject();
			
			arquivoConfLer.close();
			
		
		//return dados;
	}
	
	public void limparConf() {
		
		File arquivo = new File("configuracoesServer.dat");
		
		arquivo.delete();
		
		arquivo = null;
		
	}
	
	public Vector getConfig() {
		
		return dadosConfig;
	}
	
	/*
	public String getDiretorioATF() {
		
		return diretorioATF;
		
	}
	*/

}
