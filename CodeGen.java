package CodeGenerator;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.EnumMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@ViewScoped
public class CodeGen implements Serializable {

    private Part part;
    String url;
    Image jsf_imaged;

    String string_to_code = "Purity Kawira Murimi";

    public void CodeQr() {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String file_path = "/Users/DeadMeat/Documents/BarCodeQR.png";
        int size = 250;
        String file_type = "png";
        File myFile = new File(file_path);

        try {
            Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hintMap.put(EncodeHintType.MARGIN, 1);
            hintMap.putIfAbsent(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix byteMatrix = qrCodeWriter.encode(string_to_code, BarcodeFormat.QR_CODE, size, size, hintMap);
            int SetWidth = byteMatrix.getWidth();

            BufferedImage img = new BufferedImage(SetWidth, SetWidth, BufferedImage.TYPE_INT_RGB);
            img.createGraphics();
            Graphics2D graphics = (Graphics2D) img.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, SetWidth, SetWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < SetWidth; i++) {
                for (int j = 0; j < SetWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(img, file_type, myFile);
        } catch (WriterException | IOException e) {
        }
        setUrl(file_path);
        System.out.println("\n\nYou have successfully created QR code.");
    }

    public void BarCode() {
        String file_path = "/Users/DeadMeat/Documents/BarCode128.png";
        int width = 500;
        int height = 70;
        String file_type = "png";
        File myFile = new File(file_path);

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Font font = new Font("Times", Font.PLAIN, 11);
        Graphics2D graphics = (Graphics2D) img.getGraphics();
        graphics.setFont(font);
        FontMetrics fm = graphics.getFontMetrics();
        int textWidth = fm.stringWidth(string_to_code);
        int textHeight = fm.getHeight();
        graphics.dispose();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = img.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        graphics.setFont(font);
        fm = graphics.getFontMetrics();
        graphics.setColor(Color.BLACK);
        graphics.drawString(string_to_code, Math.round((width - textWidth) / 2) - 2, height - fm.getAscent());
        graphics.dispose();

        try {
            Code128Writer code_128Writer = new Code128Writer();
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix bitMatrix = code_128Writer.encode(string_to_code, BarcodeFormat.CODE_128, width, height - textHeight - (2 * fm.getAscent()), hintMap);

            int matrixWidth = bitMatrix.getWidth();
            int matrixHeight = bitMatrix.getHeight();

            Graphics2D graphic_final = (Graphics2D) img.getGraphics();
            img.getGraphics();
            graphic_final.setColor(Color.BLACK);

            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixHeight; j++) {
                    if (bitMatrix.get(i, j)) {
                        graphic_final.fillRect(i, j + fm.getAscent(), 1, 1);
                    }
                }
            }
            ImageIO.write(img, file_type, myFile);
            setJsf_imaged(img);
            setUrl(file_path);
            System.out.println("\n\n\n\nYou have successfully created BarCode code.");
        } catch (WriterException | IOException e) {
        }
    }

    public String upload() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("images");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\resources\\images\\";
        return "success";
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getString_to_code() {
        return string_to_code;
    }

    public void setString_to_code(String string_to_code) {
        this.string_to_code = string_to_code;
    }

    public String getUrl() {
        return "Users/user/Documents/QRImage.png";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image getJsf_imaged() {
        return jsf_imaged;
    }

    public void setJsf_imaged(Image jsf_imaged) {
        this.jsf_imaged = jsf_imaged;
    }

}
