package prog3.grafos.utiles;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Algoritmos {
	private <T> boolean analizar (Grafo<T> grafo, boolean [] marca, int i, int longitud, Vertice<T> pri, boolean exito) {
		if (i!=pri.posicion())
			marca[i]=true;
		Vertice<T> v = grafo.listaDeVertices().elemento(i);
		if (!exito && longitud!=4) {
			ListaGenerica<Arista<T>> ady = grafo.listaDeAdyacentes(v);
			if (!ady.esVacia()) {
				ady.comenzar();
				longitud++;
				while (!ady.fin()) {
					Arista<T> prox= ady.proximo();
					int j = prox.verticeDestino().posicion();
					if (!marca[j]) {
						exito=analizar(grafo, marca, j, longitud, pri, exito);
					}
				}
			}
		}
		else {
			if (longitud==4 && v.dato().equals(pri.dato()))
				exito=true;
		}
		return exito;
	}
	private <T> void inicializar (boolean [] marca, Grafo<T> grafo) {
		int i;
		for (i=0;i<grafo.listaDeVertices().tamanio();i++)
			marca[i]=false;
	}
	public <T> boolean subgrafoCuadrado (Grafo<T> grafo) {
		ListaGenerica<Vertice<T>> vertices=grafo.listaDeVertices();
		boolean [] marca = new boolean [grafo.listaDeVertices().tamanio()];
		boolean cumple=false;
		Vertice<T> pri=null;
		vertices.comenzar();
		while (!vertices.fin() && !cumple) {
			inicializar(marca, grafo);
			pri=vertices.proximo();
			cumple=analizar(grafo, marca, pri.posicion(), 0, pri, false);
		}	
		return cumple;
	}
	private <T> int sumarIN (Grafo<T> grafo, ListaGenerica<Vertice<T>> vertAux, Vertice<T> verticeAct) {
		int in=0;
		vertAux.comenzar();
		while (!vertAux.fin()) {
			ListaGenerica<Arista<T>> adyAux=grafo.listaDeAdyacentes(vertAux.proximo());
			boolean encontro=false;
			adyAux.comenzar();
			while (!adyAux.fin() && !encontro) {
				if (adyAux.proximo().verticeDestino().dato().equals(verticeAct.dato())) {
					encontro=true;
					in++;
				}
			}
		}
		return in;
	}
	private <T> int sumarOUT (Grafo<T> grafo, Vertice<T> verticeAct) {
		ListaGenerica<Arista<T>> ady = grafo.listaDeAdyacentes(verticeAct);
		int out=0;
		ady.comenzar();
		while (!ady.fin()) {
			out++;
			ady.proximo();
		}
		return out;
	}
	public <T> int getGrado (Grafo<T> grafo) {
		ListaGenerica<Vertice<T>> vertices=grafo.listaDeVertices();
		ListaGenerica<Vertice<T>> vertAux= new ListaGenericaEnlazada<Vertice<T>>();
		vertices.comenzar();
		while (!vertices.fin())
			vertAux.agregarFinal(vertices.proximo());
		int gradoAct, gradoMax=Integer.MIN_VALUE;
		Vertice<T> verticeAct=null;
		vertices.comenzar();
		while (!vertices.fin()) {
			gradoAct=0;
			verticeAct=vertices.proximo();
			gradoAct+=sumarOUT(grafo, verticeAct); // sumo los nodos de salida
			gradoAct+=sumarIN(grafo, vertAux, verticeAct); //sumo los nodos de entrada
			if (gradoAct>gradoMax) {
				gradoMax=gradoAct;
			}
		}
		return gradoMax;
	}
}
