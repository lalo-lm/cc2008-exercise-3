import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageEditorModel {
    private String inputFileName;
    // private Image inputImage;
    private ImageEditor editor;
    private List<Image> history;
    private List<String> filterNames;

    public ImageEditorModel() {
        this.history = new ArrayList<>();
        this.filterNames = new ArrayList<>();
    }

    public String getInputFileName() {
        return this.inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public Image getInputImage() {
        if (this.history.isEmpty()) {
            return null;
        }
        return this.history.getFirst();
    }

    public Image getCurrentImage() {
        if (this.history.isEmpty()) {
            return null;
        }
        return this.history.getLast();
    }

    public void setInputImage(Image inputImage) {
        this.history.clear();
        this.filterNames.clear();
        history.add(inputImage);
        this.editor = new ImageEditor(inputImage);
    }

    public int getHistorySize() {
        return this.history.size();
    }

    public List<String> getFilterNames() {
        return Collections.unmodifiableList(this.filterNames);
    }

    public void reset() throws ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No hay una imagen cargada para reiniciar.");
        }
        Image original = this.history.getFirst();
        this.history.clear();
        this.filterNames.clear();
        this.history.add(original);
        this.editor = new ImageEditor(original);
    }

    private void checkImageLoaded() throws ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha cargado ninguna imagen. Por favor cargue una imagen primero.");
        }
    }

    public Image negativeFilter() throws ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image negative = this.editor.negative();
        history.add(negative);

        return history.getLast();
    }

    public Image grayscaleFilter() throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image grayscale = this.editor.grayscale();
        history.add(grayscale);

        return history.getLast();
    }

    public Image oneChannelRFilter() throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image oneChannelR = this.editor.keepOnlyChannel(0);
        history.add(oneChannelR);

        return history.getLast();
    }

    public Image oneChannelGFilter() throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image oneChannelG = this.editor.keepOnlyChannel(1);
        history.add(oneChannelG);

        return history.getLast();
    }

    public Image oneChannelBFilter() throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image oneChannelB = this.editor.keepOnlyChannel(2);
        history.add(oneChannelB);

        return history.getLast();
    }

    public Image brightnessFilter(int amount) throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image brightness = this.editor.brightness(amount);
        history.add(brightness);

        return history.getLast();
    }

    public Image blackAndWhiteFilter(int limit) throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image blackAndWhite = this.editor.blackAndWhite(limit);
        history.add(blackAndWhite);

        return history.getLast();
    }

    public Image mirroredHorizontalFilter() throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image mirroredHorizontal = this.editor.mirrorHorizontal();
        history.add(mirroredHorizontal);

        return history.getLast();
    }

    public Image rotate90Filter() throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }
        
        Image rotate90 = this.editor.rotate90();
        history.add(rotate90);

        return history.getLast();
    }

    public Image blurFilter(int radius) throws  ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("No se ha encontrado una imagen, por favor subir primero la imagen.");
        }

        Image blur = this.editor.blur(radius);
        history.add(blur);

        return history.getLast();
    }

    public Image undo() throws EmptyHistoryException {
        if (this.history.size() <= 1) {
            throw new EmptyHistoryException("El historial de filtros se encuentra vacío. No se puede deshacer.");
        }

        this.history.removeLast();
        if (!this.filterNames.isEmpty()) {
            this.filterNames.removeLast();
        }

        Image lastImage = getCurrentImage();
        this.editor = new ImageEditor(lastImage);
        return lastImage;
    }
}
