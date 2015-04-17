package org.jboss.tools.examples.model;

public enum MediaType {

    IMAGE("Image", true);
    private final String description;
    private final boolean cacheable;
    private MediaType(String description, boolean cacheable) {
        this.description = description;
        this.cacheable = cacheable;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCacheable() {
        return cacheable;
    }

}
