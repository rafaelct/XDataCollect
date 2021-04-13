import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class JanelaErroInfo {

JFrame gui;
TextAreaStatus textAreaStatus;
String titulo = "Informações";
	public JanelaErroInfo() {
		// TODO Auto-generated constructor stub
		
		gui = new JFrame(titulo);
		final JPanel panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));
		gui.setBounds(0, 0, 950, 300);
		
		textAreaStatus = new TextAreaStatus();
		textAreaStatus.reset();
		
		JButton btnConfirma = new JButton("Confirma");
		panel.add( new JScrollPane( textAreaStatus.get() ),"wrap,width 900:900:900,height 200:200:200" );
		panel.add( btnConfirma );
		
		gui.add(panel);
		this.setVisivel(false);
		
		btnConfirma.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui.dispose();
			}
			
		});
		
		
	
	}
	
	public void adicionarTxtAzul( String txt ) {
		textAreaStatus.adicionarBlue(txt);
	}
	
	public void adicionarTxtVerde( String txt ) {
		textAreaStatus.adicionarGreen(txt);
	}
	
	public void adicionarTxtVermelho( String txt ) {
		textAreaStatus.adicionarRed(txt);
	}
	
	public void setVisivel(boolean opcao) {
		gui.setVisible(opcao);
	}
	

}



