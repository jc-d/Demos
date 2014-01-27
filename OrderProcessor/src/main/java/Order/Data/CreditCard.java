package Order.Data;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class CreditCard {
	private String cardNumber;
	private int ccv;
	private String name;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCcv() {
		return ccv;
	}

	public void setCcv(int ccv) {
		this.ccv = ccv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
