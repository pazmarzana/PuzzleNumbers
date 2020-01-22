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

/**
 * Rompecabezas de numeros tipo "trabado".
 * Mediante clicks se van moviendo las piezas a los lugares vacios.
 * Una vez que se gana se aumenta la dificultad. 
 *
 * @author  Maria de la Paz Marzana
 */

public class Rompe9v2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int tamInicial = 3;

		nuevaPartida(tamInicial);

	}//cierra main

	protected static void nuevaPartida(int tamanio){

		Partida miPartida = new Partida(tamanio);

	}//cierra NuevaPartida

}//cierra clase principal Rompe9v2

/**
 *Crea una nueva partida, en esta clase se desarrollara la parte principal del juego
 */
class Partida{
	int tam;
	int movimientos;
	boolean gano;

	public Partida(int tamanio) {//constructor Partida
		tam = tamanio;
		movimientos = 0;
		gano = false;

		MiMarco miMarco1 = new MiMarco();

	}//cierra constructor Partida

	//sigo dentro de clase Partida

	/**
	 *Crea un marco, una ventana, para el juego,
	 *toma en cuenta las medidas de la pantalla y el tamanio del rompecabezas
	 */
	class MiMarco extends JFrame{

		public MiMarco() {//constructor MiMarco
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int widthMarco = 400 + 100 * tam / 3;//el tamaño lo agrando en forma proporcional
			int heightMarco = 400 + 100 * tam /3;
			int xInicioMarco = (screenSize.width-widthMarco) / 2;
			int yInicioMarco = (screenSize.height-heightMarco) / 2;

			setBounds(xInicioMarco,yInicioMarco,widthMarco,heightMarco);

			//creo la imagen para el icono de la ventana
			URL pathMiIcono = getClass().getResource("paz.png");
			ImageIcon miIcono = new ImageIcon(pathMiIcono);
			setIconImage(miIcono.getImage());

			MiLamina milamina1 = new MiLamina();
			add(milamina1);
			setVisible(true);

		}//cierra constructor MiMarco
	}//cierra clase MiMarco
	//sigo dentro de clase Partida

	/**
	 *Crea una lamina que se agrega a la ventana,
	 *con los botones que son los que representan las fichas del rompecabezas
	 */
	class MiLamina extends JPanel{

		Color colorPrendido = new Color(0, 204, 153); //color de las fichas
		Color colorApagado = new Color(59, 89, 182);  //color del lugar donde no hay una ficha	

		JButton[][] botones = new JButton[tam][tam];

		public MiLamina() {//constructor MiLamina

			setLayout(new BorderLayout());
			setBorder(new EmptyBorder(10, 10, 10, 10));
			JPanel lamina_general = new JPanel();
			lamina_general.setLayout(new GridLayout(tam,tam));
			add(lamina_general,BorderLayout.CENTER);

			Escucha miEscucha = new Escucha();

			//inicializo los botones
			for (int i = 0; i < tam; i++) {
				for (int j = 0; j < tam; j++) {

					botones[i][j] = new JButton(Integer.toString(i * tam + j + 1)); //les escribo el texto de los numeros a las fichas

					botones[i][j].setBackground(colorPrendido);
					botones[i][j].setForeground(Color.WHITE);
					botones[i][j].setFocusPainted(false);
					botones[i][j].setFont(new Font("Tahoma", Font.BOLD, (int) Math.floor(35 * (3.0 / tam)) + 2));// con 36 me gustaba para el inicial, disminuyo proporcionalmente

					botones[i][j].addActionListener(miEscucha);
					lamina_general.add(botones[i][j]);

				}//cierra for j
			}//cierra for i

			//vacio el ultimo espacio
			botones[tam - 1][tam - 1].setText("");
			botones[tam - 1][tam - 1].setBackground(colorApagado);

			mezclar(); //los desordeno

		}//cierra constructor MiLamina

		//sigo dentro de la clase MiLamina

		/**
		 *Metodo que intenta mover la ficha que esta en la posicion (i,j) (al unico lugar disponible, que es el que se encuentra vacio)
		 *@param i posicion x de la ficha
		 *@param j posicion y de la ficha
		 */
		private void mover(int i, int j) { //
			//
			//me voy fijando en las cuatro posiciones posibles, arriba, derecha, abajo, izquierda si esta vacio y si sigo dentro del tablero

			if(j+1 < tam && botones[i][j+1].getText() == "") { //si no me fui por la derecha del tablero y la de la derecha esta vacia "la muevo"
				botones[i][j+1].setText(botones[i][j].getText());//mover en realidad es cambiar los textos y los colores entre ambas fichas
				botones[i][j+1].setBackground(colorPrendido);
				botones[i][j].setText("");
				botones[i][j].setBackground(colorApagado);
			}else if(i+1 < tam && botones[i+1][j].getText() == "") {
				botones[i+1][j].setText(botones[i][j].getText());
				botones[i+1][j].setBackground(colorPrendido);
				botones[i][j].setText("");
				botones[i][j].setBackground(colorApagado);
			}else if(j-1 >= 0 && botones[i][j-1].getText() == "") {
				botones[i][j-1].setText(botones[i][j].getText());
				botones[i][j-1].setBackground(colorPrendido);
				botones[i][j].setText("");
				botones[i][j].setBackground(colorApagado);}
			else if(i-1 >= 0 && botones[i-1][j].getText() == "") {
				botones[i-1][j].setText(botones[i][j].getText());
				botones[i-1][j].setBackground(colorPrendido);
				botones[i][j].setText("");
				botones[i][j].setBackground(colorApagado);
			}//cierra if 

		}//cierra mover

		/**
		 *Metodo que mezcla las fichas
		 */
		private void mezclar() {
			int a;
			int b;

			Random rand = new Random();

			for(int m = 0; m < 1000 * tam * tam; m++) {//mezcla una cantidad de veces lo hago poporcional a la cantidad de fichas
				a = rand.nextInt(tam);
				b = rand.nextInt(tam);
				mover(a,b);

			}
		}//cierra  mezclar

		/**
		 *Metodo que chequea si se gano, o sea si las fichas estan en el orden original
		 */
		private boolean chequeoGano() {

			for (int i = 0; i < tam; i++) {
				for (int j = 0; j < tam; j++) {
					if (i != tam - 1 || j != tam - 1){//me fijo el ultimo aparte

						if (! botones[i][j].getText().equals(Integer.toString(i * tam + j + 1))) //chequeo si coincide con la alineacion original, osea ordenados
						{
							return false;
						}
					}//cierre del if de los botones que no son el ultimo	
					if (i == -1 && j == -1){	//me fijo el ultimo 
						if (! botones[i][j].getText().equals(""))
						{
							return false;
						}
					}//cierre del if del ultimo boton

				}//cierra for j
			}//cierra for i

			return true; //si ninguna era incorrecta, gano

		}//cierra ChequeoGano

		/**
		 *Metodo que pregunta si se quiere seguir jugando
		 */
		private boolean continuarJugando() {
			int opcion=JOptionPane.showConfirmDialog(null,
					"Quieres seguir jugando?", "Jugar?", JOptionPane.YES_NO_OPTION);
			if (opcion == JOptionPane.YES_OPTION ){
				return true;
			}
			else{
				return false;
			}

		}//cierra ContinuarJugando

		//sigo dentro de la clase MiLamina

		/**
		 *Crea la escucha para detectar cuando se hizo click en alguna de las piezas
		 *y llama a los metodos mover, chequeoGano, continuarJugando y nuevaPartida
		 */
		class Escucha implements ActionListener{
			String nl = System.getProperty("line.separator");
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = -1; //en estos indices guardare los indices del boton que fue pulsado
				int j = -1;
				boolean seguir = true;

				Object fuente = e.getSource();

				for (int k = 0; k < tam; k++) { //busco en que indice del array de botones esta el que se hizo click
					for(int n = 0; n < tam; n++) {

						if(botones[k][n] == fuente) {
							i = k;
							j = n;
						}

					}//cierra for n
				}//cierra for k

				mover(i,j);
				movimientos++;

				gano=chequeoGano();

				//si gano, lo informo, muestro cantidad de movimientos y pregunto si quiere seguir jugando y si es asi aumento el tamanio en 1 y vuelve a jugar
				if (gano) {
					if(movimientos != 1) { //separo caso simplemente para que no quede "1 movimientos"
						JOptionPane.showMessageDialog(null, "Has ganado! Felicitaciones! "+nl+ "Lo has logrado en "+movimientos+" movimientos","Felicitaciones", JOptionPane.PLAIN_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Has ganado! Felicitaciones!" +nl+ "Lo has logrado en "+movimientos+" movimiento","Felicitaciones", JOptionPane.PLAIN_MESSAGE);
					}

					if(gano) {
						seguir = continuarJugando();
						if (seguir) {
							tam ++;
							Rompe9v2.nuevaPartida(tam);

						}
						else {

							System.exit(0);
						}	
					}

				}//cierre if de gano

			}//cierra actionPerformed

		}//cierra clase Escucha	

	}//cierra clase MiLamina

}//cierra clase Partida

