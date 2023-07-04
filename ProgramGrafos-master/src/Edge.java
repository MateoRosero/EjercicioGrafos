public class Edge {
    private Vertice verticeInicial;
    private int peso;
    private Vertice verticeFinal;


    public Edge(Vertice verticeInicial, Vertice verticeFinal, int peso) {
        this.verticeInicial = verticeInicial;
        this.verticeFinal = verticeFinal;
        this.peso = peso;
    }

    public Vertice getVerticeInicial() {
        return verticeInicial;
    }

    public Vertice getVerticeFinal() {
        return verticeFinal;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return "Edge { " +
                "Vertice Inicial = " + verticeInicial +
                ", Vertice Final = " + verticeFinal +
                ", peso = " + peso ;

    }
}
