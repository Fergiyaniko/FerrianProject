package com.example.servingwebcontent;

import com.example.servingwebcontent.models.Product;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadController {

	@GetMapping("/")
	public String index() {

		return "index";
	}

	@PostMapping("/uploadExcelFile")
	public String uploadFile(Model model, MultipartFile file) throws IOException {
		InputStream in = file.getInputStream();
		File currDir = new File(".");

		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "excel.xls";
		FileOutputStream f = new FileOutputStream(fileLocation);
		int ch = 0;
		while ((ch = in.read()) != -1) {
			f.write(ch);
		}
		f.flush();
		f.close();
		model.addAttribute("message", "Upload success");

//		List<Product> products = readUploadedExcelFile(fileLocation);

		return "index";
	}

	private void loadProduct(){
		String sqlSelectAllPersons = "SELECT * FROM product";
		String connectionUrl = "jdbc:mysql://ubi1zqjgepmd1bnv:snkjiu4kn73q3bk8@uyu7j8yohcwo35j3.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/xuoo1749kiqvekc2?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true";

		try (Connection conn = DriverManager.getConnection(connectionUrl, "ubi1zqjgepmd1bnv", "snkjiu4kn73q3bk8");
			 PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long id = rs.getLong("ID");
				String productName = rs.getString("name");
				String productDesc = rs.getString("description");

				System.out.println("TEST YAK : " + id + " " + productName + " " + productDesc);

				// do something with the extracted data...
			}
		} catch (SQLException e) {
			// handle the exception
		}
	}

}
