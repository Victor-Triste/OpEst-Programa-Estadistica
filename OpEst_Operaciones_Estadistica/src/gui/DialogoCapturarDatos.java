package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class DialogoCapturarDatos extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton botonAceptar;
	private JButton botonCancelar;
	private JTextField[] cajasTexto;
	
	
	public DialogoCapturarDatos(JFrame framePrincipal, int numElementos) {
		super(framePrincipal,"Capturar datos");		
		
		 int numFilas = (int) Math.ceil(Math.sqrt(numElementos));
         int numColumnas = (int) Math.ceil((double) numElementos / numFilas);
		
		
		JPanel panelCentral = new JPanel(new GridLayout(numFilas, numColumnas));
		
		
		
		
		
		/*
		int columnas = 0;
		if(numElementos < 50){
			columnas = 5;
		} else {
			columnas = 10;
		}
		int filas = (numElementos/columnas);*/

		JPanel panelBotones = new JPanel();
		JPanel panelAux = new JPanel();
		JPanel panelTitulos = new JPanel();
		
		panelTitulos.setLayout(new GridLayout(3,1));
		JLabel titulo = new JLabel("Datos de Entrada");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Roboto", Font.BOLD, 22));
		JLabel indicacion = new JLabel("Por favor rellene todos los campos");
		indicacion.setFont(new Font("Roboto", Font.PLAIN, 14));
		
		panelTitulos.add(titulo);
		panelTitulos.add(indicacion);

		getContentPane().add(panelTitulos, BorderLayout.NORTH);
		
		
		//JPanel panelCentral = new JPanel();
		/*panelCentral.setLayout(new GridLayout(filas,columnas));
		cajasTexto = new JTextField[filas][columnas];
		
		for(int i=0;i<filas;i++){
			for(int j=0;j<columnas;j++){
				panelAux = new JPanel();
				cajasTexto[i][j] = new JTextField();
				cajasTexto[i][j].setPreferredSize(new Dimension(80, 30));
				cajasTexto[i][j].setEditable(true);
				panelAux.add(cajasTexto[i][j]);
				panelCentral.add(panelAux);
			}
			
		}*/
		cajasTexto = new JTextField[numElementos];
		for (int i = 0; i < numElementos; i++) {
			cajasTexto[i] = new JTextField();
			cajasTexto[i].setPreferredSize(new Dimension(80, 30));
			cajasTexto[i].setEditable(true);
            panelCentral.add(cajasTexto[i]);
        }
		
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		
		panelAux = new JPanel();
		panelBotones.setLayout(new GridLayout(1, 1));
		botonAceptar = new JButton("Aceptar");
		panelAux.setLayout(new FlowLayout(FlowLayout.CENTER));
		botonAceptar.setPreferredSize(new Dimension(120, 40));
		botonAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		botonAceptar.addActionListener(this); 
		
		botonCancelar = new JButton("Cancelar");
		botonCancelar.setPreferredSize(new Dimension(120, 40));
		botonCancelar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		botonCancelar.addActionListener(this); 

		panelAux.add(botonAceptar);
		panelAux.add(botonCancelar);
		panelBotones.add(panelAux);
		getContentPane().add(panelBotones, BorderLayout.SOUTH);
		

		this.setBounds(getSize().width / 2 - (this.getWidth() / 2), getSize().height / 2 - (this.getHeight() / 2),
				342, 247);
		pack();
		this.setResizable(false); 
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(framePrincipal);
		this.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(botonCancelar)){
			cerrarDialogo();
		} else if(e.getSource().equals(botonAceptar)){
			
			JOptionPane.showMessageDialog(this, "Se envió correctamente los datos");
			cerrarDialogo();
		}
		
	}
	
	private void cerrarDialogo(){
		this.dispose();
	}
}
