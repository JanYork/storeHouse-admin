package com.storehouse.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * SMTP邮件工具类
 * @author JanYork
 */
public class EmailSmtpUtils extends Thread {
    public static String emailAccount;
    public static String emailPassword;
    public static String emailSMTPHost;
    public static String smtpPort;
    public static String emailAccountName;


    private String body;
    private String receiveMailAccount;

    static {
        Properties properties = new Properties();
        InputStream in = EmailSmtpUtils.class.getClassLoader().getResourceAsStream("EmailSmtpUtils.properties");
        try {
            properties.load(in);
            emailAccount = properties.getProperty("EmailAccount");
            emailPassword = properties.getProperty("EmailPassword");
            emailSMTPHost = properties.getProperty("EmailSMTPHost");
            smtpPort = properties.getProperty("SmtpPort");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EmailSmtpUtils(String body, String receiveMailAccount) {
        this.body = body;
        this.receiveMailAccount = receiveMailAccount;
    }
    @Override
    public void run() {
        // SMTP参数配置对象
        Properties props = new Properties();
        // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.transport.protocol", "smtp");
        // 发件人的邮箱的 SMTP
        props.setProperty("mail.smtp.host", emailSMTPHost);
        // 验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置端口
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        // 创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(false);
        // 根据 Session 获取邮件传输对象
        Transport transport = null;
        try {
            transport = session.getTransport();
            // 创建一封邮件
            MimeMessage message = createMimeMessage(session, emailAccount, receiveMailAccount, body);
            // 根据 Session 获取邮件传输对象
            transport = session.getTransport();
            // 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            transport.connect(emailAccount, emailPassword);
            // 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        // 关闭连接
        try {
            transport.close();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static MimeMessage createMimeMessage(Session session, String emailAccount, String receiveMail, String body) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(emailAccount, emailAccountName, "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "未知用户", "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject("[StoreHouse]团队", "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(body, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }
}
