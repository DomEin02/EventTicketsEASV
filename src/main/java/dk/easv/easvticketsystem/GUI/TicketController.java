package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import dk.easv.easvticketsystem.model.TicketType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import dk.easv.easvticketsystem.DAL.TicketDAO;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.UUID;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class TicketController {

    @FXML private ComboBox<TicketType> type;
    @FXML private Label ticketId;
    @FXML private Label eventNameLabel;
    @FXML private VBox ticketContainer;
    @FXML private ImageView qrImage;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private ImageView barcodeImage;
    @FXML private Label customerNameLabel;
    @FXML private Label customerEmailLabel;


    private TicketDAO ticketDAO = new TicketDAO();

    public static String selectedEventName;

    @FXML
    public void initialize() {
        try {
            List<TicketType> types = ticketDAO.getAllTicketTypes();
            System.out.println("Ticket types: " + types.size());
            type.getItems().addAll(types);
            type.getSelectionModel().selectFirst();

        } catch (Exception e) {
            ticketId.setText("Error loading ticket types");
            e.printStackTrace();
        }

        if(selectedEventName != null)
            eventNameLabel.setText("Event: " + selectedEventName);

        refreshTickets();
    }

    @FXML
    private void generate() {

        TicketType selectedType = type.getValue();

        if (selectedType == null) {
            ticketId.setText("⚠ Please select ticket type!");
            return;
        }

        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            ticketId.setText("⚠ Please fill all fields!");
            return;
        }

        String id = UUID.randomUUID().toString();
        ticketId.setText(id);
        customerNameLabel.setText(name);
        customerEmailLabel.setText(email);

        try {
            int eventId = 3;
            int ticketTypeId = selectedType.getId();

            ticketDAO.createTicket(id, eventId, ticketTypeId, name, email);
            Image qr = generateQR(id);
            qrImage.setImage(qr);
            Image barcode = generateBarcode(id);
            barcodeImage.setImage(barcode);

            refreshTickets();

        } catch (Exception e) {
            ticketId.setText("⚠ Error creating ticket");
            e.printStackTrace();
        }
    }

    private void refreshTickets() {

        try {
            ticketContainer.getChildren().clear();

            List<String> tickets = ticketDAO.getAllTickets();

            for (String t : tickets) {
                Label label = new Label(t);
                ticketContainer.getChildren().add(label);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        nameField.clear();
        emailField.clear();
    }

    @FXML
    private void openFreeTicket() {
        SceneManager.load("freeTicket.fxml");
    }

    private Image generateQR(String text) throws Exception {

        int width = 150;
        int height = 150;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelWriter.setColor(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }

        return image;
    }

    private Image generateBarcode(String text) throws Exception {

        int width = 300;
        int height = 100;

        com.google.zxing.oned.Code128Writer barcodeWriter =
                new com.google.zxing.oned.Code128Writer();

        BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.CODE_128, width, height);

        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelWriter.setColor(x, y,
                        bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }

        return image;
    }
}