package unaventanaAlPasado.com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
//asi esta funcionando pero se puede mejorar varias cosas
//cosas para mejorar
//CAMBIAR NO ANDA ICONO
//DESORDENAR BIEN *
//cambiar en vez de tener muchos acionlistener utilizar getSource *
//en vez de repetir botones usar for *
//centrar la pantalla
//cambiar tam letra segun tam cantidad de cuadrados


public class Rompe9v2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiMarco miMarco1=new MiMarco();

	}//cierra main

}//cierra clase principal

class MiMarco extends JFrame{
	
	public MiMarco() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600,200,500,500);
		
		
		//arreglar para que quede relativo
		//ImageIcon img = new ImageIcon("../paz.png");
		//setIconImage(img.getImage());
		
		setIconImage(new ImageIcon("D:\\Documents\\1Paz\\eclipse-workspace\\Rompe9v2\\src\\unaventanaAlPasado\\com\\paz.png").getImage());
		
				
		MiLamina milamina1=new MiLamina();
		add(milamina1);
		setVisible(true);

	}//cierra constructor MiMarco
}	//cierra clase MiMarco

class MiLamina extends JPanel{
	public MiLamina() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		JPanel lamina_general=new JPanel();
		lamina_general.setLayout(new GridLayout(tam,tam));
		add(lamina_general,BorderLayout.CENTER);
		
		Escucha miEscucha=new Escucha();
		
		
		//inicializo los botones
		for (int i=0; i<tam; i++) {
			for (int j=0;j<tam;j++) {

				botones[i][j]=new JButton(Integer.toString(i*tam+j+1));
				
				botones[i][j].setBackground(colorPrendido);
				
				botones[i][j].setForeground(Color.WHITE);
				botones[i][j].setFocusPainted(false);
				botones[i][j].setFont(new Font("Tahoma", Font.BOLD, 36));
				
				botones[i][j].addActionListener(miEscucha);
				lamina_general.add(botones[i][j]);

			}//cierra for j
		}//cierra for i

		//vacio el ultimo espacio
		
		botones[tam-1][tam-1].setText("");
		botones[tam-1][tam-1].setBackground(colorApagado);
		
		mezclar();
		
		
		
	}//cierra constructor MiLamina
		
	//atributos de clase	
	int tam = 3;

	JButton[][] botones = new JButton[tam][tam];
		
	Color colorPrendido = new Color(0, 204, 153);
	Color colorApagado = new Color(59, 89, 182);
	
	
private void mover(int i, int j) {
	
	if(i>=0 && i<tam && j>=0 && j<tam) {
		
		if(j+1<tam && botones[i][j+1].getText()=="") {
			botones[i][j+1].setText(botones[i][j].getText());
			botones[i][j+1].setBackground(colorPrendido);
			botones[i][j].setText("");
			botones[i][j].setBackground(colorApagado);
		}else if(i+1<tam && botones[i+1][j].getText()=="") {
			botones[i+1][j].setText(botones[i][j].getText());
			botones[i+1][j].setBackground(colorPrendido);
			botones[i][j].setText("");
			botones[i][j].setBackground(colorApagado);
		}else if(j-1>=0 && botones[i][j-1].getText()=="") {
			botones[i][j-1].setText(botones[i][j].getText());
			botones[i][j-1].setBackground(colorPrendido);
			botones[i][j].setText("");
			botones[i][j].setBackground(colorApagado);}
		else if(i-1>=0 && botones[i-1][j].getText()=="") {
			botones[i-1][j].setText(botones[i][j].getText());
			botones[i-1][j].setBackground(colorPrendido);
			botones[i][j].setText("");
			botones[i][j].setBackground(colorApagado);}
	
	}//cierra chequeo basico de estar en el array
	
}//cierra metodo mover

private void mezclar() {
	int a;
	int b;
	
	Random rand = new Random();

	for(int m=0; m<1000; m++) {
		a=rand.nextInt(tam);
		b=rand.nextInt(tam);
		mover(a,b);
		
	}
}//fin metodo mezclar

private class Escucha implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int i=-1; //en estos indices guardare los indices del boton que fue pulsado
		int j=-1;
		Object fuente=e.getSource();
		
		for (int k=0;k<tam;k++) { //busco en que indice del array de botones esta el que se clickeo
			for(int n=0;n<tam;n++) {

				if(botones[k][n]==fuente) {
					i=k;
					j=n;
				}
			
			}//cierra for n
		}//cierra for k
	
		//hay otra forma, puedo preguntar si botones[i][j].isSelected
		mover(i,j);

	}//cierra action performed
}//cierra escucha	


}//cierra clase lamina