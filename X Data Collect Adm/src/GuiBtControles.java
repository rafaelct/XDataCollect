import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;


public class GuiBtControles {

	JPanel panel;
	GuiServidores guiServidores;
	GuiCadCmd guiCadCmd;
	
	public GuiBtControles(GuiServidores guiServ,GuiCadCmd guiCmd) {
		// TODO Auto-generated constructor stub
		
		panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));
		guiServidores = guiServ;
		guiCadCmd = guiCmd;
		
		JButton btSetaEsquerda = new JButton();
		JButton btExcluir = new JButton();
		JButton btSetaDireita = new JButton();
		
		Icon iconSetaEsquerda = new ImageIcon( getClass().getResource("seta_esquerda.png") );
		Icon iconSetaDireita = new ImageIcon( getClass().getResource("seta_direita.png") );
		Icon iconErrado = new ImageIcon( getClass().getResource("errado.gif") );
		
		btSetaEsquerda.setIcon(iconSetaEsquerda);
		btSetaDireita.setIcon(iconSetaDireita);
		btExcluir.setIcon( iconErrado );
		
		panel.add(btSetaEsquerda,"gaptop 100,wrap");
		panel.add(btExcluir,"wrap");
		panel.add(btSetaDireita,"wrap");
		
		btSetaEsquerda.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				Vector vetDados = guiCadCmd.getCmdSelecionado();
				
				if( vetDados.size() != 0 ) {
					guiServidores.adicionaComando( vetDados );
				}
			}
			
		});
		
		btSetaDireita.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				Vector vetDados = guiServidores.getCmdSelecionado();
				
				if( vetDados.size() != 0 ) {
					guiCadCmd.adicionaComando( vetDados );
				}
			}
			
		});
		
		btExcluir.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				guiServidores.removeComando();
				
			}
			
		});
	}

	public JPanel getPanel() {
		return panel;
	}

}
