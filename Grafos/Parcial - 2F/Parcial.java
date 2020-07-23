package prog3.grafos.utiles.parcial;

import java.util.Scanner;
import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.GrafoImplListAdy;
import prog3.grafos.Vertice;
import prog3.grafos.VerticeImplListAdy;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial {
	private static void procesar (ListaGenerica<Local> act, ListaGenerica<Local> fin) {
		while (!fin.esVacia()) 
			fin.eliminarEn(0);
		act.comenzar();
		while (!act.fin()) {
			fin.agregarFinal(act.proximo());
		}
	}
	private static boolean dfs (int i, boolean [] marca, ListaGenerica<Local> localesAct,
	ListaGenerica<Local> localesFinal, Integer distancia, Integer personas, boolean exito, 
	String origen, String destino, Grafo<Local> grafo) {
		marca[i]=true;
		Vertice<Local> v = grafo.listaDeVertices().elemento(i);
		if (v.dato().getPersonas()>personas && !v.dato().getNombre().equals(origen)
			&& !v.dato().getNombre().equals(destino)) {
			localesAct.agregarFinal(v.dato());
		}
		if (v.dato().getNombre().equals(destino)) {
			exito=true;
		}
		else {
			ListaGenerica<Arista<Local>> ady = grafo.listaDeAdyacentes(v);
			Arista<Local> adyAct=null;
			ady.comenzar();
			while (!ady.fin()) {
				adyAct=ady.proximo();
				int j= adyAct.verticeDestino().posicion();
				if (!marca[j] && adyAct.peso()<distancia) {
					exito=dfs(j, marca, localesAct, localesFinal, distancia, personas,
					exito,origen,destino,grafo);
					if (v.dato().getNombre().equals(origen)) {
						exito=false;
					}
				}
			}
		}
		if (!exito)
			localesAct.eliminarEn(localesAct.tamanio()-1);
		else
			procesar(localesAct, localesFinal);
		marca[i]=false;
		return exito;
	}
	private static int buscar (Grafo<Local> g, String dato) {
		ListaGenerica<Vertice<Local>> l = g.listaDeVertices();
		Vertice<Local> act = null;
		l.comenzar();
		boolean encontro=false;
		while (!l.fin() && !encontro) {
			act=l.proximo();
			if (act.dato().getNombre().equals(dato))
				encontro=true;
		}
		if (encontro)
			return act.posicion();
		else
			return -1;
	}
	public static ListaGenerica<Local> resolver(Grafo<Local> locales, String origen, Integer distancia, Integer personas) {
		ListaGenerica<Local> listaLocales = new ListaGenericaEnlazada<Local>();
		ListaGenerica<Local> listaLocalesF = new ListaGenericaEnlazada<Local>();
		int O = buscar(locales, origen);
		if (O!=-1) {
			System.out.println("Ingrese destino");
			Scanner in = new Scanner(System.in);
			String destino=in.next();
			int D = buscar(locales, destino);
			if (D!=-1) {
				boolean [] marca = new boolean [locales.listaDeVertices().tamanio()];
				dfs(O, marca, listaLocales, listaLocalesF, distancia, personas, false,
				origen, destino, locales);
			}
			in.close();
		}
		return listaLocalesF;
	}
	private static void construirGrafo (Grafo<Local> grafo) {
		Vertice<Local> vert1 = new VerticeImplListAdy<Local>(new Local("Municipalidad", 0));
		grafo.agregarVertice(vert1);
		Vertice<Local> vert2 = new VerticeImplListAdy<Local>(new Local("Bar1", 30));
		grafo.agregarVertice(vert2);
		Vertice<Local> vert3 = new VerticeImplListAdy<Local>(new Local("Indumentaria1", 20));
		grafo.agregarVertice(vert3);
		Vertice<Local> vert4 = new VerticeImplListAdy<Local>(new Local("Indumentaria2", 10));
		grafo.agregarVertice(vert4);
		Vertice<Local> vert5 = new VerticeImplListAdy<Local>(new Local("Legislatura", 0));
		grafo.agregarVertice(vert5);
		Vertice<Local> vert6 = new VerticeImplListAdy<Local>(new Local("Bar2", 50));
		grafo.agregarVertice(vert6);
		Vertice<Local> vert7 = new VerticeImplListAdy<Local>(new Local("Restaurante2", 40));
		grafo.agregarVertice(vert7);
		Vertice<Local> vert8 = new VerticeImplListAdy<Local>(new Local("Indumentaria4", 20));
		grafo.agregarVertice(vert8);
		grafo.conectar(vert1,vert2,99);
		grafo.conectar(vert2,vert1,99);
		grafo.conectar(vert2,vert3,20);
		grafo.conectar(vert3,vert2,20);
		grafo.conectar(vert3,vert4,30);
		grafo.conectar(vert4,vert3,30);
		grafo.conectar(vert4,vert5,80);
		grafo.conectar(vert5,vert4,80);
		grafo.conectar(vert5,vert6,40);
		grafo.conectar(vert6,vert5,40);
		grafo.conectar(vert6,vert3,90);
		grafo.conectar(vert3,vert6,90);
		grafo.conectar(vert3,vert7,40);
		grafo.conectar(vert7,vert3,40);
		grafo.conectar(vert7,vert6,200);
		grafo.conectar(vert6,vert7,200);
		grafo.conectar(vert6,vert8,50);
		grafo.conectar(vert8,vert6,50);
		grafo.conectar(vert8,vert5,30);
		grafo.conectar(vert5,vert8,30);
		grafo.conectar(vert7,vert1,10);
		grafo.conectar(vert1,vert7,10);
		Vertice<Local> vert9 = new VerticeImplListAdy<Local>(new Local("A", 50));
		grafo.agregarVertice(vert9);
		grafo.conectar(vert1,vert9,2);
		grafo.conectar(vert9,vert1,2);
	}
	private static void imprimir (Grafo<Local> grafo) {
		ListaGenerica<Local> l = resolver(grafo, "Municipalidad", 100, 30);
		l.comenzar();
		while (!l.fin())
			System.out.println(l.proximo().getNombre());
	}
	public static void main (String args[]) {
		Grafo<Local> grafo = new GrafoImplListAdy<Local>();
		construirGrafo(grafo);
		imprimir(grafo);
	}
	
}
