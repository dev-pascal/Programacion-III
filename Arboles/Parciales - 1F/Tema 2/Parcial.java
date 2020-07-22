package prog3.arbol.general;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial {
	private static boolean resolver (ArbolGeneral<Integer> arbol, boolean exito, ListaGenerica<Integer> lista) {
		if (lista.incluye(arbol.getDatoRaiz())) {
			exito=true;
		}
		else {
			lista.agregarFinal(arbol.getDatoRaiz());
			ListaGenerica<ArbolGeneral<Integer>> hijos = arbol.getHijos();
			hijos.comenzar();
			while (!hijos.fin() && !exito) {
				exito=resolver(hijos.proximo(), exito, lista);
			}
			lista.eliminarEn(lista.tamanio()-1);
		}
		return exito;
	}
	public static boolean resolver (ArbolGeneral<Integer> arbol) {
		ListaGenerica<Integer> lista = new ListaGenericaEnlazada<>();
		return resolver(arbol, false, lista);
	}
	private static void armarArbol (ArbolGeneral<Integer> uno) {
		ArbolGeneral<Integer> dos = new ArbolGeneral<Integer>(2);
		dos.agregarHijo(new ArbolGeneral<Integer>(3));
		dos.agregarHijo(new ArbolGeneral<Integer>(1));
		ArbolGeneral<Integer> cuatro = new ArbolGeneral<Integer>(4);
		cuatro.agregarHijo(new ArbolGeneral<Integer>(5));
		uno.agregarHijo(dos);
		uno.agregarHijo(cuatro);
	}
	public static void main (String args[]) {
		ArbolGeneral<Integer> uno = new ArbolGeneral<Integer>(1);
		armarArbol(uno);
		System.out.println(resolver(uno));
	}
}
