import java.util.ArrayList;
import java.util.List;

/**
 * Representação por matriz de adjacência.
 * matriz[i][j] = 1 indica aresta de i para j.
 * Espaço O(V²); verificação de aresta O(1); listar vizinhos O(V).
 */
public class GrafoMatrizAdjacencia implements Grafo {
    private final int[][] matriz;
    private final int vertices;
    private final boolean direcionado;
    private int arestas;

    public GrafoMatrizAdjacencia(int vertices, boolean direcionado) {
        if (vertices <= 0) {
            throw new IllegalArgumentException("O grafo precisa ter pelo menos um vértice.");
        }
        this.vertices = vertices;
        this.direcionado = direcionado;
        this.matriz = new int[vertices][vertices];
    }

    private void validar(int v) {
        if (v < 0 || v >= vertices) {
            throw new IndexOutOfBoundsException("Vértice inválido: " + v);
        }
    }

    @Override
    public void adicionarAresta(int origem, int destino) {
        validar(origem);
        validar(destino);
        if (matriz[origem][destino] == 1) {
            return; // aresta já existe
        }
        matriz[origem][destino] = 1;
        if (!direcionado) {
            matriz[destino][origem] = 1;
        }
        arestas++;
    }

    @Override
    public void removerAresta(int origem, int destino) {
        validar(origem);
        validar(destino);
        if (matriz[origem][destino] == 0) {
            return;
        }
        matriz[origem][destino] = 0;
        if (!direcionado) {
            matriz[destino][origem] = 0;
        }
        arestas--;
    }

    @Override
    public boolean existeAresta(int origem, int destino) {
        validar(origem);
        validar(destino);
        return matriz[origem][destino] == 1;
    }

    @Override
    public List<Integer> vizinhos(int vertice) {
        validar(vertice);
        List<Integer> lista = new ArrayList<>();
        for (int j = 0; j < vertices; j++) {
            if (matriz[vertice][j] == 1) {
                lista.add(j);
            }
        }
        return lista;
    }

    @Override
    public int grau(int vertice) {
        return vizinhos(vertice).size();
    }

    @Override
    public int numeroVertices() {
        return vertices;
    }

    @Override
    public int numeroArestas() {
        return arestas;
    }

    @Override
    public void imprimir() {
        System.out.print("    ");
        for (int j = 0; j < vertices; j++) {
            System.out.printf("%3d", j);
        }
        System.out.println();
        for (int i = 0; i < vertices; i++) {
            System.out.printf("%3d ", i);
            for (int j = 0; j < vertices; j++) {
                System.out.printf("%3d", matriz[i][j]);
            }
            System.out.println();
        }
    }
}
