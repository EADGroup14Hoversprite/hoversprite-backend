package hoversprite.email.internal.service;

import hoversprite.common.external.enums.CropType;
import hoversprite.common.external.enums.OrderSlot;
import hoversprite.common.external.enums.OrderStatus;
import hoversprite.common.external.type.LunarDate;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import hoversprite.email.external.service.EmailService;

import java.time.LocalDate;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setFrom("hoverspritemailnoreply@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            emailSender.send(message);
        } catch (Exception e) {
             System.out.print(e.getMessage());
        }
    }

    @Override
    public void sendOrderCreationEmail(String email, String fullName, Long id, String farmerName, String farmerPhoneNumber, String address, CropType cropType, LocalDate desiredDate, Double farmlandArea, Double totalCost, OrderSlot timeSlot, OrderStatus status, LocalDate createdAt) {
        MimeMessage message = emailSender.createMimeMessage();
        String text = "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<style>" +
                        "  body { font-family: Arial, sans-serif; }" +
                        "  .container { width: 100%; padding: 20px; background-color: #f9f9f9; }" +
                        "  .email-header { background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; }" +
                        "  .email-body { margin: 20px; padding: 20px; background-color: white; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                        "  .email-body h2 { color: #4CAF50; }" +
                        "  .email-body p { line-height: 1.6; }" +
                        "  .order-details { margin-top: 20px; }" +
                        "  .order-details th, .order-details td { text-align: left; padding: 8px; }" +
                        "  .order-details th { background-color: #4CAF50; color: white; }" +
                        "  .order-details td { background-color: #f2f2f2; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "  <div class='container'>" +
                        "    <div class='email-header'>" +
                        "      <h1>Order Confirmation</h1>" +
                        "    </div>" +
                        "    <div class='email-body'>" +
                        "      <h2>Hello, " + fullName + "!</h2>" +
                        "      <p>We are pleased to confirm that you have successfully booked a spraying order with Hoversprite. Below are the details of your order:</p>" +
                        "      <table class='order-details' width='100%'>" +
                        "        <tr><th>Order ID:</th><td>" + id + "</td></tr>" +
                        "        <tr><th>Farmer Name:</th><td>" + farmerName + "</td></tr>" +
                        "        <tr><th>Farmer Phone Number:</th><td>" + farmerPhoneNumber + "</td></tr>" +
                        "        <tr><th>Address:</th><td>" + address + "</td></tr>" +
                        "        <tr><th>Crop Type:</th><td>" + cropType.name() + "</td></tr>" +
                        "        <tr><th>Desired Date (Gregorian):</th><td>" + desiredDate + "</td></tr>" +
                        "        <tr><th>Desired Date (Lunar):</th><td>" + new LunarDate(desiredDate) + "</td></tr>" +
                        "        <tr><th>Farmland Area:</th><td>" + farmlandArea + " decare</td></tr>" +
                        "        <tr><th>Total Cost:</th><td>" + totalCost + " VND</td></tr>" +
                        "        <tr><th>Time Slot:</th><td>" + timeSlot.toString() + "</td></tr>" +
                        "        <tr><th>Status:</th><td>" + status + "</td></tr>" +
                        "        <tr><th>Created At:</th><td>" + createdAt + "</td></tr>" +
                        "      </table>" +
                        "      <p>Please wait for a receptionist to confirm this order.</p>" +
                        "      <p>Thank you for choosing Hoversprite. We will keep you updated on the progress of your order.</p>" +
                        "      <p>Best regards,<br>The Hoversprite Team</p>" +
                        "    </div>" +
                        "  </div>" +
                        "</body>" +
                        "</html>";
        sendEmail(email, message, text);
    }

    @Override
    public void sendOrderConfirmationEmail(String email, String fullName, Long id) {
        MimeMessage message = emailSender.createMimeMessage();
        String text = "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<style>" +
                        "  body { font-family: Arial, sans-serif; }" +
                        "  .container { width: 100%; padding: 20px; background-color: #f9f9f9; }" +
                        "  .email-header { background-color: #28a745; color: white; padding: 10px 20px; text-align: center; }" +
                        "  .email-body { margin: 20px; padding: 20px; background-color: white; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                        "  .email-body h2 { color: #28a745; }" +
                        "  .email-body p { line-height: 1.6; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "  <div class='container'>" +
                        "    <div class='email-header'>" +
                        "      <h1>Order Confirmation</h1>" +
                        "    </div>" +
                        "    <div class='email-body'>" +
                        "      <h2>Dear " + fullName + ",</h2>" +
                        "      <p>We are pleased to inform you that your order <strong>#" + id + "</strong> has been confirmed.</p>" +
                        "      <p>We are currently searching for suitable sprayers to assign to this order. You will receive another notification once a sprayer has been assigned.</p>" +
                        "      <p>Thank you for choosing Hoversprite. We will keep you updated on the progress of your order.</p>" +
                        "      <p>Best regards,<br>The Hoversprite Team</p>" +
                        "    </div>" +
                        "  </div>" +
                        "</body>" +
                        "</html>";
        sendEmail(email, message, text);
    }

    @Override
    public void sendOrderAssignmentEmail(String email, String fullName, Long id, String farmerName, String farmerPhoneNumber, String address, CropType cropType, LocalDate desiredDate, Double farmlandArea, Double totalCost, OrderSlot timeSlot) {
        MimeMessage message = emailSender.createMimeMessage();
        String text = "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<style>" +
                        "  body { font-family: Arial, sans-serif; }" +
                        "  .container { width: 100%; padding: 20px; background-color: #f9f9f9; }" +
                        "  .email-header { background-color: #007BFF; color: white; padding: 10px 20px; text-align: center; }" +
                        "  .email-body { margin: 20px; padding: 20px; background-color: white; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                        "  .email-body h2 { color: #007BFF; }" +
                        "  .email-body p { line-height: 1.6; }" +
                        "  .order-details { margin-top: 20px; }" +
                        "  .order-details th, .order-details td { text-align: left; padding: 8px; }" +
                        "  .order-details th { background-color: #007BFF; color: white; }" +
                        "  .order-details td { background-color: #f2f2f2; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "  <div class='container'>" +
                        "    <div class='email-header'>" +
                        "      <h1>Order Assignment Notification</h1>" +
                        "    </div>" +
                        "    <div class='email-body'>" +
                        "      <h2>Hello, " + fullName + "!</h2>" +
                        "      <p>This is an email to inform you that you have been assigned to the following order:</p>" +
                        "      <table class='order-details' width='100%'>" +
                        "        <tr><th>Order ID:</th><td>" + id + "</td></tr>" +
                        "        <tr><th>Farmer Name:</th><td>" + farmerName + "</td></tr>" +
                        "        <tr><th>Farmer Phone Number:</th><td>" + farmerPhoneNumber + "</td></tr>" +
                        "        <tr><th>Address:</th><td>" + address + "</td></tr>" +
                        "        <tr><th>Crop Type:</th><td>" + cropType.name() + "</td></tr>" +
                        "        <tr><th>Desired Date (Gregorian):</th><td>" + desiredDate + "</td></tr>" +
                        "        <tr><th>Desired Date (Lunar):</th><td>" + new LunarDate(desiredDate) + "</td></tr>" +
                        "        <tr><th>Farmland Area:</th><td>" + farmlandArea + " hectare</td></tr>" +
                        "        <tr><th>Total Cost:</th><td>" + totalCost + " VND</td></tr>" +
                        "        <tr><th>Time Slot:</th><td>" + timeSlot.toString() + "</td></tr>" +
                        "      </table>" +
                        "      <p>Please proceed to complete this order as per the schedule. If you have any questions, feel free to contact us.</p>" +
                        "      <p>Thank you for your hard work!</p>" +
                        "    </div>" +
                        "  </div>" +
                        "</body>" +
                        "</html>";
        sendEmail(email, message, text);
    }

    private void sendEmail(String email, MimeMessage message, String text) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setFrom("hoverspritemailnoreply@gmail.com");
            helper.setTo(email);
            helper.setSubject("Order Creation Confirmation");
            helper.setText(text, true);
            emailSender.send(message);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
