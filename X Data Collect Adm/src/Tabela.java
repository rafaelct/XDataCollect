import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.Dimension;
import java.awt.print.PrinterException;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.Vector;
import java.util.ArrayList;


public class Tabela {

private String tipo_tabela;
private Box box_tabela;
private JScrollPane scroll;
private JScrollBar scrollbar_vertical;
// Object[] colunas;
// Object[][] dados;
private JTable tabela;
//Vector dados;
// JPanel panel;
private DefaultTableModel modelo;
private int indice_coluna;

	public void init(String tipo) {
	
		tipo_tabela = tipo;
		box_tabela = Box.createHorizontalBox();
		indice_coluna = 0;
		
		criaTabela();

				
		
		
	} // fim do metodo init
	
	
	private void criaTabela() {
	

		// panel = new JPanel();
		Object[] colunas = {"Login","Cod", "Codinome", "Nome","Skill",};

		// Object[][] dados  = { {"99999", "12345678900","Smith da Silva Sauro Santos de Souza Cordeiro","21-136" } };

		modelo = new DefaultTableModel(0,0) {
			
			 public boolean isCellEditable(int rowIndex, int mColIndex){  
			 	 return true;  
			 }     
		
		};
		//modelo.addColumn("Skill rrrRetorno");

		tabela = new JTable(modelo);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		if(tipo_tabela.equals("CS")) {
	
			adicionaColuna("Descri??o");
			adicionaColuna("Comando");
			//adicionaColuna("Data/Hora ativado");
			//adicionaColuna("Data/Hora Schedulado");
			//adicionaColuna("Data/Hora Bloqueado");
			//adicionaColuna("Quem Ativou");
			//adicionaColuna("Quem Bloqueio");
			//adicionaColuna("Status");
			
			ajustaTamanhoColunas();
		}
		
		scroll = new JScrollPane(tabela);
		
		box_tabela.add( scroll );
		modelo.addRow( new String [] {"","","","",""} );

		//scroll = new JScrollPane(panel);
		//modelo.addColumn("Skill Retorno");
		// adicionaLinha("87664","12345678900","Rafael Teixeira","Rafael Costa Teixeira","21-136","21");
		setVisivel(false);
		
	} // fim do metodo CriaTabelaCadastroNumerico
	
	public int getItemSelecionado() {
		
		return tabela.getSelectedRow();
		
	}
	
	public void removeLinha(int index,boolean validacao) {
		
		if(index == -1) {
			
			JOptionPane.showMessageDialog(null , "N\u00e3o a campos selecionados para remover","Erro", JOptionPane.ERROR_MESSAGE);
			return;			
		}

		if( validacao ) {
			
			
			if(modelo.getRowCount() == 1 || modelo.getRowCount() == index +1 ) {
				JOptionPane.showMessageDialog(null , "N?o posso remover essa linha","Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		}
		
		modelo.removeRow(index);
		
	}
	
	public void removeAllLinhas() {
		
		System.out.println("Count de linhas:" + modelo.getRowCount() );
		
		if(modelo.getRowCount() == 1 ) {
			/*
			this.removeLinha(0);
			Vector vetLinhaPadrao = new Vector();
			vetLinhaPadrao.add("");
			vetLinhaPadrao.add("");
			this.adicionaLinha(vetLinhaPadrao);
			vetLinhaPadrao.clear();
			vetLinhaPadrao = null;
			*/
			return;
		}
		//for( int i = 0 ; i < modelo.getRowCount() ;i++) {
		while( modelo.getRowCount() > 1 ) {
			//modelo.removeRow(i);
			System.out.println("Count de linhas:" + modelo.getRowCount() );
			
			this.removeLinha(0,false);
		}
		/*
		Vector vetLinhaPadrao = new Vector();
		vetLinhaPadrao.add("");
		vetLinhaPadrao.add("");
		this.adicionaLinha(vetLinhaPadrao);
		vetLinhaPadrao.clear();
		vetLinhaPadrao = null;
		*/
	}
	
	private void atualizaScrollTabela() {
	
				System.out.println(tabela.getHeight());
		System.out.println(tabela.getRowHeight());
		// modelo.addRow( new String [] {cmp1,cmp2,cmp3,cmp4,cmp5} ); 
		 
		 //scroll.getVerticalScrollBar();
		 System.out.println("Inseri linha");
		 scrollbar_vertical = scroll.getVerticalScrollBar();
		 scrollbar_vertical.setMaximum(scrollbar_vertical.getMaximum()+16);
		 System.out.println(scrollbar_vertical.getMaximum());
		 scrollbar_vertical.setValue(scrollbar_vertical.getMaximum());
		 scrollbar_vertical.setValue(scrollbar_vertical.getMaximum());
		// scroll.getVerticalScrollBar().setValue(700);
		// tabela.setRowSelectionInterval(modelo.getRowCount()-1,modelo.getRowCount()-1);

	}
	
	public Vector getDados() {
		System.out.println( modelo.getRowCount() );
		
		Vector vetorDados = new Vector();

		for(int linha = 0;linha< modelo.getRowCount()-1;linha++) {
			
			vetorDados.add( modelo.getValueAt(linha, 0) );
			vetorDados.add( modelo.getValueAt(linha,1) );
			
		
		}
		
		System.out.println(vetorDados);
		
		return vetorDados;
		
	}
	
	public Vector getDados(int linha) {
		System.out.println( modelo.getRowCount() );
		
		Vector vetorDados = new Vector();

//		for(int linha = 0;linha< modelo.getRowCount()-1;linha++) {
			
			vetorDados.add( modelo.getValueAt(linha, 0) );
			vetorDados.add( modelo.getValueAt(linha,1) );
			
		
//		}
		
		System.out.println(vetorDados);
		
		return vetorDados;
		
	}
	
	public Vector getDadosSelecionados() {
		System.out.println( modelo.getRowCount() );
		
		Vector vetorDados = new Vector();

//		for(int linha = 0;linha< modelo.getRowCount()-1;linha++) {
			
			int itemSelecionado = this.getItemSelecionado();
			
			if( itemSelecionado != -1 ) {
				
				vetorDados.add( modelo.getValueAt(itemSelecionado, 0) );
				vetorDados.add( modelo.getValueAt(itemSelecionado,1) );
			
			} else {
				vetorDados.clear();
			}
		
//		}
		
		//System.out.println(vetorDados);
		
		return vetorDados;
		
	}
	
	public void adicionaLinha(Vector dados) {
	
		Vector dados_novalinha = new Vector();
		//modelo.addRow(dados);
		
		System.out.println("indx:" + modelo.getRowCount() );
		
		if( modelo.getRowCount() == 0 ) {

			for(int i=0;i < dados.size();i++) {
				
				
				dados_novalinha.add("");
			}
			
			modelo.addRow(dados_novalinha);
			//dados_novalinha.clear();
		}
		
		if( modelo.getRowCount() > 0 ) {
		
			for(int i=0;i < modelo.getColumnCount();i++) {
				
				System.out.println("Index: " + modelo.getRowCount() );
				
				modelo.setValueAt(dados.get(i),modelo.getRowCount()-1,i);
			}
			
		} 
		dados_novalinha.clear();
		for(int i=0;i < dados.size();i++) {
		
	
			dados_novalinha.add("");
		}
		
		modelo.addRow(dados_novalinha);
		
		//if( tipo_tabela.equals("CN") ) {

		//	modelo.addRow( new String [] {"","","","",""} );

		//}
	
		
		atualizaScrollTabela();
	
	}
	
	
	private void ajustaTamanhoColunas(){
		
		
		if( tipo_tabela.equals("CS") ) {

			tabela.getColumnModel().getColumn(0).setPreferredWidth(250);
			tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
			//tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
			//tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
			//tabela.getColumnModel().getColumn(4).setPreferredWidth(150);
			//tabela.getColumnModel().getColumn(5).setPreferredWidth(250);
			//tabela.getColumnModel().getColumn(6).setPreferredWidth(200);

			
		}
				
	}
	
	public void adicionaColuna(String coluna) {
		
		modelo.addColumn(coluna);
		//tabela.getColumnModel().getColumn(indice_coluna).setPreferredWidth(500);
		if(indice_coluna > 6) {
		ajustaTamanhoColunas();
		}
		indice_coluna++;
	}
	public void setVisivel(Boolean opcao) {
	
		box_tabela.setVisible(opcao);
	}
	
	public Box get() {
	
		return box_tabela;
		// return scroll;
	}
	
	
} // fim da classe tabela
