import java.util.List;

/**
 * Contrato comum para as duas representações de grafo.
 * Vértices são identificados por índices de 0 a n-1.
 */
public interface Grafo {
    void adicionarAresta(int origem, int destino);

    void removerAresta(int origem, int destino);

    boolean existeAresta(int origem, int destino);

    List<Integer> vizinhos(int vertice);

    int grau(int vertice);

    int numeroVertices();

    int numeroArestas();

    void imprimir();
}
