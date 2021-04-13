import  java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompactadorZip {
   
   //Constantes
   static final int TAMANHO_BUFFER = 4096; // 4kb
   
   //método para compactar arquivo
   public static void compactarParaZip(String arqSaida,String arqEntrada) throws IOException{
       int cont;
       byte[] dados = new byte[TAMANHO_BUFFER];
                   
       BufferedInputStream origem = null;
       FileInputStream streamDeEntrada = null;
       FileOutputStream destino = null;
       ZipOutputStream saida = null;
       ZipEntry entry = null;
              
       try {
            destino = new FileOutputStream(new File(arqSaida));
            saida = new ZipOutputStream(new BufferedOutputStream(destino));
            File file = new File(arqEntrada);
            if(file.isDirectory()){
                for (File arquivos : file.listFiles()) {
                    streamDeEntrada = new FileInputStream(arquivos);
                    origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
                    entry = new ZipEntry(arquivos.getName());
                    saida.putNextEntry(entry);
                    while((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
                        saida.write(dados, 0, cont);
                    }
                }
            }else{
                streamDeEntrada = new FileInputStream(file);
                origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
                entry = new ZipEntry(file.getName());
                saida.putNextEntry(entry);
                while((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
                    saida.write(dados, 0, cont);
                }
            }
                       
            origem.close();
            saida.close();
        } catch(IOException e) {
            throw new IOException(e.getMessage());
        }
   }
   
   public static void compactarParaZip(String arqSaida,Vector vetListaArquivos,Connect connect) throws IOException{
       int cont;
       byte[] dados = new byte[TAMANHO_BUFFER];
                   
       //BufferedInputStream origem = null;
       InputStream origem = null;
       FileInputStream streamDeEntrada = null;
       FileOutputStream destino = null;
       ZipOutputStream saida = null;
       ZipEntry entry = null;
              
       
       try {
            destino = new FileOutputStream(new File(arqSaida));
            saida = new ZipOutputStream(new BufferedOutputStream(destino));
            
            //File file = new File(arqEntrada);
                //streamDeEntrada = new FileInputStream(file);
            for(int i = 0; i < vetListaArquivos.size(); i++) {
            connect.open();
            Socket socket = connect.getSocket();
            ObjectOutputStream output = connect.getObjOutputStream();
            
            output.writeObject("<getArquivoServer>");
            output.flush();

            output.writeObject( connect.getDirATF() );
			output.flush();
			
			output.writeObject( vetListaArquivos.get(i) );
			output.flush();

            origem = socket.getInputStream();
                //origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
                //entry = new ZipEntry(file.getName());
            	entry = new ZipEntry((String) vetListaArquivos.get(i) );
            	
                saida.putNextEntry(entry);
                
                //cont = origem.read(dados);
                
                while( ( cont = origem.read(dados) ) != -1 ) {
                    saida.write(dados,0,cont);
                    saida.flush();
                    
                }
            
            connect.close();
            
            } // fim do for
            
            origem.close();
            saida.close();
            
            
        } catch(IOException e) {
            e.printStackTrace();
        }
   }
   
        public static void compactarParaZip(String arqSaida,ObjectInputStream input) throws IOException{
            int cont;
            byte[] dados = new byte[TAMANHO_BUFFER];
                        
            BufferedInputStream origem = null;
            FileInputStream streamDeEntrada = null;
            FileOutputStream destino = null;
            ZipOutputStream saida = null;
            ZipEntry entry = null;
                   
            try {
                 destino = new FileOutputStream(new File(arqSaida));
                 saida = new ZipOutputStream(new BufferedOutputStream(destino));
                 
                // File file = new File(arqEntrada);
                 while(true) {
                	 
                	 String nomeArquivo = (String) input.readObject();
                	 
                	 if( nomeArquivo.equals("<EOF>") ) {
                		 //origem.close();
                         saida.close();
                		 break;
                	 }
                	 ArrayList arrayList = (ArrayList) input.readObject();
                 
                	 //if( ! file.getName().equals("<EOF>") ) {
                		//streamDeEntrada = new FileInputStream(file);
                     	//origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
                     	//entry = new ZipEntry(file.getName());
                     	entry = new ZipEntry(nomeArquivo);
                     	saida.putNextEntry(entry);
                     	
                     	for(int i = 0 ; i < arrayList.size() ; i = i+2 ) {
                     	
                     		//while((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
                     		int len = (Integer) arrayList.get(i);
                     		byte[] buf = (byte[]) arrayList.get(i+1);
                     		System.out.println(i + "/" + arrayList.size() );
                     		
                     			//for(int j = 0; j < buf.length ; j++) {
                     			//saida.write(dados, 0, cont);
                     				saida.write(buf);
                     				saida.flush();
                     				//}
                     	
                     	}
                  
                 }      
                 
                 //origem.close();
                 //saida.close();
             } catch(IOException e) {
            	 e.printStackTrace();
             } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   }
   
}

