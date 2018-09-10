package org.demo;

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
