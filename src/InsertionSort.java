import java.util.Arrays;

public class InsertionSort<T extends Comparable<T>> implements IOrdenador<T> {
    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0/1_000_000;

    @Override
    public int getComparacoes() {
        return comparacoes;
    }

    @Override
    public int getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    private void iniciar(){
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar(){
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    private void swap(int x, int y, T[] vetor) {
        T temp = vetor[x];
        vetor[x] = vetor[y];
        vetor[y] = temp;
        movimentacoes+=3;
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;
        iniciar();
        for (int i = 1; i < tamanho; i++) {
            T temp = dadosOrdenados[i];
            this.movimentacoes++; // Movimento: leitura do valor do array para 'temp'
            int j = i - 1;
            while (j >= 0) {
                this.comparacoes++; // Contabiliza a comparação a ser feita abaixo
                if (dadosOrdenados[j].compareTo(temp) > 0) {
                    dadosOrdenados[j+1] = dadosOrdenados[j];
                    j--;
                    this.movimentacoes++;            
                } else {
                    break; // Interrompe o while caso o elemento já esteja no lugar certo
                }
            }
            dadosOrdenados[j+1] = temp;
            this.movimentacoes++; // Movimento: escrita de 'temp' na sua posição correta no array
        }	
        terminar();
        return dadosOrdenados;
    }
}
