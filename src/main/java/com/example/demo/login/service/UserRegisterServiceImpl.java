package com.example.demo.login.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;

import org.springframework.stereotype.Service;

@Service
public class UserRegisterServiceImpl implements UserRegisterService{

	@Override
	public Map<String,String> SendEmail(Map<String,String> map) {
		// TODO Auto-generated method stub
		Map<String,String> newmap = new HashMap<String,String>();
		String title = map.get("title");
		String content = map.get("content");
		String fromemail = map.get("fromemail");
		String fromname = map.get("fromname");
		String frompasseord = map.get("frompassword");
		String fromhost = map.get("fromhost");
		String fromport = map.get("fromport");
		String toemail = map.get("toemail");
		
		/*MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();  
	    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");  
	    messageHelper.setFrom(fromemail, fromname);   
	    messageHelper.setSubject(title);    
	    messageHelper.setTo(toemail);    
	    messageHelper.setText(content, true); // html: true   
	    */
	     // 配置发送邮件的环境属性
        final Properties props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", fromhost);
        props.put("mail.smtp.port", fromport);
        // 发件人的账号
        props.put("mail.user", fromemail);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", frompasseord);
        
        try {
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"),MimeUtility.encodeText(fromname,"gbk","b"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress(toemail);
        message.setRecipient(RecipientType.TO, to);

        // 设置抄送
//        InternetAddress cc = new InternetAddress("luo_aaaaa@yeah.net");
//        message.setRecipient(RecipientType.CC, cc);

        // 设置密送，其他的收件人不能看到密送的邮件地址
//        InternetAddress bcc = new InternetAddress("aaaaa@163.com");
//        message.setRecipient(RecipientType.CC, bcc);

        // 设置邮件标题
        message.setSubject(MimeUtility.encodeText(title,"gbk","b"));

        // 设置邮件的内容体
        message.setContent(content, "text/html;charset=gbk");

        // 发送邮件
        
			Transport.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newmap;
	}
	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("title", "zhou");
		map.put("content", "测试");
		map.put("fromemail", "2898115960@qq.com");
		map.put("fromname", "haha");
		map.put("frompassword", "");
		map.put("fromhost", "smtp.qq.com");
		map.put("fromport", "587");
		map.put("toemail", "2898115960@qq.com");
		new UserRegisterServiceImpl().SendEmail(map);
	}

}
