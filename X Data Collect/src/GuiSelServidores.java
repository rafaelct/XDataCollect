import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;

import net.miginfocom.swing.MigLayout;


public class GuiSelServidores {

	JPanel panel,panel_externo;
	JScrollPane scrollPane;
	
	Box box;
	
	public GuiSelServidores(Vector vetServidores) {
		// TODO Auto-generated constructor stub
		panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));
		panel.setBackground(java.awt.Color.white);
		
		panel_externo = new JPanel();
		panel_externo.setLayout( new MigLayout("nogrid"));
		panel_externo.setBackground(java.awt.Color.white);
		panel_externo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black) );
		//SplitPaneBorder basicBorder = new BasicBorders.SplitPaneBorder(new Color(0,255,0), new Color(255,0,0) );
		 
		JLabel lblNomeServidor = new JLabel("Escolha os servidores que ser?o coletados os logs:");
		
		panel_externo.add(lblNomeServidor,"wrap");
		
		//box = Box.createHorizontalBox();
		
		//JScrollPane scrollPane = new JScrollPane();
		
		ArrayList<CpSelServidores> arrayCheck = new ArrayList<CpSelServidores>();
		
		for(int i = 0 ; i < vetServidores.size() ; i=i+2 ) {
			
			CpSelServidores cpSelServidores = new CpSelServidores( (String) vetServidores.get(i),(String) vetServidores.get(i+1) );
			
			panel.add( cpSelServidores.getPanel() ,"width 300:300:300,height 60:60:60,wrap");
			//panel.remove(cpSelServidores.getPanel());
			arrayCheck.add( cpSelServidores );
			
		}
		scrollPane = new JScrollPane(panel);
		//scrollPane.add(panel);
		panel_externo.add(scrollPane,"width 340:340:340,height 160:160:160,wrap");
		//box.add(scrollPane);
		//panel.add(box,"width 200:200:200,height 400:400:400,wrap");
		
	}
	
	public JPanel getPanel() {
		
		return panel_externo;
		
	}


}
