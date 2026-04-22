import java.util.Arrays;
import java.util.Comparator;

/**
 * Implementação do algoritmo de ordenação Quicksort.
 * Esta classe é polimórfica e implementa a interface IOrdenador para ser
 * compatível com o sistema de medição de desempenho.
 * @param <T> O tipo de dado a ser ordenado.
 */
public class Quicksort<T extends Comparable<T>> implements IOrdenador<T> {

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
        quickSort(dadosOrdenados, 0, dadosOrdenados.length - 1, comparador);
        this.tempoExecucao = System.nanoTime() - start;
        return dadosOrdenados;
    }

    private void quickSort(T[] arr, int begin, int end, Comparator<T> comparador) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, comparador);
            quickSort(arr, begin, partitionIndex - 1, comparador);
            quickSort(arr, partitionIndex + 1, end, comparador);
        }
    }

    private int partition(T[] arr, int begin, int end, Comparator<T> comparador) {
        T pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            comparacoes++;
            if (comparador.compare(arr[j], pivot) <= 0) {
                i++;
                swap(i, j, arr);
            }
        }
        swap(i + 1, end, arr);
        return i + 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] ordenar(T[] dados) {
        return ordenar(dados, (a, b) -> ((Comparable<T>) a).compareTo(b));
    }
}