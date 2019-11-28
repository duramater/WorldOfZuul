import java.util.HashMap;

public class Objects {
    private String itemName;
    private double itemWeight;
    private Room currentRoom;

    public Objects(String name, double weight) {
        itemName = name;
        itemWeight = weight;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemWeight(double itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public String getLongString() {
        return itemName + ", " + itemWeight;
    }
}
