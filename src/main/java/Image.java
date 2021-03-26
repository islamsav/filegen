import org.apache.commons.lang3.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Image {
    private Dimension dimension = new Dimension(1920, 1080);
    private Color backgroundColor = Color.BLACK;
    private Path path = Paths.get("target");
    private String fileFormat = "jpeg";
    private String text = "";
    private Color textColor = Color.WHITE;
    private int textSize = 32;

    public Image(Dimension dimension, Color backgroundColor, String path, String text, Color textColor, int textSize) {
        this.dimension = dimension;
        this.backgroundColor = backgroundColor;
        this.path = Paths.get(path);
        this.text = text;
        this.textColor = textColor;
        this.textSize = textSize;
        this.build();
    }

    public Image(Dimension dimension, Color backgroundColor, String path) {
        this.dimension = dimension;
        this.backgroundColor = backgroundColor;
        this.path = Paths.get(path);
        this.build();
    }

    public Image() {
    }

    public Image size(Dimension dimension) {
        this.dimension = Optional
                .ofNullable(dimension)
                .orElseThrow(() -> new IllegalArgumentException("argument 'dimension' must not be null"));
        return this;
    }

    public Image size(int width, int height) {
        this.dimension = new Dimension(width, height);
        return this;
    }

    public Image color(Color color) {
        this.backgroundColor = Optional
                .ofNullable(color)
                .orElseThrow(() -> new IllegalArgumentException("argument 'color' must not be null"));
        return this;
    }

    public Image path(String path) {
        Optional
                .ofNullable(path)
                .orElseThrow(() -> new IllegalArgumentException("argument 'path' must not be null"));
        this.path = Paths.get(path);
        return this;
    }

    public Image format(String format) {
        Optional
                .ofNullable(format)
                .orElseThrow(() -> new IllegalArgumentException("argument 'format' must not be null"));
        this.fileFormat = format;
        return this;
    }

    public Image text(String text, Color color, int size) {
        this.text = text;
        this.textColor = color;
        this.textSize = size;
        return this;
    }

    public Path random(String fileName) {
        int[] width = {600, 800, 1000, 1200, 1400, 1920};
        int[] height = {600, 800, 1000, 1200, 1400, 1080};
        Color[] colors = {Color.RED, Color.WHITE, Color.BLACK, Color.GREEN, Color.DARK_GRAY};
        this.dimension = new Dimension(
                width[ThreadLocalRandom.current().nextInt(width.length - 1)],
                height[ThreadLocalRandom.current().nextInt(width.length - 1)]);
        this.backgroundColor = colors[ThreadLocalRandom.current().nextInt(colors.length - 1)];
        this.textColor = colors[ThreadLocalRandom.current().nextInt(colors.length - 1)];

        BufferedImage image = new BufferedImage(
                this.dimension.width,
                this.dimension.height,
                ColorSpace.TYPE_RGB
        );
        Graphics graphics = image.createGraphics();
        graphics.setColor(this.backgroundColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        this.text = RandomStringUtils.randomAlphabetic(10, 30);
        graphics.setColor(this.textColor);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
        graphics.drawString(this.text, (int) (this.dimension.width * 0.2), this.dimension.height / 2);

        this.fileFormat = "jpeg";
        this.path = Paths.get(fileName);
        createImage(image, this.fileFormat, this.path);
        this.createImage(image, this.fileFormat, this.path);
        return this.path;
    }

    public Path build() {
        BufferedImage image = new BufferedImage(
                this.dimension.width,
                this.dimension.height,
                ColorSpace.TYPE_RGB
        );

        Graphics graphics = image.createGraphics();
        graphics.setColor(this.backgroundColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        if (textSize != 0 || textColor != null) {
            graphics.setColor(this.textColor);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, this.textSize));
            graphics.drawString(this.text, (int) (this.dimension.width * 0.2), this.dimension.height / 2);
        }

        createImage(image, this.fileFormat, path);
        return this.path;
    }

    private void createImage(BufferedImage bufferedImage, String format, Path path) {
        try {
            ImageIO.write(bufferedImage, format, path.toFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}