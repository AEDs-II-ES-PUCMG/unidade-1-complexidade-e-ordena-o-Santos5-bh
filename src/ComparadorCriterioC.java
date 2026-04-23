import java.util.Comparator;

/**
 * Critério C - Índice de Economia (decrescente).
 * O índice de economia é a diferença entre o valor de catálogo atual e o valor efetivamente pago.
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */


// #region fiz seguindo o enunciado do codigo 


public class ComparadorCriterioC implements Comparator<Pedido> {
    @Override
    public int compare(Pedido p1, Pedido p2) {
        
        int comp = Double.compare(calcularEconomia(p2), calcularEconomia(p1));
        if (comp == 0) {
            comp = Double.compare(ComparadorCriterioA.calcularValorFinal(p1), ComparadorCriterioA.calcularValorFinal(p2));
            if (comp == 0) {
                comp = Integer.compare(p1.getIdPedido(), p2.getIdPedido());
            }
        }
        return comp;
    }

    public static double calcularEconomia(Pedido p) {
        double economia = 0;
        if (p.getItens() != null) {
            for (ItemDePedido item : p.getItens()) {
                if (item != null && item.getProduto() != null) {
                    double precoAtualCatalogo = item.getProduto().valorDeVenda();
                    double precoPago = item.getPrecoCongelado();
                    economia += (precoAtualCatalogo - precoPago) * item.getQuantidade();
                }
            }
        }
        return economia;
    }
}

// #region fiz seguindo o enunciado do codigo 
