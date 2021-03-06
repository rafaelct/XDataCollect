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


public class GuiSelGrupos {

	JPanel panel,panel_externo;
	JScrollPane scrollPane;
	
	Box box;
	
	public GuiSelGrupos(Vector vetGrupos) {
		// TODO Auto-generated constructor stub
		panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));
		panel.setBackground(java.awt.Color.white);
		
		panel_externo = new JPanel();
		panel_externo.setLayout( new MigLayout("nogrid"));
		panel_externo.setBackground(java.awt.Color.white);
		panel_externo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black) );
		
		JLabel lblNomeGrupo1 = new JLabel("Selecione os Grupos de servidores");
		JLabel lblNomeGrupo2 = new JLabel("que ser?o coletados os logs:");
		
		panel_externo.add(lblNomeGrupo1,"wrap");
		panel_externo.add(lblNomeGrupo2,"wrap");
		
		
		//SplitPaneBorder basicBorder = new BasicBorders.SplitPaneBorder(new Color(0,255,0), new Color(255,0,0) );
		 
		//box = Box.createHorizontalBox();
		
		//JScrollPane scrollPane = new JScrollPane();
		
		ArrayList<CpSelGrupos> arrayCheck = new ArrayList<CpSelGrupos>();
		
		for(int i = 0 ; i < vetGrupos.size() ; i++ ) {
			
			CpSelGrupos cpSelGrupos = new CpSelGrupos( (String) vetGrupos.get(i) );
			
			panel.add( cpSelGrupos.getPanel() ,"width 200:200:200,height 40:40:40,wrap");
			
			arrayCheck.add( cpSelGrupos );
			
		}
		scrollPane = new JScrollPane(panel);
		//scrollPane.add(panel);
		panel_externo.add(scrollPane,"width 240:240:240,height 150:150:150,wrap");
		//box.add(scrollPane);
		//panel.add(box,"width 200:200:200,height 400:400:400,wrap");
		
	}
	
	public JPanel getPanel() {
		
		return panel_externo;
		
	}


}
