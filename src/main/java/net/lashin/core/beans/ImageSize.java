package net.lashin.core.beans;


public enum ImageSize {
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
}
