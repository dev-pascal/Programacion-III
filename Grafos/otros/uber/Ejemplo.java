public class Ejemplo {
	private boolean dfs (int i, boolean [] marca, ListaGenerica<String> caminoAct, ListaGenerica<String> conductores,
	Grafo<Barrio> ciudad, Uber uber, String destino, boolean encontro) {
		marca[i]=true;
		Vertice<Barrio> v = ciudad.listaDeVertices().elemento(i);
		caminoAct.agregarFinal(v.dato().getNombre());
		if (!v.dato().getNombre().equals(destino)) {
			ListaGenerica<Arista<Barrio>> ady = ciudad.listaDeAdyacentes(v);
			Arista<Barrio> adyAct=null;
			ady.comenzar();
			while (!ady.fin() && !encontro) {
				adyAct=ady.proximo();
				int j=adyAct.verticeDestino().posicion();
				if (!marca[j] && adyAct.peso()!=1 && !adyAct.verticeDestino().dato().tienePolicia()) {
					Uber ant = new Uber (uber.getNombre(), uber.getReputacion(), uber.getCombustible());
					uber.setCombustible(uber.getCombustible()-adyAct.peso());
					if (uber.getCombustible<0) {
						uber=buscarUber(ciudad,j);
						conductores.agregarFinal(uber.getNombre());
						encontro=dfs(j,marca,caminoAct,conductores,ciudad,uber,destino);
						ady.comenzar(); //por que tengo que hacer esto?
						uber.setCombustible(uber.getCombustible()+adyAct.peso());
						uber=ant;
					}
				}
			}
		}
		else {
			encontro=true;
		}
		if (!encontro) {
			caminoAct.eliminarEn(caminoAct.tamanio()-1);
			marca[i]=false;
		}
		return encontro;
	}
	private void setear (int min[], int peso, Uber uberMin, Uber uberAct, int [] pos, int i) {
		pos[0]=i;
		peso=min;
		uberMin.setNombre(uberAct.getNombre());
		uberMin.setPuntaje(uberAct.getPuntaje());
		uberMin.setCombustible(uberAct.getCombustible());
	}
	private void actualizarMin (int min[], int peso, Uber uberMin, Uber uberAct, int [] pos, int i) {
		if (min[0]>peso) {
			setear(min,peso,uberMin,uberAct,pos,i);
		}
		else {
			if (min[0]==peso) {
				if (uberMin.getReputacion()>uberAct.getReputacion()) {
					setear(min,peso,uberMin,uberAct,pos,i);
				}
			}
		}	
	}
	private void dfsMin (Grafo<Barrio> ciudad, int i, boolean [] marca, int peso, int min[], Uber uberAct, Uber uberMin) {
		marca[i]=true;
		Vertice<Barrio> v = ciudad.listaDeVertices().elemento(i);
		if (v.dato().tieneUber()) {
			actualizarMin(min,peso,uberMin,uberAct,pos,i);
		}
		else {
			ListaGenerica<Arista<Barrio>> ady=ciudad.listaDeAdyacentes(v);
			Arista<Barrio> adyAct=null;
			ady.comenzar();
			while (!ady.fin()) {
				adyAct=ady.proximo();
				int j=adyAct.verticeDestino().posicion();
				if (!marca[j] && adyAct.peso()!=1 && !adyAct.verticeDestino().dato().tienePolicia()) {
					peso-=adyAct.peso();
					dfsMin(ciudad, j, marca, peso, min, uberAct, uberMin);
					peso+=adyAct.peso();
				}
			}
		}
		marca[i]=false;
	}
	private Uber buscarUber (Grafo<Barrio> ciudad, int origen, Uber uberAct) {
		Uber uberMin = new Uber ("", -1, -1);
		int [] min = new int [1];
		min[0]=Integer.MAX_VALUE;
		int [] pos = new int [1];
		dfsMin(ciudad, origen, marca, 0, min, uberAct, uberMin, pos);
		Barrio barrioActualizado = ciudad.listaDeVertices.elemento(pos[0]);
		barrioActualizado.setUber(false);
		barrioActualizado.setUberObject(null);
		return uberMin;
	}
	private int buscar (Grafo<Barrio> ciudad, String dato) {
		ListaGenerica<Vertice<Barrio>> l = ciudad.listaDeVertices();
		String act = null;
		l.comenzar();
		while (!l.fin() && !encontro) {
			act=l.proximo();
			if (act.dato().getNombre().equals(dato))
				encontro=true;
		}
		if (encontro)
			if (!act.dato().tienePolicia())
				return act.posicion();
		else
			return -1;
	}
	public Respuesta resolver (Grafo<Barrio> Ciudad, String origen, String destino) {
		int O = buscar(Ciudad, origen);
		ListaGenerica<String> conductores = new ListaGenericaEnlazada<String>();
		ListaGenerica<String> camino = new ListaGenericaEnlazada<String>();
		Respuesta respuesta = new Respuesta(camino, conductores);
		Uber uber = buscarUber(Ciudad, O, new Uber ("", -1, -1));
		if (O!=-1)
			dfs(O, marca, camino, conductores, Ciudad, uber, destino, false);
		return respuesta;
	}
}
