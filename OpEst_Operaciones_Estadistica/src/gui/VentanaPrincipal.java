package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class VentanaPrincipal extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuBar barraMenu;
	private JMenu menuArchivo;
	private JMenu menuTecnicasConteo;
	private JMenu menuDistribucionesP;
	private JMenu menuEstadisticaDescriptiva;
	private JMenu menuSistemasMultivariados;
	private JMenu menuAyuda;
	
	private JMenuItem opSalir;
	private JMenuItem opFactorial;
	private JMenuItem opPermutacion;
	private JMenuItem opCombinacion;
	private JMenuItem opDistribucionBinomial;
	private JMenuItem opIntervaloBinomial;
	private JMenuItem opAnalisisDatosAgrupados;
	private JMenuItem opAnalisisDatosNoAgrupados;
	private JMenuItem opAnalisisDatos;
	private JMenuItem opAcercaDe;
	private JMenuItem opAbrirManual;
	
	public VentanaPrincipal() {
		super("OpEst");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/icono.png")));
		
		menuArchivo = new JMenu("Archivo");
		menuArchivo.setIcon(new ImageIcon(getClass().getResource("/imagenes/archivo.png")));
		menuArchivo.setMnemonic(KeyEvent.VK_A);
		
		opSalir = new JMenuItem("Salir");
		opSalir.setIcon(new ImageIcon(getClass().getResource("/imagenes/salir.png")));
		opSalir.setMnemonic(KeyEvent.VK_S);
		opSalir.addActionListener(this);
		opSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		menuArchivo.add(opSalir);
		
		menuTecnicasConteo = new JMenu("Técnicas de conteo");
		menuTecnicasConteo.setIcon(new ImageIcon(getClass().getResource("/imagenes/conteo.png")));
		menuTecnicasConteo.setMnemonic(KeyEvent.VK_T);
		
		opFactorial = new JMenuItem("Factorial");
		opFactorial.setIcon(new ImageIcon(getClass().getResource("/imagenes/signoExclamacion.png")));
		opFactorial.setMnemonic(KeyEvent.VK_F);
		opFactorial.addActionListener(this);
		opFactorial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.ALT_DOWN_MASK));
		menuTecnicasConteo.add(opFactorial);
		
		opPermutacion = new JMenuItem("Permutación");
		opPermutacion.setIcon(new ImageIcon(getClass().getResource("/imagenes/letraP.png")));
		opPermutacion.setMnemonic(KeyEvent.VK_P);
		opPermutacion.addActionListener(this);
		opPermutacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_DOWN_MASK));
		menuTecnicasConteo.add(opPermutacion);
		
		opCombinacion = new JMenuItem("Combinación");
		opCombinacion.setIcon(new ImageIcon(getClass().getResource("/imagenes/letraC.png")));
		opCombinacion.setMnemonic(KeyEvent.VK_C);
		opCombinacion.addActionListener(this);
		opCombinacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		menuTecnicasConteo.add(opCombinacion);
		
		menuDistribucionesP = new JMenu("Distr. de probabilidad");
		menuDistribucionesP.setIcon(new ImageIcon(getClass().getResource("/imagenes/probabilidad.png")));
		menuDistribucionesP.setMnemonic(KeyEvent.VK_T);
		
		opDistribucionBinomial = new JMenuItem("Distribución binomial");
		opDistribucionBinomial.setIcon(new ImageIcon(getClass().getResource("/imagenes/binomial.png")));
		opDistribucionBinomial.setMnemonic(KeyEvent.VK_B);
		opDistribucionBinomial.addActionListener(this);
		opDistribucionBinomial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_DOWN_MASK));
		menuDistribucionesP.add(opDistribucionBinomial);
		
		opIntervaloBinomial = new JMenuItem("Intervalo binomial");
		opIntervaloBinomial.setIcon(new ImageIcon(getClass().getResource("/imagenes/intervaloBinomial.png")));
		opIntervaloBinomial.setMnemonic(KeyEvent.VK_I);
		opIntervaloBinomial.addActionListener(this);
		opIntervaloBinomial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_DOWN_MASK));
		menuDistribucionesP.add(opIntervaloBinomial);
		
		menuEstadisticaDescriptiva = new JMenu("Estad. descriptiva");
		menuEstadisticaDescriptiva.setIcon(new ImageIcon(getClass().getResource("/imagenes/descriptiva.png")));
		menuEstadisticaDescriptiva.setMnemonic(KeyEvent.VK_D);
		
		opAnalisisDatosAgrupados = new JMenuItem("Análisis de datos agrupados");
		opAnalisisDatosAgrupados.setIcon(new ImageIcon(getClass().getResource("/imagenes/salir.png")));
		opAnalisisDatosAgrupados.setMnemonic(KeyEvent.VK_A);
		opAnalisisDatosAgrupados.addActionListener(this);
		opAnalisisDatosAgrupados.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));
		menuEstadisticaDescriptiva.add(opAnalisisDatosAgrupados);
		
		opAnalisisDatosNoAgrupados = new JMenuItem("Análisis de datos no agrupados");
		opAnalisisDatosNoAgrupados.setIcon(new ImageIcon(getClass().getResource("/imagenes/salir.png")));
		opAnalisisDatosNoAgrupados.setMnemonic(KeyEvent.VK_N);
		opAnalisisDatosNoAgrupados.addActionListener(this);
		opAnalisisDatosNoAgrupados.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK));
		menuEstadisticaDescriptiva.add(opAnalisisDatosNoAgrupados);
		
		menuSistemasMultivariados = new JMenu("Sistemas multivariados");
		menuSistemasMultivariados.setIcon(new ImageIcon(getClass().getResource("/imagenes/descriptiva.png")));
		menuSistemasMultivariados.setMnemonic(KeyEvent.VK_M);
		
		opAnalisisDatos = new JMenuItem("Análisis de datos");
		opAnalisisDatos.setIcon(new ImageIcon(getClass().getResource("/imagenes/salir.png")));
		opAnalisisDatos.setMnemonic(KeyEvent.VK_D);
		opAnalisisDatos.addActionListener(this);
		opAnalisisDatos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_DOWN_MASK));
		menuSistemasMultivariados.add(opAnalisisDatos);
		
		menuAyuda = new JMenu("Ayuda");
		menuAyuda.setIcon(new ImageIcon(getClass().getResource("/imagenes/ayuda.png")));
		menuAyuda.setMnemonic(KeyEvent.VK_Y);
		
		opAbrirManual = new JMenuItem("Abrir manual");
		opAbrirManual.setIcon(new ImageIcon(getClass().getResource("/imagenes/manual.png")));
		opAbrirManual.setMnemonic(KeyEvent.VK_M);
		opAbrirManual.addActionListener(this);
		opAbrirManual.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_DOWN_MASK));
		menuAyuda.add(opAbrirManual);
		
		opAcercaDe = new JMenuItem("Acerca de...");
		opAcercaDe.setIcon(new ImageIcon(getClass().getResource("/imagenes/acercaDe.png")));
		opAcercaDe.setMnemonic(KeyEvent.VK_E);
		opAcercaDe.addActionListener(this);
		opAcercaDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK));
		menuAyuda.add(opAcercaDe);
		
		barraMenu = new JMenuBar();
		barraMenu.add(menuArchivo);
		barraMenu.add(menuTecnicasConteo);
		barraMenu.add(menuDistribucionesP);
		barraMenu.add(menuEstadisticaDescriptiva);
		barraMenu.add(menuSistemasMultivariados);
		barraMenu.add(menuAyuda);
		this.setJMenuBar(barraMenu);
		
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setResizable(false);
		this.getContentPane().setLayout(new FlowLayout());
		JLabel fondo = new JLabel();
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg"));
		Image imagenEscalada = imagenFondo.getImage().getScaledInstance(getSize().width + 1080, getSize().height + 720,
				Image.SCALE_SMOOTH);
		fondo.setIcon(new ImageIcon(imagenEscalada));
		this.getContentPane().add(fondo);
		this.getContentPane().setBackground(Color.BLACK);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(opSalir)){
			System.exit(0);
		} else if(e.getSource().equals(opFactorial)){
			new DialogoTecnicasConteo(this,"factorial");
		} else if(e.getSource().equals(opPermutacion)){
			new DialogoTecnicasConteo(this,"permutación");
		} else if(e.getSource().equals(opCombinacion)){
			new DialogoTecnicasConteo(this,"combinación");
		} else if(e.getSource().equals(opDistribucionBinomial)){
			new DialogoDistProbabilidad(this, "binomial");
		} else if(e.getSource().equals(opIntervaloBinomial)){
			new DialogoDistProbabilidad(this, "intervalo binomial");
		} else if(e.getSource().equals(opAnalisisDatosAgrupados)){
			new DialogoEstadDescriptiva(this,"datos agrupados");
		} else if(e.getSource().equals(opAnalisisDatosNoAgrupados)){
			new DialogoEstadDescriptiva(this,"datos no agrupados");
		} else if(e.getSource().equals(opAnalisisDatos)){
			
		} else if(e.getSource().equals(opAbrirManual)){
			abrirManual();
		} else if(e.getSource().equals(opAcercaDe)){
			mensajeAcercaDe();
		}
		
	}
	
	private void mensajeAcercaDe() {
		JOptionPane.showMessageDialog(this, "", "Acerca de...OpEst", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(getClass().getResource("/Imagenes/creditos.jpg")));
	}

	public void abrirManual() {
		File archivo = new File("src/Manual OpEst.pdf");
		try {
			Desktop.getDesktop().open(archivo);
		} catch (IOException e) {
		}
	}
}
