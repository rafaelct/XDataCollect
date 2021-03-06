import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;


public class GuiServidores {

	JPanel panel;
	JComboBox comboServidores;
	ActionListener comboServidoresActionListener;
	Tabela tabela;
	
	public GuiServidores() {
		// TODO Auto-generated constructor stub
		
		panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));

		Icon iconCerto = new ImageIcon( getClass().getResource("certo.gif") );
		
		JLabel lblServidores = new JLabel("Servidores:");
		
		JLabel lblNomeVersao = new JLabel("Vers?o:");
		final JLabel lblVersao = new JLabel("");
		lblVersao.setForeground(Color.blue );
		
		comboServidores = new JComboBox();
		comboServidores.addItem("<Selecione>");

		final JButton btnSalvar = new JButton("Salvar Configura??es");
		btnSalvar.setIcon( iconCerto );
		tabela = new Tabela();
		tabela.init("CS");
		tabela.setVisivel(true);

		final JRadioButton opcaoSalvarTodos = new JRadioButton("Salvar em todos Servidores");
		
		final JRadioButton opcaoSalvar = new JRadioButton("Salvar apenas no servidor " + comboServidores.getSelectedItem() );

		if(comboServidores.getSelectedItem().equals("<Selecione>") ) {
			opcaoSalvar.setVisible(false);
			opcaoSalvarTodos.setSelected(true);
			opcaoSalvar.setSelected(false);
			
		}
		
		panel.add(lblServidores);
		panel.add(comboServidores);
		panel.add(lblNomeVersao);
		panel.add(lblVersao,"wrap 30");
		panel.add(tabela.get(),"width 430:430:430,height 200:200:200,wrap");
		panel.add(opcaoSalvarTodos,"wrap");
		panel.add(opcaoSalvar,"wrap");
		panel.add(btnSalvar);
		
		
		btnSalvar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				Vector vetorServidores = new Vector(); // guarda lista de servidores a consultar
				Vector vetorInfoServer = new Vector(); // guarda informa?oes do servidor consultado
				
				ArrayList<ConnectThreadAdm> arrayNet = new ArrayList<ConnectThreadAdm>();
				ArrayList<Thread> arrayTarefa = new ArrayList<Thread>();

			//	if( comboServidores.getSelectedItem().equals("<Selecione>") ) {
				if( opcaoSalvarTodos.isSelected() ) {
					
					Configuracoes configuracoes = new Configuracoes(); // abre configuracoes
					
					configuracoes.lerConf(); // le as configuracoes

					// Consulta todos os servidores especificados na configuracao
					vetorServidores = configuracoes.getInfoServidores(); // pega informacao da configuracao

					for(int linha = 0 ; linha < vetorServidores.size() ; linha = linha + 2 ) {
						
						
						ConnectThreadAdm net = new ConnectThreadAdm( (String) vetorServidores.get(linha),(String) vetorServidores.get(linha+1) );
						net.gravaConfigServer( tabela.getDados() );
						
						Thread tarefa = new Thread(net);
						tarefa.start();
						
						//while(tarefa.isAlive() ) {
						
						//}
					
						arrayTarefa.add(tarefa);
					
						arrayNet.add(net);
					
						//Vector vetorInfoServer = new Vector();
						
					}
					
					try{
						Thread.sleep(1);
					}catch(InterruptedException e1) {
						e1.printStackTrace();
					}
					
					JanelaErroInfo janelaErroInfo = new JanelaErroInfo();
					
					
					//textAreaStatus.reset();
					//textAreaStatus.adicionarGreen("Coletando informa??es , Aguarde...");
					// Verifica as tarefas que ainda estao em execucao
					// aguarda finalizacao de todas para continuar.	
					
					int progressoIndice = 0;
					
					while(arrayTarefa.size() > 0) {
						
						
						for(int i = 0; i < arrayTarefa.size(); i++ ) {
							
							//areaStatus.updateUI();
							//gui.repaint(0, 0, 1024, 700);
							Thread tarefa = arrayTarefa.get(i);
							if( ! tarefa.isAlive() ) { 
								arrayTarefa.remove(i);
								i = 0;
							}
							
						}
						
					}
					
					// Pega as informacoes coletadas e exibe na tela.
					int indice = 0;
					int codErro = 0;
					
					for(int i = 0 ; i < arrayNet.size() ; i++) {
					
						ConnectThreadAdm net = arrayNet.get(i);
					
						codErro = net.returnCod();
						
						if(codErro != 0 ) {
							janelaErroInfo.adicionarTxtVermelho("\n !!! Falha de comunica??o com Servidor " + (String) vetorServidores.get(indice) );
						} else {
							janelaErroInfo.adicionarTxtAzul("\nConfigura??es Gravadas com sucesso no servidor " + net.getNomeServidor() );
						}
						
						indice = indice + 2;
						
					}
					
					janelaErroInfo.setVisivel(true);


				} else {
					
				
					Vector vetorDadosTabela = tabela.getDados();
					String server = (String) comboServidores.getSelectedItem();
					String ipHostServer[] = server.split("-");
					
					ConnectAdm net = new ConnectAdm( ipHostServer[1] );
					
					net.gravaConfigServer(vetorDadosTabela);
					int codErro = net.codRetorno();
					
					if( codErro != 0 ) {
						JOptionPane.showMessageDialog(panel, "!!! Falha de comunica??o com Servidor " + ipHostServer[0] , "Erro: Servidor", JOptionPane.ERROR_MESSAGE );
					} else {
						JOptionPane.showMessageDialog(panel, "Configura??es Gravadas com sucesso no servidor " + ipHostServer[0], "Informa??o: Servidor",JOptionPane.INFORMATION_MESSAGE );
					}
				}

				
			}
				
				
				
			
		});

		comboServidoresActionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Tipo de evento: "+ e.getActionCommand());
				
				//if( comboServidores.getSelectedItem().equals("<Selecione>") ) {
				if(comboServidores.getSelectedIndex() == 0 ) {
					opcaoSalvar.setVisible(false);
					opcaoSalvarTodos.setSelected(true);
					opcaoSalvar.setSelected(false);
					btnSalvar.setText("Salvar Configura??es");

				} else {
					
					tabela.removeAllLinhas();
					
					String server = (String) comboServidores.getSelectedItem();
					String ipHostServer[] = server.split("-");
					
					ConnectAdm net = new ConnectAdm( ipHostServer[1] );
					
					lblVersao.setText( net.versaoServidor() );

					Vector vetConfigServidores = net.lerConfigServer();
					
					int codErro = net.codRetorno();
					
					if( codErro != 0 ) {
						JOptionPane.showMessageDialog(panel,"Erro ao obter informa??es de configura??o do servidor.","Erro", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					/*
					if(vetConfigServidores.size() == 0 ) {
						Vector vetor = new Vector();
						
						vetor.add("");
						vetor.add("");
						
						tabela.adicionaLinha(vetor);
						vetor.clear();
						
						return;
					}
										
					*/
					
					for(int i = 0; i < vetConfigServidores.size();i=i+2) {
						
						Vector vetor = new Vector();
						
						vetor.add( (String) vetConfigServidores.get(i) );
						vetor.add( (String) vetConfigServidores.get(i+1) );
						
						tabela.adicionaLinha(vetor);
						vetor = null;
					}
					
					String strServidorSelecionado[] = ((String) comboServidores.getSelectedItem()).split("-");
					opcaoSalvar.setText("Salvar apenas no servidor " + strServidorSelecionado[0] );
					opcaoSalvar.setVisible(true);
					
					//btnSalvar.setText("Salvar Configura??es em " + strServidorSelecionado[0] );
					
				}

				
			}

			
		};
		
		comboServidores.addActionListener(comboServidoresActionListener);
		
		opcaoSalvarTodos.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

								
				opcaoSalvarTodos.setSelected(true);
				opcaoSalvar.setSelected(false);
				//String strServidorSelecionado[] = ((String) comboServidores.getSelectedItem()).split("-");
				btnSalvar.setText("Salvar Configura??es");

			}
			
		});

		opcaoSalvar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				opcaoSalvarTodos.setSelected(false);
				opcaoSalvar.setSelected(true);
				String strServidorSelecionado[] = ((String) comboServidores.getSelectedItem()).split("-");
				btnSalvar.setText("Salvar Configura??es em " + strServidorSelecionado[0] );

				
			}
			
		});

	}

	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return panel;
	}

	public JComboBox getComboServidores() {
		// TODO Auto-generated method stub
		return comboServidores;
	}

	public ActionListener getComboActionListenerServidores() {
		// TODO Auto-generated method stub
		return comboServidoresActionListener;
	}

	public void adicionaComando(Vector vetCmd) {
		// TODO Auto-generated method stub
		tabela.adicionaLinha(vetCmd);
		
	}

	public Vector getCmdSelecionado() {
		// TODO Auto-generated method stub
		Vector vetDados = tabela.getDadosSelecionados();
		
		// Se selecionou a linha em branco ent?o limpa vetDados
		if(vetDados.size() == 2 && vetDados.get(0).equals("") && vetDados.get(1).equals("") ) {
			vetDados.clear();
		}
		
		return vetDados;

	}

	public void removeComando() {
		// TODO Auto-generated method stub
		tabela.removeLinha(tabela.getItemSelecionado(),true);
		
	}

}
