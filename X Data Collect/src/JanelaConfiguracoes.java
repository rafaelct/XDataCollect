import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import net.miginfocom.swing.MigLayout;


public class JanelaConfiguracoes {

	private JPanel panel;
	private Vector vetorInfoServidores;
	private JComboBox combo;

	public JanelaConfiguracoes(boolean inicial,JComboBox comboServidores ) {
		// TODO Auto-generated constructor stub
		
		JFrame gui = new JFrame("Configurações");
		combo = comboServidores;
		
		panel = new JPanel();
		
		panel.setLayout(new MigLayout("nogrid"));
		
		JLabel lbCadastrarServer = new JLabel("Cadastrar Servidores");
		
		JLabel lbServidor1 = new JLabel("Nome do Servidor: ");
		JLabel lbhostip1 = new JLabel("Hostname/IP:");
		final JTextField entryServidor1 = new JTextField(25);
		final JTextField entryHostip1 = new JTextField(10);
		
		JLabel lbDiretorioATF = new JLabel("Pasta onde estão arquivos ATF no servidor: ");
		
		final JTextField entryDiretorioATF = new JTextField(25);
		
		
		
		final Tabela tabela = new Tabela();
		tabela.init("CS");
		tabela.setVisivel(true);
		
		
		final Configuracoes configuracoes = new Configuracoes();
		
		configuracoes.lerConf();
		
		vetorInfoServidores = new Vector();

		
		vetorInfoServidores = configuracoes.getInfoServidores();

		entryDiretorioATF.setText(configuracoes.getDiretorioATF() );
		
		Vector vetorDados = new Vector();
		
		for(int linha = 0;linha < vetorInfoServidores.size();linha = linha+2) {
			
			vetorDados.add( (String) vetorInfoServidores.get(linha) );
			vetorDados.add( (String) vetorInfoServidores.get(linha+1) );
			
			tabela.adicionaLinha(vetorDados);
			vetorDados.clear();
			
		}
		
		JButton botaoAdicionar = new JButton("Adiciona");
		JButton botaoExcluir = new JButton("Excluir Servidor");
		JButton botaoConfirma = new JButton("Confirma");
		
		panel.add(lbCadastrarServer,"wrap");
		panel.add(lbServidor1);
		panel.add(entryServidor1);
		panel.add(lbhostip1);
		panel.add(entryHostip1);
		panel.add(botaoAdicionar,"wrap");
		

		panel.add(tabela.get(),"width 500:500:500,height 200:200:200");
		panel.add(botaoExcluir,"wrap");
		panel.add(lbDiretorioATF);
		panel.add(entryDiretorioATF,"wrap");
		panel.add(botaoConfirma,"wrap");

		gui.add(panel);
		
		gui.setBounds(0,0,800,600);
		
		gui.setVisible(true);
		
	botaoConfirma.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				int respostaUsuario = JOptionPane.showConfirmDialog(null,"Deseja salvar as configurações ?");
				
				if ( respostaUsuario == 0 ) {
				
					Vector vetorInfoServidores = new Vector();
					Vector vetorInfoServerCMP = new Vector();
					
					vetorInfoServidores = tabela.getDados();
					vetorInfoServerCMP = vetorInfoServidores;
					
					// Atualiza comboBox da janela principal
					
					//vetComboServidores.add("<Todos>");
					combo.removeAllItems();
					combo.addItem("<Todos>");
					for(int i = 0; i < vetorInfoServidores.size();i=i+2) {
					
						combo.addItem( (String) vetorInfoServidores.get(i) + "-" + (String) vetorInfoServidores.get(i+1) );
						
						
					}
					
					// Grava as configurações
					configuracoes.gravaConf(vetorInfoServidores,entryDiretorioATF.getText() );
				
				}
								
			}
			
		});

		
		botaoAdicionar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				
				Vector vetorServer = new Vector();
				
				vetorServer.add(entryServidor1.getText());
				vetorServer.add(entryHostip1.getText());
				
				// Laço for para verificar se o nome dos servidores e 
				// Hostname/IP definidos possuem caracteres invalidos 
				// \/:*?"<>|
				
				StringBuffer strNaoPode = new StringBuffer("\\/:*?\"<>|-");

				for(int i = 0 ; i < vetorServer.size(); i++ ) {
					
					StringBuffer str = new StringBuffer( (String) vetorServer.get(i) );
				
					for(int j = 0 ; j < strNaoPode.length() ; j++) {
						
						if(str.toString().lastIndexOf(strNaoPode.charAt(j)) != -1 ) {
							
							JOptionPane.showMessageDialog(panel, "O nome dos servidores e Hostname/IP não podem conter os caracteres \\/:*?\"<>|-","Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}
					
					}
				
				
				str = null;
				
				}
				
				tabela.adicionaLinha(vetorServer);

			}
		});
		botaoExcluir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				tabela.removeLinha(tabela.getItemSelecionado(),true);
				
					
			}
			
		});
	}

}
