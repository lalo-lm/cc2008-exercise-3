public class ImageNotFoundException extends Exception {
    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(String m, Throwable cause) {
        super(m, cause);
    }
}