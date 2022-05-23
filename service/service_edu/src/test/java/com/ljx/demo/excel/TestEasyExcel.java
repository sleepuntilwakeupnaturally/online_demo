package com.ljx.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        /*// 实现写操作
        // 1.设置写入文件夹名字和 excel名称
        String filename = "D:\\write.xlsx";

        // 调用easyExcel里方法实现写操作
        // write 里两个参数：1 参数文件路径名称 2.实体类class
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());*/

        // 实现读操作1
        String filename = "D:\\write.xlsx";
        EasyExcel.read(filename, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    // 创建返回方法list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("www" + i);
            list.add(data);
        }

        return list;
    }


}
