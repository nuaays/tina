package com.zhongan.test.ID;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random; 

public class CertiGeneration {
	private int     minAge = 18;
	  private int     maxAge = 60;
	  private String  strMinAge = "18";
	  private String  strMaxAge = "60";
	  private String  gender = "random";
	  public  String  birthday = null;
	  
	  //获得随机身份证号码
	  public String getRandomCertiCode() {
	    
	    String certiCode  = null;
	    String cityCode   = this.getRandomCityCode();
	    String birthCode  = this.getRandomBirthCode();
	    String orderCode  = this.getRandomOrderCode();
	    String checkCode  = this.getCheckCode(cityCode, birthCode, orderCode);
	    certiCode         = cityCode + birthCode + orderCode + checkCode;
	    return certiCode;
	    
	  }
	  
	  //获得指定性别的随机身份证号码
	  public String getRandomCertiCode(String gender) {
	    
	    String certiCode  = null;
	    this.gender       = gender;
	    String cityCode   = this.getRandomCityCode();
	    String birthCode  = this.getRandomBirthCode();
	    String orderCode  = this.getRandomOrderCode();
	    String checkCode  = this.getCheckCode(cityCode, birthCode, orderCode);
	    certiCode         = cityCode + birthCode + orderCode + checkCode;
	    return certiCode;
	    
	  }
	  
	//获得指定性别和出生年月 的身份证号
	  public String getRandomCertiCode(String gender,String birthday) {
	    
	    String certiCode  = null;
	    this.gender       = gender;
	    String cityCode   = this.getRandomCityCode();
	    String birthCode  = birthday;
	    String orderCode  = this.getRandomOrderCode();
	    String checkCode  = this.getCheckCode(cityCode, birthCode, orderCode);
	    certiCode         = cityCode + birthCode + orderCode + checkCode;
	    return certiCode;
	    
	  }
	  
	  //获得最小年龄至最大年龄范围内的随机身份证号码
	  public String getRandomCertiCode(int minAge, int maxAge) {
	    
	    String certiCode  = null;
	    this.minAge       = minAge;
	    this.maxAge       = maxAge + 1;
	    String cityCode   = this.getRandomCityCode();
	    String birthCode  = this.getRandomBirthCode();
	    String orderCode  = this.getRandomOrderCode();
	    String checkCode  = this.getCheckCode(cityCode, birthCode, orderCode);
	    certiCode         = cityCode + birthCode + orderCode + checkCode;
	    return certiCode;
	    
	  }
	  
	  // 获得最小年龄至最大年龄范围内指定性别的随机身份证号码
	  public String getRandomCertiCode(int minAge, int maxAge, String gender) {

	    String certiCode  = null;
	    this.minAge       = minAge;
	    this.maxAge       = maxAge + 1;
	    this.gender       = gender;
	    String cityCode   = this.getRandomCityCode();
	    String birthCode  = this.getRandomBirthCode();
	    String orderCode  = this.getRandomOrderCode();
	    String checkCode  = this.getCheckCode(cityCode, birthCode, orderCode);
	    certiCode         = cityCode + birthCode + orderCode + checkCode;
	    return certiCode;

	  }
	  
	  public String getRandomCertiCode2(String strMinAge, String strMaxAge, String gender) {
	    
	    String certiCode  = null;
	    this.strMinAge    = strMinAge;
	    this.strMaxAge    = strMaxAge;
	    this.gender       = gender;
	    String cityCode   = this.getRandomCityCode();
	    String birthCode  = this.getRandomBirthCode2();
	    String orderCode  = this.getRandomOrderCode();
	    String checkCode  = this.getCheckCode(cityCode, birthCode, orderCode);
	    certiCode         = cityCode + birthCode + orderCode + checkCode;
	    return certiCode;
	    
	  }
	  
	  //随机生成省市代码
	  private String getRandomCityCode() {
	    
	    String cityCode = null;
	    cityCode = TextUtil.cities[new Random().nextInt(TextUtil.cities.length - 1)];
	    return cityCode;
	    
	  }
	  
	  //随机生成出生年月
	  private String getRandomBirthCode() {
	    
	    Random    random    = new Random();
	    String    birthCode = null;
	    Calendar  minDate   = Calendar.getInstance();
	    Calendar  maxDate   = Calendar.getInstance();
	    minDate.setTime(new Date());
	    maxDate.setTime(new Date());
	    minDate.add(Calendar.YEAR, -maxAge);
	    maxDate.add(Calendar.YEAR, -minAge);
	    int diff = random.nextInt((int)((maxDate.getTimeInMillis() - minDate.getTimeInMillis())
	        / (24 * 60 * 60 * 1000)) +1 );
	    minDate.add(Calendar.DATE, diff);
	    birthCode = new SimpleDateFormat("yyyyMMdd").format(minDate.getTime());
	    return birthCode;
	    
	  }
	  
	  private String getRandomBirthCode2() {
	    
	    String  birthCode = null;
	    Random  random    = new Random();
	    Calendar maxDate = Calendar.getInstance();
	    Calendar minDate = Calendar.getInstance();
	    minDate.setTime(new Date());
	    maxDate.setTime(new Date());
	    int     minAgeTag = strMinAge.indexOf("d");
	    if (minAgeTag > 0) {
	      int minAge = Integer.valueOf(strMinAge.substring(0, minAgeTag));
	      maxDate.add(Calendar.DATE, -minAge);
	    }else {
	      int minAge = Integer.valueOf(strMinAge);
	      maxDate.add(Calendar.YEAR, -minAge);
	    }
	    int     maxAgeTag = strMaxAge.indexOf("d");
	    if (maxAgeTag > 0) {
	      int maxAge = Integer.valueOf(strMaxAge.substring(0, maxAgeTag));
	      minDate.add(Calendar.DATE, -maxAge);
	    }else {
	      int maxAge = Integer.valueOf(strMaxAge) + 1;
	      minDate.add(Calendar.YEAR, -maxAge);
	    }
	    int diff = random.nextInt((int)((maxDate.getTimeInMillis() - minDate.getTimeInMillis()) 
	        / (24 * 60 * 60 * 1000)) + 1 );
	    minDate.add(Calendar.DATE, diff);
	    birthCode = new SimpleDateFormat("yyyyMMdd").format(minDate.getTime());
	    return birthCode;
	    
	  }
	  
	  //随机生成顺序号
	  private String getRandomOrderCode() {
	    
	    Random  random    = new Random();
	    String  orderCode = null;
	    String  orderNum1 = Integer.toString(new Random().nextInt(9));
	    String  orderNum2 = Integer.toString(new Random().nextInt(9));
	    int     num3      = random.nextInt(9);
	    if ("female".equals(this.gender)) {
	      do {
	        num3 = random.nextInt(9);
	      } while(num3 % 2 != 0);
	    }
	    String orderNum3  = Integer.toString(num3);
	    orderCode         = orderNum1 + orderNum2 + orderNum3;
	    this.gender       = "random";
	    return orderCode;
	    
	  }
	  
	  // 生成校验码
	  private String getCheckCode(String cityCode, String birthCode, String orderCode) {
	    
	    String  checkCode   = null; 
	    // 初始化系数表
	    int     ratio[]     = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
	    // 初始化校验位码表
	    String  checkList[] = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
	    char num17[] = (cityCode + birthCode + orderCode).toCharArray();
	    int sum = 0;
	    for (int i=0; i<num17.length; i++) {
	      sum = sum + (num17[i] - 48) * ratio[i];
	    }
	    checkCode = checkList[sum % 11];
	    return checkCode;
	    
	  }

}
