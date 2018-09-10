package org.demo;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

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
