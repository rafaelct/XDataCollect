import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


public class GuiCadCmd {

	JPanel panel;
	Tabela tabelaCmd;
	
	public GuiCadCmd() {
		// TODO Auto-generated constructor stub

		panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));

		JButton btnCmdAdd = new JButton();
		JButton btnCmdDel = new JButton();
		
		Icon iconCerto = new ImageIcon( getClass().getResource("certo.gif") );
		Icon iconErrado = new ImageIcon( getClass().getResource("errado.gif") );
		
		btnCmdAdd.setIcon( iconCerto );
		btnCmdDel.setIcon( iconErrado );
				
		JLabel lblCmdDesc = new JLabel("Descri??o:");
		final JTextField txtCmdDesc = new JTextField();
		
		JLabel lblCmdExec = new JLabel("Comando:");
		final JTextField txtCmdExec = new JTextField();
		

		tabelaCmd = new Tabela();
		
		tabelaCmd.init("CS");
		tabelaCmd.setVisivel(true);

		panel.add(lblCmdDesc);
		panel.add(txtCmdDesc,"width 300:300:300,height 20:20:20,wrap");

		panel.add(lblCmdExec);
		panel.add(txtCmdExec,"width 200:200:200,height 20:20:20");
		panel.add(btnCmdAdd);
		panel.add(btnCmdDel,"wrap");
		
		panel.add(tabelaCmd.get(),"width 430:430:430,height 200:200:200,wrap");

		btnCmdAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				
				if(txtCmdDesc.getText().trim().equals("") || txtCmdExec.getText().trim().equals("") ) {
				
					JOptionPane.showMessageDialog(null, "Campos Descri??o e/ou Comando n?o podem estar vazios.", "Erro: Valida??o de campos", JOptionPane.ERROR_MESSAGE );
					return;
					
				}

				Vector vetorDados = new Vector();
				vetorDados.add( txtCmdDesc.getText() );
				vetorDados.add( txtCmdExec.getText() );
				tabelaCmd.adicionaLinha(vetorDados);
				
				txtCmdDesc.setText("");
				txtCmdExec.setText("");
				
			}
			
		});
		
		btnCmdDel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tabelaCmd.removeLinha(tabelaCmd.getItemSelecionado(),true);
			}
				
				
				
			
		});		

	}

	public JPanel getPanel() {
		return panel;
	}

	public Vector getCmdSelecionado() {
		// TODO Auto-generated method stub
		Vector vetDados = tabelaCmd.getDadosSelecionados();
		
		// Se selecionou a linha em branco ent?o limpa vetDados
		if(vetDados.size() == 2 && vetDados.get(0).equals("") && vetDados.get(1).equals("") ) {
			vetDados.clear();
		}
		
		return vetDados;
	}

	public void adicionaComando(Vector vetCmd) {
		// TODO Auto-generated method stub
		tabelaCmd.adicionaLinha(vetCmd);
		
	}

}
