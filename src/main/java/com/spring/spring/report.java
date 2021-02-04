package com.spring.spring;

import com.spring.spring.models.condot.tbl_product_pkg_level_details;
import com.spring.spring.models.condot.tbl_production_packaging_data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class report {



    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    // static File file = new File("C:\\Users\\vladislav.sorokin\\Desktop\\Closed jobs.xls");
    static File file;

    public static File mapReport(String jobID, Iterable<tbl_production_packaging_data> posts) throws SQLException, IOException, NullPointerException {

        file = new File("\\\\backup\\Share\\CondotReports\\MapReport_"+jobID+".xlsx");

/*        List<tbl_production_packaging_data> listAllGoods = new ArrayList<>();*/
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Report map");

        List<tbl_production_packaging_data> allTER = new ArrayList<>();
        List<tbl_production_packaging_data> allSEC3 = new ArrayList<>();
        List<tbl_production_packaging_data> allSEC = new ArrayList<>();

        for (tbl_production_packaging_data list : posts) {
            if (list.getPkg_level().equals("TER")) {
                allTER.add(list);
            }
            if (list.getPkg_level().equals("IL3")) {
                allSEC3.add(list);
            }
            if (list.getPkg_level().equals("SEC")) {
                allSEC.add(list);
            }
        }

        int rownum = 1;
        Cell cell;
        Row row;
        boolean b=false;

        for (tbl_production_packaging_data ter : allTER) {

            rownum = sheet.getLastRowNum() + 1;
            row = sheet.createRow(rownum);
            cell = row.createCell(0);
            cell.setCellValue(ter.getUidcode());

            if (allSEC3.size() == 0) {
                for (tbl_production_packaging_data sec : allSEC) {
                    if (sec.getNextlevel_uidcode()!=null  && sec.getNextlevel_uidcode().equals(ter.getUidcode())) {
                        if (!b) {
                            rownum = sheet.getLastRowNum() + 1;
                            row = sheet.createRow(rownum);
                            cell = row.createCell(3);
                            cell.setCellValue(sec.getUidcode());
                            b=true;

                        }
                        else {
                            rownum = sheet.getLastRowNum();
                            row = sheet.getRow(rownum);
                            cell = row.createCell(6);
                            cell.setCellValue(sec.getUidcode());
                            b=false;
                        }
                    }

                }
            } else {

                for (tbl_production_packaging_data sec3 : allSEC3) {

                    if (sec3.getNextlevel_uidcode()!=null  &&sec3.getNextlevel_uidcode().equals(ter.getUidcode())) {
                        rownum = sheet.getLastRowNum() + 1;
                        row = sheet.createRow(rownum);
                        cell = row.createCell(3);
                        cell.setCellValue(sec3.getUidcode());
                        b=false;
                        for (tbl_production_packaging_data sec : allSEC) {

                            if (sec.getNextlevel_uidcode()!=null && sec.getNextlevel_uidcode().equals(sec3.getUidcode())) {

                                if (!b) {
                                    rownum = sheet.getLastRowNum() + 1;
                                    row = sheet.createRow(rownum);
                                    cell = row.createCell(6);
                                    cell.setCellValue(sec.getUidcode());
                                    b=true;

                                }
                                else {
                                    rownum = sheet.getLastRowNum();
                                    row = sheet.getRow(rownum);
                                    cell = row.createCell(9);
                                    cell.setCellValue(sec.getUidcode());
                                    b=false;
                                }
                            }

                        }


                    }
                }
            }
        }
        try {
        //    file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
return file;
    }

    public static File detailedJob(String jobID, Iterable<tbl_production_packaging_data> posts,
                                   Iterable<tbl_product_pkg_level_details> list3) throws SQLException, IOException, NullPointerException, InterruptedException {
  /*      "\\\\backup\\Share\\CondotReports\\DetailedJob_"+jobID+".xlsx");*/

        file = new File("\\\\backup\\Share\\CondotReports\\DetailedJob_"+jobID+".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle style = createStyleForTitle(workbook);
        XSSFSheet sheet2 = workbook.createSheet("Detailed Job (Ter)");
        XSSFSheet sheet3 = workbook.createSheet("Detail Job (Sec-3)");
        XSSFSheet sheet4 = workbook.createSheet("Detail Job (Sec)");

        Cell cell;
        Row row;

        row = sheet4.createRow(0);

        cell = row.createCell(0);
        cell.setCellValue("Aggregated");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Printed and Rejected");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("Sampled UID");
        cell.setCellStyle(style);

        cell = row.createCell(12);
        cell.setCellValue("Unused UID");
        cell.setCellStyle(style);

        cell = row.createCell(15);
        cell.setCellValue("Accepted & NOT Aggregated");
        cell.setCellStyle(style);

        cell = row.createCell(18);
        cell.setCellValue("All Secondary");
        cell.setCellStyle(style);

        int t1 = 2;int t2 = 2;int t4 = 2;
        int p3 = 2;int p4 = 2;int p5 = 2;int p6 = 2;int p7 = 2;int p8 = 2;
        int s2 = 2;int s3 = 2;int s4 = 2;int s5 = 2;int s6 = 2;int s7 = 2;int s8 = 2;
        boolean b = false;



        row = sheet2.createRow(0);
        cell = row.createCell(15);
        cell.setCellValue("Secondary-3");
        cell.setCellStyle(style);

        cell = row.createCell(18);
        cell.setCellValue("Tertiary");
        cell.setCellStyle(style);

        for (tbl_product_pkg_level_details list2 : list3) {
            if (list2.getPkg_level().equals("IL3")){
                if (sheet2.getRow(1) == null)
                    row = sheet2.createRow(1);
                else
                    row = sheet2.getRow(1);

                cell = row.createCell(15);
                cell.setCellValue(list2.getPcmapcount());
                cell.setCellStyle(style);
            }

            if (list2.getPkg_level().equals("TER")){
                if (sheet2.getRow(1) == null)
                    row = sheet2.createRow(1);
                else
                    row = sheet2.getRow(1);
                cell = row.createCell(18);
                cell.setCellValue(list2.getPcmapcount());
                cell.setCellStyle(style);
            }
        }


        for (tbl_production_packaging_data list : posts) {

            if (list.getPkg_level().equals("TER")) {
// printed
                if ( list.getIsinspected().equals("1") && list.getIssampled().equals("0")) {

                    if (sheet2.getRow(t1) == null)
                        row = sheet2.createRow(t1);
                    else
                        row = sheet2.getRow(t1);
                    cell = row.createCell(0);
                    cell.setCellValue(list.getUidcode());
                    t1++;
                }
                //rejected
                if (list.getIsrejected().equals("1")) {
                    if (sheet2.getRow(t2) == null)
                        row = sheet2.createRow(t2);
                    else
                        row = sheet2.getRow(t2);
                    cell = row.createCell(3);
                    cell.setCellValue(list.getUidcode());
                    t2++;
                }

                if (list.getIsprinted().equals("0")) {
                    if (sheet2.getRow(t4) == null)
                        row = sheet2.createRow(t4);
                    else
                        row = sheet2.getRow(t4);
                    cell = row.createCell(6);
                    cell.setCellValue( list.getUidcode());
                    t4++;

                }

            }


            if (list.getPkg_level().equals("IL3")) {

//aggregated
                if ( list.getIsaggregated().equals("1")) {

                    if (sheet3.getRow(p3) == null)
                        row = sheet3.createRow(p3);
                    else
                        row = sheet3.getRow(p3);
                    cell = row.createCell(0);
                    cell.setCellValue(list.getUidcode());
                    p3++;

                }

//rejected
                if (list.getIsrejected().equals("1")) {

                    if (sheet3.getRow(p4) == null)
                        row = sheet3.createRow(p4);
                    else
                        row = sheet3.getRow(p4);
                    cell = row.createCell(3);
                    cell.setCellValue(list.getUidcode());
                    p4++;

                }
//sampled
                if (list.getIssampled().equals("1")) {

                    if (sheet3.getRow(p5) == null)
                        row = sheet3.createRow(p5);
                    else
                        row = sheet3.getRow(p5);
                    cell = row.createCell(6);
                    cell.setCellValue(list.getUidcode());
                    p5++;

                }
//unused
                if (list.getIsprinted().equals("0")) {

                    if (sheet3.getRow(p6) == null)
                        row = sheet3.createRow(p6);
                    else
                        row = sheet3.getRow(p6);
                    cell = row.createCell(9);
                    cell.setCellValue(list.getUidcode());
                    p6++;

                }
                // accepted and not aggregated
                if ( list.getIsinspected().equals("1") &&  list.getIsaggregated().equals("0") &&
                        list.getIsrejected().equals("0") && list.getIssampled().equals("0")) {

                    if (sheet3.getRow(p7) == null)
                        row = sheet3.createRow(p7);
                    else
                        row = sheet3.getRow(p7);
                    cell = row.createCell(12);
                    cell.setCellValue(list.getUidcode());
                    p7++;

                }
                if (sheet3.getRow(p8) == null)
                    row = sheet3.createRow(p8);
                else
                    row = sheet3.getRow(p8);
                cell = row.createCell(15);
                cell.setCellValue(list.getUidcode());
                p8++;

            }

            if (list.getPkg_level().equals("SEC")) {
                //sec report
                //aggregated
                if (  list.getIsaggregated().equals("1")) {
                    if (b==false) {
                        if (sheet4.getRow(s3) == null)
                            row = sheet4.createRow(s3);
                        else
                            row = sheet4.getRow(s3);
                        cell = row.createCell(0);
                        cell.setCellValue(list.getUidcode());
                        s3++;
                        b=true;
                        s2++;
                    }
                    else {
                        row = sheet4.getRow(s3-1);
                        cell = row.createCell(3);
                        cell.setCellValue(list.getUidcode());
                        b=false;
                        s2++;

                    }

                }
//rejected
                if (list.getIsrejected().equals("1")) {

                    if (sheet4.getRow(s4) == null)
                        row = sheet4.createRow(s4);
                    else
                        row = sheet4.getRow(s4);
                    cell = row.createCell(6);
                    cell.setCellValue(list.getUidcode());
                    s4++;
                }
//sampled
                if (list.getIssampled().equals("1")) {

                    if (sheet4.getRow(s5) == null)
                        row = sheet4.createRow(s5);
                    else
                        row = sheet4.getRow(s5);
                    cell = row.createCell(9);
                    cell.setCellValue(list.getUidcode());
                    s5++;
                }
//unused
                if (list.getIsprinted().equals("0")) {

                    if (sheet4.getRow(s6) == null)
                        row = sheet4.createRow(s6);
                    else
                        row = sheet4.getRow(s6);
                    cell = row.createCell(12);
                    cell.setCellValue(list.getUidcode());
                    s6++;

                }
                // accepted and not aggregated
                if (list.getIsinspected().equals("1") &&   list.getIsaggregated().equals("0")
                        && list.getIsrejected().equals("0") && list.getIssampled().equals("0")) {

                    if (sheet4.getRow(s7) == null)
                        row = sheet4.createRow(s7);
                    else
                        row = sheet4.getRow(s7);
                    cell = row.createCell(15);
                    cell.setCellValue(list.getUidcode());
                    s7++;
                }

                s8++;


            }

        }

        //ter report
        row = sheet2.getRow(0);
        cell = row.createCell(0);
        cell.setCellValue("Printed and Accepted");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Printed and Rejected");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Unused UID");
        cell.setCellStyle(style);


        if (sheet2.getRow(1) == null)
            row = sheet2.createRow(1);
        else
            row = sheet2.getRow(1);

        cell = row.createCell(0);
        cell.setCellValue(t1 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue(t2 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue(t4 - 2);
        cell.setCellStyle(style);

        //sec-3 report

        row = sheet3.createRow(0);

        cell = row.createCell(0);
        cell.setCellValue("Aggregated");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Printed and Rejected");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Sampled UID");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("Unused UID");
        cell.setCellStyle(style);

        cell = row.createCell(12);
        cell.setCellValue("Accepted & NOT Aggregated");
        cell.setCellStyle(style);

        cell = row.createCell(15);
        cell.setCellValue("All Secondary-3");
        cell.setCellStyle(style);

        row = sheet3.createRow(1);

        cell = row.createCell(0);
        cell.setCellValue(p3 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue(p4 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue(p5 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue(p6 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(12);
        cell.setCellValue(p7 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(15);
        cell.setCellValue(p8 - 2);
        cell.setCellStyle(style);

        row = sheet4.createRow(1);

        cell = row.createCell(0);
        cell.setCellValue(s2 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue(s4 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue(s5 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(12);
        cell.setCellValue(s6 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(15);
        cell.setCellValue(s7 - 2);
        cell.setCellStyle(style);

        cell = row.createCell(18);
        cell.setCellValue(s8 - 2);
        cell.setCellStyle(style);

        try {
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            //     outFile.flush();
            outFile.close();
            workbook.close();
        } catch (Exception e) {       }
        return file;
    }
}