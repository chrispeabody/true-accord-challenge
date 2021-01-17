package data;

/**
 * This class is meant purely for storing data related to a debt.
 * id       - The identifier for this debt.
 * amount   - The total amount owed in USD.
 */
public class Debt {
    private final int id;
    private final double amount;

    public Debt(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
}