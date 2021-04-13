import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JOptionPane;


public class Configuracoes {

	private FileOutputStream arquivoConf;
	private FileInputStream arquivoConfLer;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String diretorioATF;
	private Vector dadosServidores;
//	private Vector usuariosOcultos;
	
	public Configuracoes() {
		
		dadosServidores = new Vector();
//		usuariosOcultos = new Vector();

	}
	
	public void criaConf() {
		
		try {
			
			dadosServidores.clear();
			
			dadosServidores.add("Maquina Local");
			dadosServidores.add("localhost");
			
			arquivoConf = new FileOutputStream("configuracoes.dat");
			output = new ObjectOutputStream(arquivoConf);
			
			output.writeObject(dadosServidores);
			output.flush();
			
			output.writeObject("d:\\Trace");
			output.flush();
			
			output.close();
			output.close();
			
			arquivoConf.close();
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
	}

	public void gravaConf( Vector infoServidores , String infoDiretorioATF) {
		
		try {
			
			
			arquivoConf = new FileOutputStream("configuracoes.dat");
			output = new ObjectOutputStream(arquivoConf);
			
			output.writeObject(infoServidores);
			output.flush();
			
			output.writeObject(infoDiretorioATF);
			output.flush();
			
			output.close();
			output.close();
			
			arquivoConf.close();
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
	}

	public void lerConf() {
		
		try{
			
			File arquivo = new File("configuracoes.dat");
			
			if(! arquivo.exists() ) {
				this.criaConf();
			}
		
			arquivoConfLer = new FileInputStream("configuracoes.dat");
			input = new ObjectInputStream(arquivoConfLer);
			
			dadosServidores = (Vector) input.readObject();
			diretorioATF = (String) input.readObject();
			
			arquivoConfLer.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//return dados;
	}
	
	public void limparConf() {
		
		File arquivo = new File("configuracoes.dat");
		
		arquivo.delete();
		
		arquivo = null;
		
	}
	
	public Vector getInfoServidores() {
		
		return dadosServidores;
	}
	
	public String getDiretorioATF() {
		
		return diretorioATF;
		
	}
	

}
