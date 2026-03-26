import java.util.Arrays;
import java.util.Random;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;
    

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;        
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }


    public static void main(String[] args) {
        int tam = 20;
        Integer[] vetor = gerarVetorObjetos(tam);

        BubbleSort<Integer> bolha = new BubbleSort<>();

        Integer[] vetorOrdenadoBolha = bolha.ordenar(vetor);

        System.out.println("\nVetor ordenado método Bolha:");
        System.out.println("Comparações: " + bolha.getComparacoes());
        System.out.println("Movimentações: " + bolha.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + bolha.getTempoOrdenacao());

        InsertionSort<Integer> insercao = new InsertionSort<>();
        insercao.ordenar(vetor);
        System.out.println("\nVetor ordenado método Inserção:");
        System.out.println("Comparações: " + insercao.getComparacoes());
        System.out.println("Movimentações: " + insercao.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + insercao.getTempoOrdenacao());

        SelectionSort<Integer> selecao = new SelectionSort<>();
        selecao.ordenar(vetor);
        System.out.println("\nVetor ordenado método Seleção:");
        System.out.println("Comparações: " + selecao.getComparacoes());
        System.out.println("Movimentações: " + selecao.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + selecao.getTempoOrdenacao());

        System.out.println("\n--- Variando o tamanho do vetor para comparar os algoritmos ---");
        for (int tamanho : tamanhosTesteMedio) {
            System.out.println("\n[ Tamanho do vetor: " + tamanho + " ]");
            Integer[] vetorTeste = gerarVetorObjetos(tamanho);

            bolha.ordenar(vetorTeste);
            System.out.printf("Bolha    -> Comparações: %12d | Movimentações: %12d | Tempo: %8.2f ms\n", bolha.getComparacoes(), bolha.getMovimentacoes(), bolha.getTempoOrdenacao());
            insercao.ordenar(vetorTeste);
            System.out.printf("Inserção -> Comparações: %12d | Movimentações: %12d | Tempo: %8.2f ms\n", insercao.getComparacoes(), insercao.getMovimentacoes(), insercao.getTempoOrdenacao());
            selecao.ordenar(vetorTeste);
            System.out.printf("Seleção  -> Comparações: %12d | Movimentações: %12d | Tempo: %8.2f ms\n", selecao.getComparacoes(), selecao.getMovimentacoes(), selecao.getTempoOrdenacao());
        }
    }
}
