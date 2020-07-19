public class ejercicioLibrerias {
	private static void buscarLibrerias (Grafo<Lugar> grafo, ListaGenerica<Lugar> librerias) {
		ListaGenerica<Vertice<Lugar>> vertices= grafo.listaDeVertices();
		Vertice<Lugar> act=null;
		vertices.comenzar();
		while (!vertices.fin()) {
			act=vertices.proximo();
			if (act.dato().esLibreria()) {
				librerias.agregarFinal(act.dato());
			}
		}
	}
	private static void actualizarMin (int pesoTotal, int [] minPeso, ListaGenerica<Lugar> caminoAct,
	ListaGenerica<Lugar> caminoMin) {
		if (minPeso[0]>pesoTotal) {
			minPeso[0]=pesoTotal;
			reiniciar(caminoMin);
			caminoAct.comenzar();
			while (!caminoAct.fin())
				caminoMin.agregarFinal(caminoAct.proximo());
		}
	}
	private static void dfs (int i, boolean [] marca, ListaGenerica<Lugar> caminoAct, ListaGenerica<Lugar> caminoMin,
	Lugar libreriaAct, int pesoTotal, int [] minPeso, ListaGenerica<Lugar> libreriasProhibidas, Grafo<Lugar> grafo) {
		marca[i]=true;
		Vertice<Lugar> v = grafo.listaDeVertices().elemento(i);
		caminoAct.agregarFinal(v.dato());
		if (v.dato().getNombre().equals(libreriaAct.getNombre())) {
			actualizarMin(pesoTotal, minPeso, caminoAct, caminoMin);
		}
		else {
			ListaGenerica<Arista<Lugar>> ady = grafo.listaDeAdyacentes(v);
			Arista<Lugar> adyAct=null;	
			ady.comenzar();
			while (!ady.fin()) {
				adyAct=ady.proximo();
				int j= adyAct.verticeDestino().posicion();
				if (!marca[j] && !libreriasProhibidas.incluye(adyAct.verticeDestino().dato())) {
					dfs(j, marca, caminoAct, caminoMin, libreriaAct, pesoTotal+adyAct.peso(), minPeso,
					libreriasProhibidas, grafo);
				}
			}
		}
		marca[i]=false;
		caminoAct.eliminarEn(caminoAct.tamanio()-1);
	}
	private static void reiniciar (ListaGenerica<Lugar> l) {
		while (!l.esVacia()) {
			l.eliminarEn(0);
		}
	}
	private static void procesar (ListaGenerica<Lugar> libreriasProhibidas, Lugar libreriaAct, Grafo<Lugar> grafo) {
		reiniciar(libreriasProhibidas);
		buscarLibrerias(grafo, libreriasProhibidas);
		libreriasProhibidas.eliminar(libreriaAct);
	}
	public static Respuesta [] devolverCaminosMinimos (Grafo<Lugar> grafo, Vertice<Lugar> Casa) {
		int origen=Casa.posicion();
		ListaGenerica<Lugar> caminoAct= new ListaGenericaEnlazada<>();
		ListaGenerica<Lugar> librerias = new ListaGenericaEnlazada<>();
		buscarLibrerias(grafo, librerias);
		Respuesta [] caminosMinimos = new Respuesta [librerias.tamanio()];
		int i=0;
		librerias.comenzar();
		while (!librerias.fin()) {
			Lugar libreriaAct = librerias.proximo();
			caminosMinimos[i]= new Respuesta();
			caminosMinimos[i].setNombre(libreriaAct.getNombre());
			int [] minPeso= new int[1];
			minPeso[0]= Integer.MAX_VALUE;
			int pesoTotal=0;
			boolean [] marca = new boolean [grafo.listaDeVertices().tamanio()];
			ListaGenerica<Lugar> libreriasProhibidas = new ListaGenericaEnlazada<Lugar>();
			procesar(libreriasProhibidas, libreriaAct, grafo);
			dfs(origen, marca, caminoAct, caminosMinimos[i].getCamino(), libreriaAct, pesoTotal, minPeso,
			libreriasProhibidas, grafo);
			i++;
		}
		return caminosMinimos;	
	}
}