package hoversprite.otp.internal.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import hoversprite.common.external.util.UtilFunctions;
import hoversprite.otp.external.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class SmsServiceImpl implements SmsService {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.phone_number}")
    private String fromNumber;

    private final ConcurrentHashMap<String, String> verificationIds = new ConcurrentHashMap<>();

    @Override
    public Boolean verifyOtp(Long orderId, String otp) {
        String storedOtp = verificationIds.get(String.valueOf(orderId));

        if (storedOtp != null && storedOtp.equals(otp)) {
            verificationIds.remove(String.valueOf(orderId));
            return true;
        }
        return false;
    }
    public void sendOtpSms(Long orderId, String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            phoneNumber = phoneNumber.replaceFirst("0", "+1");
        }
        Twilio.init(accountSid, authToken);

        String otp = UtilFunctions.generateOtp();
        Message message = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(fromNumber),
                        "Your order completion OTP is: " + otp + ". Please show it to your sprayer for confirmation that the order is completed.")
                .create();

        System.out.println("SMS sent successfully: " + message.getBody());
        verificationIds.put(String.valueOf(orderId), otp);
    }

}
