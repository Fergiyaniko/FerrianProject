package com.example.servingwebcontent.utils;

/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */


import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.tableOfContentsCustomizer;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;

import java.awt.Color;
import java.util.Locale;

import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;


public class Templates {
    public static final StyleBuilder rootStyle;
    public static final StyleBuilder boldStyle;
    public static final StyleBuilder italicStyle;
    public static final StyleBuilder boldCenteredStyle;
    public static final StyleBuilder boldCenteredBorderStyle;
    public static final StyleBuilder boldRightBorderStyle;
    public static final StyleBuilder normalCenteredStyle;
    public static final StyleBuilder normalCenteredVerticalStyle;
    public static final StyleBuilder iniColumnTitleStyle;
    public static final StyleBuilder iniColumnStyle;
    public static final StyleBuilder normalRightStyle;
    public static final StyleBuilder normalRightBorderStyle;
    public static final StyleBuilder normalNoBorder;
    public static final StyleBuilder normalNoBorderCenteredStyle;
    public static final StyleBuilder normalBotNoBorder;
    public static final StyleBuilder boldLeftStyle;
    public static final StyleBuilder bold12LeftStyle;
    public static final StyleBuilder bold14LeftStyle;
    public static final StyleBuilder bold12CenteredStyle;
    public static final StyleBuilder bold10CenteredStyle;
    public static final StyleBuilder bold16CenteredStyle;
    public static final StyleBuilder bold18CenteredStyle;
    public static final StyleBuilder bold22CenteredStyle;
    public static final StyleBuilder columnStyle;
    public static final StyleBuilder columnTitleStyle;
    public static final StyleBuilder columnTitleStyle_2;
    public static final StyleBuilder columnTitleStyle_3;
    public static final StyleBuilder groupStyle;
    public static final StyleBuilder groupStyle_2;
    public static final StyleBuilder groupStyle_3;
    public static final StyleBuilder subtotalStyle;
    public static final StyleBuilder totalRowStyle;
    public static final StyleBuilder totalRowStyle_2;
    public static final StyleBuilder textStyle;
    public static final StyleBuilder labelStyle;

    public static final ReportTemplateBuilder reportTemplate;
    public static final ReportTemplateBuilder reportTemplate_2;
    public static final CurrencyType currencyType;
    //public static final ComponentBuilder<?, ?> dynamicReportsComponent;
    public static final ComponentBuilder<?, ?> footerComponent;

    static {
        rootStyle           = stl.style().setPadding(2).setFontName("Calibri");
        boldStyle           = stl.style(rootStyle).bold();
        italicStyle         = stl.style(rootStyle).italic();
        boldCenteredStyle   = stl.style(boldStyle)
                .setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);

        normalCenteredStyle = stl.style(rootStyle).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE)
                .setBorder(stl.pen().setLineWidth(0.5f));
        boldCenteredBorderStyle = stl.style(boldCenteredStyle).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE)
                .setBorder(stl.pen().setLineWidth(0.5f));
        boldRightBorderStyle = stl.style(boldCenteredStyle).setAlignment(HorizontalAlignment.RIGHT, VerticalAlignment.MIDDLE)
                .setBorder(stl.pen().setLineWidth(0.5f));
        normalCenteredVerticalStyle = stl.style(rootStyle).setAlignment(HorizontalAlignment.LEFT, VerticalAlignment.MIDDLE)
                .setBorder(stl.pen().setLineWidth(0.5f));

        iniColumnTitleStyle = stl.style(boldCenteredStyle).setBorder(stl.pen().setLineWidth(0.5f));
        iniColumnStyle = stl.style(rootStyle).setBorder(stl.pen().setLineWidth(0.5f));

        normalRightStyle = stl.style(rootStyle).setAlignment(HorizontalAlignment.RIGHT, VerticalAlignment.MIDDLE);

        normalRightBorderStyle = stl.style(rootStyle).setAlignment(HorizontalAlignment.RIGHT, VerticalAlignment.MIDDLE).setBorder(stl.pen().setLineWidth(0.5f));;

        normalNoBorder = stl.style(rootStyle).setBorder(stl.pen().setLineWidth(0F));

        normalNoBorderCenteredStyle = stl.style(rootStyle).setBorder(stl.pen().setLineWidth(0F)).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);

        normalBotNoBorder = stl.style(rootStyle).setBottomBorder(stl.pen().setLineWidth(1F));

        boldLeftStyle = stl.style(boldStyle)
                .setAlignment(HorizontalAlignment.LEFT, VerticalAlignment.MIDDLE);
        bold12CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(12);
        bold12LeftStyle = stl.style(boldLeftStyle)
                .setFontSize(12);
        bold14LeftStyle = stl.style(boldLeftStyle)
                .setFontSize(14);
        bold16CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(16);
        bold10CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(10);
        bold18CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(18);
        bold22CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(22);
        columnStyle         = stl.style(rootStyle).setVerticalAlignment(VerticalAlignment.MIDDLE);
        columnTitleStyle    = stl.style(columnStyle)
                .setBorder(stl.pen1Point())
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBackgroundColor(Color.LIGHT_GRAY)
                .bold();
        columnTitleStyle_2    = stl.style(rootStyle)
                .setBorder(stl.pen())
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                //.setBackgroundColor(Color.LIGHT_GRAY)
                .bold();
        columnTitleStyle_3    = stl.style(columnStyle)
                .setBorder(stl.pen())
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBackgroundColor(new Color(207,226,243))
                .bold();

        groupStyle          = stl.style(boldStyle)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBackgroundColor(Color.LIGHT_GRAY);
        groupStyle_2        = stl.style(boldStyle)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        groupStyle_3          = stl.style(rootStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.JUSTIFIED);
        //.setBackgroundColor(Color.GRAY);
        subtotalStyle       = stl.style(boldStyle)
                .setTopBorder(stl.pen1Point());
        totalRowStyle       = stl.style(boldStyle)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        totalRowStyle_2     = stl.style(boldStyle)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        // label
        textStyle = stl.style(rootStyle).setFontSize(10);
        labelStyle = stl.style(textStyle).setHorizontalAlignment(HorizontalAlignment.LEFT).bold();

        StyleBuilder crosstabGroupStyle      = stl.style(columnTitleStyle);
        StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle)
                .setBackgroundColor(new Color(170, 170, 170));
        StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle)
                .setBackgroundColor(new Color(140, 140, 140));
        StyleBuilder crosstabCellStyle       = stl.style(columnStyle)
                .setBorder(stl.pen1Point());

        TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer()
                .setHeadingStyle(0, stl.style(rootStyle).bold());

        reportTemplate = template()
                .setLocale(Locale.CHINA)
                .setColumnStyle(columnStyle)
                .setColumnTitleStyle(columnTitleStyle)
                .setGroupStyle(groupStyle)
                .setGroupTitleStyle(groupStyle)
                .setSubtotalStyle(subtotalStyle)
                .highlightDetailEvenRows()
                .crosstabHighlightEvenRows()
                .setCrosstabGroupStyle(crosstabGroupStyle)
                .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
                .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
                .setCrosstabCellStyle(crosstabCellStyle)
                .setTableOfContentsCustomizer(tableOfContentsCustomizer);

        reportTemplate_2 = template()
                .setLocale(Locale.CHINA)
                .setColumnStyle(columnStyle)
                .setColumnTitleStyle(columnTitleStyle)
                .setGroupStyle(groupStyle_2)
                .setGroupTitleStyle(groupStyle_2)
                .setSubtotalStyle(subtotalStyle)
                .highlightDetailEvenRows()
                .crosstabHighlightEvenRows()
                .setCrosstabGroupStyle(crosstabGroupStyle)
                .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
                .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
                .setCrosstabCellStyle(crosstabCellStyle)
                .setTableOfContentsCustomizer(tableOfContentsCustomizer);

        currencyType = new CurrencyType();

        // TODO 临时注释顶部LOGO通栏
//		HyperLinkBuilder link = hyperLink("http://www.dynamicreports.org");
//		dynamicReportsComponent =
//		  cmp.horizontalList(
//		  	cmp.image(Templates.class.getResource("images/dynamicreports.png")).setFixedDimension(60, 60),
//		  	cmp.verticalList(
//		  		cmp.text("DynamicReports").setStyle(bold22CenteredStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
//		  		cmp.text("http://www.dynamicreports.org").setStyle(italicStyle).setHyperLink(link))).setFixedWidth(300);

        footerComponent = cmp.pageXslashY()
                .setStyle(
                        stl.style(boldCenteredStyle)
                                .setTopBorder(stl.pen1Point()));
    }

    /**
     * Creates custom component which is possible to add to any report band component
     */
    public static ComponentBuilder<?, ?> createTitleComponent(String label) {
        return cmp.horizontalList()
//		        .add(
//		        	dynamicReportsComponent,
//		        	cmp.text(label).setStyle(bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                .newRow()
                .add(cmp.line())
                .newRow()
                .add(cmp.verticalGap(10));
    }

    public static CurrencyValueFormatter createCurrencyValueFormatter(String label) {
        return new CurrencyValueFormatter(label);
    }

    public static class CurrencyType extends BigDecimalType {
        private static final long serialVersionUID = 1L;

        @Override
        public String getPattern() {
            return "$ #,###.00";
        }
    }

    private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {
        private static final long serialVersionUID = 1L;

        private String label;

        public CurrencyValueFormatter(String label) {
            this.label = label;
        }

        @Override
        public String format(Number value, ReportParameters reportParameters) {
            return label + currencyType.valueToString(value, reportParameters.getLocale());
        }
    }
}
