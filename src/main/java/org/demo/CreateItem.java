package org.demo;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

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
