import java.io.File;

public class ImageEditorController {
    private ImageEditorView view;
    private ImageEditorModel model;

    public ImageEditorController(ImageEditorModel model, ImageEditorView view) {
        this.view = view;
        this.model = model;

        // hookup action listeners
        this.view.addLoadImageListener(e -> handleLoadImage());
        this.view.addSaveImageListener(e -> handleSaveImage());
        this.view.addResetImageListener(e -> handleReset());
        this.view.addNegativeListener(e -> handleNegativeFilter());
        this.view.addGrayscaleListener(e -> handleGrayscaleFilter());
        this.view.addOneChannelRListener(e -> handleOneChannelRFilter());
        this.view.addOneChannelGListener(e -> handleOneChannelGFilter());
        this.view.addOneChannelBListener(e -> handleOneChannelBFilter());
        this.view.addBrightnessListener(e -> handleBrightnessFilter());
        this.view.addBlackAndWhiteListener(e -> handleBlackAndWhiteFilter());
        this.view.addMirroredHorizontalListener(e -> handleMirroredHorizontalFilter());
        this.view.addRotate90Listener(e -> handleRotate90Filter());
        this.view.addBlurListener(e -> handleBlurFilter());
        this.view.addUndoListener(e -> handleUndo());
    }

    public void handleLoadImage() {
        File selectedFile = view.showInputImageChooser();
        if (selectedFile == null) {
            return;
        }

        try {
            // mutate the application state
            model.setInputFileName(selectedFile.getAbsolutePath());
            model.setInputImage(ImageUtils.load(selectedFile.getAbsolutePath()));
        } catch (Exception e) {
            // view.showErrorDialog("couldn't load image: " + e.getMessage());
        }

        // we updated the state of the model, we must re-draw the view layer
        refresh();
    }

    private void handleSaveImage() {
        try {
            Image current = model.getCurrentImage();
            if (current == null) {
                view.showErrorDialog("No hay ninguna imagen cargada para guardar.");
                return;
            }

            File file = view.showSaveImageChooser();
            if (file == null) return;

            ImageUtils.save(current, file.getAbsolutePath());
            view.showInfoDialog("Imagen guardada exitosamente en:\n" + file.getAbsolutePath());
        } catch (Exception e) {
            view.showErrorDialog("Error al guardar la imagen: " + e.getMessage());
        }
    }

    private void handleReset() {
        try {
            model.reset();
            refresh();
        } catch (ImageNotFoundException e) {
            view.showErrorDialog(e.getMessage());
        }
    }

    private void handleUndo() {
        try {
            model.undo();
            refresh();
        } catch (EmptyHistoryException e) {
            view.showErrorDialog(e.getMessage());
        }
    }

    public void handleNegativeFilter() {
        try {
            Image negative = this.model.negativeFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(negative));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        } 
        // catch (Exception e) {
        //     // Mostrar el error al usuario
        //     System.out.println("Unknown errror.");
        // }
    }

    public void handleGrayscaleFilter() {
        try {
            Image grayscale = this.model.grayscaleFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(grayscale));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        } 
    }

    public void handleOneChannelRFilter() {
        try {
            Image oneChannelRed = this.model.oneChannelRFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(oneChannelRed));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        }
    }

    public void handleOneChannelGFilter() {
        try {
            Image oneChannelGreen = this.model.oneChannelGFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(oneChannelGreen));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        }
    }

    public void handleOneChannelBFilter() {
        try {
            Image oneChannelBlue = this.model.oneChannelBFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(oneChannelBlue));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        }
    }

    public void handleBrightnessFilter() {
        try {
            Integer amount = this.view.showBrightnessDialog();
            if (amount == null) {
                return;
            }

            Image brightness = this.model.brightnessFilter(amount);
            this.view.showInputImage(ImageUtils.toBufferedImage(brightness));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        }
    }

    public void handleBlackAndWhiteFilter() {
        try {
            Integer limit = this.view.showBlackAndWhiteDialog();
            if (limit == null) {
                return;
            }

            Image blackAndWhite = this.model.blackAndWhiteFilter(limit);
            this.view.showInputImage(ImageUtils.toBufferedImage(blackAndWhite));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        }
    }

    public void handleMirroredHorizontalFilter() {
        try {
            Image mirroredHorizontal = this.model.mirroredHorizontalFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(mirroredHorizontal));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        }
    }

    public void handleRotate90Filter() {
        try {
            Image rotate90 = this.model.rotate90Filter();
            this.view.showInputImage(ImageUtils.toBufferedImage(rotate90));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        }
    }

    public void handleBlurFilter() {
        try {
            Integer radius = this.view.showBlurDialog();
            if (radius == null) {
                return;
            }

            Image blur = this.model.blurFilter(radius);
            this.view.showInputImage(ImageUtils.toBufferedImage(blur));
        } catch (Exception ImageNotFoundException) {
            this.view.showInfoDialog(ImageNotFoundException.getMessage());
        } 
    }


    // call the view to re-draw the application state
    private void refresh() {
        view.showOriginalImage(ImageUtils.toBufferedImage(model.getInputImage()));
        view.showCurrentImage(ImageUtils.toBufferedImage(model.getCurrentImage()));
        view.updateHistory(model.getFilterNames(), model.getHistorySize());
    }
}
