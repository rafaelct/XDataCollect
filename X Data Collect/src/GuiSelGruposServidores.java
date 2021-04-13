import java.util.Vector;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;


public class GuiSelGruposServidores {

	JPanel panel;
	
	public GuiSelGruposServidores(Vector vetListaGrupos, Vector vetListaServidores) {
		// TODO Auto-generated constructor stub
		
		panel = new JPanel();
		panel.setLayout( new MigLayout("nogrid"));
		panel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black) );
		
		GuiSelGrupos guiSelGrupos = new GuiSelGrupos(vetListaGrupos);
		
		GuiSelServidores guiSelServidores = new GuiSelServidores(vetListaServidores);

		panel.add(guiSelGrupos.getPanel(),"width 250:250:250,height 200:200:200");
		panel.add(guiSelServidores.getPanel(),"width 350:350:350,height 200:200:200,wrap");

	}

	public JPanel getPanel() {
		return panel;
	}
}
