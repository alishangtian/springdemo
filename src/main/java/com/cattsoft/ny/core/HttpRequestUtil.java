package com.cattsoft.ny.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Document;

import com.cattsoft.ny.base.entity.XmlModelInfo;

public class HttpRequestUtil {
	private static String url = "http://10.21.3.201:8080/rpc/xml";
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
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
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");// 设置接收数据的格式  
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
            // conn.setRequestProperty("Pragma:", "no-cache");  
            // conn.setRequestProperty("Cache-Control", "no-cache");  
           //  conn.setRequestProperty("Content-Type", "text/xml");  
             conn.setRequestProperty("Content-Type",  
                     "application/x-www-form-urlencoded");
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
            in = new BufferedReader(new UnicodeReader(conn.getInputStream(),""));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }    
    
    
    /**
     *参数拼接
     * @param addr 节点物理id  设备表 DEVICE_ID
     * @param port （设备）设备网络拓扑关系 控制器物理id  PARENT_PORT
     * @param busAddr  控制器物理id 设备表 DEVICE_ID
     * @param on on=1&off=0
     * @param relay -查全部状态 relay 取EQUIP_PORT_INFO  EQUIP_INFO_ID= 设备表 id  
     * @return
     */
    public static String sedPostTxst(String addr,String port ,String busAddr ,String on ,String relay){
    	String test ="<?xml version=\"1.0\" encoding=\"utf-8\"?> "+
    			"<methodCall> "+
    			"<methodName>SiteCommand.control</methodName> "+
    			"<params>"+
    			"<param><value><string>addr="+addr+"</string></value></param> "+
    			"<param><value><string>port="+port+"</string></value></param> "+
    			"<param><value><string>bus_addr="+busAddr+"</string></value></param> "+
    			"<param><value><string>act="+on+"</string></value></param> "+
    			"<param><value><string>relay="+relay+"</string></value></param> "+
    			"</params> "+
    			"</methodCall> " ;
    	return test;
    }
    //解析xml
    public static Map xmlModelInfo(String str){
    	Map<String,String> map = new HashMap<String,String>();
    	//tring resultStr = str.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
    	if(!"".equals(str)){
	    	List<Element> refund_charge=null;
			try {
				Document doc =   DocumentHelper.parseText(str);
				Element root =  doc.getRootElement().element("params");
				Element param = root.element("param");
				Element value = param.element("value");
				refund_charge = value.element("struct").elements();
				for (int i = 0; i < refund_charge.size(); i++) {
					String name = refund_charge.get(i).element("name").getText().trim();
					Element values = refund_charge.get(i).element("value");
					String val = values.element("i4").getText().trim();
					map.put(name, val);
				}
			} catch (Exception e) {
				map=null;
				return map;
			}
    	}
		return map;
    }
    /**
     *  1参数拼接 2 发送http请求 3解析返回结果
     * @param addr 节点物理id  设备表 DEVICE_ID
     * @param port （设备）设备网络拓扑关系 控制器物理id  PARENT_PORT
     * @param busAddr  控制器物理id 设备表 DEVICE_ID
     * @param on on=1&off=0
     * @param relay -查全部状态 relay 取EQUIP_PORT_INFO  EQUIP_INFO_ID= 设备表 id  
     * @return
     */
    public static Map httpInfoXml(String addr,String port ,String busAddr ,String on ,String relay){
    	String s = HttpRequestUtil.sedPostTxst(addr, port, busAddr, on, relay);
    	String xmlInfo = HttpRequestUtil.sendPost(url, s);
    	Map map = HttpRequestUtil.xmlModelInfo(xmlInfo);
    	return map;
    }
    public static void main(String[] args) {
        //发送 GET 请求
       /* String s=HttpRequestUtil.sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456");
        System.out.println(s);*/
        String test ="<?xml version=\"1.0\" encoding=\"utf-8\"?> "+
		"<methodCall> "+
		"<methodName>SiteCommand.control</methodName> "+
		"<params>"+
		"<param><value><string>addr=1</string></value></param> "+
		"<param><value><string>port=1</string></value></param> "+
		"<param><value><string>bus_addr=1</string></value></param> "+
		"<param><value><string>act=on</string></value></param> "+
		"<param><value><string>relay=1</string></value></param> "+
		"</params> "+
		"</methodCall> " ;
         
        //发送 POST 请求
        String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?><methodResponse><params><param><value><struct><member><name>board_id</name><value><i4>201</i4></value></member><member><name>_sNodeId</name><value><i4>1</i4></value></member><member><name>port</name><value><i4>1</i4></value></member><member><name>bus_addr</name><value><i4>1</i4></value></member><member><name>relay1</name><value><i4>1</i4></value></member><member><name>relay2</name><value><i4>0</i4></value></member><member><name>relay3</name><value><i4>0</i4></value></member><member><name>relay4</name><value><i4>0</i4></value></member><member><name>relay5</name><value><i4>0</i4></value></member><member><name>relay6</name><value><i4>0</i4></value></member><member><name>relay7</name><value><i4>0</i4></value></member><member><name>relay8</name><value><i4>1</i4></value></member></struct></value></param></params></methodResponse>";
        
        
        String ss = 	"<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
        		"<orderinfo>"+ 
        		"<pnr>MB6M7G</pnr> "+
        		"<code>1</code> "+
        		"<message> "+
        		"</message> "+
        		"<orderno> "+
        		"</orderno> "+
        		"<orderstatus>1</orderstatus> "+
        		"<paystatus>1</paystatus> "+
        		"<pnrsrcid>1203271100120</pnrsrcid> "+
        		"<payprice>1677.20</payprice> "+
        		"<tradeno>2012032745584212</tradeno> "+
        		"<tickets> "+
        		"<ticket> "+
        		"<passenger>小王</passenger> "+
        		"<tktno>781-2101872789</tktno> "+
        		"</ticket> "+
        		"<ticket> "+
        		"<passenger>小张</passenger>"+ 
        		"<tktno>781-2101872788</tktno> "+
        		"</ticket>"+ 
        		"</tickets>"+ 
        		"</orderinfo> "; 
        
       //String sr=HttpRequestUtil.sendPost(url, test);
       //byte[] sr =  HttpRequestUtil.postaaaa(test);
       HttpRequestUtil.xmlModelInfo(s);
       // String sr = HttpRequestUtil.sedPostTxst("http://10.21.3.201:8080/rpc/xml",test.getBytes());
        //System.out.println("-----"+sr.toString());
	}




}



