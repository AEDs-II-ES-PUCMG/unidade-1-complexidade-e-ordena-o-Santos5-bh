import java.util.Comparator;

public class ComparadorCriterioA implements Comparator<Pedido> {
    @Override
    public int compare(Pedido p1, Pedido p2) {
        int comp = Double.compare(calcularValorFinal(p1), calcularValorFinal(p2));
        if (comp == 0) {
            comp = Integer.compare(calcularVolumeTotal(p1), calcularVolumeTotal(p2));
            if (comp == 0) {
                comp = Integer.compare(obterIdPrimeiroItem(p1), obterIdPrimeiroItem(p2));
            }
        }
        return comp;
    }

    public static double calcularValorFinal(Pedido p) {
        double total = 0;
        if (p.getItens() != null) {
            for (ItemDePedido item : p.getItens()) {
                if (item != null) total += item.getQuantidade() * item.getPrecoCongelado();
            }
        }
        return total;
    }

    public static int calcularVolumeTotal(Pedido p) {
        int vol = 0;
        if (p.getItens() != null) {
            for (ItemDePedido item : p.getItens()) {
                if (item != null) vol += item.getQuantidade();
            }
        }
        return vol;
    }

    public static int obterIdPrimeiroItem(Pedido p) {
        if (p.getItens() != null && p.getItens().length > 0 && p.getItens()[0] != null) {
            return p.getItens()[0].getProduto().hashCode();
        }
        return 0;
    }
}