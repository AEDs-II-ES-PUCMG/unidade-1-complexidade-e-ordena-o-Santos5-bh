import java.util.Comparator;

/**
 * Comparador de produtos que ordena com base na porcentagem de desconto,
 * do maior para o menor. Produtos com maior desconto aparecem primeiro na lista ordenada.
 */
public class ComparadorPorDesconto implements Comparator<Produto> {

    /**
     * Compara dois produtos com base em sua porcentagem de desconto.
     * @return Um valor que indica se o desconto de p1 é maior, igual ou menor que o de p2.
     */
    @Override
    public int compare(Produto p1, Produto p2) {
        return Double.compare(p2.getPorcentagemDesconto(), p1.getPorcentagemDesconto());
    }
}