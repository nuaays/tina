import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.biz.commplan.dto.CommPlanRequest;
import com.zhongan.scorpoin.biz.commplan.sample.ValidatePolicyFullPara;
import com.zhongan.scorpoin.common.ZhongAnApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;  
import org.apache.http.NameValuePair;  
import org.apache.http.ParseException;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;  
import org.apache.http.conn.ssl.SSLContexts;  
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;  
import org.apache.http.entity.ContentType;  
import org.apache.http.entity.mime.MultipartEntityBuilder;  
import org.apache.http.entity.mime.content.FileBody;  
import org.apache.http.entity.mime.content.StringBody;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

	/*public static String setParameter()
	{
		Boolean flg = true;
		String validate ="11111";
		if (flg.equals(true))
			return "true";
		else 
			return "false";
	}*/
	public static void main(String[] args) {
		
		String str1="Y";
		String str2="N";
		
		if (str1!=str2)
		
		/*JSONObject params = new JSONObject();

        params.put("productMask", "ff633ad8736e6bf5b50fae903e4dad1223debc94");//众安提供的产品唯一的掩码
        params.put(
                "policyHolderUserInfo",
                "{\"policyHolderUserName\": \"测试投保人\",\"policyHolderCertiType\": \"I\",\"policyHolderCertiNo\": \"370101198005060013\",\"policyHolderPhone\": \"18600000000\",\"policyHolderBirth\": \"19800506\",\"policyHolderEmail\": \"xxx@qq.com\",\"policyHolderSex\":\"M\"}");//投保人信息
        params.put(
                "insureUserInfo",
                "[{\"insureUserName\": \"测试被保人\",\"insureCertiType\": \"I\",\"insureCertiNo\": \"370101198005060013\",\"insureBirth\": \"19800506\",\"insurePhone\": \"18600000000\",\"insureRelation\":\"父子\",\"insureSex\":\"M\"}]");//被保人信息
        params.put("premium", "58");//
        params.put("policyBeginDate", "20170709000000");//保单起期，格式yyyyMMddHHmmss
        params.put("channelId", "1140");//渠道id 由众安提供
        params.put("extraInfo", "");//Json格式的业务扩展字符串
        params.put("policyEndDate", "20180708000000");
        params.put("sumInsured", "6000");

        System.out.println(params.toJSONString());
        
        String str = "["+params.toJSONString()+"]";
        List<ValidatePolicyFullPara> VPFP = new  ArrayList<ValidatePolicyFullPara>();
        VPFP.add((ValidatePolicyFullPara) JSON.parseArray(str, ValidatePolicyFullPara.class));
        
        JSONObject params2 = new JSONObject();

        params2.put("productMask", "ff633ad8736e6bf5b50fae903e4dad1223debc94");//众安提供的产品唯一的掩码
        params2.put(
                "policyHolderUserInfo",
                "{\"policyHolderUserName\": \"测试投保人\",\"policyHolderCertiType\": \"I\",\"policyHolderCertiNo\": \"370101198005060013\",\"policyHolderPhone\": \"18600000000\",\"policyHolderBirth\": \"19800506\",\"policyHolderEmail\": \"xxx@qq.com\",\"policyHolderSex\":\"M\"}");//投保人信息
        params2.put(
                "insureUserInfo",
                "[{\"insureUserName\": \"测试被保人\",\"insureCertiType\": \"I\",\"insureCertiNo\": \"370101198005060013\",\"insureBirth\": \"19800506\",\"insurePhone\": \"18600000000\",\"insureRelation\":\"父子\",\"insureSex\":\"M\"}]");//被保人信息
        params2.put("premium", "60");//
        params2.put("policyBeginDate", "20170709000000");//保单起期，格式yyyyMMddHHmmss
        params2.put("channelId", "1140");//渠道id 由众安提供
        params2.put("extraInfo", "");//Json格式的业务扩展字符串
        params2.put("policyEndDate", "20180708000000");
        params2.put("sumInsured", "6000");

        System.out.println(params2.toJSONString());
        
        String str2 = "["+params2.toJSONString()+"]";
       
        VPFP.add((ValidatePolicyFullPara) JSON.parseArray(str2, ValidatePolicyFullPara.class));
        
        System.out.println(VPFP.size());
        System.out.print(VPFP.get(0).getPremium());
        System.out.print(VPFP.get(1).getPremium());*/
        
	       /* // 创建默认的httpClient实例.    
	        CloseableHttpClient httpclient = HttpClients.createDefault();  
	        // 创建httppost    
	        HttpPost httppost = new HttpPost("https://cashieruat.zhongan.com/za-cashier-web/gateway.do");  
	        // 创建参数队列    
	        List<namevaluepair> formparams = new ArrayList<namevaluepair>();  
	        formparams.add(new BasicNameValuePair("type", "house"));  
	        UrlEncodedFormEntity uefEntity;  
	        try {  
	            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
	            httppost.setEntity(uefEntity);  
	            System.out.println("executing request " + httppost.getURI());  
	            CloseableHttpResponse response = httpclient.execute(httppost);  
	            try {  
	                HttpEntity entity = response.getEntity();  
	                if (entity != null) {  
	                    System.out.println("--------------------------------------");  
	                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
	                    System.out.println("--------------------------------------");  
	                }  
	            } finally {  
	                response.close();  
	            }  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (UnsupportedEncodingException e1) {  
	            e1.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            // 关闭连接,释放资源    
	            try {  
	                httpclient.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	  */
		
		
		/*HttpGet httpget = new HttpGet("http://10.139.107.206:3080/getdata/certi");  
        System.out.println("executing request" + httpget.getRequestLine());  
        CloseableHttpClient httpclient =HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			HttpEntity entity = response.getEntity();  
            System.out.println("----------------------------------------");  
            System.out.println(response.getStatusLine());  
            if (entity != null) {  
                System.out.println("Response content length: " + entity.getContentLength());  
                
                String str = EntityUtils.toString(entity);
                System.out.println(str);  
              
                
                Document doc = Jsoup.parse("http://10.139.107.206:3080/getdata/certi");
                System.out.println("----------------------------------------"); 
                System.out.println(doc);
                Element content = doc.getElementById("container");
                System.out.println("----------------------------------------"); 
                System.out.println(content);
                Elements links = content.getElementsByTag("certi");
                System.out.println(links); 
			
		}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  */

	
		
	}
}
