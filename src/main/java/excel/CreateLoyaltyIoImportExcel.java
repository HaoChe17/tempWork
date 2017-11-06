package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import jxl.JXLException;
import jxl.write.WriteException;


public class CreateLoyaltyIoImportExcel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
//			new CreateLoyaltyIoImportExcel().createExcelFile("C:\\Users\\Administrator\\Desktop\\test_data\\loyalty-importOrExport\\testData\\test.xls", 
//					"C:\\Users\\Administrator\\Desktop\\test_data\\loyalty-importOrExport\\testData",10,"test", 20, 15928867498L,"重庆冷锅鱼啊 啊");
			new CreateLoyaltyIoImportExcel().createExcelFile(args[0],args[1],Integer.parseInt(args[2]),args[3],Integer.parseInt(args[4]),Long.parseLong(args[5]),args[6]);
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param srcExcelFile excel模板文件，从此文件中修改数据，然后重新存入其他excel文件
	 * @param filePath 创建的文件需要存放的路径
	 * @param lineNum 每个文件需要创建的行数
	 * @param nameKeyWords 创建的文件名称的关键字
	 * @param fileNum 需要创建的文件的数量
	 * @param startMobile 起始手机号
	 * @param shopName 门店名称
	 * @throws IOException
	 * @throws JXLException
	 * @throws WriteException
	 */
	public void createExcelFile(String srcExcelFile,String filePath,int lineNum,String nameKeyWords,int fileNum,long startMobile,String shopName) throws IOException, WriteException{

		//打开文件
		FileInputStream fs=new FileInputStream(srcExcelFile);  //获取模板文件流  
        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
        HSSFWorkbook wb=null;    
        HSSFSheet sheet;  //获取到工作表
        
        String fileSeparator=java.io.File.separator;
        if(!filePath.endsWith(fileSeparator))filePath+=fileSeparator;
        StringBuilder fileNameSb;
        FileOutputStream outputStream;
        
        //开始循环创建excel文件
        for(int j=0;j<fileNum;j++){
        	//获取到模板工作表
        	wb=new HSSFWorkbook(ps);
        	sheet=wb.getSheetAt(0);
        	
        	//拼接文件名
        	fileNameSb=new StringBuilder();
            fileNameSb.append(filePath).append(nameKeyWords).append(j).append(".xls");
            //创建excel行  
          	HSSFRow row;
            for(int i=0;i<lineNum;i++){
            	row = sheet.createRow(2+i);
            	//创建单元格并设置数据。以下表示创建row行的第1--6以及11列的单元格，并设置数据
        		row.createCell(0).setCellValue("import");
        		row.createCell(1).setCellValue("男");
        		row.createCell(2).setCellValue(new Long(startMobile++).toString());
        		row.createCell(3).setCellValue("2017/11/02");
        		row.createCell(4).setCellValue("1");
        		row.createCell(5).setCellValue(shopName);
        		row.createCell(10).setCellValue("123456");
            }
    		//输出到指定文件  
    		outputStream = new FileOutputStream(fileNameSb.toString());
    		outputStream.flush();
    		wb.write(outputStream);
    		fileNameSb.delete(0, fileNameSb.length());
    		outputStream.close();
    		
        }
        wb.close();
        
	}
}
