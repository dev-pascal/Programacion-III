package prog3.arbol.general;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial {
	private static boolean preOrden (ArbolGeneral<Integer> arbol, int suma,
	ListaGenerica<Integer> lista, boolean exito) {
		suma+=arbol.getDatoRaiz();
		if (arbol.esHoja()) {
			if (lista.incluye(suma)) {
				exito=true;
			}
			else {
				lista.agregarFinal(suma);
			}
		}
		else {
			ListaGenerica<ArbolGeneral<Integer>> hijos = arbol.getHijos();
			hijos.comenzar();
			while (!hijos.fin() && !exito) {
				exito=preOrden(hijos.proximo(), suma, lista, exito);
			}
		}
		return exito;
	}
	public static boolean resolver (ArbolGeneral<Integer> arbol) {
		int suma=0;
		ListaGenerica<Integer> lista = new ListaGenericaEnlazada<>();
		boolean exito=false;
		return preOrden(arbol,suma,lista,exito);
	}
	private static void armarArbol (ArbolGeneral<Integer> dos) {
		ArbolGeneral<Integer> uno = new ArbolGeneral<Integer>(1);
		ArbolGeneral<Integer> cinco = new ArbolGeneral<Integer>(5);
		ArbolGeneral<Integer> cuatro = new ArbolGeneral<>(4);
		ArbolGeneral<Integer> tres = new ArbolGeneral<Integer>(3);
		ArbolGeneral<Integer> ocho = new ArbolGeneral<Integer>(8);
		ArbolGeneral<Integer> veinticinco = new ArbolGeneral<Integer>(25);
		uno.agregarHijo(cinco);
		cuatro.agregarHijo(tres);
		cuatro.agregarHijo(new ArbolGeneral<Integer>(11));
		uno.agregarHijo(cuatro);
		dos.agregarHijo(uno);
		dos.agregarHijo(ocho);
		dos.agregarHijo(veinticinco);
	}
	public static void main (String args[]) {
		ArbolGeneral<Integer> dos = new ArbolGeneral<Integer>(2);
		armarArbol(dos);
		System.out.println(resolver(dos));
		
	}
}
