package prog3.grafos.utiles;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.Vertice;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Delta {
	private int dfs (int i, Grafo<String> grafo, boolean [] marca, int cant) {
		marca[i]=true;
		cant++;
		Vertice<String> v = grafo.listaDeVertices().elemento(i);
		ListaGenerica<Arista<String>> ady = grafo.listaDeAdyacentes(v);
		ady.comenzar();
		while (!ady.fin()) {
			int j=ady.proximo().verticeDestino().posicion();
			if (!marca[j]) {
				cant=dfs(j, grafo, marca, cant);
			}
		}
		return cant;
	}
	private Vertice<String> buscarMuelle (Grafo<String> grafo) {
		ListaGenerica<Vertice<String>> vertices = grafo.listaDeVertices();
		Vertice<String> act=null;
		boolean encontro=false;
		vertices.comenzar();
		while (!vertices.fin() && !encontro) {
			act=vertices.proximo();
			if (act.dato().equals("Muelle Principal"))
				encontro=true;
		}
		return act;
	}
	public int maxIslasDistintas (Grafo<String> grafo) {
		Vertice<String> muellePpal=buscarMuelle(grafo);
		ListaGenerica<Arista<String>> adysMuellePpal = grafo.listaDeAdyacentes(muellePpal);
		int max=Integer.MIN_VALUE, cantAct;
		boolean [] marca = new boolean [grafo.listaDeVertices().tamanio()];
		Arista<String> adyAct=null;
		adysMuellePpal.comenzar();
		while (!adysMuellePpal.fin()) {
			adyAct=adysMuellePpal.proximo();
			cantAct=dfs(adyAct.verticeDestino().posicion(), grafo, marca, 0);
			if (cantAct>max) {
				max=cantAct;
			}
		}
		return max;
	}
	
	//-----
	
	private int buscar (String dato, Grafo<String> grafo) {
		ListaGenerica<Vertice<String>> vertices = grafo.listaDeVertices();
		Vertice<String> vertAct=null;
		boolean encontro=false;
		vertices.comenzar();
		while (!vertices.fin() && !encontro) {
			vertAct=vertices.proximo();
			if (vertAct.dato().equals(dato))
				encontro=true;
		}
		if (encontro)
			return vertAct.posicion();
		else
			return -1;
	}
	private void actualizarMin (int [] min, ListaGenerica<String> caminoAct, ListaGenerica<String> caminoMin) {
		if (caminoAct.tamanio()<min[0]) {
			min[0]=caminoAct.tamanio();
			while (!caminoMin.esVacia())
				caminoMin.eliminarEn(0);
			caminoAct.comenzar();
			while (!caminoAct.fin())
				caminoMin.agregarFinal(caminoAct.proximo());
		}
	}
	private void dfsMin (int i, String islaD, boolean [] marca, Grafo<String> grafo, ListaGenerica<String> caminoAct, ListaGenerica<String> caminoMin, int [] min, boolean [] pasaPorMuellePpal) {
		marca[i]=true;
		Vertice<String> v = grafo.listaDeVertices().elemento(i);
		caminoAct.agregarFinal(v.dato());
		if (v.dato().equals("Muelle Principal"))
			pasaPorMuellePpal[0]=true;
		if (!v.dato().equals(islaD)) {
			ListaGenerica<Arista<String>> ady = grafo.listaDeAdyacentes(v);
			ady.comenzar();
			while (!ady.fin()) {
				int j=ady.proximo().verticeDestino().posicion();
				dfsMin(j, islaD, marca, grafo, caminoAct, caminoMin, min, pasaPorMuellePpal);
			}
		}
		else {
			marca[i]=false;
			actualizarMin(min, caminoAct, caminoMin);
		}
	}
	public RutaMinima caminoMasCorto (Grafo<String> grafo, String islaO, String islaD) {
		int origen=buscar(islaO, grafo);
		boolean [] marca = new boolean [grafo.listaDeVertices().tamanio()];
		ListaGenerica<String> caminoAct = new ListaGenericaEnlazada<String>();
		ListaGenerica<String> caminoMin = new ListaGenericaEnlazada<String>();
		int [] min= new int [1];
		boolean [] pasaPorMuelle = new boolean [1];
		pasaPorMuelle[0]=false;
		min[0]=Integer.MAX_VALUE;
		dfsMin(origen, islaD, marca, grafo, caminoAct, caminoMin, min, pasaPorMuelle);
		return new RutaMinima(caminoMin, pasaPorMuelle[0]);
	}
}
