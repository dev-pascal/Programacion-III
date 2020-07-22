package prog3.arbol.general;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial {
    private static void procesar(ArbolGeneral<Integer> arbol, int minimo, ListaGenerica<Integer> lista) {
        int suma=0;
        ListaGenerica<ArbolGeneral<Integer>> hijos=arbol.getHijos();
        hijos.comenzar();
        while (!hijos.fin()) {
            suma+=hijos.proximo().getDatoRaiz();
        }
        if (suma>minimo && !lista.incluye(arbol.getDatoRaiz())) {
            lista.agregarFinal(arbol.getDatoRaiz());
        }   
    }
   
    private static void resolver (ArbolGeneral<Integer> arbol, int minimo, ListaGenerica<Integer> lista) {
        if (!arbol.esHoja()) {
            ListaGenerica<ArbolGeneral<Integer>> hijos=arbol.getHijos();
            hijos.comenzar();
            ArbolGeneral<Integer> dato=null;
            while (!hijos.fin()) {
            	dato=hijos.proximo();
            	resolver(dato, minimo, lista);
                procesar(dato, minimo, lista);
            }
        }
        procesar(arbol,minimo,lista);
    }
    public static ListaGenerica<Integer> resolver (ArbolGeneral<Integer> arbol, int minimo) {
        ListaGenerica<Integer> lista= new ListaGenericaEnlazada<Integer>();
        resolver(arbol,minimo,lista);
        return lista;
    }
    private static void armarArbol (ArbolGeneral<Integer> dos) {
        ArbolGeneral<Integer> veinticinco1= new ArbolGeneral<Integer>(25);
        veinticinco1.agregarHijo(new ArbolGeneral<Integer>(10));
        veinticinco1.agregarHijo(new ArbolGeneral<Integer>(6));
        ArbolGeneral<Integer> veinticinco2= new ArbolGeneral<Integer>(25);
        ArbolGeneral<Integer> diecisiete= new ArbolGeneral<Integer>(17);
        diecisiete.agregarHijo(new ArbolGeneral<Integer>(30));
        veinticinco2.agregarHijo(diecisiete);
        dos.agregarHijo(veinticinco1);
        dos.agregarHijo(new ArbolGeneral<Integer>(8));
        dos.agregarHijo(veinticinco2);
    }
    public static void main (String args []) {
        ArbolGeneral<Integer> dos= new ArbolGeneral<Integer>(2);
        armarArbol(dos);
        System.out.println(resolver(dos,15));
    }
}