import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by XiaoDu on 2019/6/23.
 */
public class test {
    public static void main(String[] args) {
        //zheli1xiugail
        String to = "2205997640@qq.com";
        // 需要提到发件人的电子邮件ID
        String from = "2190046630@qq.com";
        final String username = "2190046630@qq.com";// 需要提到发件人的电子邮件ID
        final String password = "gmduwircznszeaai";// 16位授权码，非邮箱登录密码
        //假设您正在通过relay.jangosmtp.net发送电子邮件
        String host = "relay.jangosmtp.net";
        Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        //163邮箱设置
        //props.setProperty("mail.host", "smtp.163.com");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // 创建一个默认的MimeMessage对象。
            Message message = new MimeMessage(session);

            //设置自:页眉字段的页眉。
            message.setFrom(new InternetAddress(from));

            //设置为:页眉的页眉字段。
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            //邮箱的标题
            message.setSubject("标题");

            // 这封邮件有两部分，正文和嵌入图像
            MimeMultipart multipart = new MimeMultipart("related");
            //随机数
            int time = (int) ((Math.random() * 9 + 1) * 100000);


            //第一部分(html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "[鸿鹄狮鹫]您的验证码是" + time +
                    " 如非本人操作，请忽省本短信。该验证码将在20分钟后失效<img src='cid:image' style='width:300px;'>";
            //指定编码格式，不然一定乱码
            messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");
            //添加物品
            multipart.addBodyPart(messageBodyPart);

            //以下多行注释解除可以在邮件中发送图片（记得改一下文件位置）
            /*
            //第二部分(图片)
            messageBodyPart = new MimeBodyPart();
            //图片位置
            DataSource fds = new FileDataSource("D:\\0.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            //向多部分添加图像
            multipart.addBodyPart(messageBodyPart);
            */
            // 把一切放在一起
            message.setContent(multipart);
            // 发送信息
            Transport.send(message);

            System.out.println("成功发送消息.");
            //下面是倒计时
            int midTime = 60;//多少秒(分钟)失效。
            while (midTime > 0) {
                midTime--;
                long hh = midTime / 60 / 60 % 60;//小时
                long mm = midTime / 60 % 60;//分钟
                long ss = midTime % 60;//秒
                System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
                try {
                    if (ss == 0) {//判断验证码是否失效
                        System.out.println("验证码失效");
                        time = 1;//如果失效则改变验证码的值。
                        Scanner input=new Scanner(System.in);
                        System.out.println("请输入一个整数：");
                        int length=input.nextInt();//输入一个整数
                        System.out.println(time);
                        if(time==length){//在指定的时间内验证码和输入的值相同则进入，否则验证不通过.
                            System.out.println("验证通过");
                        }else{
                            System.out.println("验证不通过");
                        }
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
