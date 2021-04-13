import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		byte b[] = new byte[1];
		
		while(true) {
			
		System.out.println("Executando...");
			try {
				
				// Verifica se existe atualização
				
				File fileNew = new File("XDataCollect-new.jar");
				File fileOld = new File("XDataCollect-old.jar");
				File fileAtual = new File("X Data Collect Server.jar");
				
				if( fileNew.exists() ) {
					
					fileAtual.renameTo(new File("XDataCollect-old.jar") );
					
					CopiarArquivo copiarArquivo = new CopiarArquivo();
					copiarArquivo.copiar(fileNew, new File("X Data Collect Server.jar") );
					fileNew.delete();
					
				}
				Process p = Runtime.getRuntime().exec("java -jar \"X Data Collect Server.jar\"");
				InputStream in = p.getInputStream();
				StringBuffer str = new StringBuffer();
				
				Thread.sleep(3000);
				
				int byte_letra = in.read();
				//System.out.println("primeira letra: " + byte_letra);
				
				if( byte_letra != 35 ) {
					System.out.print("Erro ao tentar iniciar Servidor...");
					
					fileAtual.delete();
					CopiarArquivo copiarArquivo = new CopiarArquivo();
					copiarArquivo.copiar(fileOld, new File("X Data Collect Server.jar") );
					System.exit(1);
				}
				
				while( in.read(b) > -1) {
					
				System.out.print(new String(b) );
					
				}
				
								
				System.out.println( p.waitFor() );
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}

}
