package libreria;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class LibreriaMatematica {
	/**
	 * 
	 * @param numero Al cual se le va a calcular el factorial
	 * @return El resultado del factorial calculado
	 */
	public static BigInteger factorial(int numero) {
		BigInteger NUMERO = new BigInteger(String.valueOf(numero));
		if (numero == 0) {
			return BigInteger.ONE;
		} else {
			return NUMERO.multiply(factorial(numero-1));
		}
	}
	
	public static BigInteger combinacion(int n, int k){
		BigInteger val = factorial(k).multiply(factorial(n-k));
		return factorial(n).divide(val);
	}
	
	public static BigInteger permutacion(int n, int k) {
		return factorial(n).divide(factorial(n - k));
	}
	
	public static double binomial(int n, int k, double probabilidad){
		BigInteger combinacion = combinacion(n, k);
		double resultadoCombinacion = Double.valueOf(combinacion.toString()).doubleValue();
	
		double operacionP = Math.pow(probabilidad, k);
		double operacionQ = Math.pow(1-probabilidad, n-k);
		double resultado = resultadoCombinacion * operacionP * operacionQ;
		
		return resultado;
	}
	
	public static ArrayList<String> intervaloBinomial(int n, double probabilidad, int intervaloInicio, int intervaloFin){
		ArrayList<String> resultados = new ArrayList<String>();
		DecimalFormat decimalFormat =  new DecimalFormat("#.###");
		double acumulador = 0.000;
		for(int i=intervaloInicio;i<=intervaloFin;i++){
			acumulador += binomial(n,i,probabilidad);
			resultados.add("P["+i+"]= "+decimalFormat.format(binomial(n,i,probabilidad)));
		}
		resultados.add(decimalFormat.format(acumulador));
		return resultados;
	}
	
	
	
	
	public static ArrayList<ArrayList<Object>> calculosDatosNoAgrupados(String[] arregloDatos){
		ArrayList<ArrayList<Object>> calculos = new ArrayList<ArrayList<Object>>();
		int acumuladorFrecuencia = 0;
		int totalXF = 0;
		DecimalFormat decimalFormat =  new DecimalFormat("#.###");
		
		List<Double> datos = new ArrayList<Double>(); //lista de datos
		for(int i=0;i<arregloDatos.length;i++){
			datos.add(Double.parseDouble(arregloDatos[i].toString()));
		}
		Collections.sort(datos);
		LinkedHashSet<Double> set = new LinkedHashSet<Double>(datos);
		
		for(Double x: set){
			ArrayList<Object> filaX = new ArrayList<Object>();
			filaX.add(x); 
			
			int frecuencia = Collections.frequency(datos,x); 
			filaX.add(frecuencia); 
			
			acumuladorFrecuencia = acumuladorFrecuencia + frecuencia; 
			filaX.add(acumuladorFrecuencia); 
			
			double fr = (double)frecuencia / (double)arregloDatos.length;
			String formatoFr = decimalFormat.format(fr);
			filaX.add(formatoFr); 
			
			double xf = x*frecuencia; 
			filaX.add(xf); 
			totalXF += xf;
			
			double x2 = Math.pow(x,2); 
			filaX.add(x2); 
			
			double fx2= frecuencia*x2; 
			filaX.add(fx2);
			
			calculos.add(filaX);
		}
		
		double media = (double)totalXF/arregloDatos.length; //Se calculo la media
		
		for(int i=0;i<calculos.size();i++){
			double xMedia = Double.valueOf((calculos.get(i).get(0).toString())) - media;
			String formatoXMedia = decimalFormat.format(xMedia);
			calculos.get(i).add(formatoXMedia);
			
			double absXMedia = Math.abs(xMedia);
			String formatoabsXMedia = decimalFormat.format(absXMedia);
			calculos.get(i).add(formatoabsXMedia);
			
			double absXMediaF = absXMedia * Double.parseDouble(calculos.get(i).get(1).toString());
			String formatoabsXMediaF = decimalFormat.format(absXMediaF);
			calculos.get(i).add(formatoabsXMediaF);
			
			double logX = Double.parseDouble(calculos.get(i).get(1).toString()) * Math.log10(Double.parseDouble(calculos.get(i).get(0).toString()));
			String logXx = decimalFormat.format(logX);
			calculos.get(i).add(logXx);
			
		}		
		return calculos;
	}
	
	public static ArrayList<Object> medidasTendenciaCentral(ArrayList<ArrayList<Object>> tablaDatos, String[] arregloDatos){
		ArrayList<Object> resultados = new ArrayList<Object>();
		DecimalFormat decimalFormat =  new DecimalFormat("#.###");
		int totalF = 0;
		double totalXf = 0.0;
		double totalFx2 = 0.0;
		double totalabsXMediaF = 0.0;
		double totalLogX = 0.0;
		int indiceMayor = 0;
		double moda = 0;
		
		for(int i=0;i<tablaDatos.size();i++){
			totalF += Integer.parseInt(tablaDatos.get(i).get(1).toString()); 
			totalXf += Double.parseDouble(tablaDatos.get(i).get(4).toString());
			totalFx2 += Double.parseDouble(tablaDatos.get(i).get(6).toString());
			totalabsXMediaF += Double.parseDouble(tablaDatos.get(i).get(9).toString());
			totalLogX += Double.parseDouble(tablaDatos.get(i).get(10).toString());
			int frecuencia = Integer.parseInt(tablaDatos.get(i).get(1).toString());
			if(frecuencia> indiceMayor){
				indiceMayor = frecuencia;
				moda = Double.parseDouble(tablaDatos.get(i).get(0).toString());
			}
		}
		
		Arrays.sort(arregloDatos);
		double mediana;
		int mitad = arregloDatos.length/2;
		if (arregloDatos.length % 2 == 0) {
			mediana = (Double.parseDouble(arregloDatos[mitad-1].toString()) + Double.parseDouble(arregloDatos[mitad].toString())) /2;
		} else {
			mediana = Double.parseDouble(arregloDatos[mitad].toString());
		}
		
		//MEDIDAS DE TENDENCIA CENTRAL
		double mediaAritmetica = (double)totalXf/totalF;
		resultados.add(decimalFormat.format(mediaAritmetica)); //media aritmetica
		double mediaGeometrica = Math.pow(10,(totalLogX / totalF));
		resultados.add(decimalFormat.format(mediaGeometrica)); //media geometrica
		resultados.add("");//media ponderada
		resultados.add(decimalFormat.format(mediana)); //mediana
		resultados.add(decimalFormat.format(moda));//moda
		
		//MEDIDAS DE DISPERSION
		double varianza = ((totalFx2 - ((Math.pow(totalXf,2))/totalF)) / (totalF-1));
		resultados.add(decimalFormat.format(varianza)); //varianza
		double desvEstandar = Math.sqrt(varianza);
		resultados.add(decimalFormat.format(desvEstandar)); //desv. estandar
		double desvMedia = totalabsXMediaF / totalF;
		resultados.add(decimalFormat.format(desvMedia)); //desv. media
		resultados.add(""); //desv. mediana
		double ultimoNumero = Double.parseDouble(tablaDatos.get(tablaDatos.size()-1).get(0).toString());
		double primerNumero = Double.parseDouble(tablaDatos.get(0).get(0).toString());
		double rango = ultimoNumero - primerNumero;
		resultados.add(decimalFormat.format(rango)); //rango
		
		return resultados;
	}
	
	
	public static ArrayList<ArrayList<Object>> calculosDatosAgrupados(String[] arregloDatos){
		
		DecimalFormat decimalFormat =  new DecimalFormat("#.###");
		
		List<Double> datos = new ArrayList<Double>(); //lista de datos
		for(int i=0;i<arregloDatos.length;i++){
			datos.add(Double.parseDouble(arregloDatos[i].toString()));
		}
		Collections.sort(datos);

		int numElementos = datos.size();
		double valorMaximo = Double.parseDouble(datos.get(datos.size()-1).toString());
		double valorMinimo = Double.parseDouble(datos.get(0).toString());
		double rango = valorMaximo-valorMinimo;
		
		double numIntervalos = 1+3.22*Math.log10(numElementos);
		if (numIntervalos % 1 == 0) {    
	    } else {
	        numIntervalos = (int)numIntervalos;
	        numIntervalos = (int)numIntervalos +1;
	    }
		
		double amplitud = rango/numIntervalos;
		if (amplitud % 1 == 0) {
	    } else {
	        amplitud = (int)amplitud;
	        amplitud = amplitud +1;
	    }
		
		double nuevoRango = amplitud * numIntervalos;
		double diferencia= nuevoRango - rango;
		double excedente = diferencia/2;
		
		/*System.out.println("numero de elementos: "+numElementos);
		System.out.println(valorMinimo);
		System.out.println(valorMaximo);
		System.out.println("rango: "+rango);
		System.out.println("num intervalos: "+numIntervalos);
		System.out.println("amplitud: "+amplitud);
		System.out.println("nuevo rango: " + nuevoRango);
		System.out.println("diferencia: " + diferencia);
		System.out.println("excedente: " + excedente);
		*/
		
		
		
		
		ArrayList<ArrayList<Object>> calculos = new ArrayList<ArrayList<Object>>();
		double limiteInferior = 0;
		double limiteSuperior = 0;
		//Ciclo que permite crear los intervalos y los almacena en el arraylist
		for(int i=0;i<(int)numIntervalos;i++){
			ArrayList<Object> filaIntervaloX = new ArrayList<Object>();
			
			if(i==0){
				limiteInferior = valorMinimo-excedente;
				limiteSuperior = limiteInferior+amplitud;
			} else {
				limiteInferior = Double.parseDouble(calculos.get(i-1).get(1).toString());
				limiteSuperior = limiteInferior +amplitud;
			}
			
			filaIntervaloX.add(limiteInferior);
			filaIntervaloX.add(limiteSuperior);
			
			calculos.add(filaIntervaloX); //intervalos li, ls
		}
		
		
		for(int i=0;i<calculos.size();i++){
			double li = Double.parseDouble(calculos.get(i).get(0).toString());
			double ls = Double.parseDouble(calculos.get(i).get(1).toString());
			double x = (ls+li)/2;
			calculos.get(i).add(x); // columna x
		}
		
		
		
		
		//Calcula la frecuencia de cada intervalo
		int contador = 0;
		int j=0;
		for(int i=0;i<datos.size();i++){

				double li = Double.parseDouble(calculos.get(j).get(0).toString());
				double ls = Double.parseDouble(calculos.get(j).get(1).toString());
				if(datos.get(i) >=li && datos.get(i)<ls){
					contador = contador+1;
					if(i == datos.size()-1){
						calculos.get(j).add(contador); //columna f
					}
					//System.out.println(datos.get(i).toString() + "entro en el rango: "+li+","+ls);
				}else {
					calculos.get(j).add(contador); //columna f
					j++;
					i-=1;
					contador=0;
				}
		}
		int F=0;
		double totalXF=0;
		for(int i=0;i<calculos.size();i++){
			F += Integer.parseInt(calculos.get(i).get(3).toString());
			calculos.get(i).add(F); //columna F
			
			double fr = (double)Integer.parseInt(calculos.get(i).get(3).toString()) / (double)arregloDatos.length;
			//String formatoFr = decimalFormat.format(fr);
			calculos.get(i).add(decimalFormat.format(fr)); //fr
			
			double xf = (double)(Double.parseDouble(calculos.get(i).get(2).toString()) * Integer.parseInt(calculos.get(i).get(3).toString()));
			calculos.get(i).add(xf); //xf
			totalXF += xf;
			
			double x2 = Math.pow(Double.parseDouble(calculos.get(i).get(2).toString()),2); 
			calculos.get(i).add(x2); //x2
			
			double fx2= Double.parseDouble(calculos.get(i).get(3).toString())*x2; 
			calculos.get(i).add(fx2); //fx2
		}

		double media = (double)(totalXF/arregloDatos.length); //Se calculo la media

		for(int i=0;i<calculos.size();i++){
			double xMedia = Double.parseDouble((calculos.get(i).get(2).toString())) - media;
			String formatoXMedia = decimalFormat.format(xMedia);
			calculos.get(i).add(formatoXMedia);
			
			double absXMedia = Math.abs(xMedia);
			String formatoabsXMedia = decimalFormat.format(absXMedia);
			calculos.get(i).add(formatoabsXMedia);
			
			double absXMediaF = absXMedia * Double.parseDouble(calculos.get(i).get(3).toString());
			String formatoabsXMediaF = decimalFormat.format(absXMediaF);
			calculos.get(i).add(formatoabsXMediaF);
			
			double logX = Double.parseDouble(calculos.get(i).get(3).toString()) * Math.log10(Double.parseDouble(calculos.get(i).get(2).toString()));
			String logXx = decimalFormat.format(logX);
			calculos.get(i).add(logXx);
			
		}		
		
		return calculos;
	}
	
	public static ArrayList<Object> medidasTendenciaDatosAgrupados(ArrayList<ArrayList<Object>> tablaDatos, String[] arregloDatos){
		ArrayList<Object> resultados = new ArrayList<Object>();
		DecimalFormat decimalFormat =  new DecimalFormat("#.###");
		int totalF = 0;
		double totalXf = 0.0;
		double totalFx2 = 0.0;
		double totalabsXMediaF = 0.0;
		double totalLogX = 0.0;
		int indiceMayor = 0;
		int indiceModa = 0;
		
		for(int i=0;i<tablaDatos.size();i++){
			totalF += Integer.parseInt(tablaDatos.get(i).get(3).toString()); 
			totalXf += Double.parseDouble(tablaDatos.get(i).get(6).toString());
			totalFx2 += Double.parseDouble(tablaDatos.get(i).get(8).toString());
			totalabsXMediaF += Double.parseDouble(tablaDatos.get(i).get(11).toString());
			totalLogX += Double.parseDouble(tablaDatos.get(i).get(12).toString());
			int frecuencia = Integer.parseInt(tablaDatos.get(i).get(3).toString());
			if(frecuencia> indiceMayor){
				indiceMayor = frecuencia;
				indiceModa = i;
			}
		}
		System.out.println("totallogx:"+totalLogX);
		
		
		
		
		List<Double> datos = new ArrayList<Double>(); //lista de datos
		for(int i=0;i<arregloDatos.length;i++){
			datos.add(Double.parseDouble(arregloDatos[i].toString()));
		}
		Collections.sort(datos);

		int numElementos = datos.size();
		double valorMaximo = Double.parseDouble(datos.get(datos.size()-1).toString());
		double valorMinimo = Double.parseDouble(datos.get(0).toString());
		double rango = valorMaximo-valorMinimo;
		
		double numIntervalos = 1+3.22*Math.log10(numElementos);
		if (numIntervalos % 1 == 0) {    
	    } else {
	        numIntervalos = (int)numIntervalos;
	        numIntervalos = (int)numIntervalos +1;
	    }
		
		double amplitud = rango/numIntervalos;
		if (amplitud % 1 == 0) {
	    } else {
	        amplitud = (int)amplitud;
	        amplitud = amplitud +1;
	    }
		
		double auxiliarModa = Double.parseDouble(tablaDatos.get(indiceModa).get(3).toString())-Double.parseDouble(tablaDatos.get(indiceModa-1).get(3).toString());
		double moda = auxiliarModa + (Double.parseDouble(tablaDatos.get(indiceModa).get(3).toString())-Double.parseDouble(tablaDatos.get(indiceModa+1).get(3).toString()));
		moda = Double.parseDouble(tablaDatos.get(indiceModa).get(0).toString())+(auxiliarModa/moda)* amplitud;
		
		double mediana;
		int mitad = tablaDatos.size()/2;
		System.out.println(mitad);
		System.out.println(amplitud);
		//if (tablaDatos.size() % 2 == 0) {
			mediana = (totalF/2)-Double.parseDouble(tablaDatos.get(mitad-1).get(4).toString());
			System.out.println(mediana);
			mediana = mediana /Double.parseDouble(tablaDatos.get(mitad).get(3).toString());
			mediana = mediana * amplitud;
			System.out.println(mediana);
			mediana = Double.parseDouble(tablaDatos.get(mitad).get(0).toString())+mediana;
			System.out.println(mediana);
			//mediana = (Double.parseDouble(arregloDatos[mitad-1].toString()) + Double.parseDouble(arregloDatos[mitad].toString())) /2;
		//} else {
			//mediana = Double.parseDouble(arregloDatos[mitad].toString());
			//System.out.println("no entro");
		//}
		
		System.out.println(arregloDatos.length % 2);
		//MEDIDAS DE TENDENCIA CENTRAL
		double mediaAritmetica = (double)totalXf/totalF;
		resultados.add(decimalFormat.format(mediaAritmetica)); //media aritmetica
		double mediaGeometrica = Math.pow(10,((double)(totalLogX / totalF)));
		resultados.add(decimalFormat.format(mediaGeometrica)); //media geometrica
		resultados.add("");//media ponderada
		resultados.add(decimalFormat.format(mediana)); //mediana
		resultados.add(decimalFormat.format(moda));//moda
		
		//MEDIDAS DE DISPERSION
		double varianza = ((totalFx2 - ((Math.pow(totalXf,2))/totalF)) / (totalF-1));
		resultados.add(decimalFormat.format(varianza)); //varianza
		double desvEstandar = Math.sqrt(varianza);
		resultados.add(decimalFormat.format(desvEstandar)); //desv. estandar
		double desvMedia = (double)(totalabsXMediaF /totalF);
		resultados.add(decimalFormat.format(desvMedia)); //desv. media
		resultados.add(""); //desv. mediana
		double ultimoNumero = Double.parseDouble(tablaDatos.get(tablaDatos.size()-1).get(2).toString());
		double primerNumero = Double.parseDouble(tablaDatos.get(0).get(2).toString());
		rango = ultimoNumero - primerNumero;
		resultados.add(decimalFormat.format(rango)); //rango
		System.out.println(totalLogX / totalF);
		return resultados;
	}
}
