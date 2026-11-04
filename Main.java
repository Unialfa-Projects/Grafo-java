/**
 * Grafo de exemplo (não direcionado, 5 vértices):
 *
 *     0 --- 1
 *     |     |
 *     2 --- 3 --- 4
 *      \_________/  (aresta 2-4)
 *
 * Arestas: 0-1, 0-2, 1-3, 2-3, 3-4, 2-4
 *
 * Para usar o grafo passado em aula, basta trocar VERTICES e ARESTAS.
 */
public class Main {
    static final int VERTICES = 5;
    static final int[][] ARESTAS = {
        {0, 1}, {0, 2}, {1, 3}, {2, 3}, {3, 4}, {2, 4}
    };

    public static void main(String[] args) {
        Grafo matriz = new GrafoMatrizAdjacencia(VERTICES, false);
        Grafo lista = new GrafoListaAdjacencia(VERTICES, false);

        for (int[] a : ARESTAS) {
            matriz.adicionarAresta(a[0], a[1]);
            lista.adicionarAresta(a[0], a[1]);
        }

        System.out.println("=== Matriz de Adjacência ===");
        matriz.imprimir();

        System.out.println();
        System.out.println("=== Lista de Adjacência ===");
        lista.imprimir();

        System.out.println();
        System.out.println("Vértices: " + matriz.numeroVertices() + " | Arestas: " + matriz.numeroArestas());
        for (int v = 0; v < VERTICES; v++) {
            System.out.println("Grau(" + v + ") = " + matriz.grau(v) + "  vizinhos: " + lista.vizinhos(v));
        }
    }
}
