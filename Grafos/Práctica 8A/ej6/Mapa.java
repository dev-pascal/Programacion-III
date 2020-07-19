package prog3.grafos;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Mapa {
	private Grafo<String> mapaCiudades;
	
	// ------ CARGAR EL MAPA --------
	
	public Mapa () {
		
		this.mapaCiudades= new GrafoImplListAdy<String>();
		
		Vertice<String> A=new VerticeImplListAdy<String>("A");
		this.mapaCiudades.agregarVertice(A);
		
		Vertice<String> B=new VerticeImplListAdy<String>("B");
		this.mapaCiudades.agregarVertice(B);
		
		Vertice<String> D=new VerticeImplListAdy<String>("D");
		this.mapaCiudades.agregarVertice(D);
		
		Vertice<String> E=new VerticeImplListAdy<String>("E");
		this.mapaCiudades.agregarVertice(E);
		
		Vertice<String> C=new VerticeImplListAdy<String>("C");
		this.mapaCiudades.agregarVertice(C);
		
		this.mapaCiudades.conectar(A, B, 2);
		this.mapaCiudades.conectar(B, A, 2);
		
		this.mapaCiudades.conectar(B, D, 4);
		this.mapaCiudades.conectar(D, B, 4);
		
		this.mapaCiudades.conectar(B, C, 9);
		this.mapaCiudades.conectar(C, B, 9);
		
		this.mapaCiudades.conectar(D, E, 6);
		this.mapaCiudades.conectar(E, D, 6);
		
		this.mapaCiudades.conectar(E, C, 20);
		this.mapaCiudades.conectar(C, E, 20);
		
	}
	
	// -- EJERCICIO 6, implementacion del metodo DevolverCamino --
	
	private void dfs(int i, boolean[] marca, ListaGenerica<String> lista, String ciudad1, String ciudad2, boolean [] encontro){
		marca[i] = true; 
		Vertice<String> v = this.mapaCiudades.listaDeVertices().elemento(i);
		lista.agregarInicio(v.dato());
		if (!v.dato().equals(ciudad2)) {
			 ListaGenerica<Arista<String>> ady = this.mapaCiudades.listaDeAdyacentes(v);
			 if (!ady.esVacia()) {
				 ady.comenzar();
				 while (!ady.fin() && !encontro[0]) {
					 int j = ady.proximo().verticeDestino().posicion();
					 if (!marca[j]){
						 this.dfs(j, marca, lista, ciudad1, ciudad2, encontro);
					 }
				 }
			 }
		}
		else
			encontro[0]=true;
		if (!encontro[0])
			lista.eliminar(v.dato());
	}
	private ListaGenerica<String> dfs (String ciudad1, String ciudad2, int ciudad1_pos) {
		ListaGenerica<String> lista=new ListaGenericaEnlazada<String>();
		boolean[] mat= new boolean [this.mapaCiudades.listaDeVertices().tamanio()];
		boolean [] encontro=new boolean [1];
		this.dfs(ciudad1_pos, mat, lista, ciudad1, ciudad2, encontro);
		if (!encontro[0])
			lista=new ListaGenericaEnlazada<String>();
		return lista;
	}
	private int buscarCiudad (String ciudad) {
		ListaGenerica<Vertice<String>> vertices= this.mapaCiudades.listaDeVertices();
		Vertice<String> ciudadActual=null;
		boolean encontro=false;
		vertices.comenzar();
		while (!vertices.fin() && !encontro) {
			ciudadActual=vertices.proximo();
			if (ciudadActual.dato().equals(ciudad))
				encontro=true;
		}
		return ciudadActual.posicion();
	}
	public ListaGenerica<String> devolverCamino (String ciudad1, String ciudad2) {
		int ciudad1_pos=buscarCiudad(ciudad1);
		ListaGenerica<String> lista=dfs(ciudad1, ciudad2, ciudad1_pos);
		ListaGenerica<String> l=new ListaGenericaEnlazada<String>();
		if (!lista.esVacia()) {
			lista.comenzar();
			while (!lista.fin()) {
				l.agregarInicio(lista.proximo());
			}
		}
		return l;
	}
	
	// -- EJERCICIO 6, implementacion del metodo DevolverCaminoExceptuando --
	
	private void dfsExc(int i, boolean[] marca, ListaGenerica<String> lista, String ciudad1, String ciudad2, boolean [] encontro){
		marca[i] = true; 
		Vertice<String> v = this.mapaCiudades.listaDeVertices().elemento(i);
		if (!v.dato().equals(ciudad2)) {
			lista.agregarInicio(v.dato());
			 if (!v.dato().equals(ciudad1)) {
				 ListaGenerica<Arista<String>> ady = this.mapaCiudades.listaDeAdyacentes(v);
				 if (!ady.esVacia()) {
					 ady.comenzar();
					 while (!ady.fin() && !encontro[0]) {
						 int j = ady.proximo().verticeDestino().posicion();
						 if (!marca[j]){
							 this.dfsExc(j, marca, lista, ciudad1, ciudad2, encontro);
						 }
					 }
				 }
			 }
		}
		else
			encontro[0]=true;
		if (!encontro[0])
			lista.eliminar(v.dato());
	}
	private ListaGenerica<String> dfsExceptuando (String ciudad1, String ciudad2, int ciudad1_pos, boolean [] encontro) {
		ListaGenerica<String> lista=new ListaGenericaEnlazada<String>();
		boolean[] mat= new boolean [this.mapaCiudades.listaDeVertices().tamanio()];
		this.dfsExc(ciudad1_pos, mat, lista, ciudad1, ciudad2, encontro);
		if (!encontro[0])
			lista=new ListaGenericaEnlazada<String>();
		return lista;
	}
	public ListaGenerica<String> devolverCaminoExceptuando (String ciudad1, String ciudad2) {
		int ciudad1_pos=buscarCiudad(ciudad1);
		boolean [] encontro=new boolean [1];
		Vertice<String> v=this.mapaCiudades.vertice(ciudad1_pos);
		ListaGenerica<Arista<String>> A= this.mapaCiudades.listaDeAdyacentes(v);
		ListaGenerica<String> lista= new ListaGenericaEnlazada<String>();
		while (!A.fin() && !encontro[0]) {
			lista=dfsExceptuando(ciudad1, ciudad2, A.proximo().verticeDestino().posicion(), encontro);
		}
		ListaGenerica<String> l=new ListaGenericaEnlazada<String>();
		if (!lista.esVacia()) {
			lista.comenzar();
			while (!lista.fin()) {
				l.agregarInicio(lista.proximo());
			}
		}
		return l;
	}
	
	// -- EJERCICIO 6, Implementacion del metodo caminoMasCorto --
	
	private void actualizarMin (int [] min, int pesoAcumulado, ListaGenerica<String> caminoActual, ListaGenerica<String> caminoMasCorto) {
		if (min[0]>pesoAcumulado) {
			min[0]=pesoAcumulado;
			while (!caminoMasCorto.esVacia())
				caminoMasCorto.eliminarEn(0);
			caminoActual.comenzar();
			while (!caminoActual.fin()) {
				caminoMasCorto.agregarFinal(caminoActual.proximo());
			}
		}
	}
	private void dfsCorto(int i, boolean[] marca, ListaGenerica<String> caminoActual, ListaGenerica<String> caminoMasCorto, String ciudad2, int pesoAcumulado, int [] min){
		marca[i] = true; 
		Vertice<String> v = this.mapaCiudades.listaDeVertices().elemento(i);
		caminoActual.agregarFinal(v.dato());
		if (!v.dato().equals(ciudad2)) {
			 ListaGenerica<Arista<String>> ady = this.mapaCiudades.listaDeAdyacentes(v);
			 if (!ady.esVacia()) {
				 ady.comenzar();
				 while (!ady.fin()) {
					 Arista<String> prox= ady.proximo();
					 int j = prox.verticeDestino().posicion();
					 if (!marca[j]){
						 pesoAcumulado+=prox.peso();
						 this.dfsCorto(j, marca, caminoActual, caminoMasCorto, ciudad2, pesoAcumulado, min);
						 pesoAcumulado-=prox.peso();
					 }
				 }
			 }
		}
		else {
			marca[i]=false;
			actualizarMin(min, pesoAcumulado, caminoActual, caminoMasCorto);
		}
		caminoActual.eliminarEn(caminoActual.tamanio()-1);
	}
	private ListaGenerica<String> dfsMasCorto (String ciudad1, String ciudad2, int ciudad1_pos) {
		ListaGenerica<String> caminoActual=new ListaGenericaEnlazada<String>();
		ListaGenerica<String> caminoMasCorto=new ListaGenericaEnlazada<String>();
		boolean[] mat= new boolean [this.mapaCiudades.listaDeVertices().tamanio()];
		int [] min=new int [1];
		min[0]=Integer.MAX_VALUE;
		int pesoAcumulado=0;
		this.dfsCorto(ciudad1_pos, mat, caminoActual, caminoMasCorto, ciudad2, pesoAcumulado, min);
		return caminoMasCorto;
	}
	public ListaGenerica<String> caminoMasCorto (String ciudad1, String ciudad2) {
		int ciudad1_pos=buscarCiudad(ciudad1);
		ListaGenerica<String> lista=dfsMasCorto(ciudad1, ciudad2, ciudad1_pos);
		return lista;
	}
	// ----- caminoSinCargarCombustible -----
	
	private void SinCargar (int i, String ciudad2, boolean [] marca, ListaGenerica<String> caminoActual, int tanqueAuto, boolean [] exito) {
		if (!exito[0] || !exito[1]) {
			marca[i] = true; 
			Vertice<String> v = this.mapaCiudades.listaDeVertices().elemento(i);
			caminoActual.agregarFinal(v.dato());
			if (!v.dato().equals(ciudad2) && tanqueAuto>0) {
				 ListaGenerica<Arista<String>> ady = this.mapaCiudades.listaDeAdyacentes(v);
				 if (!ady.esVacia()) {
					 ady.comenzar();
					 while (!ady.fin()) {
						 Arista<String> prox= ady.proximo();
						 int j = prox.verticeDestino().posicion();
						 if (!marca[j]){
							 tanqueAuto-=prox.peso();
							 this.SinCargar(j, ciudad2, marca, caminoActual, tanqueAuto, exito);
						 }
					 }
				 }
			}
			else {
				if (v.dato().equals(ciudad2))
					exito[0]=true;
				if (tanqueAuto<=0)
					exito[1]=true;
			}
		}
	}
	public ListaGenerica<String> caminoSinCargarCombustible (String ciudad1, String ciudad2, int tanqueAuto) {
		int ciudad1_pos=buscarCiudad(ciudad1);
		ListaGenerica<String> lista = new ListaGenericaEnlazada<String>();
		boolean[] marca= new boolean [this.mapaCiudades.listaDeVertices().tamanio()];
		boolean [] exito= new boolean [2];
		//La posicion 0 representa si se encontró la ciudad o no
		//La posicion 1 representa si el tanque del auto quedó vacío o no
		SinCargar(ciudad1_pos, ciudad2, marca, lista, tanqueAuto, exito);
		if (!exito[0] || exito[1]) {
			lista.comenzar();
			while (!lista.esVacia())
				lista.eliminarEn(0);
		}
		return lista;
	}
	
	// --- caminoConMenorCargaDeCombustible
	
	private void actualizarMinimo (int [] min, int cantCarga, ListaGenerica<String> caminoActual, ListaGenerica<String> caminoMasCorto) {
		if (min[0]>cantCarga) {
			min[0]=cantCarga;
			while (!caminoMasCorto.esVacia())
				caminoMasCorto.eliminarEn(0);
			caminoActual.comenzar();
			while (!caminoActual.fin()) {
				caminoMasCorto.agregarFinal(caminoActual.proximo());
			}
		}
	}
	private void MenorCarga (int i, String ciudad2, boolean [] marca, ListaGenerica<String> caminoActual, ListaGenerica<String> caminoMasCorto, int tanqueAuto, int cantCarga, int cargar, boolean [] encontro, int [] min) {
		marca[i] = true; 
		Vertice<String> v = this.mapaCiudades.listaDeVertices().elemento(i);
		caminoActual.agregarFinal(v.dato());
		if (!v.dato().equals(ciudad2)) {
			ListaGenerica<Arista<String>> ady = this.mapaCiudades.listaDeAdyacentes(v);
			if (!ady.esVacia()) {
				ady.comenzar();
				while (!ady.fin()) {
					Arista<String> prox= ady.proximo();
					int j = prox.verticeDestino().posicion();
					if (!marca[j]){
						while (tanqueAuto-prox.peso()<=0) {
							cantCarga++;
							tanqueAuto+=cargar;
						}
						tanqueAuto-=prox.peso();
						this.MenorCarga(j, ciudad2, marca, caminoActual, caminoMasCorto, tanqueAuto, cantCarga, cargar, encontro, min);
						caminoActual.eliminarEn(caminoActual.tamanio()-1);
					}
				}
			}
		}
		else {
			marca[i]=false;
			encontro[0]=true;
			actualizarMinimo(min, cantCarga, caminoActual, caminoMasCorto);
		}
	}
	public ListaGenerica<String> caminoConMenorCargaDeCombustible (String ciudad1, String ciudad2, int tanqueAuto) {
		int ciudad1_pos=buscarCiudad(ciudad1);
		ListaGenerica<String> camino = new ListaGenericaEnlazada<String>();
		ListaGenerica<String> caminoMasCorto = new ListaGenericaEnlazada<String>();
		boolean[] marca= new boolean [this.mapaCiudades.listaDeVertices().tamanio()];
		boolean [] encontro= new boolean[1];
		int [] min= new int [1];
		min[0]=Integer.MAX_VALUE;
		MenorCarga(ciudad1_pos, ciudad2, marca, camino, caminoMasCorto, tanqueAuto, 0, tanqueAuto, encontro, min);
		if (!encontro[0]) {
			caminoMasCorto.comenzar();
			while (!caminoMasCorto.esVacia())
				caminoMasCorto.eliminarEn(0);
		}
		return caminoMasCorto;
	}
}
