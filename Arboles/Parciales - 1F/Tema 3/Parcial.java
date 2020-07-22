package prog3.arbol.general;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial {
	private boolean resolver (ArbolGeneral<Integer> arbol, boolean exito, ListaGenerica<Integer> lista) {
		if (arbol.getDatoRaiz()!=-1) {
			if (lista.incluye(arbol.getDatoRaiz())) {
				lista.eliminar(arbol.getDatoRaiz());
			}
			else {
				lista.agregarFinal(arbol.getDatoRaiz());
			}
			ListaGenerica<ArbolGeneral<Integer>> hijos = arbol.getHijos();
			hijos.comenzar();
			while (!hijos.fin() && !exito) {
				exito=resolver(hijos.proximo(), exito, lista);
			}
		}
		else {
			exito=true;
		}
		return exito;
	}
	public ListaGenerica<Integer> resolver (ArbolGeneral<Integer> arbol) {
		ListaGenerica<Integer> lista = new ListaGenericaEnlazada<>();
		resolver(arbol,false,lista);
		return lista;
	}
}
