package hoversprite.otp.external.service;

public interface SmsService {
    void sendOtpSms(Long orderId, String phoneNumber) throws Exception;

    Boolean verifyOtp(Long orderId, String otp) throws Exception;
}
