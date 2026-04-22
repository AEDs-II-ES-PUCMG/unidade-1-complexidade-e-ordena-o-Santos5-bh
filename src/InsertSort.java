import java.util.Arrays;
import java.util.Comparator;


public class InsertSort<T extends Comparable<T>> implements IOrdenador<T>{

    private long comparacoes;
    private long movimentacoes;
    private long tempoExecucao;

    public InsertSort() {
        // Construtor padrão
    }
	
	@Override
	public T[] ordenar(T[] dados) {
		return ordenar(dados, T::compareTo);
	}

	@Override
	public T[] ordenar(T[] dados, Comparator<T> comparador) {
		T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
		int tamanho = dadosOrdenados.length;
        this.comparacoes = 0;
        this.movimentacoes = 0;
        long start = System.nanoTime();

        for (int i = 1; i < tamanho; i++) {
            T chave = dadosOrdenados[i];
            movimentacoes++; // Leitura do valor do array para a 'chave'
            int j = i - 1;

            /* Move os elementos do array que são maiores que a chave
               para uma posição à frente de sua posição atual */
            while (j >= 0) {
                comparacoes++; // Contabiliza a comparação que será feita
                if (comparador.compare(dadosOrdenados[j], chave) > 0) {
                    dadosOrdenados[j + 1] = dadosOrdenados[j];
                    movimentacoes++; // Deslocamento do elemento
                    j--;
                } else {
                    break; // Encontrou a posição correta, interrompe o deslocamento
                }
            }
            dadosOrdenados[j + 1] = chave;
            movimentacoes++; // Escrita da 'chave' na sua posição correta
		}	
        this.tempoExecucao = System.nanoTime() - start;
		return dadosOrdenados;
	}
	
    @Override
	public long getComparacoes() {
		return comparacoes;
	}
	@Override
	public long getMovimentacoes() {
		return movimentacoes;
	}
	
	public double getTempoOrdenacao() {
	    return tempoExecucao / 1_000_000.0;
	}
}