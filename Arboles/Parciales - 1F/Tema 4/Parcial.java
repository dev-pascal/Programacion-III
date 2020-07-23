package prog3.arbol.general;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial {
	private static boolean resolver (ArbolGeneral<Boolean> arbol, int nivelAct, boolean aux, ListaGenerica<Integer> niveles) {
		if (!arbol.esHoja() && !arbol.getDatoRaiz() && !aux) {
			aux=true;
		}
		else {
			if (!arbol.getDatoRaiz() && aux) {
				aux=false;
			}
		}
		ListaGenerica<ArbolGeneral<Boolean>> hijos = arbol.getHijos();
		hijos.comenzar();
		while (!hijos.fin()) {
			aux=resolver(hijos.proximo(), nivelAct+1, aux, niveles);
		}
		if (aux && !arbol.esHoja() && !arbol.getDatoRaiz()) {
			niveles.agregarFinal(nivelAct);
			aux=false;
		}
		return aux;
	}
	public static ListaGenerica<Integer> resolver (ArbolGeneral<Boolean> arbol) {
		ListaGenerica<Integer> niveles = new ListaGenericaEnlazada<>();
		resolver(arbol, 0, false, niveles);
		return niveles;
	}
	private static void armarArbol (ArbolGeneral<Boolean> raiz) {
		ArbolGeneral<Boolean> n1 = new ArbolGeneral<Boolean>(false);
		n1.agregarHijo(new ArbolGeneral<Boolean>(true));
		n1.agregarHijo(new ArbolGeneral<Boolean>(true));
		ArbolGeneral<Boolean> n2 = new ArbolGeneral<Boolean>(true);
		n2.agregarHijo(new ArbolGeneral<Boolean>(true));
		ArbolGeneral<Boolean> n3 = new ArbolGeneral<Boolean>(false);
		n3.agregarHijo(new ArbolGeneral<Boolean>(false));
		n3.agregarHijo(n1);
		ArbolGeneral<Boolean> n4 = new ArbolGeneral<Boolean>(false);
		n4.agregarHijo(new ArbolGeneral<Boolean>(true));
		n4.agregarHijo(n2);
		raiz.agregarHijo(n3);
		raiz.agregarHijo(new ArbolGeneral<Boolean>(true));
		raiz.agregarHijo(n4);
		
	}
	public static void main (String args []) {
		ArbolGeneral<Boolean> raiz = new ArbolGeneral<Boolean>(true);
		armarArbol(raiz);
		System.out.println(resolver(raiz));
	}
}
