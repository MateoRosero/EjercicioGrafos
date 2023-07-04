import java.util.*;

public class Grafo {
    private ArrayList<Vertice> vertices;
    private boolean direccionado;
    private boolean ponderacion;

    public Grafo(boolean direccionado, boolean ponderacion){
        this.vertices = new ArrayList<Vertice>();
        this.direccionado = direccionado;
        this.ponderacion = ponderacion;
    }

    public void añadirVert(String datos){
        Vertice nuevoVertice = new Vertice(datos);
        this.vertices.add(nuevoVertice);
    }

    public void addEdge(String verticeInicial, String verticeFinal, int peso){
        if(!this.ponderacion){
            peso = 1;
        }

        getVertexByValue(verticeInicial).addEdge(getVertexByValue(verticeFinal), peso);

        if(!this.direccionado){
            getVertexByValue(verticeFinal).addEdge(getVertexByValue(verticeInicial), peso);
        }
    }

    public void removeVertice(Vertice vertice){

        for (Vertice v : vertices) {
            if (v != vertice) {
                ArrayList<Edge> edgesToRemove = new ArrayList<>();
                for (Edge edge : v.getEdges()) {
                    if (edge.getVerticeFinal() == vertice) {
                        edgesToRemove.add(edge);
                    }
                }
                v.getEdges().removeAll(edgesToRemove);
            }
        }
        vertices.remove(vertice);
    }


    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public Vertice getVertexByValue(String value){
        for(Vertice v: this.vertices){
            if(v.getDatos().equals(value)){
                return v;
            }
        }
        return null;
    }

    public String print(){
        String grafoString = "";
        for(Vertice v: this.vertices){
            grafoString += v.print(ponderacion);
            grafoString += "\n";
        }
        return grafoString;
    }

    public String depthFirstTraversal(Vertice startVertex, ArrayList<Vertice> visitedVertices){
        visitedVertices.add(startVertex);
        String result = startVertex.getDatos() + "\n";
        for(Edge e: startVertex.getEdges()){
            Vertice vecino = e.getVerticeFinal();
            if(!visitedVertices.contains(vecino)){
                result += depthFirstTraversal(vecino, visitedVertices);
            }
        }
        return result;
    }

    // Método para realizar un recorrido en anchura (BFS) en el grafo a partir de un vértice inicial
    public String breadthFirstTraversal(Vertice startVertex) {
        // Lista para almacenar los vértices visitados durante el recorrido
        ArrayList<Vertice> visitedVertices = new ArrayList<>();

        // Cola para almacenar los vértices pendientes de visitar
        Queue<Vertice> queue = new LinkedList<>();

        // Agregamos el vértice inicial a la cola y a la lista de visitados
        queue.add(startVertex);
        visitedVertices.add(startVertex);

        // Variable para almacenar el resultado del recorrido
        String resultado = "";

        // Realizamos el recorrido mientras la cola no esté vacía
        while (!queue.isEmpty()) {
            // Obtenemos el vértice actual de la cola
            Vertice currentVertex = queue.poll();

            // Agregamos el vértice actual al resultado
            resultado += currentVertex.getDatos() + "\n";

            // Iteramos sobre las aristas del vértice actual
            for (Edge edge : currentVertex.getEdges()) {
                // Obtenemos el vecino a través de la arista
                Vertice neighbor = edge.getVerticeFinal();

                // Si el vecino no ha sido visitado, lo agregamos a la lista de visitados y a la cola
                if (!visitedVertices.contains(neighbor)) {
                    visitedVertices.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Devolvemos el resultado del recorrido
        return resultado;
    }

    // Método para encontrar el camino más corto utilizando el algoritmo de Dijkstra a partir de un vértice inicial
    public String dijkstra(Vertice startVertex) {
        // HashMap para almacenar las distancias desde el vértice inicial a cada vértice del grafo
        HashMap<Vertice, Integer> distances = new HashMap<>();

        // HashMap para almacenar los vértices previos en el camino más corto desde el vértice inicial
        HashMap<Vertice, Vertice> previousVertices = new HashMap<>();

        // Cola de prioridad para almacenar los vértices ordenados por distancia
        PriorityQueue<Vertice> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Inicializamos las distancias y agregamos todos los vértices a la cola
        for (Vertice vertex : vertices) {
            if (vertex.equals(startVertex)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            queue.add(vertex);
        }
        // Dijkstra
        // Recorremos el bucle mientras la cola no esté vacía
// Esto asegura que procesemos todos los vértices alcanzables desde el vértice inicial
        while (!queue.isEmpty()) {
            // Obtenemos el vértice actual de la cola
            Vertice currentVertex = queue.poll();

            // Iteramos sobre todas las aristas del vértice actual
            for (Edge edge : currentVertex.getEdges()) {
                // Obtenemos el vecino del vértice actual a través de la arista
                Vertice neighbor = edge.getVerticeFinal();

                // Calculamos la distancia del camino alternativo a través del vértice actual
                int alternatePathDistance = distances.get(currentVertex) + edge.getPeso();

                // Si la distancia del camino alternativo es menor a la distancia actual almacenada para el vecino
                // actualizamos la distancia y el vértice previo en los mapas correspondientes
                if (alternatePathDistance < distances.get(neighbor)) {
                    distances.put(neighbor, alternatePathDistance);
                    previousVertices.put(neighbor, currentVertex);

                    // Utilizamos la cola para actualizar o remover el vecino
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Recogemos los resultados
        // Variable para almacenar el resultado
        String result = "";

// Iteramos sobre todos los vértices en el grafo
        for (Vertice vertex : vertices) {
            // Verificamos si no hay un camino desde el vértice inicial al vértice actual
            if (distances.get(vertex) == Integer.MAX_VALUE) {
                result += "El camino no existe desde " + startVertex.getDatos() + " a " + vertex.getDatos() + "\n";
            } else {
                // Si hay un camino, construimos la cadena del resultado
                result += "La ruta más corta desde " + startVertex.getDatos() + " a " + vertex.getDatos() + " es [";

                // Creamos una lista para almacenar los vértices en el camino
                ArrayList<String> path = new ArrayList<>();

                // Recorremos los vértices desde el vértice final hasta el vértice inicial
                // y los agregamos a la lista del camino
                for (Vertice currentVertex = vertex; currentVertex != null; currentVertex = previousVertices.get(currentVertex)) {
                    path.add(String.valueOf(currentVertex.getDatos()));
                }

                // Invertimos la lista del camino para obtener el orden correcto
                Collections.reverse(path);

                // Agregamos el camino al resultado utilizando una cadena separada por comas
                result += String.join(", ", path);

                // Agregamos la ponderación del camino al resultado
                result += "] tiene una ponderación de: " + distances.get(vertex) + "\n";
            }
        }
// Devolvemos el resultado
        return result;

    }
}
