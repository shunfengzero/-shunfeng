package com.sf.poi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sf.entity.Books;
import com.sf.spider.BookIfno;

public class poi {
	
	public static void main(String[] args) {
		write();
	}
	
	//通过poi将数据从数据库取出来放入Excel
	public static void write() {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet booksInfo = book.createSheet("booksInfo");
		
		BookIfno b = new BookIfno();
		List<Books> list = b.bookInfos();
		HSSFRow row = booksInfo.createRow(0);
		row.createCell(0).setCellValue("序号");
		row.createCell(1).setCellValue("书名");
		row.createCell(2).setCellValue("评分");
		row.createCell(3).setCellValue("评价人数");
		row.createCell(4).setCellValue("作者");
		row.createCell(5).setCellValue("出版社");
		row.createCell(6).setCellValue("出版日期");
		row.createCell(7).setCellValue("价格");
		
		for (int i = 1; i <= 100; i++) {
			 row = booksInfo.createRow(i);
			
			row.createCell(0).setCellValue(list.get(i - 1).getId());
			row.createCell(1).setCellValue(list.get(i - 1).getTitle());
			row.createCell(2).setCellValue(list.get(i - 1).getScore());
			row.createCell(3).setCellValue(list.get(i - 1).getRating_sum());
			row.createCell(4).setCellValue(list.get(i - 1).getAuthor());
			row.createCell(5).setCellValue(list.get(i - 1).getPress());
			row.createCell(6).setCellValue(list.get(i - 1).getDate());
			row.createCell(7).setCellValue(list.get(i - 1).getPrice());
			
		}
		
		try {
			book.write(new File("f:/tt/books.xls"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
