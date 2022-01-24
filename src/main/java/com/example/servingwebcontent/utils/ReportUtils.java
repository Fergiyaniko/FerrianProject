package com.example.servingwebcontent.utils;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.component.FillerBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;

/**
 * 报表工具类
 *
 *
 *
 */
public class ReportUtils {
    public static final String CN_LANGUAGE="China";
    private static final int cellWidth = 10;
    private static final int cellHeight = 18;

    private static StyleBuilder textStyle = stl.style().setFontSize(10);
    private static StyleBuilder labelStyle = stl.style(textStyle).setHorizontalAlignment(HorizontalAlignment.LEFT).bold();
    // 报表类型
    public enum ReportType{
        E, //Excel报表
        P  //Pdf报表
    }

    /**
     * 空白行
     * @param size
     * @return
     */
    private static FillerBuilder emptyCell(int size) {
        return cmp.gap(cellWidth * size, cellHeight);
    }

    /**
     * 行类型显示数据(加粗)
     * @param text
     * @param size
     * @return
     */
    public static TextFieldBuilder<String> label(String text, int size) {
        return label(text, size, labelStyle);
    }

    /**
     * 行类型显示数据(不加粗)
     * @param text
     * @param size
     * @return
     */
    public static TextFieldBuilder<String> labelContent(String text, int size) {
        return label(text, size, textStyle);
    }

    public static TextFieldBuilder<String> label(String text, int size, StyleBuilder style) {
        TextFieldBuilder<String> label = cmp.text(text)
                .setFixedWidth(cellWidth * size);
        if (style != null) {
            label.setStyle(style);
        }
        return label;
    }

}

