import java.util.ArrayList;

public class Vertice {
    private String datos;
    private ArrayList<Edge> edges;

    public Vertice(String ingresoDatos) {
        this.edges = new ArrayList<>();
        this.datos = ingresoDatos;
    }

    public String getDatos() {
        return this.datos;
    }

    public void addEdge(Vertice verticeFinal, int peso) {
        this.edges.add(new Edge(this, verticeFinal, peso));
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public String print(boolean mostrarPonderacion) {
        StringBuilder mensaje = new StringBuilder();

        if (this.edges.isEmpty()) {
            mensaje.append(this.datos).append(" --------------->");
            return mensaje.toString();
        }

        for (int i = 0; i < this.edges.size(); i++) {
            if (i == 0) {
                mensaje.append(this.edges.get(i).getVerticeFinal().getDatos()).append(" ---------------> ");
            }

            mensaje.append(this.edges.get(i).getVerticeFinal().getDatos());

            if (i != this.edges.size() - 1) {
                mensaje.append(", ");
            }

            if (mostrarPonderacion) {
                mensaje.append(" (").append(this.edges.get(i).getPeso()).append(")");
            }
        }
        return mensaje.toString();
    }
}