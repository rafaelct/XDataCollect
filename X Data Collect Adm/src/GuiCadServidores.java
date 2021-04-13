import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;


public class GuiCadServidores {

	JPanel panel;
	JComboBox comboServidores;
	ActionListener comboServidoresActionListener;
	
	Vector vetorInfoServidores;
	
	public GuiCadServidores(JComboBox cmbServidores,ActionListener cmbServidoresActionListener ) {
		// TODO Auto-generated constructor stub
		
		comboServidores = cmbServidores;
		comboServidoresActionListener = cmbServidoresActionListener;
		
		panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));

		JLabel lbCadastrarServer = new JLabel("Cadastrar Servidores");
		JLabel lblAtualizarServidores = new JLabel("Atualizar X Data Collect Server (*.jar):");
		
		JLabel lbServidor1 = new JLabel("Nome do Servidor: ");
		JLabel lbhostip1 = new JLabel("Hostname/IP:");
		final JTextField entryServidor1 = new JTextField(25);
		final JTextField entryHostip1 = new JTextField(10);
		
		
		//JLabel lbDiretorioATF = new JLabel("Pasta onde estão arquivos ATF no servidor: ");
		
		//final JTextField entryDiretorioATF = new JTextField(25);
		
		
		
		final Tabela tabelaServidores = new Tabela();
		tabelaServidores.init("CS");
		tabelaServidores.setVisivel(true);
		
		
		final Configuracoes configuracoes = new Configuracoes();
		
		configuracoes.lerConf();
		
		vetorInfoServidores = new Vector();

		
		vetorInfoServidores = configuracoes.getInfoServidores();

		//entryDiretorioATF.setText(configuracoes.getDiretorioATF() );
		
		Vector vetorDados = new Vector();
		
		for(int linha = 0;linha < vetorInfoServidores.size();linha = linha+2) {
			
			vetorDados.add( (String) vetorInfoServidores.get(linha) );
			vetorDados.add( (String) vetorInfoServidores.get(linha+1) );
			
			tabelaServidores.adicionaLinha(vetorDados);
			comboServidores.addItem( vetorInfoServidores.get(linha) + "-" + vetorInfoServidores.get(linha+1) );
			vetorDados.clear();
			
		}
		
		JButton botaoAdicionar = new JButton("Adiciona");
		JButton botaoExcluir = new JButton("Excluir Servidor");
		JButton botaoConfirma = new JButton("Confirma");
		JButton botaoAtualizaServidores = new JButton("Atualizar X Data Collect Server...");
				
		panel.add(lbCadastrarServer,"wrap");
		panel.add(lbServidor1);
		panel.add(entryServidor1);
		panel.add(lbhostip1);
		panel.add(entryHostip1);
		panel.add(botaoAdicionar,"wrap");
		

		panel.add(tabelaServidores.get(),"width 500:500:500,height 200:200:200");
		panel.add(botaoExcluir,"wrap");
		//panelAbaServidores.add(lbDiretorioATF);
		//panelAbaServidores.add(entryDiretorioATF,"wrap");
		panel.add(botaoConfirma,"wrap");
		panel.add(lblAtualizarServidores);
		panel.add(botaoAtualizaServidores);

		botaoAtualizaServidores.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos jar","jar");
				
				fileChooser.setFileFilter( filter );
				
				
				int codigoRetorno = fileChooser.showOpenDialog(null);
				
				//if( codigoRetorno != JFileChooser)
				File arquivoJar = fileChooser.getSelectedFile();
				
				if(arquivoJar == null) {
					return;
				}
				
				int resposta = JOptionPane.showConfirmDialog(panel, "Essa operação causara o reboot do X Data Collect Server em todos servidores.\nTem certeza que deseja continuar ?", "Informação: Atualização", JOptionPane.INFORMATION_MESSAGE );
				
				if( resposta != JOptionPane.OK_OPTION ) {
					return;
				}
				if( ! arquivoJar.exists() ) {
					JOptionPane.showMessageDialog(null, "ERRO: Arquivo " + arquivoJar.getAbsolutePath() + " não existe.", "Erro: Arquivo", JOptionPane.ERROR_MESSAGE );
				}
				
				if( ! arquivoJar.canRead() ) { 
					
					JOptionPane.showMessageDialog(null, "ERRO: Arquivo " + arquivoJar.getAbsolutePath() + " não pode ser lido.", "Erro: Arquivo", JOptionPane.ERROR_MESSAGE );
					
				}
				
				// Atualizar arquivo JAR do X Data Collect Server em todos servidores.
				
				Vector vetorServidores = new Vector(); // guarda lista de servidores a consultar
				Vector vetorInfoServer = new Vector(); // guarda informaçoes do servidor consultado
				
				ArrayList<ConnectThreadAdm> arrayNet = new ArrayList<ConnectThreadAdm>();
				ArrayList<Thread> arrayTarefa = new ArrayList<Thread>();

				Configuracoes configuracoes = new Configuracoes(); // abre configuracoes
				
				configuracoes.lerConf(); // le as configuracoes

				// Consulta todos os servidores especificados na configuracao
				vetorServidores = configuracoes.getInfoServidores(); // pega informacao da configuracao

				for(int linha = 0 ; linha < vetorServidores.size() ; linha = linha + 2 ) {
					
					
					ConnectThreadAdm net = new ConnectThreadAdm( (String) vetorServidores.get(linha),(String) vetorServidores.get(linha+1) );
					net.AtualizaServer( arquivoJar.getAbsolutePath() );
					
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
				//textAreaStatus.adicionarGreen("Coletando informações , Aguarde...");
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
						janelaErroInfo.adicionarTxtVermelho("\n !!! Falha de comunicação com Servidor " + (String) vetorServidores.get(indice) );
					} else {
						janelaErroInfo.adicionarTxtAzul("\nServidor " + net.getNomeServidor() + " Atualizado com sucesso , com a versão " + net.getVersaoServidor() );
					}
					
					indice = indice + 2;

				}
				
				janelaErroInfo.setVisivel(true);
				//checaVersoes();

			}
			
		});
		
	botaoConfirma.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				int respostaUsuario = JOptionPane.showConfirmDialog(null,"Deseja salvar as configurações ?");
				
				if ( respostaUsuario == 0 ) {
				
					Vector vetorInfoServidores = new Vector();
					Vector vetorInfoServerCMP = new Vector();
					
					vetorInfoServidores = tabelaServidores.getDados();
					vetorInfoServerCMP = vetorInfoServidores;
					
					// Atualiza comboBox da janela principal
					
					//vetComboServidores.add("<Todos>");
					
					
					//for(int i = 0 ; i < comboServidores.getComponentCount() ;i++) {
					//comboServidores.disable();
					comboServidores.removeActionListener(comboServidoresActionListener);
					comboServidores.removeAllItems();
					comboServidores.addItem("<Selecione>");
					//comboServidores.enable();
					
					while( comboServidores.getItemCount() > 1) {
						
						//if( ! comboServidores.getItemAt(i).equals("<Selecione>") ) {
							comboServidores.removeItemAt(1);
						//}
						
					}
					
					
					//comboServidores.removeAllItems();
					//comboServidores.addItem("<Selecione>");
					
					//comboServidores.addItem("<SELECIONE>");
					for(int i = 0; i < vetorInfoServidores.size();i=i+2) {
					
						comboServidores.addItem( (String) vetorInfoServidores.get(i) + "-" + (String) vetorInfoServidores.get(i+1) );
						
						
					}
					comboServidores.addActionListener(comboServidoresActionListener);
					
					// Grava as configurações
					configuracoes.gravaConf(vetorInfoServidores," " );
				
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
				
				tabelaServidores.adicionaLinha(vetorServer);

			}
		});
		botaoExcluir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				tabelaServidores.removeLinha(tabelaServidores.getItemSelecionado(),true);
				
					
			}
			
		});

		// Faz verificação se todos os servidores estão com a mesma versão do X Data Collect Server
		checaVersoes();

	}
	
	public void checaVersoes() {
		
		
		Vector vetorServidores = new Vector(); // guarda lista de servidores a consultar
		Vector vetorInfoServer = new Vector(); // guarda informaçoes do servidor consultado
		
		ArrayList<ConnectThreadAdm> arrayNet = new ArrayList<ConnectThreadAdm>();
		ArrayList<Thread> arrayTarefa = new ArrayList<Thread>();

		Configuracoes configuracoes = new Configuracoes(); // abre configuracoes
		
		configuracoes.lerConf(); // le as configuracoes

		// Consulta todos os servidores especificados na configuracao
		vetorServidores = configuracoes.getInfoServidores(); // pega informacao da configuracao

		for(int linha = 0 ; linha < vetorServidores.size() ; linha = linha + 2 ) {
			
			
			ConnectThreadAdm net = new ConnectThreadAdm( (String) vetorServidores.get(linha),(String) vetorServidores.get(linha+1) );
			net.versaoServidor();
			
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
		//textAreaStatus.adicionarGreen("Coletando informações , Aguarde...");
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
		
		boolean versoesDiferentes = false;
		
		janelaErroInfo.adicionarTxtVermelho("!!! ATENÇÃO X DATA COLLECT SERVER COM VERSÕES DIFERENTES !!!");
		
		String versaoAtual = "";
		String versao = "";
		for(int i = 0 ; i < arrayNet.size() ; i++) {
		
			ConnectThreadAdm net = arrayNet.get(i);
			
			
			
			codErro = net.returnCod();
			
			versaoAtual = net.getVersaoServidor();
			
			if(codErro != 0 ) {
				janelaErroInfo.adicionarTxtVermelho("\n !!! Falha de comunicação com Servidor " + (String) vetorServidores.get(indice) );
			} else {
				janelaErroInfo.adicionarTxtAzul("\nServidor " + net.getNomeServidor() + " esta com a versão " + versaoAtual );
			}
			
			//if( net.getVersaoServidor() != null ) {
			
			try {
			
				if( codErro == 0 && ! versao.equals("") ) {
					
					if( ! versao.equals( versaoAtual ) ) {
						
						versoesDiferentes = true;
						
					}
			
				}
				
			versao = versaoAtual;
			
			} catch( NullPointerException e ) {
				//versoesDiferentes = true;
				versao = "";
			}
			
			indice = indice + 2;
			
		} // Fim do loop for
		
		try {
			
			if( codErro == 0 && ! versao.equals("") ) {
				
				if( ! versao.equals( versaoAtual ) ) {
					
					versoesDiferentes = true;
					
				}
		
			}
			
		versao = versaoAtual;
		
		} catch( NullPointerException e ) {
			//versoesDiferentes = true;
			versao = "";
		}

		
		if( versoesDiferentes ) {
			janelaErroInfo.setVisivel(true);
		}

	
	
	}

	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return panel;
	}

	

}
