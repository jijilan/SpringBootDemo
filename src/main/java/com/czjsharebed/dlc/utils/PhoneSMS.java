package com.czjsharebed.dlc.utils;

import com.czjsharebed.dlc.exception.MyException;
import com.czjsharebed.dlc.result.ResultEnum;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class PhoneSMS {

	public static final String ZHUTONG_URL="http://www.api.zthysms.com/sendSms.do";

	/**
	 * 助通短信
	 * @param messageContent
	 * @param phone
	 * @return
	 */
	public static int sendSMS(String  messageContent, String phone){
		String dateTime=DateUtils.getCurrentDateTime("yyyyMMddHHmmss");
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("username", "wzrc888hy");
		params.add("password", MD5Util.MD5Encode(MD5Util.MD5Encode("IgwSF2", "utf-8") + dateTime, "utf-8"));
		params.add("tkey", dateTime);
		params.add("mobile", phone);
		params.add("content", messageContent);
		try {
			HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, headers);
			String account = restTemplate.postForObject(ZHUTONG_URL, httpEntity, String.class);
			if (account != null){
				String [] strs=account.split(",");
				return Integer.valueOf(strs[0]);
			}
		}catch (Exception e){
			throw new MyException(ResultEnum.CODE_1001);
		}
		return -1;
	}

	/**
	 * http get请求 发送方法 其他方法同理 返回值>0 提交成功
	 * 
	 * @param Mobile
	 *            手机号码
	 * @param Content
	 *            发送内容
	 * @param send_time
	 *            定时发送的时间；可以为空，为空时为及时发送
	 * @return 返回值
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public static int sendSMSGet(String Mobile, String Content, String send_time)
			throws MalformedURLException, UnsupportedEncodingException {
		URL url = null;
		// 账户名
		String CorpID = "wzrc888hy";
		// 密码
		String Pwd = "IgwSF2";
		// 发送内容
		String send_content = URLEncoder.encode(
				Content.replaceAll("<br/>", " "), "GBK");
		url = new URL("http://www.ztsms.cn/sendNSms.do?username="
				+ CorpID + "&password=" + Pwd + "&tkey="+ send_time+"mobile=" + Mobile + "&content="
				+ send_content+"&productid="+"dalian");//
		BufferedReader in = null;
		int inputLine = 0;
		try {
			System.out.println("开始发送短信手机号码为 ：" + Mobile);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			inputLine = new Integer(in.readLine()).intValue();
		} catch (Exception e) {
			System.out.println("网络异常,发送短信失败！");
			inputLine = -2;
		}
		System.out.println("结束发送短信返回值：  " + inputLine);
		return inputLine;
	}

	/**
	 * Hppt POST请求发送方法 返回值>0 为 提交成功
	 *
	 * @param Mobile
	 *            电话号码
	 * @param Content
	 *            发送内容
	 * @param send_time
	 *            定时发送时间，为空时，为及时发送
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public static int sendSMSPost(String Mobile, String Content,
			String send_time) throws MalformedURLException,
			UnsupportedEncodingException {

		String inputLine = "";
		int value = -2;
		DataOutputStream out = null;
		InputStream in = null;
		// 账户名
		String CorpID = "wzrc888hy";
		// 密码
		String Pwd = "IgwSF2";
		// 发送内容
		String send_content = URLEncoder.encode(
				Content.replaceAll("<br/>", " "), "GBK");

		String strUrl = "http://sdk2.028lk.com:9880/sdk2/BatchSend2.aspx";
		String param = "CorpID=" + CorpID + "&Pwd=" + Pwd + "&Mobile=" + Mobile
				+ "&Content=" + send_content + "&Cell=&SendTime=" + send_time;

		try {

			inputLine = sendPost(strUrl, param);

			System.out.println("开始发送短信手机号码为 ：" + Mobile);

			value = new Integer(inputLine).intValue();

		} catch (Exception e) {

			System.out.println("网络异常,发送短信失败！");
			value = -2;

		}

		System.out.println(String.format("返回值：%d", value));

		return value;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String messageModel(String mobile_code,int modelType){
		StringBuffer sbf=new StringBuffer();
		if(modelType==0){
			sbf.append("【创智家共享陪护床】恭喜您,您在本平台注册的用户账号已经通过了审核,可以登陆app进行使用!");
		}
		if(modelType==1){
			sbf.append("【创智家共享陪护床】验证码："+mobile_code+".有效期为3分钟,当前操作为完善用户信息!");
		}
		if(modelType==2){
			sbf.append("【创智家共享陪护床】验证码："+mobile_code+"。有效期为3分钟，当前操作为修改手机!");
		}
		if(modelType==3){
			sbf.append("【创智家共享陪护床】验证码："+mobile_code+"。有效期为3分钟，当前操为验证新手机!");
		}
		if(modelType==4){
			sbf.append("【创智家共享陪护床】验证码："+mobile_code+"。有效期为3分钟，当前操作为提现申请!");
		}
		if(modelType==5){
			sbf.append("【创智家共享陪护床】验证码："+mobile_code+"。有效期为3分钟,当前操作为绑定微信!");
		}
		if(modelType==6){
			sbf.append("【创智家共享陪护床】验证码："+mobile_code+"。有效期为3分钟，当前操作为注册!");
		}
		return sbf.toString();
	}


	public static void main(String[] args) {
//		String str = LotteryUtil.getRandomNum(6);
//		int num=sendSMS(messageModel(str,1),"18814139173");
		int num=sendSMS("【大连停车位】温馨提示:您预约的车位还有15分钟到期，请及时离场!","15019200770");
		System.out.println(num);
	}

}
