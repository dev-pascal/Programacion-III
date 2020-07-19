package prog3.grafos.utiles.parcial;

import prog3.grafos.*;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Parcial {
	private static void actualizar (ListaGenerica<String> camino, ListaGenerica<String> nombres) {
		camino.comenzar();
		String act=null;
		while (!camino.fin()) {
			act=camino.proximo();
			if (!nombres.incluye(act))
				nombres.agregarFinal(act);
		}
	}
	private static void dfs (int i, boolean [] marca, ListaGenerica<String> camino,
	ListaGenerica<String> nombres, Grafo<Ciudad> mapa, String destino) {
		marca[i]=true;
		Vertice<Ciudad> v = mapa.listaDeVertices().elemento(i);
		camino.agregarFinal(v.dato().getNombre());
		if (v.dato().getNombre().equals(destino)) {
			actualizar(camino,nombres);	
		}
		else {
			ListaGenerica<Arista<Ciudad>> ady = mapa.listaDeAdyacentes(v);
			Arista<Ciudad> adyAct = null;
			while (!ady.fin()) {
				adyAct=ady.proximo();
				int j=adyAct.verticeDestino().posicion();
				//considere el peso de la arista 1 como ruta bloqueada
				if (!marca[j] && adyAct.verticeDestino().dato().getFase()!=1
				&& adyAct.peso()!=1)
					dfs(j,marca,camino,nombres,mapa,destino);	
			}	
		}
		camino.eliminarEn(camino.tamanio()-1);
		marca[i]=false;
	}
	private static int buscar (Grafo<Ciudad> ciudades, String nombre) {
		ListaGenerica<Vertice<Ciudad>> vL = ciudades.listaDeVertices();
		Vertice<Ciudad> act = null;
		vL.comenzar();
		boolean encontro=false;
		while (!vL.fin() && !encontro) {
			act=vL.proximo();
			if (act.dato().getNombre().equals(nombre))
				encontro=true;
		}
		int fin=-1;
		if (encontro)
			if (act.dato().getFase()!=1)
				fin=act.posicion();
		return fin;
	}
	public static ListaGenerica<String> resolver (Grafo<Ciudad> ciudades, String origen, String destino) {
		int O=buscar(ciudades,origen);
		boolean [] marca=  new boolean [ciudades.listaDeVertices().tamanio()];
		ListaGenerica<String> camino = new ListaGenericaEnlazada<String>();
		ListaGenerica<String> nombres = new ListaGenericaEnlazada<String>();
		if (O!=-1)
			dfs(O,marca,camino,nombres,ciudades,destino);
		return nombres;
	}
	private static void armarGrafo (Grafo<Ciudad> g) {
		Ciudad LP = new Ciudad("La Plata",3);
		Ciudad Q = new Ciudad ("Quilmes", 3);
		Ciudad Moreno = new Ciudad ("Moreno", 4);
		Ciudad Carlos = new Ciudad ("Carlos Keen", 5);
		Ciudad Suipacha = new Ciudad ("Suipacha", 3);
		Ciudad Navarro = new Ciudad ("Navarro", 2);
		Ciudad Saladillo = new Ciudad ("Saladillo", 2);
		Ciudad Lobos = new Ciudad ("Lobos", 1);
		Ciudad Cañuelas = new Ciudad ("Cañuelas", 2);
		Ciudad Abasto = new Ciudad ("Abasto", 4);
		Ciudad Altamirano = new Ciudad ("Altamirano", 1);
		Vertice<Ciudad> lp = new VerticeImplListAdy<>(LP);
		Vertice<Ciudad> q = new VerticeImplListAdy<>(Q);
		Vertice<Ciudad> moreno = new VerticeImplListAdy<>(Moreno);
		Vertice<Ciudad> carlos = new VerticeImplListAdy<>(Carlos);
		Vertice<Ciudad> suipacha = new VerticeImplListAdy<>(Suipacha);
		Vertice<Ciudad> navarro = new VerticeImplListAdy<>(Navarro);
		Vertice<Ciudad> saladillo = new VerticeImplListAdy<Ciudad>(Saladillo);
		Vertice<Ciudad> lobos = new VerticeImplListAdy<Ciudad>(Lobos);
		Vertice<Ciudad> cañuelas = new VerticeImplListAdy<Ciudad>(Cañuelas);
		Vertice<Ciudad> abasto = new VerticeImplListAdy<Ciudad>(Abasto);
		Vertice<Ciudad> altamirano = new VerticeImplListAdy<Ciudad>(Altamirano);
		g.agregarVertice(lp);
		g.agregarVertice(q);
		g.agregarVertice(moreno);
		g.agregarVertice(carlos);
		g.agregarVertice(suipacha);
		g.agregarVertice(navarro);
		g.agregarVertice(saladillo);
		g.agregarVertice(lobos);
		g.agregarVertice(cañuelas);
		g.agregarVertice(abasto);
		g.agregarVertice(altamirano);
		g.conectar(lp, q, 2);
		g.conectar(q, lp, 2);
		g.conectar(moreno, q, 2);
		g.conectar(q, moreno, 2);
		g.conectar(carlos, moreno, 2);
		g.conectar(moreno, carlos, 2);
		g.conectar(suipacha, carlos, 2);
		g.conectar(carlos, suipacha, 2);
		g.conectar(lp,  abasto, 2);
		g.conectar(abasto, lp, 2);
		g.conectar(abasto, cañuelas, 2);
		g.conectar(cañuelas, abasto, 2);
		g.conectar(abasto, moreno, 3);
		g.conectar(moreno, abasto, 3);
		g.conectar(cañuelas, navarro, 2);
		g.conectar(navarro, cañuelas, 2);
		g.conectar(navarro, saladillo, 1);
		g.conectar(saladillo, navarro, 1);
		g.conectar(navarro, lobos, 4);
		g.conectar(navarro, suipacha, 2);
		g.conectar(suipacha, navarro, 2);
		g.conectar(lobos, navarro, 4);
		g.conectar(cañuelas, altamirano);
		g.conectar(altamirano, cañuelas);
		g.conectar(lp, altamirano);
		g.conectar(altamirano, lp);
	}
	public static void main (String args[]) {
		Grafo<Ciudad> grafo = new GrafoImplListAdy<Ciudad>();
		armarGrafo(grafo);
		System.out.println(resolver(grafo,"La Plata","Suipacha"));
	}
}
