package prog3.grafos.utiles;

import java.util.Scanner;
import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class GuiaDeTurismo {
	private int calcularViajes (int turistas, int minPeso) {
		int cant=0;
		while (turistas>0) {
			turistas-=(minPeso-1);
			cant++;
		}
		return cant;
	}
	private void actualizarMinViajes (int viajesAct, int [] viajesMin, ListaGenerica<String> caminoAct,
	ListaGenerica<String> caminoMin) {
		if (viajesMin[0]>viajesAct) {
			viajesMin[0]=viajesAct;
			while (!caminoMin.esVacia())
				caminoMin.eliminarEn(0);
			caminoAct.comenzar();
			while (!caminoAct.fin())
				caminoMin.agregarFinal(caminoAct.proximo());
		}
	}
	private void dfs (int i, boolean [] marca, ListaGenerica<String> caminoAct, ListaGenerica<String> caminoMin,
	int viajesAct, int [] viajesMin, int minPeso, String destino, Grafo<String> g, int turistas) {
		marca[i]=true;
		Vertice<String> v = g.listaDeVertices().elemento(i);
		caminoAct.agregarFinal(v.dato());
		if (v.dato().equals(destino)) {
			viajesAct=calcularViajes(turistas, minPeso);
			actualizarMinViajes(viajesAct, viajesMin, caminoAct, caminoMin);
		}
		else {
			ListaGenerica<Arista<String>> ady = g.listaDeAdyacentes(v);
			Arista<String> adyAct=null;
			ady.comenzar();
			while (!ady.fin()) {
				adyAct=ady.proximo();
				int j=adyAct.verticeDestino().posicion();
				if (!marca[j]) {
					if (minPeso>adyAct.peso())
						minPeso=adyAct.peso();
					dfs(j, marca, caminoAct, caminoMin, viajesAct, viajesMin, minPeso, destino, g, turistas);
				}
			}
		}
		marca[i]=false;
		caminoAct.eliminarEn(caminoAct.tamanio()-1);
	}
	private int buscar (Grafo<String> g, String dato) {
		ListaGenerica<Vertice<String>> l = g.listaDeVertices();	
		l.comenzar();
		boolean encontro=false;
		Vertice<String> act = null;
		while (!l.fin() && !encontro) {
			act=l.proximo();
			if (act.dato().equals(dato))
				encontro=true;
		}
		if (encontro)
			return act.posicion();
		else
			return -1;
	}
	public ListaGenerica<String> caminoConMenorNrodeViajes (Grafo<String> grafo, String puntoInteresOrigen, String
	puntoInteresDestino) {
		int origen = buscar(grafo, puntoInteresOrigen);
		int destino = buscar(grafo, puntoInteresDestino);
		ListaGenerica<String> caminoMin = new ListaGenericaEnlazada<>();
		if (origen!=-1 && destino!=-1) {
			boolean [] marca = new boolean [grafo.listaDeVertices().tamanio()];
			ListaGenerica<String> caminoAct= new ListaGenericaEnlazada<>();
			int [] viajesMin = new int[1];
			int minPeso = Integer.MAX_VALUE;
			viajesMin[0]=Integer.MAX_VALUE;
			Scanner in = new Scanner (System.in);
			System.out.println("Ingrese cantidad de turistas");
			int turistas=in.nextInt();
			in.close();
			dfs(origen, marca, caminoAct, caminoMin, 0, viajesMin, minPeso, puntoInteresDestino, grafo, turistas);
		}
		return caminoMin;
	}
}
