import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;


public class CpSelServidores {

	JPanel panel_interno;
	
	public CpSelServidores(String nomeDoServidor,String ipHost) {
		// TODO Auto-generated constructor stub
		
		final JCheckBox checkBox = new JCheckBox( nomeDoServidor );
		checkBox.setBackground(java.awt.Color.white);
		
		panel_interno = new JPanel();
		panel_interno.setLayout( new MigLayout("nogrid"));
		panel_interno.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black) );
		panel_interno.setBackground(java.awt.Color.white);
		
		JLabel lblServHostIp = new JLabel( ipHost );
		JLabel lblNomeServHostIp = new JLabel("Host/Ip:");
		
		lblServHostIp.setFont( new Font(Font.SANS_SERIF,Font.ITALIC,9) );
		lblServHostIp.setForeground( java.awt.Color.blue );

		panel_interno.add(checkBox,"wrap");
		panel_interno.add(lblNomeServHostIp);
		panel_interno.add(lblServHostIp);

		checkBox.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if( checkBox.isSelected() ) {
					panel_interno.setBackground(java.awt.Color.yellow);
					checkBox.setBackground(java.awt.Color.yellow);
					
					
				} else {
					panel_interno.setBackground(java.awt.Color.white);
					checkBox.setBackground(java.awt.Color.white);
				}
			}
			
			
		});
	}

	public JPanel getPanel() {
		return panel_interno;
	}

}
