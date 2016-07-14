package com.zhongan.test.DbDataCheck;

import java.sql.*;

public class QueryData {
	 
     
	
	 private Connection con = null;
	    private Statement stmt = null;
	    private String url = "jdbc:mysql://rds6fbqrf6fbqrf.mysql.rds.aliyuncs.com:3306/policy_00";
	    private String user = "za_test_policy";
	    private String pwd = "za_test_policy_f9b940";
	    /** *//** Creates a new instance of Operation */
	    public QueryData() {
	        init();
	    }
	    
	    /** *//** init */
	    private void init(){
	        try {        
	        	Class.forName("com.mysql.jdbc.Driver");
	            System.out.println("成功加载MySQL驱动！");
	                
	            con = DriverManager.getConnection(url,user,pwd);
	            Statement stmt = con.createStatement(); //创建Statement对象
	            System.out.println("成功连接到数据库！");
	        } catch (Exception e){
	          // your installation of JDBC Driver Failed
	          e.printStackTrace();
	        }
	    }
	   
	    /** *//**
	     * TODO 查询记录
	     * @param sn 记录的学生姓名
	     * @return String 查询的结果
	     */
	    public String searchByPolicyNo(String policyNo){
	        
	        String sql = "SELECT premium,policy_no,policy_status,sum_insured,insure_date,effective_date,expiry_date FROM " +
            		"(SELECT * FROM policy_00.policy_0000 UNION ALL SELECT * FROM policy_00.policy_0001 UNION ALL SELECT * FROM policy_01.policy_0002 " +
            		"UNION ALL SELECT * FROM policy_01.policy_0003 ) AS total where policy_no = '" + policyNo + "';";  
	        try{
	            ResultSet rs = stmt.executeQuery(sql);           
	            if(rs.next()){   
	                str = str+rs.getString("sn")+" 手机号："
	                        +rs.getString("sa");
	            }else str = "该记录不存在！！！";
	        }catch(Exception e){
	           e.printStackTrace();
	        }
	        return str;
	    }
	    
	           /**关闭连接*/
	    public void close(){
	        try{
	            if(con != null) con.close();
	            if(stmt != null) stmt.close();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }

		
	public static void main(String[] args){
	      
	    }
	


}
