package gui;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JPanel;
import java.awt.BorderLayout;
/**
 * Esta clase crea un gráfico a partir de valores dados.
 * @author Victor Triste Pérez
 * @version 15-02-2023
 */
public class DialogoHistograma extends JDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase DialogoHistograma que recibe 3 parámetros.
	 * @param principal El frame en el cual se va a centrar el diálogo.
	 * @param x El arreglo de valores que se va a utilizar para el eje X de la gráfica a crear.
	 * @param frecuencia El arreglo de valores que se va utilizar para el eje Y de la gráfica a crear.
	 */
	public DialogoHistograma(JFrame principal, Double[] x, int[] frecuencia) {
		super(principal, "Histograma");
		JPanel panelPrincipal = new JPanel();
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);

		DefaultCategoryDataset datos = new DefaultCategoryDataset(); 
		for(int i=0;i<x.length;i++){
			datos.setValue(frecuencia[i], "matematicas", x[i]);
		}
		JFreeChart grafico = ChartFactory.createBarChart("Calificaciones de matematicas", "Calificaciones(x)", "Frecuencia",
				datos);
		ChartPanel panel = new ChartPanel(grafico);
		panel.setMouseWheelEnabled(true);
		panel.setPreferredSize(new Dimension(60*16, 500));

		panelPrincipal.setLayout(new BorderLayout());
		panelPrincipal.add(panel, BorderLayout.NORTH);

		pack();
		repaint();
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(principal);
		this.setVisible(true);
	}
}
