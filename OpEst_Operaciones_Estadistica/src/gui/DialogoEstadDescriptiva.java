package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import libreria.LibreriaMatematica;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class DialogoEstadDescriptiva extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton botonInsertar;
	private JButton botonSeleccionarArchivoCsv;
	private JButton botonGraficar;
	private JButton botonLimpiar;
	private JSpinner spinnerNumElementos;
	private JFrame principal;
	private File archivoSeleccionado;
	private int numElementos;
	private JLabel labelNumElementos;
	private JTable tablaFrecuencias;
	private JLabel labelModa;
	private JLabel labelMediaPonderada;
	private JLabel labelMediaGeometrica;
	private JLabel labelMediaAritmetica;
	private JLabel labelMediana;
	private JLabel labelVarianza;
	private JLabel labelDesvEstandar;
	private JLabel labelDesvMedia;
	private JLabel labelDesvMediana;
	private JLabel labelRango;
	private int filaX=0;
	private int filaFrecuencia=1;
	private String operacion;
	private ArrayList<ArrayList<Object>> datos = new ArrayList<ArrayList<Object>>();

	// se crea el modelo de la tabla y se sobrescribe el metodo isCellEditable
	// para que la tabla no sea editable.
	private DefaultTableModel modeloTabla = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int fila, int columna) {
			return false;
		}
	};

	public DialogoEstadDescriptiva(JFrame framePrincipal, String operacionSeleccionada) {
		super(framePrincipal, "Estadística descriptiva: " + operacionSeleccionada);
		principal = framePrincipal;
		operacion = operacionSeleccionada;
		getContentPane().setLayout(null);
		String[] columnas = null;

		switch (operacion) {
		case "datos agrupados":
			columnas = new String[] { "Li", "Ls", "x", "f", "F", "fr", "xf", "x^2", "fx^2", "x-media", "|x-media|",
					"|x-media|*f", "log(x)" };
			break;
		case "datos no agrupados":
			columnas = new String[] { "x", "f", "F", "fr", "xf", "x^2", "fx^2", "x-media", "|x-media|",
					"|x-media|*f", "log(x)" };
			break;
		}

		tablaFrecuencias = new JTable();
		tablaFrecuencias.setModel(modeloTabla);

		for (int i = 0; i < columnas.length; i++) {
			modeloTabla.addColumn(columnas[i]);
		}

		JScrollPane scroll = new JScrollPane(tablaFrecuencias);
		scroll.setBounds(371, 52, 801, 172);
		getContentPane().add(scroll);

		JLabel labelTitulo = new JLabel("Tabla de frecuencias y cálculos para el análisis");
		labelTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
		labelTitulo.setBounds(555, 11, 462, 32);
		getContentPane().add(labelTitulo);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(10, 11, 351, 211);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel labelIndicacion = new JLabel("Insertar datos");
		labelIndicacion.setFont(new Font("Roboto", Font.BOLD, 15));
		labelIndicacion.setBounds(123, 11, 104, 18);
		panel.add(labelIndicacion);

		JLabel labelN = new JLabel("Número de elementos (n)");
		labelN.setFont(new Font("Roboto", Font.PLAIN, 12));
		labelN.setBounds(10, 52, 176, 14);
		panel.add(labelN);

		spinnerNumElementos = new JSpinner();
		spinnerNumElementos.setBounds(177, 50, 83, 20);
		SpinnerNumberModel modeloSpinner = new SpinnerNumberModel();
		modeloSpinner.setMinimum(2);
		modeloSpinner.setMaximum(100);
		//modeloSpinner.setStepSize(1);
		spinnerNumElementos.setModel(modeloSpinner);
		panel.add(spinnerNumElementos);
		modeloSpinner.setValue(2);

		JLabel lblOBienPuede = new JLabel("O bien puede cargar datos desde un archivo de su computadora");
		lblOBienPuede.setFont(new Font("Roboto", Font.ITALIC, 12));
		lblOBienPuede.setBounds(6, 130, 341, 14);
		panel.add(lblOBienPuede);

		botonSeleccionarArchivoCsv = new JButton("Seleccionar archivo CSV");
		botonSeleccionarArchivoCsv.setBounds(76, 155, 202, 37);
		botonSeleccionarArchivoCsv.setIcon(new ImageIcon(getClass().getResource("/imagenes/abrirArchivo.png")));
		panel.add(botonSeleccionarArchivoCsv);
		botonSeleccionarArchivoCsv.addActionListener(this);

		botonInsertar = new JButton("Insertar");
		botonInsertar.setBounds(10, 82, 115, 29);
		botonInsertar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		panel.add(botonInsertar);
		botonInsertar.addActionListener(this);

		labelNumElementos = new JLabel("N = ");
		labelNumElementos.setFont(new Font("Roboto", Font.PLAIN, 13));
		labelNumElementos.setBounds(374, 27, 26, 14);
		getContentPane().add(labelNumElementos);

		labelNumElementos = new JLabel("0");
		labelNumElementos.setFont(new Font("Roboto", Font.PLAIN, 13));
		labelNumElementos.setForeground(Color.RED);
		labelNumElementos.setBounds(394, 27, 26, 14);
		getContentPane().add(labelNumElementos);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(371, 235, 801, 114);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblMedidasDeTendencia = new JLabel("Medidas de tendencia central");
		lblMedidasDeTendencia.setBounds(300, 11, 223, 18);
		panel_1.add(lblMedidasDeTendencia);
		lblMedidasDeTendencia.setFont(new Font("Roboto", Font.BOLD, 15));

		JLabel lblMediaAritmtica = new JLabel("Media Aritmética");
		lblMediaAritmtica.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblMediaAritmtica.setBounds(24, 34, 98, 14);
		panel_1.add(lblMediaAritmtica);

		JLabel lblMediaGeomtrica = new JLabel("Media Geométrica");
		lblMediaGeomtrica.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblMediaGeomtrica.setBounds(190, 34, 111, 14);
		panel_1.add(lblMediaGeomtrica);

		JLabel lblMediaPonderada = new JLabel("Media Ponderada");
		lblMediaPonderada.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblMediaPonderada.setBounds(378, 34, 104, 14);
		panel_1.add(lblMediaPonderada);

		JLabel lblMediana = new JLabel("Mediana");
		lblMediana.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblMediana.setBounds(568, 34, 57, 14);
		panel_1.add(lblMediana);

		JLabel lblModa = new JLabel("Moda");
		lblModa.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblModa.setBounds(705, 34, 40, 14);
		panel_1.add(lblModa);

		labelMediaAritmetica = new JLabel("0.0");
		labelMediaAritmetica.setBounds(24, 59, 46, 14);
		panel_1.add(labelMediaAritmetica);

		labelMediaGeometrica = new JLabel("0.0");
		labelMediaGeometrica.setBounds(190, 59, 46, 14);
		panel_1.add(labelMediaGeometrica);

		labelMediaPonderada = new JLabel("0.0");
		labelMediaPonderada.setBounds(378, 59, 46, 14);
		panel_1.add(labelMediaPonderada);

		labelMediana = new JLabel("0.0");
		labelMediana.setBounds(568, 59, 46, 14);
		panel_1.add(labelMediana);

		labelModa = new JLabel("0.0");
		labelModa.setBounds(705, 59, 46, 14);
		panel_1.add(labelModa);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(371, 360, 801, 114);
		getContentPane().add(panel_2);

		JLabel lblMedidasDeDispersin = new JLabel("Medidas de dispersión");
		lblMedidasDeDispersin.setFont(new Font("Roboto", Font.BOLD, 15));
		lblMedidasDeDispersin.setBounds(300, 11, 182, 18);
		panel_2.add(lblMedidasDeDispersin);

		JLabel lblVarianza = new JLabel("Varianza");
		lblVarianza.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblVarianza.setBounds(27, 35, 57, 14);
		panel_2.add(lblVarianza);

		JLabel lblDesviacinEstndar = new JLabel("Desviación estándar");
		lblDesviacinEstndar.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblDesviacinEstndar.setBounds(145, 35, 128, 14);
		panel_2.add(lblDesviacinEstndar);

		JLabel lblDesviacinMedia = new JLabel("Desviación media");
		lblDesviacinMedia.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblDesviacinMedia.setBounds(343, 35, 104, 14);
		panel_2.add(lblDesviacinMedia);

		JLabel lblDesviacinMediana = new JLabel("Desviación mediana");
		lblDesviacinMediana.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblDesviacinMediana.setBounds(516, 35, 122, 14);
		panel_2.add(lblDesviacinMediana);

		JLabel lblRango = new JLabel("Rango");
		lblRango.setFont(new Font("Roboto", Font.PLAIN, 13));
		lblRango.setBounds(704, 33, 40, 18);
		panel_2.add(lblRango);

		labelRango = new JLabel("0.0");
		labelRango.setBounds(704, 62, 46, 14);
		panel_2.add(labelRango);

		labelDesvMediana = new JLabel("0.0");
		labelDesvMediana.setBounds(516, 60, 46, 14);
		panel_2.add(labelDesvMediana);

		labelDesvMedia = new JLabel("0.0");
		labelDesvMedia.setBounds(343, 60, 46, 14);
		panel_2.add(labelDesvMedia);

		labelDesvEstandar = new JLabel("0.0");
		labelDesvEstandar.setBounds(145, 60, 46, 14);
		panel_2.add(labelDesvEstandar);

		labelVarianza = new JLabel("0.0");
		labelVarianza.setBounds(27, 60, 46, 14);
		panel_2.add(labelVarianza);

		botonGraficar = new JButton("Graficar histograma");
		botonGraficar.setBounds(84, 233, 190, 39);
		botonGraficar.setIcon(new ImageIcon(getClass().getResource("/imagenes/grafica.png")));
		botonGraficar.addActionListener(this);
		getContentPane().add(botonGraficar);

		botonLimpiar = new JButton("Limpiar datos");
		botonLimpiar.setBounds(84, 300, 190, 37);
		botonLimpiar.setIcon(new ImageIcon(getClass().getResource("/imagenes/limpiar.png")));
		getContentPane().add(botonLimpiar);
		botonLimpiar.addActionListener(this);

		this.setSize(new Dimension(1200, 550));
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(principal);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(botonInsertar)) {
			numElementos = (int) spinnerNumElementos.getValue();
			new DialogoCapturarDatos(principal, numElementos);

		} else if (e.getSource().equals(botonSeleccionarArchivoCsv)) {
			abrirArchivo();
		} else if (e.getSource().equals(botonGraficar)) {
			crearHistograma();
		} else if (e.getSource().equals(botonLimpiar)) {
			limpiarDatos();
		}
	}

	private void abrirArchivo() {
		JFileChooser dialogo = new JFileChooser();
		dialogo.setDialogTitle("Abrir archivo CSV");
		FileFilter filtro = new FileNameExtensionFilter("Archivos CSV", "csv", "CSV");
		dialogo.setFileFilter(filtro);
		dialogo.setAcceptAllFileFilterUsed(false);
		dialogo.setSelectedFile(null);
		dialogo.setMultiSelectionEnabled(false);
		int valor = dialogo.showOpenDialog(null);

		// Si el usuario presionó abrir o guardar.
		if (valor == JFileChooser.APPROVE_OPTION) {
			File archivo = dialogo.getSelectedFile();
			if (archivo.exists()) {
				if(archivoSeleccionado != null){
					limpiarDatos();
				}
				archivoSeleccionado = archivo;
				leerArchivo(archivoSeleccionado);
			} else {
				JOptionPane.showMessageDialog(this,
						"El archivo que usted seleccionó no existe.\n Vuelva a intentarlo por favor",
						"Archivo CSV no encontrado", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void leerArchivo(File archivoSeleccionado) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(archivoSeleccionado.getAbsolutePath()));
			String line = br.readLine();
			while (null != line) {
				String[] fields = line.split(",");
				line = br.readLine();
				cargarDatosTabla(fields);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se pudo recuperar la información del archivo. Verifique que su archivo sea correcto \n o intente abrir el archivo con el otro tipo de dato(agrupado o no agrupado)",
					"Error de lectura", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void cargarDatosTabla(String[] datosEntrada) {
		labelNumElementos.setText(String.valueOf(datosEntrada.length));
		ArrayList<Object> resultados = new ArrayList<Object>();
		
		if (operacion == "datos agrupados") {
			datos = LibreriaMatematica.calculosDatosAgrupados(datosEntrada);
			for (int i = 0; i < datos.size(); i++) {
				modeloTabla.addRow(new Object[] { datos.get(i).get(0), datos.get(i).get(1), datos.get(i).get(2),
						datos.get(i).get(3), datos.get(i).get(4), datos.get(i).get(5), datos.get(i).get(6),
						datos.get(i).get(7), datos.get(i).get(8), datos.get(i).get(9), datos.get(i).get(10),datos.get(i).get(11),datos.get(i).get(12) });
			}
			resultados = LibreriaMatematica.medidasTendenciaDatosAgrupados(datos, datosEntrada);
			filaX=2;
			filaFrecuencia=3;
		} else {
			datos = LibreriaMatematica.calculosDatosNoAgrupados(datosEntrada);
			for (int i = 0; i < datos.size(); i++) {
				modeloTabla.addRow(new Object[] { datos.get(i).get(0), datos.get(i).get(1), datos.get(i).get(2),
						datos.get(i).get(3), datos.get(i).get(4), datos.get(i).get(5), datos.get(i).get(6),
						datos.get(i).get(7), datos.get(i).get(8), datos.get(i).get(9), datos.get(i).get(10) });
			}
			resultados = LibreriaMatematica.medidasTendenciaCentral(datos, datosEntrada);
			
			
			filaX=0;
			filaFrecuencia=1;
		}
		labelMediaAritmetica.setText(resultados.get(0).toString());
		labelMediaGeometrica.setText(resultados.get(1).toString());
		labelMediaPonderada.setText(resultados.get(2).toString());
		labelMediana.setText(resultados.get(3).toString());
		labelModa.setText(resultados.get(4).toString());
		labelVarianza.setText(resultados.get(5).toString());
		labelDesvEstandar.setText(resultados.get(6).toString());
		labelDesvMedia.setText(resultados.get(7).toString());
		labelDesvMediana.setText(resultados.get(8).toString());
		labelRango.setText(resultados.get(9).toString());
	}

	private void crearHistograma() {
		if (labelNumElementos.getText() == "0") {
			JOptionPane.showMessageDialog(this, "No hay datos para crear la gráfica", "Tabla vacía",
					JOptionPane.ERROR_MESSAGE);
		} else {
			Double[] x = new Double[datos.size()];
			int[] frecuencia = new int[datos.size()];

			for (int i = 0; i < datos.size(); i++) {
				x[i] = Double.parseDouble(datos.get(i).get(filaX).toString());
				frecuencia[i] = Integer.parseInt(datos.get(i).get(filaFrecuencia).toString());
			}
			new DialogoHistograma(principal, x, frecuencia);
		}
	}

	private void limpiarDatos() {
		if (labelNumElementos.getText() == "0") {
			JOptionPane.showMessageDialog(this, "No hay datos para limpiar", "Tabla vacía", JOptionPane.ERROR_MESSAGE);
		} else {
			for (int i = 0; i < tablaFrecuencias.getRowCount(); i++) {
				modeloTabla.removeRow(i);
				i -= 1;
			}
			labelNumElementos.setText("0");
			labelMediaAritmetica.setText("0.0");
			labelMediaGeometrica.setText("0.0");
			labelMediaPonderada.setText("0.0");
			labelMediana.setText("0.0");
			labelModa.setText("0.0");
			labelVarianza.setText("0.0");
			labelDesvEstandar.setText("0.0");
			labelDesvMedia.setText("0.0");
			labelDesvMediana.setText("0.0");
			labelRango.setText("0.0");
		}
	}
}
