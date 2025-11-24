
//salta esto cuando quiero comprar stock del que no hay disponible
public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String message) {
        super(message);
    }
}
