package color;

/**
 * color.Color class, it is using for drawing map as png
 */
public enum Color {
    WHITE, RED, BLACK, BLUE;

    /**
     * getter for color.Color
     * @return returns RGB color as int
     */
    public int getColor()
    {
        switch (this)
        {
            case WHITE:
                return 16777215;
            case RED:
                return 16711680;
            case BLACK:
                return 2171934;
            case BLUE:
                return 5806296;
            default:
                return -1;
        }
    }
}
