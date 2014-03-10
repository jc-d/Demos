package Order.Data;

import Reflections.FieldData;
import Reflections.Generators.AverageSizedStringGenerator;
import Reflections.Generators.PositiveDoubleGenerator;

/**
 * Created by Jeremy Carey-dressler.
 * Date: 12/21/13
 */
public class Item {

    @FieldData(dataGenerators = PositiveDoubleGenerator.class)
    private double cost;
    @FieldData(dataGenerators = AverageSizedStringGenerator.class)
	private String name;
    @FieldData(dataGenerators = AverageSizedStringGenerator.class)
	private String description;
    @FieldData(dataGenerators = AverageSizedStringGenerator.class)
	private String manufacturer;
    @FieldData(dataGenerators = AverageSizedStringGenerator.class)
	private String vendorCode;
	private boolean ableToOrder;

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public boolean isAbleToOrder() {
		return ableToOrder;
	}

	public void setAbleToOrder(boolean ableToOrder) {
		this.ableToOrder = ableToOrder;
	}
}