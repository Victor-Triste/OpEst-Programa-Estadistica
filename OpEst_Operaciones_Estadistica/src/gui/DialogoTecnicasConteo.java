package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import libreria.LibreriaMatematica;

public class DialogoTecnicasConteo extends JDialog implements ActionListener, FocusListener {

	private static final long serialVersionUID = 1L;
	private JTextField campo1;
	private JTextField campo2;
	private JTextField campoResultado;
	private JButton botonCalcular;
	private JLabel labelResultado;
	private String operacion;
	private boolean esNumero1 = false;
	private boolean esNumero2 = false;

	public DialogoTecnicasConteo(JFrame principal, String operacionSeleccionada) {
		super(principal, "Técnicas de conteo: " + operacionSeleccionada);
		operacion = operacionSeleccionada;
		getContentPane().setLayout(null);

		JLabel lblTitulo = new JLabel();
		lblTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
		lblTitulo.setText(operacionSeleccionada);
		getContentPane().add(lblTitulo);

		JLabel labelTipo = new JLabel();
		labelTipo.setFont(new Font("Roboto", Font.BOLD, 20));
		labelTipo.setBounds(162, 75, 18, 14);
		getContentPane().add(labelTipo);

		campo1 = new JTextField();
		getContentPane().add(campo1);
		campo1.setColumns(10);
		campo1.addFocusListener(this);

		switch (operacionSeleccionada) {
		case "permutación":
		case "combinación":
			labelTipo.setText(operacionSeleccionada.substring(0, 1).toUpperCase());
			lblTitulo.setBounds(115, 11, 147, 24);
			campo1.setBounds(66, 66, 86, 29);
			campo2 = new JTextField();
			campo2.setBounds(190, 66, 86, 29);
			getContentPane().add(campo2);
			campo2.setColumns(10);
			campo2.addFocusListener(this);
			break;

		case "factorial":
			lblTitulo.setBounds(135, 11, 147, 24);
			campo1.setBounds(130, 66, 86, 29);
			break;
		}

		botonCalcular = new JButton("Calcular");
		botonCalcular.setBounds(130, 120, 86, 29);
		getContentPane().add(botonCalcular);
		botonCalcular.addActionListener(this);

		labelResultado = new JLabel("Resultado:");
		labelResultado.setFont(new Font("Roboto", Font.PLAIN, 13));
		labelResultado.setBounds(25, 176, 160, 14);
		getContentPane().add(labelResultado);

		campoResultado = new JTextField();
		campoResultado.setEditable(false);
		campoResultado.setBounds(195, 170, 120, 29);
		getContentPane().add(campoResultado);
		campoResultado.setColumns(10);

		this.setSize(new Dimension(372, 244));
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(principal);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(botonCalcular)) {
			switch (operacion) {
			case "permutación":
				calcularPermutacion();
				break;
			case "combinación":
				calcularCombinacion();
				break;
			case "factorial":
				calcularFactorial();
				break;
			}
		}
	}

	private void calcularFactorial() {
		if (esNumero1) {
			int valor1 = Integer.parseInt(campo1.getText());
			labelResultado.setText("El factorial de " + valor1 + " es: ");
			campoResultado.setText(String.valueOf(LibreriaMatematica.factorial(valor1)));
		} else {
			JOptionPane.showMessageDialog(this, "El campo debe de contener solo números enteros",
					"Entrada de datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void calcularPermutacion() {
		if (esNumero1 && esNumero2) {
			int valor1 = Integer.parseInt(campo1.getText());
			int valor2 = Integer.parseInt(campo2.getText());

			if (esEntradaCorrecta(valor1, valor2)) {
				labelResultado.setText("El resultado de " + valor1 + " P " + valor2 + " es: ");
				campoResultado.setText(String.valueOf(LibreriaMatematica.permutacion(valor1, valor2)));
			}
		} else {
			JOptionPane.showMessageDialog(this, "Los campos deben de contener solo números enteros",
					"Entrada de datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void calcularCombinacion() {
		if (esNumero1 && esNumero2) {
			int valor1 = Integer.parseInt(campo1.getText());
			int valor2 = Integer.parseInt(campo2.getText());

			if (esEntradaCorrecta(valor1, valor2)) {
				labelResultado.setText("El resultado de " + valor1 + " C " + valor2 + " es: ");
				campoResultado.setText(String.valueOf(LibreriaMatematica.combinacion(valor1, valor2)));
			}
		} else {
			JOptionPane.showMessageDialog(this, "Los campos deben de contener solo números enteros",
					"Entrada de datos incorrectos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean esEntradaCorrecta(int valor1, int valor2) {
		if (valor1 < valor2) {
			JOptionPane.showMessageDialog(this,
					"No se puede realizar esta operación porque el \n primer valor debe ser más grande que el segundo",
					"Operación incorrecta", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource().equals(campo1)) {
			if (!esNumerico(campo1.getText())) {
				esNumero1 = false;
			} else {
				esNumero1 = true;
			}
		} else if (e.getSource().equals(campo2)) {
			if (!esNumerico(campo2.getText())) {
				esNumero2 = false;
			} else {
				esNumero2 = true;
			}
		}
	}

	private boolean esNumerico(String valor) {
		try {
			Integer.parseInt(valor);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
