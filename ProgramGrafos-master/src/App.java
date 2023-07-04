import javax.swing.*;
import java.util.ArrayList;

public class App extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField textFieldVertice;
    private JCheckBox direccionadoCheckBox;
    private JCheckBox PonderacionCheckBox;
    private JButton crearGrafoButton;
    private JComboBox<String> comboBoxAristaInicio;
    private JButton quemarButton;
    private JButton insertarButton;
    private JTextField textFieldPeso;
    private JButton insertarButton1;
    private JButton mostrarGrafoButton;
    private JComboBox<String> comboBoxVerticeInicial;
    private JButton BFSButton;
    private JButton DFSButton;
    private JButton dijkstraButton;
    private JTextArea textArea1;
    private JComboBox<String> comboBoxAristaFin;
    private JButton eliminarButton;
    Grafo grafo;

    public App() {
        setContentPane(panel1);
        crearGrafoButton.addActionListener(e -> crearGrafo());
        quemarButton.addActionListener(e -> quemarDatos());
        mostrarGrafoButton.addActionListener(e -> mostrarGrafo());
        insertarButton.addActionListener(e -> insertarVertice());
        insertarButton1.addActionListener(e -> ingresoArista());
        DFSButton.addActionListener(e -> dfs());
        BFSButton.addActionListener(e -> bsf());
        dijkstraButton.addActionListener(e -> dijkstra());
        eliminarButton.addActionListener(e -> eliminarVertice());
    }

    public void crearGrafo() {
        grafo = new Grafo(direccionadoCheckBox.isSelected(), PonderacionCheckBox.isSelected());

        if (!PonderacionCheckBox.isSelected()) {
            textFieldPeso.setEnabled(false);
            textFieldPeso.setText("1");
        } else {
            textFieldPeso.setEnabled(true);
            textFieldPeso.setText("");
        }

        reiniciarGrafo();
        activarCampos();

        JOptionPane.showMessageDialog(null, "Se ha creado el grafo");
    }

    public void quemarDatos() {
        grafo.añadirVert("5");
        actualizarCombos("5", true);
        grafo.añadirVert("9");
        actualizarCombos("9", true);
        grafo.añadirVert("6");
        actualizarCombos("6", true);
        grafo.añadirVert("3");
        actualizarCombos("3", true);
        grafo.añadirVert("8");
        actualizarCombos("8", true);

        grafo.addEdge(grafo.getVertices().get(0).getDatos(), grafo.getVertices().get(1).getDatos(), 2);
        grafo.addEdge(grafo.getVertices().get(0).getDatos(), grafo.getVertices().get(2).getDatos(), 4);

        grafo.addEdge(grafo.getVertices().get(1).getDatos(), grafo.getVertices().get(2).getDatos(), 9);
        grafo.addEdge(grafo.getVertices().get(1).getDatos(), grafo.getVertices().get(3).getDatos(), 8);

        grafo.addEdge(grafo.getVertices().get(2).getDatos(), grafo.getVertices().get(3).getDatos(), 7);
        grafo.addEdge(grafo.getVertices().get(2).getDatos(), grafo.getVertices().get(4).getDatos(), 6);




        quemarButton.setEnabled(false);
        mostrarGrafo();
    }

    public void mostrarGrafo(){
        textArea1.setText(grafo.print());
    }

    public void actualizarCombos(String datos, boolean bool){

        if(bool){
            comboBoxAristaInicio.addItem(datos);
            comboBoxAristaFin.addItem(datos);
            comboBoxVerticeInicial.addItem(datos);
        }else{
            comboBoxAristaInicio.removeItem(datos);
            comboBoxAristaFin.removeItem(datos);
            comboBoxVerticeInicial.removeItem(datos);
        }

    }

    public void insertarVertice(){

        if(!textFieldVertice.getText().isEmpty()){
            if(grafo.getVertexByValue(textFieldVertice.getText()) == null){
                grafo.añadirVert(textFieldVertice.getText());
                actualizarCombos(textFieldVertice.getText(), true);
                mostrarGrafo();
                textFieldVertice.setText("");
            }else{
                JOptionPane.showMessageDialog(null, "Este vertice ya ha sido ingresado");
            }
        }else{
            JOptionPane.showMessageDialog(null, "No se puede ingresar un vertice nulo");
        }

    }

    public void eliminarVertice(){
        if(!textFieldVertice.getText().isEmpty()){
            if(grafo.getVertexByValue(textFieldVertice.getText()) != null){
                grafo.removeVertice(grafo.getVertexByValue(textFieldVertice.getText()));
                actualizarCombos(textFieldVertice.getText(), false);
                mostrarGrafo();
                textFieldVertice.setText("");
                JOptionPane.showMessageDialog(null, "Vertice eliminado");
            }else{
                JOptionPane.showMessageDialog(null, "No se ha ingresado este vertice");
            }
        }else{
            JOptionPane.showMessageDialog(null, "No se puede eliminar un vertice nulo.");
        }
    }

    public void ingresoArista(){
        String inicio = comboBoxAristaInicio.getSelectedItem().toString();
        String fin = comboBoxAristaFin.getSelectedItem().toString();

        if(comboBoxAristaInicio.getSelectedItem() != null && comboBoxAristaFin.getSelectedItem() != null){
            if(!textFieldPeso.getText().isEmpty()){
                if(inicio.equals(fin)) {
                    JOptionPane.showMessageDialog(null, "Estas generando un bucle, no se puede");
                } else {
                    Vertice verticeInicio = grafo.getVertexByValue(inicio);
                    Vertice verticeFin = grafo.getVertexByValue(fin);

                    boolean aristaExistente = false;
                    for (Edge edge : verticeInicio.getEdges()) {
                        if (edge.getVerticeFinal().equals(verticeFin)) {
                            aristaExistente = true;
                            break;
                        }
                    }

                    if (aristaExistente) {
                        JOptionPane.showMessageDialog(null, "Ya se ha ingresado la arista");
                    } else {
                        grafo.addEdge(inicio, fin, Integer.parseInt(textFieldPeso.getText()));
                        mostrarGrafo();
                        JOptionPane.showMessageDialog(null, "Se ha generado el vértice");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "No es posible agregar una arista con peso nulo en el grafo.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "No es posible agregar una arista con un vértice vacío en el grafo");
        }
    }

    public void dfs(){
        if(comboBoxVerticeInicial.getSelectedItem() != null){
            ArrayList<Vertice> visitedVertices = new ArrayList<>();
            textArea1.setText(grafo.depthFirstTraversal(grafo.getVertexByValue(comboBoxVerticeInicial.getSelectedItem().toString()), visitedVertices));
        }else{
            JOptionPane.showMessageDialog(null, "Aún no se ingresa el vertice de partida");
        }
    }

    public void bsf(){
        if(comboBoxVerticeInicial.getSelectedItem() != null){
            textArea1.setText(grafo.breadthFirstTraversal(grafo.getVertexByValue(comboBoxVerticeInicial.getSelectedItem().toString())));
        }else{
            JOptionPane.showMessageDialog(null, "Aún no se ingresa el vertice de partida");
        }
    }

    public void dijkstra(){
        if(comboBoxVerticeInicial.getSelectedItem() != null){
            textArea1.setText(grafo.dijkstra(grafo.getVertexByValue(comboBoxVerticeInicial.getSelectedItem().toString())));
        }else{
            JOptionPane.showMessageDialog(null, "Aún no se ingresa el vertice de partida");
        }
    }

    public void activarCampos(){
        quemarButton.setEnabled(true);
        textFieldVertice.setEnabled(true);
        eliminarButton.setEnabled(true);
        insertarButton.setEnabled(true);
        comboBoxAristaInicio.setEnabled(true);
        comboBoxAristaFin.setEnabled(true);
        insertarButton1.setEnabled(true);
        mostrarGrafoButton.setEnabled(true);
        comboBoxVerticeInicial.setEnabled(true);
        DFSButton.setEnabled(true);
        BFSButton.setEnabled(true);
        dijkstraButton.setEnabled(true);
    }

    public void reiniciarGrafo(){
        direccionadoCheckBox.setSelected(false);
        PonderacionCheckBox.setSelected(false);
        textFieldVertice.setText("");
        comboBoxAristaInicio.removeAllItems();
        comboBoxAristaFin.removeAllItems();
        comboBoxVerticeInicial.removeAllItems();
        textArea1.setText("");
    }
}
