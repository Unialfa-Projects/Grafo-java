import java.util.ArrayList;
import java.util.List;

/**
 * Representação por lista de adjacência.
 * Cada vértice guarda a lista dos vértices com os quais tem aresta.
 * Espaço O(V + E); verificação de aresta O(grau); listar vizinhos O(grau).
 */
public class GrafoListaAdjacencia implements Grafo {
    private final List<List<Integer>> adjacencias;
    private final int vertices;
    private final boolean direcionado;
    private int arestas;

    public GrafoListaAdjacencia(int vertices, boolean direcionado) {
        if (vertices <= 0) {
            throw new IllegalArgumentException("O grafo precisa ter pelo menos um vértice.");
        }
        this.vertices = vertices;
        this.direcionado = direcionado;
        this.adjacencias = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencias.add(new ArrayList<>());
        }
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
        if (adjacencias.get(origem).contains(destino)) {
            return; // aresta já existe
        }
        adjacencias.get(origem).add(destino);
        if (!direcionado && origem != destino) {
            adjacencias.get(destino).add(origem);
        }
        arestas++;
    }

    @Override
    public void removerAresta(int origem, int destino) {
        validar(origem);
        validar(destino);
        boolean removeu = adjacencias.get(origem).remove(Integer.valueOf(destino));
        if (!removeu) {
            return;
        }
        if (!direcionado && origem != destino) {
            adjacencias.get(destino).remove(Integer.valueOf(origem));
        }
        arestas--;
    }

    @Override
    public boolean existeAresta(int origem, int destino) {
        validar(origem);
        validar(destino);
        return adjacencias.get(origem).contains(destino);
    }

    @Override
    public List<Integer> vizinhos(int vertice) {
        validar(vertice);
        List<Integer> copia = new ArrayList<>(adjacencias.get(vertice));
        copia.sort(null);
        return copia;
    }

    @Override
    public int grau(int vertice) {
        validar(vertice);
        return adjacencias.get(vertice).size();
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
        for (int i = 0; i < vertices; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i).append(" -> ");
            List<Integer> lista = adjacencias.get(i);
            for (int k = 0; k < lista.size(); k++) {
                sb.append(lista.get(k));
                if (k < lista.size() - 1) {
                    sb.append(" -> ");
                }
            }
            System.out.println(sb);
        }
    }
}
