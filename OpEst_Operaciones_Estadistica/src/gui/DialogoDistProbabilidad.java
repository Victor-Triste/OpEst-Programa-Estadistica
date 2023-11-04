package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JTextField;

import libreria.LibreriaMatematica;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class DialogoDistProbabilidad extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField campoProbabilidad;
	private JTextField campoResultado;
	private JTextField campoTotal;
	private JTextField campoNumeroVeces;
	private JSpinner spinnerIntervaloInicial;
	private JSpinner spinnerIntervaloFinal;
	private JButton botonCalcular;
	private JLabel labelResultado;
	private JTextArea textAreaResultados;
	private String operacion;
	
	public DialogoDistProbabilidad(JFrame principal, String operacionSeleccionada){
		super(principal,"Distribución de probabilidad: " +operacionSeleccionada);
		operacion = operacionSeleccionada;
		
		getContentPane().setLayout(null);
		JLabel labelTitulo = new JLabel();
		labelTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
		getContentPane().add(labelTitulo);
		
		JLabel labelFormula = new JLabel("la fórmula es: b(k; n, p)");
		labelFormula.setFont(new Font("Roboto", Font.PLAIN, 13));
		labelFormula.setBounds(10, 47, 171, 14);
		getContentPane().add(labelFormula);
		
		JLabel labelIndicacion = new JLabel("Donde conocemos:");
		labelIndicacion.setFont(new Font("Roboto", Font.PLAIN, 13));
		labelIndicacion.setBounds(10, 66, 124, 14);
		getContentPane().add(labelIndicacion);
		
		JLabel labelTotal = new JLabel("n (total)");
		labelTotal.setFont(new Font("Roboto", Font.PLAIN, 13));
		getContentPane().add(labelTotal);
		
		campoTotal = new JTextField();
		campoTotal.setColumns(10);
		getContentPane().add(campoTotal);
		
		JLabel labelProbabilidad = new JLabel("p (probabilidad)");
		labelProbabilidad.setFont(new Font("Roboto", Font.PLAIN, 13));
		getContentPane().add(labelProbabilidad);
		
		campoProbabilidad = new JTextField();
		campoProbabilidad.setColumns(10);
		getContentPane().add(campoProbabilidad);
		
		labelResultado = new JLabel("Resultado:");
		labelResultado.setFont(new Font("Roboto", Font.PLAIN, 13));
		getContentPane().add(labelResultado);
		
		campoResultado = new JTextField();
		campoResultado.setEditable(false);
		getContentPane().add(campoResultado);
		campoResultado.setColumns(10);
		
		botonCalcular = new JButton("Calcular");
		botonCalcular.setBounds(195, 180, 89, 29);
		botonCalcular.addActionListener(this);
		getContentPane().add(botonCalcular);
		
		switch (operacion) {
		case "binomial":
			JLabel labelNumeroVeces = new JLabel("k (Número de veces)");
			labelNumeroVeces.setFont(new Font("Roboto", Font.PLAIN, 13));
			labelNumeroVeces.setBounds(259, 100, 120, 14);
			getContentPane().add(labelNumeroVeces);
			
			campoNumeroVeces = new JTextField();
			campoNumeroVeces.setBounds(384, 96, 86, 24);
			getContentPane().add(campoNumeroVeces);
			
			labelTitulo.setText("Binomial");
			labelTitulo.setBounds(195, 11, 86, 24);
			labelTotal.setBounds(10, 100, 46, 14);
			campoTotal.setBounds(118, 96, 86, 24);
			labelProbabilidad.setBounds(10, 137, 103, 14);
			campoProbabilidad.setBounds(118, 133, 86, 24);
			labelResultado.setBounds(50, 233, 190, 14);
			campoResultado.setBounds(270, 230, 124, 24);
			this.setSize(new Dimension(501, 300));
			break;
			
		case "intervalo binomial":
			labelTitulo.setText("Intervalo binomial");
			labelTitulo.setBounds(168, 11, 196, 24);
			
			JLabel labelIntervaloI = new JLabel("Intervalo inicial");
			labelIntervaloI.setFont(new Font("Roboto", Font.PLAIN, 13));
			labelIntervaloI.setBounds(10, 100, 98, 14);
			getContentPane().add(labelIntervaloI);
			
			spinnerIntervaloInicial = new JSpinner();
			spinnerIntervaloInicial.setBounds(115, 96, 89, 24);
			getContentPane().add(spinnerIntervaloInicial);
			
			JLabel labelIntervaloF = new JLabel("Intervalo final");
			labelIntervaloF.setFont(new Font("Roboto", Font.PLAIN, 13));
			labelIntervaloF.setBounds(279, 104, 98, 14);
			getContentPane().add(labelIntervaloF);
			
			spinnerIntervaloFinal = new JSpinner();
			spinnerIntervaloFinal.setBounds(384, 98, 89, 24);
			getContentPane().add(spinnerIntervaloFinal);
			
			textAreaResultados = new JTextArea();
			textAreaResultados.setEditable(false);
			getContentPane().add(textAreaResultados);
			JScrollPane scroll=new JScrollPane(textAreaResultados);
			
	        scroll.setBounds(360, 170, 120, 85);
	        getContentPane().add(scroll);

			labelTotal.setBounds(10, 138, 46, 14);
			campoTotal.setBounds(118, 134, 86, 24);
			labelProbabilidad.setBounds(279, 138, 103, 14);
			campoProbabilidad.setBounds(387, 134, 86, 24);
			labelResultado.setBounds(5, 230, 234, 14);
			campoResultado.setBounds(230, 226, 124, 24);
			this.setSize(new Dimension(501, 300));
			break;
		}
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(principal);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(botonCalcular)){
			switch (operacion) {
			case "binomial":
				calcularBinomial();
				break;
			case "intervalo binomial":
				calcularIntervaloBinomial();
				break;
			}
		}
		
	}
	
	private void calcularBinomial(){
		int n = Integer.parseInt(campoTotal.getText());
		Double p = Double.parseDouble(campoProbabilidad.getText());
		int k = Integer.parseInt(campoNumeroVeces.getText());
		labelResultado.setText("El resultado de b("+k+"; "+ n + ", " + p + "):");
		String resultado = String.valueOf(LibreriaMatematica.binomial(n, k, p));
		campoResultado.setText(resultado);
	}
	
	private void calcularIntervaloBinomial(){
		int n = Integer.parseInt(campoTotal.getText());
		Double p = Double.parseDouble(campoProbabilidad.getText());
		int intervaloInicial = (int) spinnerIntervaloInicial.getValue();
		int intervaloFinal = (int) spinnerIntervaloFinal.getValue();
		
		ArrayList<String> resultado = LibreriaMatematica.intervaloBinomial(n, p, intervaloInicial, intervaloFinal);
		for(int i=0;i<resultado.size()-1;i++){
			textAreaResultados.setText(textAreaResultados.getText() + resultado.get(i) + "\n");
		}
		
		
		labelResultado.setText("La suma de los result. P("+intervaloInicial + " >= X >= " + intervaloFinal + "):");
		campoResultado.setText(resultado.get(resultado.size()-1));
		
	}
}
