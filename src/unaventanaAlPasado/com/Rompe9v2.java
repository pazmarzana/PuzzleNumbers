package unaventanaAlPasado.com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
//asi esta funcionando pero se puede mejorar varias cosas
//cosas para mejorar
//por ahora la cantidad de cuadraditos se puede cambiar dentro del programa y funciona, despues cambiarlo para poder elegirlo desde afuera
//ir agregando de 1 columna mas, debo cambiar por afuera porque hay que cambiar tamaño de la ventana



public class Rompe9v2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiMarco miMarco1=new MiMarco();

	}//cierra main
	
}//cierra clase principal

class MiMarco extends JFrame{
	
	public MiMarco() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int widthMarco = 400+100*MiLamina.tam/3;//el tamaño lo agrando en forma proporcional
		int heightMarco = 400+100*MiLamina.tam/3;
		int xInicioMarco = (screenSize.width-widthMarco)/2;
		int yInicioMarco = (screenSize.height-heightMarco)/2;
		
		setBounds(xInicioMarco,yInicioMarco,widthMarco,heightMarco);
	
		//creo la imagen para el icono de la ventana
		URL pathMiIcono = getClass().getResource("paz.png");
		ImageIcon miIcono = new ImageIcon(pathMiIcono);
		setIconImage(miIcono.getImage());
				
		MiLamina milamina1=new MiLamina();
		add(milamina1);
		setVisible(true);

	}//cierra constructor MiMarco
}//cierra clase MiMarco

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

				botones[i][j]=new JButton(Integer.toString(i*tam+j+1)); //les escribo el texto de los numeros a las fichas
				
				botones[i][j].setBackground(colorPrendido);
				botones[i][j].setForeground(Color.WHITE);
				botones[i][j].setFocusPainted(false);
				botones[i][j].setFont(new Font("Tahoma", Font.BOLD, (int) Math.floor(35*(3.0/tam))+2));// con 36 me gustaba para el inicial, disminuyo proporcionalmente
				
				botones[i][j].addActionListener(miEscucha);
				lamina_general.add(botones[i][j]);

			}//cierra for j
		}//cierra for i

		//vacio el ultimo espacio
		botones[tam-1][tam-1].setText("");
		botones[tam-1][tam-1].setBackground(colorApagado);
		
		mezclar(); //los desordeno
		
	}//cierra constructor MiLamina
		
	//atributos de clase	
	public static int tam = 3;

	JButton[][] botones = new JButton[tam][tam];
		
	Color colorPrendido = new Color(0, 204, 153); //color de las fichas
	Color colorApagado = new Color(59, 89, 182);  //color del lugar donde no hay una ficha
	int intentos=0;
	boolean gano=false;
	boolean seguirJugando=true;
	
	
private void mover(int i, int j) { //
	//intenta mover la ficha que esta en la posicion (i,j) (al unico lugar disponible, que es el que se encuentra vacio)
	//me voy fijando en las cuatro posiciones posibles, arriba, derecha, abajo, izquierda si esta vacio y s
	
	if(i>=0 && i<tam && j>=0 && j<tam) {
		
		if(j+1<tam && botones[i][j+1].getText()=="") { //si no me fui por la derecha del tablero y la de la derecha esta vacia "la muevo"
			botones[i][j+1].setText(botones[i][j].getText());//mover en realidad es cambiar los textos y los colores entre ambas fichas
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
			botones[i][j].setBackground(colorApagado);
		}//cierra if principal del metodo
	
	}//cierra chequeo basico de que los parametros correspondan a una ficha que este en el tablero
	
}//cierra metodo mover

private void mezclar() {
	int a;
	int b;
	
	Random rand = new Random();

	for(int m=0; m<1000*tam*tam; m++) {             //mezcla una cantidad de veces lo hago poporcional a la cantidad de fichas
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
		
		for (int k=0;k<tam;k++) { //busco en que indice del array de botones esta el que se hizo click
			for(int n=0;n<tam;n++) {

				if(botones[k][n]==fuente) {
					i=k;
					j=n;
				}
			
			}//cierra for n
		}//cierra for k
		
	
		mover(i,j);
		intentos++;
		gano=ChequeoGano();
		
		//si gano, pregunto si quiere jugar y si es asi reseteo variables
		if (gano) {
	
		System.out.print(intentos);  //borrar
		System.out.print(gano);//borrar
		System.out.print(seguirJugando);//borrar
		JOptionPane.showMessageDialog(null, "Has ganado! Felicitaciones, lo has logrado en "+intentos+" intentos","Felicitaciones", JOptionPane.PLAIN_MESSAGE);
		ContinuarJugando();
		if (seguirJugando) {
			//tam ++;
			intentos=0;
			gano=false;
			seguirJugando=true;
			mezclar(); 
			
		}
		else {
			seguirJugando=false;
			System.exit(0);
		}
		
		System.out.print("           ");//borrar
		System.out.print(seguirJugando);//borrar
		}

	}//cierra action performed
}//cierra escucha	

private boolean ChequeoGano() {
	
	for (int i=0; i<tam; i++) {
		for (int j=0;j<tam;j++) {
			if (i!=tam-1 || j!=tam-1){
				
				if (! botones[i][j].getText().equals(Integer.toString(i*tam+j+1))) //chequeo si coincide con la alineacion original, osea ordenados
				{
					return false;
				}
			}	
			if (i==tam-1 && j==tam-1){	//me fijo el ultimo aparte
				if (! botones[i][j].getText().equals(""))
				{
					return false;
				}
			}
			
		}//cierra for j
	}//cierra for i
	
	return true; //si ninguna era incorrecta, gano
	
}//cierra clase ChequeoGano

public void ContinuarJugando() {
	int opcion=JOptionPane.showConfirmDialog(null,
			"Quieres seguir jugando?", "Jugar?", JOptionPane.YES_NO_OPTION);
	if (opcion==JOptionPane.YES_OPTION ){
		seguirJugando=true;
	}
	else{
		seguirJugando=false;
	}

	
}


}//cierra clase lamina
