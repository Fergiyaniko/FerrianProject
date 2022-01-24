package com.example.servingwebcontent;

import com.example.servingwebcontent.models.Member;
import com.example.servingwebcontent.models.Product;
import com.example.servingwebcontent.models.SelectedProduct;
import com.example.servingwebcontent.utils.ReportUtils;
import com.example.servingwebcontent.utils.Templates;
import net.sf.dynamicreports.jasper.base.JasperReportDesign;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;

@Controller
@RequestMapping(value="/SP")
public class SPController {

    private List<Member> members;
    private List<Product> products;

    private Member selectedMember = null;
    private List<SelectedProduct> selectedProducts = new ArrayList<>();

    @GetMapping("/index")
    public String SPIndex() {
        members = new ArrayList<>();
        products = new ArrayList<>();

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "excel.xls";

        products = loadProducts(fileLocation);
        members = loadMembers(fileLocation);

        selectedProducts = new ArrayList<>();
        selectedMember = null;

        return "SP_index";
    }

    @PostMapping("/confirmMember")
    @ResponseBody
    public Map<String, Object> confirmMember(HttpServletRequest req) {
        HashMap<String, Object> result = new HashMap<>();
        String memberId = req.getParameter("member_id");

        Member member = getMemberById(memberId);

        if(member == null) {
            result.put("statusCode", -1);
            result.put("exportEnable", isExportEnable());
            return result;
        }

        selectedMember = member;

        result.put("statusCode", 1);
        result.put("data", member);
        result.put("exportEnable", isExportEnable());

        return result;
    }

    @PostMapping("/addItem")
    @ResponseBody
    public Map<String, Object> addItem(HttpServletRequest req) {
        String plu = req.getParameter("plu");
        Integer qty = Integer.valueOf(req.getParameter("qty"));
        String satuan = req.getParameter("satuan");

        HashMap<String, Object> result = new HashMap<>();

        if(!satuan.equalsIgnoreCase("CTN") && !satuan.equalsIgnoreCase("PCS")
            && !satuan.equalsIgnoreCase("MBT") && !satuan.equalsIgnoreCase("BOX")){
            result.put("message", "Satuan " + satuan + " not found");
            result.put("statusCode", -1);
            return result;
        }

        Product product = getProductById(plu);

        if(product == null) {
            result.put("message", "PLU " + plu + " not found");
            result.put("statusCode", -1);
            return result;
        }

        SelectedProduct selectedProduct = new SelectedProduct();
        selectedProduct.setPLU(product.getPLU());
        selectedProduct.setDeskripsi(product.getDeskripsi());
        selectedProduct.setFRAC(product.getFRAC());
        selectedProduct.setQty(qty);
        selectedProduct.setSatuan(satuan.toUpperCase());

        if(satuan.equalsIgnoreCase("CTN")) {
            selectedProduct.setHarga(product.getNET_CTN());
        } else if (satuan.equalsIgnoreCase("PCS")) {
            selectedProduct.setHarga(product.getNET_PCS());
        } else if (satuan.equalsIgnoreCase("MBT")) {
            selectedProduct.setHarga(product.getNET_MBT());
        } else if (satuan.equalsIgnoreCase("BOX")) {
            selectedProduct.setHarga(product.getNET_BOX());
        }

        selectedProduct.setTotal(selectedProduct.getHarga().multiply(new BigDecimal(selectedProduct.getQty())));
        selectedProducts.add(selectedProduct);

        result.put("statusCode", 1);
        result.put("data", selectedProducts);
        result.put("exportEnable", isExportEnable());

        return result;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest req, HttpServletResponse response) {

        OutputStream outputStream = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=SP.xls");

            outputStream = response.getOutputStream();

            buildFile(outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Product> loadProducts(String fileLocation){
        FileInputStream uploadedFile = null;
        List<Product> result = new ArrayList<>();

        try {
            uploadedFile = new FileInputStream(new File(fileLocation));
            int i = 0;
            HSSFWorkbook hwb = new HSSFWorkbook(uploadedFile);
            HSSFSheet sheet = hwb.getSheetAt(1);
            HSSFRow row = null;

            for (i = 1; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                HSSFCell cellDIV = row.getCell(0);
                HSSFCell cellDEP = row.getCell(1);
                HSSFCell cellKATB = row.getCell(2);
                HSSFCell cellPLU = row.getCell(3);
                HSSFCell cellDeskripsi = row.getCell(4);
                HSSFCell cellFRAC = row.getCell(5);
                HSSFCell cellNET_CTN = row.getCell(6);
                HSSFCell cellNET_PCS = row.getCell(7);
                HSSFCell cellNET_MBT = row.getCell(8);
                HSSFCell cellNET_BOX = row.getCell(9);
                HSSFCell cellTOT_CB = row.getCell(10);
                HSSFCell cellSLS = row.getCell(11);

                Product product = new Product();
                product.setDIV(getCellValue(cellDIV));
                product.setDEP(getCellValue(cellDEP));
                product.setKATB(getCellValue(cellKATB));
                if(getCellValue(cellPLU).contains(".")){
                    product.setPLU(getCellValue(cellPLU).split("\\.")[0]);
                } else {
                    product.setPLU(getCellValue(cellPLU));
                }
                product.setDeskripsi(getCellValue(cellDeskripsi));
                product.setFRAC(Double.valueOf(getCellValue(cellFRAC).equals("") ? "0" : getCellValue(cellFRAC)));
                product.setNET_CTN(new BigDecimal(getCellValue(cellNET_CTN).equals("") ? "0" : getCellValue(cellNET_CTN)));
                product.setNET_PCS(new BigDecimal(getCellValue(cellNET_PCS).equals("") ? "0" : getCellValue(cellNET_PCS)));
                product.setNET_MBT(new BigDecimal(getCellValue(cellNET_MBT).equals("") ? "0" : getCellValue(cellNET_MBT)));
                product.setNET_BOX(new BigDecimal(getCellValue(cellNET_BOX).equals("") ? "0" : getCellValue(cellNET_BOX)));
                product.setTOT_CB(new BigDecimal(getCellValue(cellTOT_CB).equals("") ? "0" : getCellValue(cellTOT_CB)));
                product.setSLS(new BigDecimal(getCellValue(cellSLS).equals("") ? "0" : getCellValue(cellSLS)));

                result.add(product);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<Member> loadMembers(String fileLocation){
        FileInputStream uploadedFile = null;
        List<Member> result = new ArrayList<>();

        try {
            uploadedFile = new FileInputStream(new File(fileLocation));
            int i = 0;
            HSSFWorkbook hwb = new HSSFWorkbook(uploadedFile);
            HSSFSheet sheet = hwb.getSheetAt(2);
            HSSFRow row = null;


            for (i = 1; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }


                HSSFCell cellKode = row.getCell(0);
                HSSFCell cellNama = row.getCell(1);
                HSSFCell cellAlamat = row.getCell(2);
                HSSFCell cellDesa = row.getCell(3);
                HSSFCell cellKabupaten = row.getCell(4);

                Member member = new Member();
                member.setKode(getCellValue(cellKode));
                member.setNama(getCellValue(cellNama));
                member.setAlamat(getCellValue(cellAlamat));
                member.setDesa(getCellValue(cellDesa));
                member.setKabupaten(getCellValue(cellKabupaten));

                result.add(member);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getCellValue(Cell cell) {// 判断单元格cell的类型并且做出相应的转换
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    strCell = String.valueOf(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    strCell = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    switch(cell.getCachedFormulaResultType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            strCell = cell.getNumericCellValue()+"";
                            break;
                        case Cell.CELL_TYPE_STRING:
                            strCell = cell.getRichStringCellValue().getString();
                            break;
                    }
                    break;
                default:
                    strCell = "";
                    break;
            }
        }else{
            strCell = "";
        }
        return strCell;
    }

    private Member getMemberById(String memberId){
        for (Member member : members) {
            if(member.getKode().equalsIgnoreCase(memberId)) {
                return member;
            }
        }

        return null;
    }

    private Product getProductById(String plu){
        for (Product product : products) {
            if(product.getPLU().equalsIgnoreCase(plu)) {
                return product;
            }
        }

        return null;
    }

    private void buildFile(OutputStream outputStream) {

        Date date = new Date();
        Locale locale = new Locale("id", "ID");
        String MMM = new SimpleDateFormat("MMM", locale).format(date).toUpperCase();
        String yyyy = new SimpleDateFormat("yyyy").format(date).toUpperCase();
        String ddMMMMMyyyy = new SimpleDateFormat("dd MMMMM yyyy", locale).format(date).toUpperCase();
        int totalQty = 0;
        BigDecimal totalTotal = new BigDecimal(0);

        for(SelectedProduct selectedProduct : selectedProducts) {
            totalQty += selectedProduct.getQty();
            totalTotal = totalTotal.add(selectedProduct.getTotal());
        }

        try {
            JasperXlsExporterBuilder xlsExporter = null;
            xlsExporter = export
                    .xlsExporter(outputStream).addSheetName("SP")
                    .setDetectCellType(true).setIgnorePageMargins(true)
                    .setShowGridLines(false)
                    .setRemoveEmptySpaceBetweenColumns(true);

            //Identity
            HorizontalListBuilder namaTitle  = cmp.horizontalList()
                    .add(ReportUtils.labelContent("NAMA", 9).setStyle(Templates.rootStyle), ReportUtils.labelContent(selectedMember.getNama().toUpperCase(), 32).setStyle(Templates.normalBotNoBorder), ReportUtils.label("", 18), ReportUtils.label("MEMBER", 10).setStyle(Templates.normalRightStyle));
            HorizontalListBuilder alamatTitle = cmp.horizontalList()
                    .add(ReportUtils.labelContent("ALAMAT", 9).setStyle(Templates.rootStyle), ReportUtils.labelContent(selectedMember.getAlamat().toUpperCase(), 32).setStyle(Templates.normalBotNoBorder), ReportUtils.label("", 18), ReportUtils.labelContent(selectedMember.getKode().toUpperCase(), 10).setStyle(Templates.normalRightStyle));

            HorizontalListBuilder totalSummary = cmp.horizontalList()
                    .add(ReportUtils.label("TOTAL", 54).setStyle(Templates.boldCenteredBorderStyle),
                            ReportUtils.label(totalQty+"", 3).setStyle(Templates.boldCenteredBorderStyle),
                            ReportUtils.label("", 5).setStyle(Templates.boldCenteredBorderStyle),
                            ReportUtils.label(String.format("%,.0f", totalTotal), 7).setStyle(Templates.boldRightBorderStyle));

            HorizontalListBuilder footer1 = cmp.horizontalList()
                    .add(ReportUtils.labelContent("", 47), ReportUtils.labelContent("KARAWANG, " + ddMMMMMyyyy, 22).setStyle(Templates.normalNoBorderCenteredStyle));
            HorizontalListBuilder footer2 = cmp.horizontalList()
                    .add(ReportUtils.labelContent("", 47), ReportUtils.labelContent("DIBUAT OLEH,", 22).setStyle(Templates.normalNoBorderCenteredStyle));
            HorizontalListBuilder footer3 = cmp.horizontalList()
                    .add(ReportUtils.labelContent("", 47), ReportUtils.labelContent("FARID", 22).setStyle(Templates.normalNoBorderCenteredStyle));

            HorizontalListBuilder blank1 = cmp.horizontalList().add(ReportUtils.label("", 0), ReportUtils.labelContent("", 0));

            // Column Table
            TextColumnBuilder<String> noColumn = col.column("NO", "no", type.stringType()).setFixedWidth(20);
            TextColumnBuilder<String> pluColumn = col.column("PLU", "plu", type.stringType()).setFixedWidth(70);
            TextColumnBuilder<String> deskripsiColumn = col.column("DESKRIPSI", "deskripsi", type.stringType()).setFixedWidth(320);
            TextColumnBuilder<String> fracColumn = col.column("FRAC", "FRAC", type.stringType()).setFixedWidth(60);
            TextColumnBuilder<String> hargaColumn = col.column("HARGA", "harga", type.stringType()).setFixedWidth(70);
            TextColumnBuilder<String> qtyColumn = col.column("QTY", "qty", type.stringType()).setFixedWidth(30);
            TextColumnBuilder<String> satuanColumn = col.column("SATUAN", "satuan", type.stringType()).setFixedWidth(50);
            TextColumnBuilder<String> totalColumn = col.column("TOTAL", "total", type.stringType()).setFixedWidth(70);

            JasperReportBuilder reportGeneration = report().title(
                        blank1,
                        cmp.text("SURAT PEMESANAN").setStyle(Templates.bold16CenteredStyle),
                        cmp.text("SPI / I / CIKAMPEK / " + MMM + " / " + yyyy).setStyle(Templates.bold10CenteredStyle),
                        blank1, blank1,
                        namaTitle,
                        alamatTitle, blank1, blank1
                    )
                    .setColumnTitleStyle(Templates.iniColumnTitleStyle)
                    .setColumnStyle(Templates.iniColumnStyle)
                    .ignorePageWidth()
                    .ignorePagination()
                    .columns(
                            noColumn.setStyle(Templates.normalCenteredStyle),
                            pluColumn.setStyle(Templates.normalCenteredStyle),
                            deskripsiColumn.setStyle(Templates.normalCenteredVerticalStyle),
                            fracColumn.setStyle(Templates.normalCenteredVerticalStyle),
                            hargaColumn.setStyle(Templates.normalCenteredVerticalStyle),
                            qtyColumn.setStyle(Templates.normalCenteredStyle),
                            satuanColumn.setStyle(Templates.normalCenteredStyle),
                            totalColumn.setStyle(Templates.normalRightBorderStyle)
                    )
                    .addSummary(totalSummary)
                    .addColumnFooter(
                            blank1,
                            footer1,
                            footer2,
                            blank1, blank1, blank1, blank1, blank1,
                            footer3
                    )
                    .setDataSource(createDataSourceex());

            reportGeneration.toXls(xlsExporter);
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private JRDataSource createDataSourceex() {
        DRDataSource dataSource=new DRDataSource("no","plu","deskripsi","FRAC","harga","qty","satuan","total");

        int no = 0;
        for (SelectedProduct selectedProduct : selectedProducts) {
            no++;

            String frac = selectedProduct.getFRAC().toString();
            String harga = selectedProduct.getHarga().toString();

            if(selectedProduct.getFRAC().toString().contains(".")){
                frac = selectedProduct.getFRAC().toString().split("\\.")[0];
            }

            if(selectedProduct.getHarga().toString().contains(".")){
                harga = selectedProduct.getHarga().toString().split("\\.")[0];
            }

            dataSource.add(
                    no+"",
                    selectedProduct.getPLU()+"",
                    selectedProduct.getDeskripsi()+"",
                    frac,
                    harga,
                    selectedProduct.getQty()+"",
                    selectedProduct.getSatuan()+"",
                    String.format("%,.0f", selectedProduct.getTotal())
            );
        }


        return dataSource;
    }

    private boolean isExportEnable(){
        if(selectedMember == null) return false;

        return selectedProducts != null && selectedProducts.size() > 0;
    }

}
