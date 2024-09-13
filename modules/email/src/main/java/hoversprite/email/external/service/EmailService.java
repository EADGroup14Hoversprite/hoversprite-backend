package hoversprite.email.external.service;

import hoversprite.common.external.enums.CropType;
import hoversprite.common.external.enums.OrderSlot;
import hoversprite.common.external.enums.OrderStatus;
import hoversprite.common.external.type.Location;

import java.time.LocalDate;

public interface EmailService {
    void sendEmail(String to, String subject, String text);

    void sendOrderCreationEmail(String email, String fullName, Long id, String farmerName, String farmerPhoneNumber, String address, CropType cropType, LocalDate desiredDate, Double farmlandArea, Double totalCost, OrderSlot timeSlot, OrderStatus status, LocalDate createdAt);

    void sendOrderConfirmationEmail(String email, String fullName, Long id);

    void sendOrderAssignmentEmail(String email, String fullName, Long id, String farmerName, String farmerPhoneNumber, String address, CropType cropType, LocalDate desiredDate, Double farmlandArea, Double totalCost, OrderSlot timeSlot);
}
