import java.util.Arrays;
import java.util.Comparator;

public class SelectionSort<T extends Comparable<T>> implements IOrdenador<T>{
    private long comparacoes;
	private long movimentacoes;
	private long tempoExecucao;

	public SelectionSort() {
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

		for (int posReferencia = 0; posReferencia < tamanho ; posReferencia++) {
            int posMenor = posReferencia;
			for (int posicao = posReferencia+1; posicao < tamanho; posicao++) {
				comparacoes++;
				if (comparador.compare(dadosOrdenados[posMenor],dadosOrdenados[posicao]) > 0){
					posMenor = posicao;
				}
			}
            // A troca só é necessária se um elemento menor for encontrado
            if (posMenor != posReferencia) {
			    swap(posReferencia, posMenor, dadosOrdenados);
            }
		}	
        this.tempoExecucao = System.nanoTime() - start;
		return dadosOrdenados;
	}

	private void swap(int i, int j, T[] vet) {
		T temp = vet[i];
	    vet[i] = vet[j];
	    vet[j] = temp;
        // Uma troca envolve 3 movimentações de dados.
        movimentacoes += 3;
	}
	
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
}
