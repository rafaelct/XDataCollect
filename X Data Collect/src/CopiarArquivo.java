import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;



public class CopiarArquivo {

	public void copiar(File src,File dst) {
		
		InputStream in;
		OutputStream out;
		try {
			in = new FileInputStream(src);
		out = new FileOutputStream(dst);           // Transferindo bytes de entrada para saída
		byte[] buf = new byte[1024];
		int len;
		
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		
		in.close();
		out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

 }
	
	public void copiar(Socket s,File dst) {
		
		InputStream in;
		OutputStream out;
		//long tamanhoRecebido = 0;
		
		try {
			in = s.getInputStream();
			out = new FileOutputStream(dst);           // Transferindo bytes de entrada para saída
			byte[] buf = new byte[1024];
			int len = 0;
			//int byte_lido = 0;
			
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
				out.flush();
			}

			in.close();
			out.close();
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}

	}
	

	public void copiar(File src,Socket s) {
		
		InputStream in;
		OutputStream out;
		//long tamanhoEnviado = 0;
		
		try {
			in = new FileInputStream(src);
			out = s.getOutputStream();      // Transferindo bytes de entrada para saída
			byte[] buf = new byte[1024];
			int len = 0;
			//int byte_lido = 0;
			
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
				out.flush();
			}
		
			in.close();
			out.close();
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}

	}


}
