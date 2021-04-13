import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.Vector;
import javax.swing.text.*;
import net.miginfocom.swing.*;
import net.miginfocom.layout.*;


public class TextAreaStatus {

static JTextArea text_area;
static JTextPane editor;
static Box box_area;
static JPanel panel;

static MutableAttributeSet attr,attr1,attr2;
static StyledDocument doc;


	public TextAreaStatus() {
	
		try {
			editor = new JTextPane();
			editor.setEditable(false);
			doc = editor.getStyledDocument();
		
			panel = new JPanel();
			panel.setLayout(new MigLayout());
		
			box_area = Box.createHorizontalBox();
			box_area.setBounds(1,1,980,100);
			box_area.add( new JScrollPane( editor ) );
		
			panel.add(box_area,"width 890:890:890,height 200:200:200,wrap");
			panel.setVisible(true);
		
			attr = new SimpleAttributeSet();
			attr1 = new SimpleAttributeSet();
			attr2 = new SimpleAttributeSet();
		
		StyleConstants.setForeground(attr, java.awt.Color.blue);
		
		StyleConstants.setForeground(attr1, java.awt.Color.red);
		StyleConstants.setForeground(attr2, java.awt.Color.green);
		
		StyleConstants.setBold(attr,true);
		}catch(Exception e) { System.out.println(e); }
		
	} 

	
	public void adicionarBlue(String txt) {
		
		try {
		//reset();
		doc.insertString(doc.getLength(),txt,attr);
		
		//panel.setVisible(true);
		
		}catch(Exception e) { e.printStackTrace(); }

		
	}
	
	public void adicionarGreen(String txt) {
		
		try {
		//reset();
		doc.insertString(doc.getLength(),txt,attr2);
		
		//panel.setVisible(true);
		
		}catch(Exception e) { e.printStackTrace(); }

		
	}
	
	public void adicionarRed(String txt) {
		
		try {
		//reset();
		doc.insertString(doc.getLength(),txt,attr1);
		
		//panel.setVisible(true);
		
		}catch(Exception e) { e.printStackTrace(); }

		
	}
	
	public void reset() {
		try {
			
			//panel.setVisible(false);
			doc.remove(0,doc.getLength());
			
		} catch(Exception e) { e.printStackTrace(); }
		
	}
	
	public JPanel get() {
		
		return panel;
	}
	
	
} // fim da classe

