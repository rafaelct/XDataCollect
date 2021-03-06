import java.awt.Color;
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

public class Janela {

	JButton botaoExecuta;
	JTextArea areaStatus,areaResultado;
	//TextAreaStatus textAreaStatus;
	JLabel lbProgresso;
	//MyTask myTask;
	//MyTaskProgresso myTaskProgresso;
	String titulo = "XDC - X Data Collect - Administrator - v3.0";
	// Alterado na v3.0
	JComboBox comboServidores;
	String diretorioATFLocalStr;
	

	
	// fim da alteração v3.0
	
	public Janela() {
		// TODO Auto-generated constructor stub
		
		final JFrame gui = new JFrame(titulo);

		final JPanel panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));
		
		final JPanel panelGeral = new JPanel();
		panelGeral.setLayout( new MigLayout("nogrid"));


		final JPanel panelAbaServidores = new JPanel();
		panelAbaServidores.setLayout( new MigLayout("nogrid"));
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setBounds(0, 0, 1024, 700);

		JTabbedPane tab = new JTabbedPane();
		//JTabbedPane tabServidores = new JTabbedPane();
		
		
		
		// Aba Configuração de Servidores
		
		// Geral
		
		panelGeral.add(tab,"width 1000:1000:1000,height 650:650:650");
		//panel.add(tabServidores);
		

		GuiServidores guiServidores = new GuiServidores();
		GuiCadCmd guiCadCmd = new GuiCadCmd();
		GuiBtControles guiBtControles = new GuiBtControles(guiServidores,guiCadCmd);
		
		GuiCadServidores guiCadServidores = new GuiCadServidores(guiServidores.getComboServidores(),guiServidores.getComboActionListenerServidores() );
		
		panelAbaServidores.add(guiCadServidores.getPanel() );
		panel.add(guiServidores.getPanel(),"top 55,width 450:450:450,height 450:450:450" );
		panel.add(guiBtControles.getPanel(),"width 80:80:80,height 450:450:450" );
		panel.add(guiCadCmd.getPanel(),"width 450:450:450,height 450:450:450" );
		
		/*
		panel.add(lblCmdDesc);
		panel.add(txtCmdDesc,"width 300:300:300,height 20:20:20");

		panel.add(lblCmdExec);
		panel.add(txtCmdExec,"width 300:300:300,height 20:20:20");
		panel.add(btnCmdAdd);
		panel.add(btnCmdDel,"wrap");
		*/
		
		//fim da alteração v3.0
		
		//panel.add(lbProgresso);
		

		tab.addTab("Comandos", panel);
		tab.addTab("Servidores", panelAbaServidores);
		
		gui.add(panelGeral);
		
		
		gui.setVisible(true);
		
	}
	
}



