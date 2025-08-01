package Model;

public enum Power {
    LOW(1),MEDIUM(2),HIGH(3);
    private final int amount;
    Power(int amount) {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }
}
