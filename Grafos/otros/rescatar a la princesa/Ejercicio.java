package prog3.grafos.utiles.juego;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.GrafoImplListAdy;
import prog3.grafos.Vertice;
import prog3.grafos.VerticeImplListAdy;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Ejercicio {
	private static int buscar (Grafo<Sitio> mapa, String dato) {
		ListaGenerica<Vertice<Sitio>> l = mapa.listaDeVertices();
		Vertice<Sitio> act = null;
		l.comenzar();
		boolean encontro=false;
		while (!l.fin() && !encontro) {
			act=l.proximo();
			if (act.dato().getNombre().equals(dato))
				encontro=true;
		}
		return act.posicion();
	}
	private static ListaGenerica<Sitio> clonar (ListaGenerica<Sitio> l) {
		ListaGenerica<Sitio> nue = new ListaGenericaEnlazada<Sitio>();
		l.comenzar();
		while (!l.fin()) {
			nue.agregarFinal(l.proximo());
		}
		return nue;
	}
	private static boolean buscaDragon (Grafo<Sitio> grafo, int i, boolean [] marca, int distancia, boolean encontro) {
		Vertice<Sitio> v = grafo.listaDeVertices().elemento(i);
		if (distancia<20) {
			if (v.dato().esDragon()) {
				encontro=true;
			}
			else {
				ListaGenerica<Arista<Sitio>> ady = grafo.listaDeAdyacentes(v);
				Arista<Sitio> adyAct=null;
				while (!ady.fin() && !encontro) {
					adyAct=ady.proximo();
					int j = adyAct.verticeDestino().posicion();
					if (!marca[j]) {
						encontro=buscaDragon(grafo, j, marca, distancia+adyAct.peso(), encontro);
					}
				}
			}
		}
		return encontro;
	}
	private static boolean estaEnPeligro (int O, Vertice<Sitio> Princesa, Grafo<Sitio> g) {
		boolean estaEnPeligro=false;
		boolean [] marca = new boolean [g.listaDeVertices().tamanio()];
		estaEnPeligro=buscaDragon(g, O, marca, 0, false);
		return estaEnPeligro;
	}
	private static void dfs (int i, boolean [] marca, ListaGenerica<Sitio> caminoAct,
	ListaGenerica<ListaGenerica<Sitio>> caminos, int [] cantPrincesas, Grafo<Sitio> g) {
		marca[i]=true;
		Vertice<Sitio> v = g.listaDeVertices().elemento(i);
		caminoAct.agregarFinal(v.dato());
		if (v.dato().esPrincesa()) {
			if (estaEnPeligro(i, v, g)) {
				caminos.agregarFinal(clonar(caminoAct));
				cantPrincesas[0]++;
				Sitio sitioAct = v.dato();
				sitioAct.setPrincesa(false);
			}
		}
		else {
			ListaGenerica<Arista<Sitio>> ady = g.listaDeAdyacentes(v);
			Arista<Sitio> adyAct=null;
			while (!ady.fin()) {
				adyAct=ady.proximo();
				int j=adyAct.verticeDestino().posicion();
				if (!marca[j] && !adyAct.verticeDestino().dato().esDragon()) {
					dfs(j, marca, caminoAct, caminos, cantPrincesas, g);
				}
			}
		}
		marca[i]=false;
		caminoAct.eliminarEn(caminoAct.tamanio()-1);
	}
 	public static Rescate resolver (Grafo<Sitio> mapa) {
		ListaGenerica<ListaGenerica<Sitio>> caminos = new ListaGenericaEnlazada<ListaGenerica<Sitio>>();
		ListaGenerica<Sitio> caminoAct = new ListaGenericaEnlazada<Sitio>();
		int [] cantPrincesas = new int [1];
		cantPrincesas[0]=0;
		boolean [] marca = new boolean [mapa.listaDeVertices().tamanio()];
		int castillo = buscar (mapa, "Castillo");
		dfs(castillo, marca, caminoAct, caminos, cantPrincesas, mapa);
		Rescate rescate = new Rescate(caminos, cantPrincesas[0]);
		return rescate;
	}
 	private static void cargarMapa (Grafo<Sitio> grafoNuevo) {
 		Vertice<Sitio> vert1 = new VerticeImplListAdy<Sitio>(new Sitio("Castillo", false, false));
 		grafoNuevo.agregarVertice(vert1);
 		Vertice<Sitio> vert2 = new VerticeImplListAdy<Sitio>(new Sitio("Dragon", true, false));
 		grafoNuevo.agregarVertice(vert2);
 		Vertice<Sitio> vert3 = new VerticeImplListAdy<Sitio>(new Sitio("Princesa A", false, true));
 		grafoNuevo.agregarVertice(vert3);
 		Vertice<Sitio> vert4 = new VerticeImplListAdy<Sitio>(new Sitio("Princesa B", false, true));
 		grafoNuevo.agregarVertice(vert4);
 		Vertice<Sitio> vert5 = new VerticeImplListAdy<Sitio>(new Sitio("Dragon", true, false));
 		grafoNuevo.agregarVertice(vert5);
 		Vertice<Sitio> vert6 = new VerticeImplListAdy<Sitio>(new Sitio("Dragon", true, false));
 		grafoNuevo.agregarVertice(vert6);
 		Vertice<Sitio> vert7 = new VerticeImplListAdy<Sitio>(new Sitio("Princesa C", false, true));
 		grafoNuevo.agregarVertice(vert7);
 		Vertice<Sitio> vert8 = new VerticeImplListAdy<Sitio>(new Sitio("Dragon", true, false));
 		grafoNuevo.agregarVertice(vert8);
 		Vertice<Sitio> vert9 = new VerticeImplListAdy<Sitio>(new Sitio("Y", false, false));
 		grafoNuevo.agregarVertice(vert9);
 		Vertice<Sitio> vert10 = new VerticeImplListAdy<Sitio>(new Sitio("X", false, false));
 		grafoNuevo.agregarVertice(vert10);
 		Vertice<Sitio> vert11 = new VerticeImplListAdy<Sitio>(new Sitio("Z", false, false));
 		grafoNuevo.agregarVertice(vert11);
 		grafoNuevo.conectar(vert1,vert2,20);
 		grafoNuevo.conectar(vert2,vert1,20);
 		grafoNuevo.conectar(vert2,vert7,15);
 		grafoNuevo.conectar(vert7,vert2,15);
 		grafoNuevo.conectar(vert5,vert7,15);
 		grafoNuevo.conectar(vert7,vert5,15);
 		grafoNuevo.conectar(vert1,vert4,20);
 		grafoNuevo.conectar(vert4,vert1,20);
 		grafoNuevo.conectar(vert8,vert7,15);
 		grafoNuevo.conectar(vert7,vert8,15);
 		grafoNuevo.conectar(vert8,vert2,15);
 		grafoNuevo.conectar(vert2,vert8,15);
 		grafoNuevo.conectar(vert4,vert9,10);
 		grafoNuevo.conectar(vert9,vert4,10);
 		grafoNuevo.conectar(vert9,vert6,5);
 		grafoNuevo.conectar(vert6,vert9,5);
 		grafoNuevo.conectar(vert1,vert10,10);
 		grafoNuevo.conectar(vert10,vert1,10);
 		grafoNuevo.conectar(vert10,vert9,10);
 		grafoNuevo.conectar(vert9,vert10,10);
 		grafoNuevo.conectar(vert11,vert10,5);
 		grafoNuevo.conectar(vert10,vert11,5);
 		grafoNuevo.conectar(vert11,vert3,10);
 		grafoNuevo.conectar(vert3,vert11,10);
 		grafoNuevo.conectar(vert3,vert6,5);
 		grafoNuevo.conectar(vert6,vert3,5);
 	}
 	public static void main (String args[]) {
 		Grafo<Sitio> mapa = new GrafoImplListAdy<Sitio>();
 		cargarMapa(mapa);
 		System.out.println(resolver(mapa).getCant());
 	}
}
