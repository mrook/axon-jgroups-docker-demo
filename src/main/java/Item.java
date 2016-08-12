import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

class CreateItem {
    @TargetAggregateIdentifier
    private final String itemId;
    private final String name;

    public CreateItem(String itemId, String naam) {
        this.itemId = itemId;
        this.name = naam;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }
}

class ItemCreated {
    private final String itemId;
    private final String name;

    public ItemCreated(String itemId, String naam) {
        this.itemId = itemId;
        this.name = naam;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }
}

class Item {
    @AggregateIdentifier
    private String itemId;
    private String name;

    public Item() {

    }

    @CommandHandler
    public Item(CreateItem createItem) {
        apply(new ItemCreated(createItem.getItemId(), createItem.getName()));
    }

    @EventHandler
    public void itemCreated(ItemCreated itemCreated) {
        itemId = itemCreated.getItemId();
        name = itemCreated.getName();
    }
}
