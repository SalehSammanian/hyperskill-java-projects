package banking;

public class CreditCard {
    //change num from string to array?
    private final String cardNum;
    private final String accNum;
    private String pin;
    private int balance;

    public CreditCard(String cardNum, String pin) {
        this.cardNum = cardNum;
        this.accNum = cardNum.substring(6, 15);
        this.pin = pin;
        this.balance = 0;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccNum() {
        return accNum;
    }
}
