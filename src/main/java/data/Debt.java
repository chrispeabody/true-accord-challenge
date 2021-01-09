package data;

/**
 * This class is meant purely for storing data related to a debt.
 * id       - The identifier for this debt.
 * amount   - The total amount owed in USD.
 */
public class Debt {
    private Integer id;
    private Double amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}