

import java.text.Collator;
import java.util.Collections;
import java.util.Vector;

public class WmiConnect {
    /**
     * 
     * @param args
     * @throws Exception
     */
	 
	public synchronized Vector<String> executa (String strQuery,String item_selecionado,String host,String user,String senha) throws Exception {
    	
		//String item = new String();
		Vector<String> vetor = new Vector<String>();
		//Jwmi jwmi = new Jwmi();
		
		try {
			String name = Jwmi.getWMIValue(strQuery,item_selecionado);
			System.out.println(name);
			
			String info[] = name.split("\r\n");
			
			for(int i = 0;i<info.length;i++) {
				info[i] = this.convertAcentosVbs2Java(info[i]);
				vetor.add(info[i]);
				
			}
			
			Collections.sort(vetor,Collator.getInstance() );

			
			//System.out.println(info[2]);
			//System.exit(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
return vetor;
	
    }
	public synchronized String executaStr (String strQuery,String item_selecionado,String host,String user,String senha) throws Exception {
    	
		//String item = new String();
		String name = "";
		//Jwmi jwmi = new Jwmi();
		
		try {
			name = Jwmi.getWMIValue(strQuery,item_selecionado);
			System.out.println(name);
			
			/*
			String info[] = name.split("\n");
			
			for(int i = 0;i<info.length;i++) {
				vetor.add(info[i]);
				
			}
			*/
			//System.out.println(info[2]);
			//System.exit(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name = convertAcentosVbs2Java(name);
return name;
	
    }
	
	private String convertAcentosVbs2Java(String str) {
		
		String resultado = "";
		
		try {
			//String wmi = wmiConnect.executaStr("SELECT * FROM Win32_UserAccount WHERE Name='j16315770'", "FullName", "localhost", "", "");
			//System.out.println(wmi);
			//Character letra = new Character('c');
			//char r[] = letra.toChars(8218);
			
			//System.out.printf("%c",111);
			//String acentosVbs = "";
			
			String acentosVbs = Jwmi.getVBSAcentos();
			String acentosJava = "àáâãäèéêëìíîïòóôõöùúûüç";
			
			//WmiConnect wmiConnect = new WmiConnect();

			//String resultado = "";
			//String resultadoFinal = "";
			
			resultado = str.replace( acentosVbs.charAt(0), acentosJava.charAt(0));

			
			for(int i = 1;i < acentosVbs.length();i++) {
			//System.out.println(acentosVbs.charAt(i)+"Java:"+acentosJava.charAt(i));
				
			resultado = resultado.replace( acentosVbs.charAt(i), acentosJava.charAt(i));
			//resultado = resultado;
			//System.out.println("Resultado parcial:"+ resultado);
			//resultadoFinal = resultado;
			//if(i < acentosVbs.length()) {
			//	resultadoFinal = resultado;
			//}
			}
			
			return resultado;
			//System.out.println("Final:"+ resultadoFinal);
			
			//for(int i = 0;i<str.length();i++) {
			//System.out.println(wmi.codePointAt(i));
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	
}