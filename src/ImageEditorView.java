import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageEditorView extends JFrame {
    JPanel mainPanel = new JPanel();
    JButton loadImageButton = new JButton("Load Image");
    JFileChooser inputImageChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
    ImagePanel imagePanel;
    JButton negativeFilterButton = new JButton("Negative");
    JButton grayscaleFilterButton = new JButton("Grayscale");
    JButton oneChannelRFilterButton = new JButton("One Channel (Red)");
    JButton oneChannelGFilterButton = new JButton("One Channel (Green)");
    JButton oneChannelBFilterButton = new JButton("One Channel (Blue)");
    JButton brightnessFilterButton = new JButton("Brightness");
    JButton blackAndWhiteFilterButton = new JButton("Black and White");
    JButton mirroredHorizontalFilterButton = new JButton("Mirrored Horizontal");
    JButton rotate90FilterButton = new JButton("Rotate 90 Degrees");
    JButton blurFilterButton = new JButton("Blur");
    JButton undoButton = new JButton("Undo");

    public ImageEditorView() {
        // We are extending the JFrame class, so we MUST call the parent constructor.
        super("Editor UVG");

        // orientation of main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // methods on the parent JFrame class
        setSize(800, 600);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputImageChooser.setFileFilter(filter);

        mainPanel.add(loadImageButton);

        // add the main panel and make the window visible
        mainPanel.add(negativeFilterButton);
        mainPanel.add(grayscaleFilterButton);
        mainPanel.add(oneChannelRFilterButton);
        mainPanel.add(oneChannelGFilterButton);
        mainPanel.add(oneChannelBFilterButton);
        mainPanel.add(brightnessFilterButton);
        mainPanel.add(blackAndWhiteFilterButton);
        mainPanel.add(mirroredHorizontalFilterButton);
        mainPanel.add(rotate90FilterButton);
        mainPanel.add(blurFilterButton);
        mainPanel.add(undoButton);

        add(mainPanel);
    }

    // ################## A section to register action listeners ################
    public void addLoadImageListener(ActionListener listener) {
        loadImageButton.addActionListener(listener);
    }

    public void addNegativeListener(ActionListener listener) {
        negativeFilterButton.addActionListener(listener);
    }

    public void addGrayscaleListener(ActionListener listener) {
        grayscaleFilterButton.addActionListener(listener);
    }

    public void addOneChannelRListener(ActionListener listener) {
        oneChannelRFilterButton.addActionListener(listener);
    }

    public void addOneChannelGListener(ActionListener listener) {
        oneChannelGFilterButton.addActionListener(listener);
    }

    public void addOneChannelBListener(ActionListener listener) {
        oneChannelBFilterButton.addActionListener(listener);
    }

    public void addBrightnessListener(ActionListener listener) {
        brightnessFilterButton.addActionListener(listener);
    }

    public Integer showBrightnessDialog() {
        String value = JOptionPane.showInputDialog(this, "Desde -255 a 255:", "Brightness", JOptionPane.QUESTION_MESSAGE);

        if (value == null) {
            return null;
        }

        try {
            int amount = Integer.parseInt(value.trim());
            if (amount < -255 || amount > 255) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor entre -255 y 255.");
                return null;
            }
            return amount;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor Inválido.");
            return null;
        }
    }

    public void addBlackAndWhiteListener(ActionListener listener) {
        blackAndWhiteFilterButton.addActionListener(listener);
    }

    public Integer showBlackAndWhiteDialog() {
        String value = JOptionPane.showInputDialog(this, "Desde 0 a 255:", "Black and White", JOptionPane.QUESTION_MESSAGE);

        if (value == null) {
            return null;
        }

        try {
            int limit = Integer.parseInt(value.trim());
            if (limit < 0 || limit > 255) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor entre 0 y 255.");
                return null;
            }
            return limit;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor Inválido.");
            return null;
        }
    }

    public void addMirroredHorizontalListener(ActionListener listener) {
        mirroredHorizontalFilterButton.addActionListener(listener);
    }

    public void addRotate90Listener(ActionListener listener) {
        rotate90FilterButton.addActionListener(listener);
    }

    public void addBlurListener(ActionListener listener) {
        blurFilterButton.addActionListener(listener);
    }

    public Integer showBlurDialog() {
        String value = JOptionPane.showInputDialog(this, "Radio del desenfoque (0 a 10):", "Blur", JOptionPane.QUESTION_MESSAGE);

        if (value == null) {
            return null;
        }

        try {
            int radius = Integer.parseInt(value.trim());
            if (radius < 0 || radius > 10) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor entre 0 y 10.");
                return null;
            }
            return radius;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor Inválido.");
            return null;
        }
    }

    public void showInfoDialog(String msg) {
        JOptionPane.showMessageDialog(
            this,
            msg,
            "Info",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void addUndoListener(ActionListener listener) {
        undoButton.addActionListener(listener);
    }

    public void addInputImageChooserListener(ActionListener listener) {
        inputImageChooser.addActionListener(listener);
    }

    // ############### A section to trigger actions in the GUI ##################
    public File showInputImageChooser() {
        int returnVal = inputImageChooser.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        return inputImageChooser.getSelectedFile();
    }

    public void showInputImage(BufferedImage image) {
        if (imagePanel != null) {
            mainPanel.remove(imagePanel);
        }

        imagePanel = new ImagePanel(image);
        imagePanel.setPreferredSize(new Dimension(600, 400));
        mainPanel.add(imagePanel);
        pack();
    }
}
