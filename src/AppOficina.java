
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class AppOficina {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static Produto[] produtosPorId;
    static Produto[] produtosPorDescricao;
    static Produto[] produtosPorDesconto;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;

    // #region utilidades
    static Scanner teclado;

    

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("\n----------------------------------------");
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("========================================");
        System.out.println("Mercado branco do biel");
        System.out.println("========================================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Localizar pedidos Premium");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("6 - Top 10 Maiores Descontos");
        System.out.println("0 - Finalizar");
       
        Integer opcao = lerNumero("Digite sua opcao", Integer.class);
        return opcao != null ? opcao : -1;
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("5 - Heapsort");
        System.out.println("6 - Quicksort");
        System.out.println("0 - Finalizar");
       
        Integer opcao = lerNumero("Digite sua opção", Integer.class);
        return opcao != null ? opcao : -1;
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Padrão");
        System.out.println("2 - Por código");
        System.out.println("3 - Por desconto");
        
        Integer opcao = lerNumero("Digite sua opção", Integer.class);
        return opcao != null ? opcao : -1;
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;
    }


    static Produto localizarProduto() {
        cabecalho();
        System.out.println("Localizando um produto");
        Integer numero = lerNumero("Digite o identificador do produto", Integer.class);
        if (numero == null || produtosPorId == null) return null;
        
        // Busca binária utilizando o array previamente ordenado
        int inicio = 0;
        int fim = quantProdutos - 1;
        
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int idMeio = produtosPorId[meio].hashCode();
            
            if (idMeio == numero) {
                return produtosPorId[meio];
            } else if (idMeio < numero) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return null;
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n----------------------------------------\n%s\n----------------------------------------", produto);            
        }
        
        System.out.println(mensagem);
    }

    // #region pra que listar o valor acima e n abaixo???
    private static void localizarPedidosPremium(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        Double valor = lerNumero("valor", Double.class);
        if (valor == null || produtos == null) return;
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("\n----------------------------------------\n");
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() > valor)
            relatorio.append(produtos[i]+"\n");
        }
        relatorio.append("----------------------------------------");
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos(){
        cabecalho();
        if (produtos == null || quantProdutos == 0) {
            System.out.println("Nenhum produto para ordenar.");
            return;
        }
        
        int opcao = exibirMenuOrdenadores();
        if (opcao == 0) return;
        
        switch (opcao) {
            case 1 -> ordenador = new BubbleSort<>();
            case 2 -> ordenador = new InsertSort<>();
            case 3 -> ordenador = new SelectionSort<>();
            case 4 -> ordenador = new Mergesort<>();
            case 5 -> ordenador = new Heapsort<>();
            case 6 -> ordenador = new Quicksort<>();
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }
        
        int opcaoComp = exibirMenuComparadores();
        Comparator<Produto> comparador;
        switch (opcaoComp) {
            case 2:
                comparador = new ComparadorPorCodigo();
                break;
            case 3:
                comparador = new ComparadorPorDesconto();
                break;
            default: // case 1 e outros
                comparador = Comparator.naturalOrder();
                break;
        }
        
        System.out.println("\nOrdenando, por favor aguarde...");
        Produto[] ordenados = ordenador.ordenar(produtos, comparador);
        
        System.out.println("\nOrdenação concluída!");
        System.out.printf("Tempo: %.2f ms\n", ordenador.getTempoOrdenacao());
        System.out.println("Comparações: " + ordenador.getComparacoes());
        System.out.println("Movimentações: " + ordenador.getMovimentacoes() + "\n");
        
        verificarSubstituicao(ordenados);
    }

    static void embaralharProdutos(){
        if (produtos != null) {
            Collections.shuffle(Arrays.asList(produtos));
        }
    }

    static void verificarSubstituicao(Produto[] copiaDados){
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)? ");
        String resposta = teclado.nextLine().trim().toUpperCase();
        if(resposta.equals("S")) {
            produtos = Arrays.copyOf(copiaDados, copiaDados.length);
            System.out.println("Dados sobrescritos com sucesso!");
        }
    }

    static void listarProdutos(){
        cabecalho();
        System.out.println("Lista de Produtos:");
        System.out.println("----------------------------------------");
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
            
            // Pausa a cada 20 produtos exibidos
            if ((i + 1) % 20 == 0 && i != quantProdutos - 1) {
                System.out.print("\n[Pressione ENTER para continuar ou digite 'S' para sair da lista]: ");
                if (teclado.nextLine().trim().equalsIgnoreCase("S")) {
                    break;
                }
            }
        }
        System.out.println("----------------------------------------");
    }

    static void listarMaioresDescontos() {
        cabecalho();
        if (produtosPorDesconto == null || quantProdutos == 0) {
            System.out.println("Nenhum produto carregado.");
            return;
        }
        System.out.println("Top 10 Produtos com Maiores Descontos:");
        System.out.println("----------------------------------------");
        int limite = Math.min(10, quantProdutos);
        boolean encontrou = false;
        for (int i = 0; i < limite; i++) {
            if (produtosPorDesconto[i].getPorcentagemDesconto() > 0) {
                System.out.println((i + 1) + "º Lugar: " + produtosPorDesconto[i]);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum produto em oferta/vencendo no momento.");
        }
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);
        
        if (produtos != null) {
            produtosPorId = Arrays.copyOf(produtos, quantProdutos);
            produtosPorDescricao = Arrays.copyOf(produtos, quantProdutos);
            produtosPorDesconto = Arrays.copyOf(produtos, quantProdutos);
            
            Arrays.sort(produtosPorId, new ComparadorPorCodigo());
            Arrays.sort(produtosPorDescricao);
            Arrays.sort(produtosPorDesconto, new ComparadorPorDesconto());
        }
        
        embaralharProdutos();

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> localizarPedidosPremium();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 6 -> listarMaioresDescontos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        }while (opcao != 0);
        teclado.close();
    }                        
}
