package com.xteam.ggq.model.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Component
public class SystemMailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.sendFrom}")
    private String sendFrom;

    // 邮箱地址之间使用英文分号（;）
    private static final String SEPARATOR = ";";

    private static final long ONE_MIN_BY_MILLISECOND = 60 * 1000;

    private static final int RETRY_TIMES = 5;

    /**
     * @param title
     *            邮件标题
     * @param text
     *            正文
     * @param to
     *            收件人邮箱地址，支持多个邮箱地址，邮箱地址之间使用英文分号（;）分隔即可
     * @param cc
     *            抄送邮箱地址，支持多个邮箱地址，邮箱地址之间使用英文分号（;）分隔即可
     * @throws MessagingException
     */
    public void sendMail(String title, String text, String to, String cc) throws MessagingException {
        sendMailWithAttachment(title, text, to, cc, null);
    }

    /**
     * @param title
     *            邮件标题
     * @param text
     *            正文
     * @param to
     *            收件人邮箱地址，支持多个邮箱地址，邮箱地址之间使用英文分号（;）分隔即可
     * @param cc
     *            抄送邮箱地址，支持多个邮箱地址，邮箱地址之间使用英文分号（;）分隔即可
     * @param attachment
     *            附件
     * @throws MessagingException
     */
    public void sendMailWithAttachment(String title, String text, String to, String cc, File attachment)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setSubject(title);
        messageHelper.setFrom(sendFrom);
        messageHelper.setText(text);
        if (to.contains(SEPARATOR)) {
            messageHelper.setTo(to.split(SEPARATOR));
        } else {
            messageHelper.setTo(to);
        }
        if (cc.contains(SEPARATOR)) {
            messageHelper.setCc(cc.split(SEPARATOR));
        } else {
            messageHelper.setCc(cc);
        }
        if (attachment != null) {
            messageHelper.addAttachment(attachment.getName(), attachment);
        }

        // 发送不成功则重试几次
        for (int i = 0; i < RETRY_TIMES; i++) {
            try {
                log.info("try send mail, count: " + (i + 1));
                mailSender.send(message);
                log.info("send mail success.");
                break;
            } catch (Exception e) {
                log.error("send mail fail, error msg: ", e.getMessage(), e);
            }

            try {
                Thread.sleep(ONE_MIN_BY_MILLISECOND);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
