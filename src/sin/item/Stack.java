package sin.item;


/**
 * Name: Stack.java
 * Purpose: Just an amount of items and an item type together. Stored in slots.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class Stack {

    public Item item;
    public int count;

    public Stack(Item item, int count) {
        this.item = item;
        this.count = count;

    }

    public Stack(Item item) {
        this(item, 1);
    }

}
