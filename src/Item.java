/**
* @Author: Joakim Olsson <lomo133>
* @Date:   2018-11-07T17:32:29+01:00
 * @Last modified by:   lomo133
 * @Last modified time: 2018-11-07T17:33:14+01:00
*/


/**
* This class is the item class of the game. All items have a description and a weight.
*/
public class Item {
    private String description;
    private double weight;

    /**
    * Constructor of the Item class.
    */
    public Item(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    /**
    * @return the description of an item.
    */
    public String getDescription() {
        return description;
    }
    /**
    * @return the weight of an item.
    */
    public double getWeight() {
        return weight;
    }
}
