import java.util.List;
import java.util.function.BiFunction;

/**
 * Testes sem dependência externa (sem JUnit).
 * Rodam a mesma bateria nas duas representações e depois comparam uma com a outra.
 */
public class TesteGrafos {
    private static int passou = 0;
    private static int falhou = 0;

    private static void verificar(boolean condicao, String descricao) {
        if (condicao) {
            passou++;
            System.out.println("  [OK]    " + descricao);
        } else {
            falhou++;
            System.out.println("  [FALHA] " + descricao);
        }
    }

    private static void bateria(String nome, BiFunction<Integer, Boolean, Grafo> fabrica) {
        System.out.println("\n### " + nome);

        // Grafo não direcionado do exemplo
        Grafo g = fabrica.apply(5, false);
        int[][] arestas = {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {3, 4}, {2, 4}};
        for (int[] a : arestas) g.adicionarAresta(a[0], a[1]);

        verificar(g.numeroVertices() == 5, "5 vértices");
        verificar(g.numeroArestas() == 6, "6 arestas");
        verificar(g.existeAresta(0, 1) && g.existeAresta(1, 0), "aresta 0-1 nos dois sentidos (não direcionado)");
        verificar(!g.existeAresta(0, 4), "não existe aresta 0-4");
        verificar(g.vizinhos(2).equals(List.of(0, 3, 4)), "vizinhos de 2 = [0, 3, 4]");
        verificar(g.grau(3) == 3, "grau de 3 = 3");
        verificar(g.grau(4) == 2, "grau de 4 = 2");

        g.adicionarAresta(0, 1);
        verificar(g.numeroArestas() == 6, "aresta duplicada não é contada");

        g.removerAresta(2, 4);
        verificar(!g.existeAresta(2, 4) && !g.existeAresta(4, 2), "remoção de 2-4 nos dois sentidos");
        verificar(g.numeroArestas() == 5, "5 arestas após remoção");
        g.removerAresta(2, 4);
        verificar(g.numeroArestas() == 5, "remover aresta inexistente não altera contagem");

        int somaGraus = 0;
        for (int v = 0; v < g.numeroVertices(); v++) somaGraus += g.grau(v);
        verificar(somaGraus == 2 * g.numeroArestas(), "soma dos graus = 2 * arestas (lema do aperto de mãos)");

        // Grafo direcionado
        Grafo d = fabrica.apply(3, true);
        d.adicionarAresta(0, 1);
        d.adicionarAresta(1, 2);
        verificar(d.existeAresta(0, 1) && !d.existeAresta(1, 0), "direcionado: 0->1 existe, 1->0 não");
        verificar(d.vizinhos(2).isEmpty(), "direcionado: vértice 2 sem sucessores");

        // Validação de entrada
        boolean lancou = false;
        try {
            g.adicionarAresta(0, 99);
        } catch (IndexOutOfBoundsException e) {
            lancou = true;
        }
        verificar(lancou, "vértice inválido lança exceção");
    }

    public static void main(String[] args) {
        bateria("Matriz de Adjacência", GrafoMatrizAdjacencia::new);
        bateria("Lista de Adjacência", GrafoListaAdjacencia::new);

        System.out.println("\n### Equivalência entre as representações");
        Grafo m = new GrafoMatrizAdjacencia(5, false);
        Grafo l = new GrafoListaAdjacencia(5, false);
        int[][] arestas = {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {3, 4}, {2, 4}};
        for (int[] a : arestas) {
            m.adicionarAresta(a[0], a[1]);
            l.adicionarAresta(a[0], a[1]);
        }
        boolean iguais = m.numeroArestas() == l.numeroArestas();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (m.existeAresta(i, j) != l.existeAresta(i, j)) iguais = false;
            }
            if (!m.vizinhos(i).equals(l.vizinhos(i))) iguais = false;
        }
        verificar(iguais, "matriz e lista representam exatamente o mesmo grafo");

        System.out.printf("%nResultado: %d passaram, %d falharam%n", passou, falhou);
        if (falhou > 0) System.exit(1);
    }
}
