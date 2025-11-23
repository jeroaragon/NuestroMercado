
//salta esto cuando quiero comprar stock del que hay disponible
public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String message) {
        super(message);
    }
}
