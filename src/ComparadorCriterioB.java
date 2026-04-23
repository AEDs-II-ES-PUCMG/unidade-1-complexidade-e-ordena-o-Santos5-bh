import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */

// #region fiz seguindo o enunciado do codigo 

public class ComparadorCriterioB implements Comparator<Pedido> {
    @Override
    public int compare(Pedido p1, Pedido p2) {
        int comp = Integer.compare(ComparadorCriterioA.calcularVolumeTotal(p1), ComparadorCriterioA.calcularVolumeTotal(p2));
        if (comp == 0) {
            if (p1.getDataPedido() != null && p2.getDataPedido() != null) {
                comp = p1.getDataPedido().compareTo(p2.getDataPedido());
            }
            if (comp == 0) {
                comp = Integer.compare(p1.getIdPedido(), p2.getIdPedido());
            }
        }
        return comp;
    }
}

// #region fiz seguindo o enunciado do codigo 