package mysql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MysqlSelect {

	public  void mysqlQuery() {
		//驱动程序名//不固定，根据驱动
		  String driver = "com.mysql.jdbc.Driver";
		  // URL指向要访问的数据库名******
		  String url = "jdbc:mysql:// rm-bp131v4sa3q248h9q342.mysql.rds.aliyuncs.com:3008/calm_gld";
		  // MySQL配置时的用户名
		  String user = "qgd_stf_wt_qa";
		  // Java连接MySQL配置时的密码******
		  String password = "PhEG6KP2nHsCnOz9jRfE";
		  
		  try {
		  // 加载驱动程序
		  Class.forName(driver);
		  
		  // 连续数据库
		  java.sql.Connection conn = DriverManager.getConnection(url, user, password);
		  if(!conn.isClosed())
		   System.out.println("Succeeded connecting to the Database!");
		  
		  // statement用来执行SQL语句
		  java.sql.Statement statement = conn.createStatement();
		  // 要执行的SQL语句id和content是表review中的项。
		  String sql = "SELECT distinct brandId FROM customer "
		  		+ "WHERE mobile IS NOT NULL "
		  		+ "AND IFNULL(commercialID, upgradeCommercialId) IS NOT NULL "
		  		+ "AND updatorId IS NOT NULL "
		  		+ "ORDER BY	brandId DESC limit 10000;";
		  ResultSet rs = statement.executeQuery(sql);  
		  
		  //输出id值和content值
		  List<String> brandIdList=new ArrayList<String>();
		  String getSqlPrefix="select brandId,commercialID from customer where brandID=";
		  String getSqlPostfix=" and mobile IS NOT NULL and IFNULL(commercialID,upgradeCommercialId) is not null AND updatorId is not null limit 1";
		  StringBuilder getSqlSb=new StringBuilder();
		  ResultSet getSqlRs=null;
		  
		  while(rs.next()) {
		   brandIdList.add(rs.getString("brandId"));
		  }
		  
		  
		  BufferedWriter wb=new BufferedWriter(
				  new FileWriter(
						  new File("C:\\Users\\Administrator\\Desktop\\test_data\\loyalty-importOrExport\\testData\\loyalty_brandIDAndShopId.txt")));
		  StringBuilder lineSb=new StringBuilder();
		  String lineSeparator=System.getProperty("line.separator");
		  int count=0;
		  for(String brandId:brandIdList){
			  getSqlSb.append(getSqlPrefix).append(brandId).append(getSqlPostfix);
			  getSqlRs=statement.executeQuery(getSqlSb.toString());
			  while(getSqlRs.next()){
		//		   System.out.println(getSqlRs.getString("brandId")+","+getSqlRs.getString("commercialID"));
				   lineSb.append(getSqlRs.getString("brandId")).append(",").append(getSqlRs.getString("commercialID")).append(lineSeparator);
				   wb.write(lineSb.toString());
				   lineSb.delete(0, lineSb.length());
				   if(++count%100==0)System.out.println("count:"+count);
				   
			   }
			  getSqlSb.delete(0, getSqlSb.length());
		  }
		  
		  wb.close();
		  getSqlRs.close();
		  rs.close(); 
		  conn.close();  
		  } catch(ClassNotFoundException e) {  
		   System.out.println("Sorry,can`t find the Driver!");  
		   e.printStackTrace();  
		  } catch(SQLException e) {
		   e.printStackTrace();
		  } catch(Exception e){
		   e.printStackTrace();
		  }
	}
}
