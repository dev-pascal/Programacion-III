package prog3.grafos.utiles.juego;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.GrafoImplListAdy;
import prog3.grafos.Vertice;
import prog3.grafos.VerticeImplListAdy;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class JackRecorrido {
	private static final int maxMinutos=120;
	private static void actualizarMax (int cantSalas, int [] maxSalas,
	ListaGenerica<Sala> listaAct, ListaGenerica<Sala> listaMax) {
		if (maxSalas[0]<cantSalas) {
			maxSalas[0]=cantSalas;
			while (!listaMax.esVacia()) {
				listaMax.eliminarEn(0);
			}
			listaAct.comenzar();
			while (!listaAct.fin()) {
				listaMax.agregarFinal(listaAct.proximo());
			}
		}
	}
	private static void dfs (int i, boolean [] marca, ListaGenerica<Sala> listaAct,
	ListaGenerica<Sala> listaMax, int cantSalas, int [] maxSalas, int minutos,
	Grafo<Sala> grafo) {
		marca[i]=true;
		Vertice<Sala> v = grafo.listaDeVertices().elemento(i);
		listaAct.agregarFinal(v.dato());
		if (!v.dato().getNombre().equals("Entrada")) {
			cantSalas++;
			actualizarMax(cantSalas, maxSalas, listaAct, listaMax);
		}
		ListaGenerica<Arista<Sala>> ady = grafo.listaDeAdyacentes(v);
		Arista<Sala> adyAct=null;
		ady.comenzar();
		while (!ady.fin()) {
			adyAct=ady.proximo();
			int j = adyAct.verticeDestino().posicion();
			if (!marca[j] && minutos+adyAct.verticeDestino().dato().getMin()+adyAct.peso()<=maxMinutos) {
				dfs(j, marca, listaAct, listaMax, cantSalas, maxSalas,
				minutos+adyAct.verticeDestino().dato().getMin()+adyAct.peso(), grafo);
			}
		}
		marca[i]=false;
		listaAct.eliminarEn(listaAct.tamanio()-1);
	}
	private static int buscarEntrada (Grafo<Sala> galeria) {
		ListaGenerica<Vertice<Sala>> lista = galeria.listaDeVertices();
		lista.comenzar();
		Vertice<Sala> act=null;
		boolean encontro=false;
		while (!lista.fin() && !encontro) {
			act=lista.proximo();
			if (act.dato().getNombre().equals("Entrada")) {
				encontro=true;
			}
		}
		if (encontro)
			return act.posicion();
		else
			return -1;
	}
	public static ListaGenerica<Sala> devolverMejorCamino (Grafo<Sala> galeria) {
		ListaGenerica<Sala> listaAct = new ListaGenericaEnlazada<Sala>();
		ListaGenerica<Sala> listaMax = new ListaGenericaEnlazada<Sala>();
		int entrada = buscarEntrada(galeria);
		boolean [] marca = new boolean [galeria.listaDeVertices().tamanio()];
		int [] maxSalas = new int [1];
		maxSalas[0]=Integer.MIN_VALUE;
		if (entrada!=-1)
			dfs(entrada, marca, listaAct, listaMax, 0, maxSalas, 0, galeria);
		return listaMax;
	}
	private static void armarGrafo (Grafo<Sala> grafoNuevo) {
		Vertice<Sala> vert1 = new VerticeImplListAdy<Sala>(new Sala("Entrada", 0));
		grafoNuevo.agregarVertice(vert1);
		Vertice<Sala> vert2 = new VerticeImplListAdy<Sala>(new Sala("B", 20));
		grafoNuevo.agregarVertice(vert2);
		Vertice<Sala> vert3 = new VerticeImplListAdy<Sala>(new Sala("F", 50));
		grafoNuevo.agregarVertice(vert3);
		Vertice<Sala> vert4 = new VerticeImplListAdy<Sala>(new Sala("C", 10));
		grafoNuevo.agregarVertice(vert4);
		Vertice<Sala> vert5 = new VerticeImplListAdy<Sala>(new Sala("D", 35));
		grafoNuevo.agregarVertice(vert5);
		Vertice<Sala> vert6 = new VerticeImplListAdy<Sala>(new Sala("E", 40));
		grafoNuevo.agregarVertice(vert6);
		Vertice<Sala> vert7 = new VerticeImplListAdy<Sala>(new Sala("A", 30));
		grafoNuevo.agregarVertice(vert7);
		grafoNuevo.conectar(vert1,vert7,5);
		grafoNuevo.conectar(vert7,vert1,5);
		grafoNuevo.conectar(vert6,vert7,5);
		grafoNuevo.conectar(vert7,vert6,5);
		grafoNuevo.conectar(vert6,vert5,3);
		grafoNuevo.conectar(vert5,vert6,3);
		grafoNuevo.conectar(vert5,vert4,1);
		grafoNuevo.conectar(vert4,vert5,1);
		grafoNuevo.conectar(vert1,vert4,1);
		grafoNuevo.conectar(vert4,vert1,1);
		grafoNuevo.conectar(vert1,vert5,2);
		grafoNuevo.conectar(vert5,vert1,2);
		grafoNuevo.conectar(vert1,vert6,2);
		grafoNuevo.conectar(vert6,vert1,2);
		grafoNuevo.conectar(vert1,vert2,2);
		grafoNuevo.conectar(vert2,vert1,2);
		grafoNuevo.conectar(vert2,vert3,1);
		grafoNuevo.conectar(vert3,vert2,1);
		grafoNuevo.conectar(vert3,vert4,1);
		grafoNuevo.conectar(vert4,vert3,1);
	}
	public static void main (String args[]) {
		Grafo<Sala> grafo= new GrafoImplListAdy<Sala>();
		armarGrafo(grafo);
		ListaGenerica<Sala> l = devolverMejorCamino(grafo);
		l.comenzar();
		while (!l.fin())
			System.out.println(l.proximo().getNombre());

	}
}
