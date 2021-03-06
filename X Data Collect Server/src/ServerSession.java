import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
//import java.sql.SQLException;
//import java.util.Date;
import java.util.Vector;


public class ServerSession implements Runnable{

	private Socket socket;

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String linha,dirATF;

	public ServerSession(Socket s) {
		
		// TODO Auto-generated constructor stub
		try{
			
			 socket = s;
			 output = new ObjectOutputStream( socket.getOutputStream());
			 input = new ObjectInputStream( socket.getInputStream());
		
			 output.flush();
		
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		
		try{
			
		
		linha = (String) input.readObject();
		System.out.println(linha);
		
		if(linha.equals("<versao>") ) {
			
			output.writeObject( Versao.versao() );
			output.flush();
			
		}
		if(linha.equals("<atualizaServer>") ) {
			
			CopiarArquivo copiarArquivo = new CopiarArquivo();
			copiarArquivo.copiar(socket, new File("XDataCollect-new.jar") );
			System.out.println("Reboot do X Data Collect Server para Atualização...");
			
			System.exit(0);
			
		}
		
		if(linha.equals("<reboot") ) {
			System.out.println("Reboot do X Data Collect Server Requisitado...");
			System.exit(0);
			
		}
		if(linha.equals("<getArquivoServer>") ) {
			
			dirATF = (String) input.readObject();
			//long tamanhoArquivo = (Long) input.readObject();
			String nomeArquivo = (String) input.readObject();
			
			if( dirATF.charAt(dirATF.length()-1 ) != '\\' ) {
				
				dirATF = dirATF + "\\";
				
				System.out.println("Nao tinha barra");
				System.out.println(dirATF);
				//return;
				
			}
			
			CopiarArquivo copiarArquivo = new CopiarArquivo();
			copiarArquivo.copiar(new File( dirATF + nomeArquivo ), socket);
			copiarArquivo = null;
			//Thread.sleep(2000);
			
		}

		if(linha.equals("<lerConfig>") ) {
			
			Vector vetorConfigServer = null;
			
			ConfiguracoesServer configServer = new ConfiguracoesServer();
			try {
			configServer.lerConf();
			
			} catch(IOException e) {
				
				output.writeObject(1);
				output.flush();
				
				socket.close();
				output.close();
				input.close();
				
				output = null;
				input = null;
				socket = null;
				return;
				
			} catch(ClassNotFoundException e) {
				
				output.writeObject(1);
				output.flush();
				
				socket.close();
				output.close();
				input.close();
				
				output = null;
				input = null;
				socket = null;
				return;
			}
			
			output.writeObject(0);
			output.flush();
			
			vetorConfigServer = configServer.getConfig();
			
			output.writeObject( vetorConfigServer );
			output.flush();
		}
		
		if(linha.equals("<gravaConfig>") ) {
			
			Vector vetorConfigServer = (Vector) input.readObject();
			
			ConfiguracoesServer configServer = new ConfiguracoesServer();
			
			try {
			configServer.lerConf();
			configServer.gravaConf(vetorConfigServer);
			} catch( IOException e ) {
				output.writeObject(1);
				output.flush();

				socket.close();
				output.close();
				input.close();
				
				output = null;
				input = null;
				socket = null;
				return;

			} catch( ClassNotFoundException e) {
				output.writeObject(1);
				output.flush();

				socket.close();
				output.close();
				input.close();
				
				output = null;
				input = null;
				socket = null;
				return;

			}
			output.writeObject(0);
			output.flush();
			
		}
		
		if(linha.equals("<getInfoServer>")) {
			
			linha = (String) input.readObject();
			dirATF = (String) input.readObject();
			
			if( dirATF.charAt(dirATF.length()-1 ) != '\\' ) {
				
				dirATF = dirATF + "\\";
				
				System.out.println("Nao tinha barra");
				System.out.println(dirATF);
				//return;
				
			}
			
			// Verifica se existe o diretorio ATF especificado.
			
			File file = new File(dirATF);
			
			if( ! file.exists() ) {
				
				output.writeObject(2);
				output.flush();
				return;
				
			} 
			
			
			WmiConnect wmiConnect = new WmiConnect(); // desabilitado para teste
			
			Vector vetorInfoServer = new Vector();
			
			// Informações de CPU
			
			vetorInfoServer.add( wmiConnect.executaStr("SELECT Name FROM CIM_Processor","Name","localhost","","") );
			vetorInfoServer.add( wmiConnect.executaStr("SELECT * FROM CIM_Processor","NumberOfCores","localhost","","") );
			vetorInfoServer.add( wmiConnect.executaStr("SELECT MaxClockSpeed FROM CIM_Processor","MaxClockSpeed","localhost","","") );
			vetorInfoServer.add( wmiConnect.executaStr("SELECT CurrentClockSpeed FROM CIM_Processor","CurrentClockSpeed","localhost","","") );
			vetorInfoServer.add( wmiConnect.executaStr("SELECT PercentProcessorTime FROM Win32_PerfFormattedData_PerfOS_Processor Where Name='_Total'","PercentProcessorTime","localhost","","") );

			// Informações de Memoria
			
			String memoriaStr = wmiConnect.executaStr("SELECT * FROM CIM_ComputerSystem","TotalPhysicalMemory","localhost","","");
			
			/* Correção efetuada em 26/09/2012
			
			Nos casos quando retorna vazio na memoriaStr , ao usar o Long.parseLong estava sendo 
			gerado um NumberFormatException
			
			Colocado um if para nesses casos não usar a conversão.
			
			*/

			long memoriaLong = 0;
			
			if( ! memoriaStr.equals("") ) {
				System.out.println("Memoria Total");
				memoriaLong = Long.parseLong(memoriaStr);
				memoriaLong = memoriaLong /1024 / 1024;
				memoriaStr = Long.toString(memoriaLong);
			}
			
			vetorInfoServer.add(memoriaStr);
			
			memoriaStr = wmiConnect.executaStr("SELECT * FROM Win32_PerfFormattedData_PerfOS_Memory","AvailableBytes","localhost","","");
			
			/* Correção efetuada em 26/09/2012
			
			Nos casos quando retorna vazio na memoriaStr , ao usar o Long.parseLong estava sendo 
			gerado um NumberFormatException
			
			Colocado um if para nesses casos não usar a conversão.
			
			*/
			
			if( ! memoriaStr.equals("") ) {
			
				System.out.println("Memoria Disponivel");
				memoriaLong = Long.parseLong(memoriaStr);
				memoriaLong = memoriaLong /1024 / 1024;
				memoriaStr = Long.toString(memoriaLong);
			
			}
			
			// fim da correção
			
			vetorInfoServer.add(memoriaStr);

			
			// Nas linha abaixo é criado um arquivo HTML com todas as informacoes coletadas
			// do servidor
			// OutputStream arquivoHtml = new FileOutputStream(dirATF + "Salvar\\" + "info.html");
			
			OutputStream arquivoHtml = new FileOutputStream(dirATF + "info.html");

			arquivoHtml.write("<html>\n".getBytes());
			arquivoHtml.write("<head>\n".getBytes());
			arquivoHtml.write("</head>\n".getBytes());
			arquivoHtml.write("<body>\n".getBytes());
			arquivoHtml.write("<h1>Informação do servidor ".getBytes());
			arquivoHtml.write(linha.getBytes());
			arquivoHtml.write("</h1>\n".getBytes());
			arquivoHtml.write("<hr><br>\n".getBytes());
			
			arquivoHtml.write("<table border=1>".getBytes());
			arquivoHtml.write("<tr><td><n>Processador:</n></td><td>".getBytes());
			arquivoHtml.write(vetorInfoServer.get(0).toString().getBytes());
			arquivoHtml.write("</td></tr>\n".getBytes());
			
			
			arquivoHtml.write("<tr><td><n>Numero de Nucleos do processador:</n></td><td>".getBytes());
			arquivoHtml.write(vetorInfoServer.get(1).toString().getBytes());
			arquivoHtml.write("</td></tr>\n".getBytes());
			
			arquivoHtml.write("<tr><td><n>Clock (max):</n></td><td>".getBytes());
			arquivoHtml.write(vetorInfoServer.get(2).toString().getBytes());
			arquivoHtml.write("</td></tr>\n".getBytes());
			
			arquivoHtml.write("<tr><td><n>Clock (atual):</n></td><td>".getBytes());
			arquivoHtml.write(vetorInfoServer.get(3).toString().getBytes());

			arquivoHtml.write("<tr><td><n>Uso de CPU (%) </n></td><td>".getBytes());
			arquivoHtml.write(vetorInfoServer.get(4).toString().getBytes());
			arquivoHtml.write("</td></tr>\n".getBytes());
			

			arquivoHtml.write("<tr><td><n>Memoria Fisica (Total)</n></td><td>".getBytes());
			arquivoHtml.write(vetorInfoServer.get(5).toString().getBytes());
			arquivoHtml.write(" MB </td></tr>\n".getBytes());
			
			arquivoHtml.write("<tr><td><n>Memoria Fisica (Disponivel)</n></td><td>".getBytes());
			arquivoHtml.write(vetorInfoServer.get(6).toString().getBytes());
			arquivoHtml.write(" MB </td></tr>\n".getBytes());
			

			ConfiguracoesServer configServer = new ConfiguracoesServer();
			configServer.lerConf();
			Vector vetorInfoConfig = configServer.getConfig();
			
			StringBuffer strInfo = new StringBuffer();
			
		for(int i = 0; i < vetorInfoConfig.size();i=i+2) {
			
			//Process p = Runtime.getRuntime().exec("netstat -a");
			try {
				Process p = Runtime.getRuntime().exec( (String) vetorInfoConfig.get(i+1) );
			
			System.out.println("PASSEI !!!!");
			
			byte b[] = new byte[1];
			byte b2[] = new byte[1024];
			
			InputStream in = p.getInputStream();
			StringBuffer str = new StringBuffer();
			
			strInfo.append( vetorInfoConfig.get(i) + ":\n\n");
			
			while( in.read(b) > -1) {
				str.append(new String(b));
				strInfo.append(new String(b));
				
			}
			
			
			//arquivoHtml.write("<tr><td><n>NetStat</n></td><td><pre>".getBytes());
			String infoCmdServer = "<tr><td><n>" + vetorInfoConfig.get(i) + " </n></td><td><pre>";
			
			arquivoHtml.write(infoCmdServer.getBytes() );
			arquivoHtml.write(str.toString().getBytes());
			arquivoHtml.write("</pre></td></tr>\n".getBytes());
			
			strInfo.append("\n\n");
			
			} catch( IOException e ) {
				// SE DER ERRO NA Execução do comando vem pra ca.
				
				String infoCmdServer = "<tr><td><n>" + vetorInfoConfig.get(i) + " </n></td><td><pre>";
				String str = "(Falha na execução do comando: " + vetorInfoConfig.get(i+1)+ " )";				
				arquivoHtml.write(infoCmdServer.getBytes() );
				arquivoHtml.write(str.toString().getBytes());
				arquivoHtml.write("</pre></td></tr>\n".getBytes());
				
				strInfo.append("\n\n");

			}

		} // fim do loop for
		
		
		configServer = null;
		
			arquivoHtml.write("</table>".getBytes());

			arquivoHtml.write("</body>\n".getBytes());
			arquivoHtml.write("</html>\n".getBytes());
			arquivoHtml.close();
			
			vetorInfoServer.add(strInfo);
			//System.out.println(new String(b2));
			
			output.writeObject(0);
			output.flush();
			
			output.writeObject(vetorInfoServer); 
			//output.writeObject(vetorDados); // habilitado para teste
			output.flush();

			// Alteração efetuada em 17/05/2013 - v3.0
			System.out.println("Dormindo por 60 segundos...");
			
			// Dormindo por 60 segundos
			try {
			Thread.sleep(60000);
			} catch( InterruptedException e) {
				e.printStackTrace();
			}
			// fim da alteração v3.0
			
			
			String strFiles[] = file.list();

			// Inicia copia de arquivos
			
			Vector vetListaArquivos = new Vector();
			
			for(int i = 0 ; i < strFiles.length ; i++) {
				
				System.out.println("Arquivo:"+ strFiles[i]);
				
				
				
				if ( strFiles[i].substring(strFiles[i].length()-4, strFiles[i].length()).equals(".ATF") || strFiles[i].equals("info.html") ) {
					System.out.println("Selecionado Arquivo:"+ strFiles[i]);
					vetListaArquivos.add( strFiles[i] );
				}
				
				
			}
			
			output.writeObject( vetListaArquivos );
			output.flush();

			
			// A linha abaixo foi desativada na v3.0
			//CompactadorZip.compactarParaZip(dirATF + "dedoduro.zip", dirATF + "Salvar\\");
			// Fim da desativação na v3.0

			wmiConnect = null;
			vetorInfoServer.clear();
			vetorInfoServer = null;
			
		}

		System.out.println("Fim da conexão !!!");
		
		socket.close();
		output.close();
		input.close();
		
		output = null;
		input = null;
		socket = null;
		
	}catch(IOException e) {
		System.out.println("Erro de E/S");
		e.printStackTrace();
		
	}catch(ClassNotFoundException e) {
		e.printStackTrace();
	}catch(InterruptedException e) {
		e.printStackTrace();
	}catch(Exception e) {
		e.printStackTrace();
	}

	} // fim do construtor

}
