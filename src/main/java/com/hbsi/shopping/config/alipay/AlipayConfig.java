package com.hbsi.shopping.config.alipay;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016100100638915";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCL17oYr8gzAobavDj4HVqZdUIh7nzI/5+K62UYBf0s5it6fAnvzb+ihJIR9UEbXzmCy57f1QyHVJefmKE4yDGt8wImCStNkfZKrfocVwv0zh0PfYmCAQHwpSAfKiTyVDiGp1xvCeMuiHeB/7AFqs+yjUKLYO4s82mBngqjNRfNBYt/Ksy7Tp1LkKbGEc/wl2CmqrkcbmHP3BNemXqTm7m1obCl8xnVcQ7dju09dLS/uPvwXTBAn0+w4u99VSG5kuLzGwlaSLFaK3s8jxTnzcD6R4GchAyyrQb2kYBUTMuCw1QNfcuL68luFPAGsx0KrC/19/zpbUmahhUdjCuusOafAgMBAAECggEBAIVaByUJYxB5hV1KhczcOQwDTDgNMsN9N4eCp1l8RIwvRSC87cDWY7q3To9/X0GZ0b7PK0Zo5FScryXKp1r9VJp7E/5vWfLTkJhwRQQHYehlMEMYRCtLiK0Q2Yu+ZZMMCwckyzk7pX83NTcrOpB87ShoGh/NI/KLf33R1nUB6RtgknpE3nvOcZMfMRyPxPpklOke3i+3lLpmHQBFXNCrbui9y4Vom1hRtZ0Hhn3X6mVmkrAO2FkHPbhzpq+AyFuU4jc+BnJ5/2yvcDWeF7cv7OnJdTQjuOwzHaWQZxmm2a3xUeJ4rgybX0QYskH+60Tct6ddfuSM9+IfnVAMtKe0woECgYEA4rdVGM6B8vHT9IfE+xob3KueD4/xoAhq5Uja57cHIn1t8nXAgjs/G40qv3nloLwtElJuYIzv+006IThEcbsuqa/huedwLguAn6xUqYwXPO7tsU0tTUeDIH6b/FwSBsfzzdJFn5qidRStLDrOki8WmRyh+K7vHzGQSH1kJbfsNUECgYEAnefPLatP0OUInujiTNKb8ecZ670rRmsSENTcd+Qrfz42b+5isC0u9NB5h+Gwl+XeZ2/ojwV6WlWWRPCUooYEDQnCdOhQd1GSgFeP/YOavgZicuUSfYiZh6kPv/CNobkuKI2ind9dACW1t2w2ztEU1u1vokbjjmJ1DdYgGR3ew98CgYEA1RYVIFywHMSYP6QASzYt68lNpTjYlF51Ag3o2ZLYXN1hIlr0VjMTTHTxMciDQAC9qBPb9FljEWJzeVemVPLml8xcr1tMv6pF+U2CwVyPVtEGjPeJ1bGaGEeHmrh6pO74QoIYuDsh6ENHxGN9Vj510fDsd40L6Niv0MIyB5WHqEECgYB7rWvNRme+oxVMefV93Fu+bZcU2FbD0esVedOwp+sVv6Gdq+ZqPQmRQJRfxHqvjqDS/Yj2NIEvreeD2bdhUHxtxd4j+S3FNwP7OBoUo3aZLaEAgsEnx80Qv28RlBUqvUMVClJ7Os655c9degm3lXWwHcqZHNLw29b43mTHp2iqmwKBgQCwKYL3daYHUwgOQtiKxCTAL1HG7OS/WH7uRUh1oSBDRaqNKE/cagsDrVA8dwif4pVLW+Q2GSalZJq75wtBD/2Hkqaa3jPXbnlvTzztDxgmKXKXJDDGSAeSHRCZHunKA9Wn5M8Oq6B3zniCFBAlFJQwWxPPLxrBj/PDiVkh+UMLNQ==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi9e6GK/IMwKG2rw4+B1amXVCIe58yP+fiutlGAX9LOYrenwJ782/ooSSEfVBG185gsue39UMh1SXn5ihOMgxrfMCJgkrTZH2Sq36HFcL9M4dD32JggEB8KUgHyok8lQ4hqdcbwnjLoh3gf+wBarPso1Ci2DuLPNpgZ4KozUXzQWLfyrMu06dS5CmxhHP8Jdgpqq5HG5hz9wTXpl6k5u5taGwpfMZ1XEO3Y7tPXS0v7j78F0wQJ9PsOLvfVUhuZLi8xsJWkixWit7PI8U583A+keBnIQMsq0G9pGAVEzLgsNUDX3Li+vJbhTwBrMdCqwv9ff86W1JmoYVHYwrrrDmnwIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.0.0.1:8082/shopping-mall/home";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1:8082/shopping-mall/home";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    public static String format = "json";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
   // public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


}

