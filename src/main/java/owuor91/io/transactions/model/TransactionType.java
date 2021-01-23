package owuor91.io.transactions.model;

public enum TransactionType {
  TRANSFER(1), DEPOSIT(2), WITHDRAWAL(3), REVERSAL(4);

  private int value;

  TransactionType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public static String getNameByValue(int value){
    for (TransactionType t: TransactionType.values()){
      if (t.value==value){
        return t.name();
      }
    }
    return null;
  }
}
