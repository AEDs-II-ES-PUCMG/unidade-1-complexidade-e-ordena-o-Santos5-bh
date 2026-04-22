import java.util.Arrays;
import java.util.Comparator;

/**
 * Implementação do algoritmo de ordenação Heapsort.
 * Esta classe é polimórfica e implementa a interface IOrdenador para ser
 * compatível com o sistema de medição de desempenho.
 * @param <T> O tipo de dado a ser ordenado.
 */
public class Heapsort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private long tempoExecucao;

    @Override
    public long getComparacoes() {
        return comparacoes;
    }

    @Override
    public long getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoExecucao / 1_000_000.0;
    }

    private void swap(int x, int y, T[] vetor) {
        T temp = vetor[x];
        vetor[x] = vetor[y];
        vetor[y] = temp;
        // Uma troca envolve 3 movimentações de dados.
        movimentacoes += 3;
    }

    @Override
    public T[] ordenar(T[] dados, Comparator<T> comparador) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        this.comparacoes = 0;
        this.movimentacoes = 0;
        long start = System.nanoTime();
        int n = dadosOrdenados.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(dadosOrdenados, n, i, comparador);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(0, i, dadosOrdenados);
            heapify(dadosOrdenados, i, 0, comparador);
        }
        this.tempoExecucao = System.nanoTime() - start;
        return dadosOrdenados;
    }

    void heapify(T[] arr, int n, int i, Comparator<T> comparador) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n) {
            comparacoes++;
            if (comparador.compare(arr[l], arr[largest]) > 0) {
                largest = l;
            }
        }

        if (r < n) {
            comparacoes++;
            if (comparador.compare(arr[r], arr[largest]) > 0) {
                largest = r;
            }
        }

        if (largest != i) {
            swap(i, largest, arr);
            heapify(arr, n, largest, comparador);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] ordenar(T[] dados) {
        return ordenar(dados, (a, b) -> ((Comparable<T>) a).compareTo(b));
    }
}