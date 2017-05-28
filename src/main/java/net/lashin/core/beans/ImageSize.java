package net.lashin.core.beans;


public enum ImageSize {
    DEFAULT(0),
    XSMALL(100),
    SMALL(300),
    MEDIUM(500),
    LARGE(700),
    XLARGE(900);

    private int size;

    ImageSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (this != DEFAULT)
            return;
        this.size = size;
    }
}
