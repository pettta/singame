package sin.item;

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
