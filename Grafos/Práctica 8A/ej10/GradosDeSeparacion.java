package prog3.grafos.utiles;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.GrafoImplListAdy;
import prog3.grafos.Vertice;
import prog3.grafos.VerticeImplListAdy;
import prog3.listagenerica.ListaGenerica;
import prog3.util.ColaGenerica;

public class GradosDeSeparacion {
	private static int maximoGradoDeSeparacion (Grafo<String> grafo, Vertice<String> vertice) {
		boolean [] visitados = new boolean [grafo.listaDeVertices().tamanio()];
		ColaGenerica<Vertice<String>> cola = new ColaGenerica<>();
		int conexiones = 0;
		visitados[vertice.posicion()]=true;
		cola.encolar(grafo.listaDeVertices().elemento(vertice.posicion()));
		cola.encolar(null);
		while (!cola.esVacia()) {
			Vertice<String> v = cola.desencolar();
			if (v!=null) {
				ListaGenerica<Arista<String>> ady = grafo.listaDeAdyacentes(v);
				ady.comenzar();
				while (!ady.fin()) {
					Arista<String> a= ady.proximo();
					Vertice<String> w= a.verticeDestino();
					if (!visitados[w.posicion()]) {
						visitados[w.posicion()]=true;
						cola.encolar(w);
					}
				}
			}
			else {
				if (!cola.esVacia()) {
					conexiones++;
					cola.encolar(null);
				}
			}
		}
		return conexiones;
	}
	public static int maximoGradoDeSeparacion (Grafo<String> grafo) {
		ListaGenerica<Vertice<String>> vertices = grafo.listaDeVertices();
		int act=0, max = Integer.MIN_VALUE;
		vertices.comenzar();
		while (!vertices.fin()) {
			act=maximoGradoDeSeparacion(grafo, vertices.proximo());
			if (act>max)
				max=act;
		}
		return max;
	}
	public static void main (String args[]) {
		Grafo<String> grafoNuevo = new GrafoImplListAdy<String>();
		Vertice<String> vert1 = new VerticeImplListAdy<String>("A");
		grafoNuevo.agregarVertice(vert1);
		Vertice<String> vert2 = new VerticeImplListAdy<String>("C");
		grafoNuevo.agregarVertice(vert2);
		Vertice<String> vert3 = new VerticeImplListAdy<String>("B");
		grafoNuevo.agregarVertice(vert3);
		grafoNuevo.conectar(vert1,vert3);
		grafoNuevo.conectar(vert2,vert3);
		grafoNuevo.conectar(vert3,vert2);
		grafoNuevo.conectar(vert2,vert1);
		grafoNuevo.conectar(vert1,vert2);
		System.out.println(maximoGradoDeSeparacion(grafoNuevo));
	}
}
