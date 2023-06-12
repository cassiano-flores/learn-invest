package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.controller.request.ForgotPasswordRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.service.SearchUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    private static final String PARAGRAPH_TAG = "<p style='text-align: justify;'>";
    private static final String DIV_TAG = "</div>";

    @Transactional
    public void generateResetToken(ForgotPasswordRequest request) throws MessagingException {

        Usuario usuario = searchUsuarioService.byEmail(request.getEmail());

        String resetToken = UUID.randomUUID().toString();

        usuario.setToken(resetToken);
        usuarioRepository.save(usuario);

        String resetUrl = "http://localhost:3000/reset-password?token=" + resetToken;
        sendResetPasswordEmail(usuario.getEmail(), resetUrl);
    }

    private void sendResetPasswordEmail(String toEmail, String resetUrl) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
        helper.setTo(toEmail);
        helper.setSubject("Solicitação de redefinição de senha - Crescendo Bolso");

        String htmlMsg = "<div style='background-color: #f2f2f2; padding: 10px;'>" +
                "<div style='background-color: white; padding: 20px; border-radius: 10px;'>" +
                "<h1 style='text-align: center; color: #003366;'>" +
                "Solicitação de redefinição de senha" +
                "</h1>" +
                "<hr style='border-top: 2px solid #f2f2f2;'>" +
                PARAGRAPH_TAG +
                "Recebemos uma solicitação para redefinir sua senha. Se você não solicitou essa alteração, ignore este e-mail." +
                "</p>" +
                PARAGRAPH_TAG +
                "Por favor, use o seguinte link para redefinir sua senha:" +
                "</p>" +
                "<div style='text-align: center;'>" +
                "<br><br><a href='" + resetUrl + "' style='background-color: #003366; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none;'>" +
                "Redefinir senha" +
                "</a><br><br>" +
                DIV_TAG +
                PARAGRAPH_TAG +
                "<br><br> Obrigado por utilizar nossa plataforma! <br><br>" +
                "</p>" +
                DIV_TAG +
                DIV_TAG;

        helper.setText(htmlMsg, true);

        javaMailSender.send(mimeMessage);
    }
}
