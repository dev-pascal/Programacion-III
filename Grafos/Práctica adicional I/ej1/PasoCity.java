package prog3.grafos.utiles;

import prog3.grafos.Arista;
import prog3.grafos.Grafo;
import prog3.grafos.GrafoImplListAdy;
import prog3.grafos.Vertice;
import prog3.grafos.VerticeImplListAdy;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class PasoCity {
	private static void actualizarMin (int cant, int [] min, ListaGenerica<Sitio> caminoAct, ListaGenerica<Sitio> caminoMin) {
		if (min[0]>cant) {
			min[0]=cant;
			while (!caminoMin.esVacia())
				caminoMin.eliminarEn(0);
			caminoAct.comenzar();
			while (!caminoAct.fin())
				caminoMin.agregarFinal(caminoAct.proximo());
		}
	}
	private static void dfs(int i, boolean [] marca, String destino, int cant, int [] min, ListaGenerica<Sitio> caminoAct, ListaGenerica<Sitio> caminoMin, Grafo<Sitio> ciudad) {
		marca[i]=true;
		Vertice<Sitio> v=ciudad.listaDeVertices().elemento(i);
		caminoAct.agregarFinal(v.dato());
		if (v.dato().getMafia())
			cant++;
		if (v.dato().getNombre().equals(destino)) {
			actualizarMin(cant,min,caminoAct,caminoMin);
		}
		else {
			ListaGenerica<Arista<Sitio>> ady=ciudad.listaDeAdyacentes(v);
			Arista<Sitio> adyAct=null;
			ady.comenzar();
			while (!ady.fin()) {
				adyAct=ady.proximo();
				int j=adyAct.verticeDestino().posicion();
				if (!marca[j]) {
					if (adyAct.peso()==1)
						cant++;
					dfs(j,marca,destino,cant,min,caminoAct,caminoMin,ciudad);
					if (adyAct.peso()==1)
						cant--;
				}
			}
		}
		caminoAct.eliminarEn(caminoAct.tamanio()-1);
		marca[i]=false;
	}
	private static int buscar (Grafo<Sitio> g, String nombre) {
		ListaGenerica<Vertice<Sitio>> l = g.listaDeVertices();
		boolean encontro=false;
		Vertice<Sitio> act=null;
		l.comenzar();
		while (!l.fin() && !encontro) {
			act=l.proximo();
			if (act.dato().getNombre().equals(nombre))
				encontro=true;
		}
		return act.posicion();
	}
	public static ListaGenerica<Sitio> devolverRuta (Grafo<Sitio> ciudad) {
		ListaGenerica<Sitio> caminoAct= new ListaGenericaEnlazada<Sitio>();
		ListaGenerica<Sitio> caminoMin= new ListaGenericaEnlazada<Sitio>();
		int origen=buscar(ciudad, "Casa del Intendente");
		boolean [] marca = new boolean [ciudad.listaDeVertices().tamanio()];
		int [] min = new int [1];
		min[0]=Integer.MAX_VALUE;
		dfs(origen,marca,"Municipalidad",0,min,caminoAct,caminoMin,ciudad);
		return caminoMin;
	}
	private static void cargarMapa (Grafo<Sitio> g) {
		Vertice<Sitio> Casa = new VerticeImplListAdy<Sitio>(new Sitio("Casa del Intendente", false));
		Vertice<Sitio> B = new VerticeImplListAdy<Sitio>(new Sitio ("B", false));
		Vertice<Sitio> C= new VerticeImplListAdy<Sitio>(new Sitio ("C", true));
		Vertice<Sitio> Municipalidad= new VerticeImplListAdy<Sitio>(new Sitio ("Municipalidad", false));
		Vertice<Sitio> E = new VerticeImplListAdy<Sitio>(new Sitio ("E", true));
		Vertice<Sitio> F= new VerticeImplListAdy<Sitio>(new Sitio ("F", true));
		g.agregarVertice(Casa);
		g.agregarVertice(B);
		g.agregarVertice(C);
		g.agregarVertice(Municipalidad);
		g.agregarVertice(E);
		g.agregarVertice(F);
		g.conectar(Casa, B, 0);
		g.conectar(B, Casa, 0);
		g.conectar(B, C, 1);
		g.conectar(C, B, 1);
		g.conectar(C, Municipalidad, 1);
		g.conectar(Casa, E, 1);
		g.conectar(E, Casa, 1);
		g.conectar(E, F, 1);
		g.conectar(F, E, 1);
		g.conectar(F, Municipalidad, 0);
		g.conectar(Municipalidad, F, 1);
	}
	private static void imprimir (Grafo<Sitio> g) {
		ListaGenerica<Sitio> l = devolverRuta(g);
		l.comenzar();
		while (!l.fin()) {
			System.out.println(l.proximo().getNombre());
		}
	}
	public static void main (String args []) {
		Grafo<Sitio> g= new GrafoImplListAdy<Sitio>();
		cargarMapa(g);
		imprimir(g);
	}
}
