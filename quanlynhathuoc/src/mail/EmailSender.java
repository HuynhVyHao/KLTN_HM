package mail;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    // Cấu hình SMTP server
    public static final String HOST_NAME = "smtp.gmail.com";
    public static final int TSL_PORT = 587; // Port cho TLS/STARTTLS
    public static final String APP_EMAIL = "huynhvminhofficial@gmail.com"; // Thay bằng email của bạn
    public static final String APP_PASSWORD = "hwxn ojnx msdk ight"; // Thay bằng mật khẩu ứng dụng (App Password)

    public static void sendEmail(String recipientEmail, String orderId, String status) {
        // Cấu hình các thuộc tính SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.port", TSL_PORT);

        // Tạo session với xác thực
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });

        try {
            // Tạo đối tượng email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(APP_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Địa chỉ email nhận
            message.setSubject("Xác nhận đơn hàng #" + orderId);

            // Nội dung email tùy theo trạng thái
            String content = "";
            if (status.equals("Đã thanh toán")) {
                content = "<h3>Cảm ơn bạn đã thanh toán!</h3>"
                        + "<p>Đơn hàng của bạn có mã: <b>" + orderId + "</b> đã được thanh toán thành công.</p>"
                        + "<p>Cảm ơn bạn đã tin tưởng sử dụng dịch vụ của chúng tôi!</p>";
            } else if (status.equals("Chưa thanh toán")) {
                content = "<h3>Cảm ơn bạn đã đặt hàng!</h3>"
                        + "<p>Đơn hàng của bạn có mã: <b>" + orderId + "</b></p>"
                        + "<p>Vui lòng thanh toán để chúng tôi tiếp tục xử lý đơn hàng của bạn.</p>";
            }

            message.setContent(content, "text/html; charset=utf-8");

            try {
                // Gửi email
                Transport.send(message);
                System.out.println("Email sent successfully!");
            } catch (MessagingException e) {
                System.out.println("Error while sending email: " + e.getMessage());
                e.printStackTrace(); // In ra thông tin chi tiết lỗi
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}