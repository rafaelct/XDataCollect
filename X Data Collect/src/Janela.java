import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import net.miginfocom.swing.MigLayout;

public class Janela {

	JButton botaoExecuta;
	JTextArea areaStatus,areaResultado;
	TextAreaStatus textAreaStatus;
	JLabel lbProgresso;
	MyTask myTask;
	MyTaskProgresso myTaskProgresso;
	String titulo = "XDC - X Data Collect v3.0";
	// Alterado na v3.0
	JComboBox comboServidores;
	String diretorioATFLocalStr;
	// fim da alteração v3.0
	
	public Janela() {
		// TODO Auto-generated constructor stub
		
		final JFrame gui = new JFrame(titulo);
		final JPanel panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setBounds(0, 0, 1024, 700);
		
		Icon iconLog = new ImageIcon( getClass().getResource("log.png") );
		Icon iconPesq = new ImageIcon( getClass().getResource("pesquisa.png") );
		
		
		//botaoExecuta = new JButton("Pegar informações dos servidores");
		botaoExecuta = new JButton("Efetuar Coleta");
		botaoExecuta.setIcon( iconLog );
		
		JButton btnPesqDirLog = new JButton();
		btnPesqDirLog.setIcon( iconPesq );
		
		JLabel labelStatus = new JLabel("Status:");
		final JLabel labelResultado = new JLabel("Resultado:");
		lbProgresso = new JLabel("");
		
		areaStatus = new JTextArea();
		areaResultado = new JTextArea();
		
		JLabel lblDirLog = new JLabel("pasta de logs coletados:");
		
		JTextField txtDirLog = new JTextField();
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Ferramentas");
		JMenu menuAjuda = new JMenu("Ajuda");
		
		JMenuItem configuracoes = new JMenuItem("Configurações...");
		//JMenuItem relatorioUsuariosScheduller = new JMenuItem("Visualizar Relatorio de usuarios Schedulados...");
		//JMenuItem relatorioUsuariosAtivos = new JMenuItem("Visualizar Relatorio de usuarios ativos");
		
		JMenuItem manualUsuario = new JMenuItem("Manual do usuario");
		JMenuItem sobre = new JMenuItem("Sobre...");
		
		//menu.add(relatorioUsuariosScheduller);
		//menu.add(relatorioUsuariosAtivos);
		menu.add(configuracoes);
		
		menuAjuda.add(manualUsuario);
		menuAjuda.add(sobre);
		
		menuBar.add(menu);
		menuBar.add(menuAjuda);
		
		gui.setJMenuBar(menuBar);

		textAreaStatus = new TextAreaStatus();
		
		// Alteração efetuada em 17/05/2013 - v3.0
		
		Configuracoes config = new Configuracoes();
		
		config.lerConf();
		
		Vector vetListaServidores = config.getInfoServidores();
		
		Vector vetComboServidores = new Vector();
		
		vetComboServidores.add("<Todos>");
		
		for(int i = 0; i < vetListaServidores.size();i=i+2) {
		
			vetComboServidores.add( (String) vetListaServidores.get(i) + "-" + (String) vetListaServidores.get(i+1) );
			
		}
		JLabel lblServidores = new JLabel("Servidores:");
		
		comboServidores = new JComboBox(vetComboServidores);
		// fim da alteração v3.0
		
		// Teste
		
		Vector vetListaGrupos = new Vector();
		
		vetListaGrupos.add("FONE");
		vetListaGrupos.add("CARTAO");
		vetListaGrupos.add("DESENVOLVIMENTO");
		vetListaGrupos.add("DESENVOLVIMENTO1");
		vetListaGrupos.add("DESENVOLVIMENTO2");
		
		
		// fim do teste
		GuiSelGruposServidores guiSelGruposServidores = new GuiSelGruposServidores(vetListaGrupos,vetListaServidores);
		
		panel.add(labelStatus,"wrap");
		panel.add(new JScrollPane( textAreaStatus.get() ),"wrap,width 900:900:900,height 150:150:150");
		panel.add(labelResultado,"wrap");
		panel.add(new JScrollPane(areaResultado),"wrap,width 900:900:900,height 150:150:150");
		//Alterado em 17/05/2013 - v3.0
		
		//panel.add(lblServidores);
		//panel.add(comboServidores,"wrap");
		//fim da alteração v3.0
		
		panel.add(guiSelGruposServidores.getPanel(),"wrap" );
		panel.add(lblDirLog);
		panel.add(txtDirLog,"width 350:350:350,height 20:20:20");
		panel.add(btnPesqDirLog);
		panel.add(botaoExecuta,"gapleft 150,wrap");
		panel.add(lbProgresso);
		

		
		gui.add(panel);
		
		
		gui.setVisible(true);
		

		configuracoes.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				JanelaConfiguracoes guiConf = new JanelaConfiguracoes(true,comboServidores);
				
			}
			
		});
		
		manualUsuario.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Desktop desktop = Desktop.getDesktop();
				
				File arquivoDoc = new File("doc.pdf");
				
				try{
				desktop.open(arquivoDoc);
				}catch(IOException ex) {
					ex.printStackTrace();
				}
				
			
			}
		});

		
		sobre.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(panel, titulo + "\n\nDesenvolvido por Rafael Costa Teixeira","Informação", JOptionPane.INFORMATION_MESSAGE);

				
			}
			
		});
		
		botaoExecuta.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(comboServidores.getSelectedItem().toString().equals("<Todos>") ) {
					int respostaUsuario = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja pegar informações de todos os servidores ?");
					
					if(respostaUsuario != 0 ) {
						return;
					}
					
				}

				// Abre caixa de dialogo para escolher aonde serão salvos os arquivos ATF.
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int codigoRetorno = fileChooser.showOpenDialog(null);
				
				if( codigoRetorno == JFileChooser.CANCEL_OPTION ) {
					JOptionPane.showMessageDialog(null, "Você deve escolhar uma pasta aonde serão salvos os arquivos ATF", "Erro: ao selecionar pasta", JOptionPane.ERROR_MESSAGE );
					return;
				}
				
				File diretorioATFLocal = fileChooser.getSelectedFile();
				
				diretorioATFLocalStr = diretorioATFLocal.toString();
				
				if( diretorioATFLocalStr.charAt(diretorioATFLocalStr.length()-1 ) != '\\' ) {
					
					diretorioATFLocalStr = diretorioATFLocalStr + "\\";
					
					System.out.println("Nao tinha barra");
					System.out.println(diretorioATFLocalStr);
					//return;
					
				}

				System.out.println("Teste");
				
				// recortado trecho que estava aqui para SwingWorker
				
				
				myTask = new MyTask();
				myTask.execute();
				
				myTaskProgresso = new MyTaskProgresso();
				myTaskProgresso.execute();
	
			
				//new MyTaskProgresso().execute();
				
				botaoExecuta.setEnabled(false);
			}
			
		});
		
		
	}
	
	private class MyTask extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			
			// Trecho transferido para o botão Executar - v3.0
			
			/*
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int codigoRetorno = fileChooser.showOpenDialog(null);
			
			if( codigoRetorno == JFileChooser.CANCEL_OPTION ) {
				JOptionPane.showMessageDialog(null, "Você deve escolhar uma pasta aonde serão salvos os arquivos ATF", "Erro: ao selecionar pasta", JOptionPane.ERROR_MESSAGE );
				return null;
			}
			
			File diretorioATFLocal = fileChooser.getSelectedFile();
			
			diretorioATFLocalStr = diretorioATFLocal.toString();
			
			if( diretorioATFLocalStr.charAt(diretorioATFLocalStr.length()-1 ) != '\\' ) {
				
				diretorioATFLocalStr = diretorioATFLocalStr + "\\";
				
				System.out.println("Nao tinha barra");
				System.out.println(diretorioATFLocalStr);
				//return;
				
			}
			*/
			// Fim do Trecho transferido para o botão Executar - v3.0
			
			Date data = new Date();
			String dataStr;
			
			Integer integer = new Integer( data.getDay() );
			dataStr = new Integer( data.getDate() ).toString() + "-" + new Integer( data.getMonth() + 1 ).toString() + "-" + new Integer( data.getYear() + 1900 ).toString() + "--" + new Integer( data.getHours() ).toString() + "-" + new Integer( data.getMinutes() ).toString() + "-" + new Integer( data.getSeconds() ).toString(); 
						
			File fileDir = new File(diretorioATFLocalStr + dataStr);
			fileDir.mkdir();
			diretorioATFLocalStr = diretorioATFLocalStr + dataStr + "\\";
			
			
			Vector vetorServidores = new Vector(); // guarda lista de servidores a consultar
			Vector vetorInfoServer = new Vector(); // guarda informaçoes do servidor consultado
			
			ArrayList<ConnectThread> arrayNet = new ArrayList<ConnectThread>();
			ArrayList<Thread> arrayTarefa = new ArrayList<Thread>();
			
							
			
			Configuracoes configuracoes = new Configuracoes(); // abre configuracoes
			
			configuracoes.lerConf(); // le as configuracoes
			
			if(comboServidores.getSelectedItem().toString().equals("<Todos>") ) {
			
				// Consulta todos os servidores especificados na configuracao
				vetorServidores = configuracoes.getInfoServidores(); // pega informacao da configuracao
				
			} else {
				String[] strServList = comboServidores.getSelectedItem().toString().split("-");
				vetorServidores.add( (String) strServList[0] );
				vetorServidores.add( (String) strServList[1] );
			}
			
			for(int linha = 0 ; linha < vetorServidores.size() ; linha = linha + 2 ) {
				
				
				ConnectThread net = new ConnectThread( (String) vetorServidores.get(linha),(String) vetorServidores.get(linha+1) );
				net.getInfoServer(configuracoes.getDiretorioATF(),diretorioATFLocalStr );
				
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
			textAreaStatus.reset();
			textAreaStatus.adicionarGreen("Coletando informações , Aguarde...");
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
			
			for(int i = 0 ; i < arrayNet.size() ; i++) {
			
				ConnectThread net = arrayNet.get(i);
			
				vetorInfoServer = net.returnVector();
				//System.out.println(vetorInfoServer.size());
			
				//while(vetorInfoServer.size() < 7) {
				//vetorInfoServer = net.returnVector();
				//System.out.println(vetorInfoServer.size());
				//}
				if( vetorInfoServer != null && ! vetorInfoServer.get(0).equals("<ERRO-4>") && ! vetorInfoServer.get(0).equals("<ERRO-3>")&& ! vetorInfoServer.get(0).equals("<ERRO-2>")&& ! vetorInfoServer.get(0).equals("<ERRO-1>") ) {
					
					
					areaResultado.append( "Servidor: " + (String) vetorServidores.get(indice) + "\n\n");
					areaResultado.append("Processador: " + vetorInfoServer.get(0) + "\n");
					areaResultado.append("Numero de Cores: " + vetorInfoServer.get(1) + "\n");
					areaResultado.append("Mhz (Max) : " + vetorInfoServer.get(2) + "\n");
					areaResultado.append("Mhz : " + vetorInfoServer.get(3) + "\n");
					areaResultado.append("Uso de CPU (%) : " + vetorInfoServer.get(4) + "\n");
					areaResultado.append("Memoria Total: " + vetorInfoServer.get(5)+ "\n");
					areaResultado.append("Memoria Disponivel: " + vetorInfoServer.get(6) + "\n");
					//areaResultado.append("Resultado NetStat:\n\n " + vetorInfoServer.get(7) + "\n\n");
					areaResultado.append( vetorInfoServer.get(7) + "\n\n");
			
					textAreaStatus.adicionarBlue("\nColeta concluida no Servidor " + (String) vetorServidores.get(indice) );
					
				} else {
					
					if( vetorInfoServer == null ) {
						
						textAreaStatus.adicionarRed("\n !!! Falha de comunicação com Servidor " + (String) vetorServidores.get(indice) + " TIMEOUT !!! ");
						
					} else {
						
						if(vetorInfoServer.get(0).equals("<ERRO-1>") ) {
							textAreaStatus.adicionarRed("\n !!! Falha de comunicação com o servidor "  + (String) vetorServidores.get(indice) + " ERRO DE E/S !!! ");
						}
						
						if(vetorInfoServer.get(0).equals("<ERRO-2>") ) {
							textAreaStatus.adicionarRed("\n !!! Falha de comunicação com o servidor "  + (String) vetorServidores.get(indice) + " NÃO ENCONTREI O DIRETORIO ATF ESPECIFICADO NAS CONFIGURAÇÕES !!! ");
						}
						if(vetorInfoServer.get(0).equals("<ERRO-3>") ) {
							textAreaStatus.adicionarRed("\n !!! Falha de comunicação com o servidor "  + (String) vetorServidores.get(indice) + " NÃO ENCONTREI O SUBDIRETORIO Salvar NO DIRETORIO ATF ESPECIFICADO NAS CONFIGURAÇÕES !!! ");
						}
						if(vetorInfoServer.get(0).equals("<ERRO-4>") ) {
							textAreaStatus.adicionarRed("\n !!! Falha de comunicação com o servidor "  + (String) vetorServidores.get(indice) + " CONEXÃO EM USO !!! ");
						}
						
						
					}
					
					
				}
				
				//areaResultado.append("Processador: " + vetorInfoServer.get(0) + "\n");
				
				indice = indice + 2;
				
			}	
			
			
			
			return null;
				
		}

		@Override
			protected void done() {
				// quando completar, faz o que deve aqui
				textAreaStatus.adicionarGreen("\nColeta de informações concluida.\n");
				botaoExecuta.setEnabled(true);
				//bar.setValue(0);
			}
			
		
		}

		private class MyTaskProgresso extends SwingWorker<Void, Void> {
			
			@Override
			protected Void doInBackground() throws Exception {
				
				Thread.sleep(1000);
				
				while( ! myTask.isDone() ) {
					
					System.gc();
				// a Thread dorme um pouco
					try {
						Thread.sleep(100);
						lbProgresso.setText("Aguarde Coletando Informacoes... --");
						Thread.sleep(100);
						lbProgresso.setText("Aguarde Coletando Informacoes... \\");
						Thread.sleep(100);
						lbProgresso.setText("Aguarde Coletando Informacoes... |");
						Thread.sleep(100);
						lbProgresso.setText("Aguarde Coletando Informacoes... /");
						//Thread.sleep(200);
						//lbProgresso.setText("Aguarde Coletando Informacoes... --");
						//Thread.sleep(200);
						
					} catch (InterruptedException ex) {
					}
					// ajusta a barra de progresso
					//setProgrss(i);
					//bar.setValue(getProgress());
				}
				//return null;
				return null;
			}

			@Override
			protected void done() {
				// quando completar, faz o que deve aqui
				lbProgresso.setText("Concluido");
				
				//area.append("Tarefa completa!\n");
				//start.setEnabled(true);
				//bar.setValue(0);
			}
			
			
		}


}



