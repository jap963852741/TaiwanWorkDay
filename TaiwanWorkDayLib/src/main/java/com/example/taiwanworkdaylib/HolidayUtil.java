package com.example.taiwanworkdaylib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.InputStreamReader;
import java.util.HashMap;

public class HolidayUtil {
    HashMap<String,String> map_data = new HashMap<String, String>();
    String date = "";
    public void set_date(String date){
        this.date = date;
        Get_data(this.date);
    }
    public void Get_data(final String date){
        this.map_data = new HashMap<String, String>();
        Thread GET_date_data = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    String pathname = ".\\data\\data.txt";
                    File filename = new File(pathname);
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
                    BufferedReader br = new BufferedReader(reader);
                    String line = "";
                    line = br.readLine();
                    boolean found_data = false;
                    while (line != null && !line.equals("") && !found_data) {
                        line = br.readLine(); // 一次讀入一行資料
//                        System.out.println(line);
                        if(line != null && !line.equals("")) {
                            String[] k_vs = line.split(",");
                            for (String k_v : k_vs) {
                                String[] temp_list = k_v.split(":");
                                String key = temp_list[0];
                                String value = temp_list.length > 1 ? temp_list[1] : "";
                                if (key != null && value != null && key.equals("date") && value.equals(date)) {
                                    String[] temp_k_vs = line.split(",");
                                    for (String temp_k_v : temp_k_vs){
                                        String[] s_temp_list = temp_k_v.split(":");
                                        String s_key = s_temp_list[0];
                                        String s_value = s_temp_list.length > 1 ? s_temp_list[1] : "";
                                        map_data.put(s_key,s_value);
                                    }
                                    found_data = true;
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        GET_date_data.start();
        try {
            GET_date_data.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("GET_date_data finish");
    }







    public boolean isHoliday(){
        if (this.map_data != null && !this.map_data.isEmpty()){
            System.out.println(this.map_data);
            return true;
        }
        return false;
    }
    public String get_holidayCategory(){
        if (this.map_data != null && !this.map_data.isEmpty()){
            System.out.println(this.map_data);
            return this.map_data.get("holidayCategory");
        }
        return "is not holiday";
    }
    public String get_description(){
        if (this.map_data != null && !this.map_data.isEmpty()){
//            System.out.println(this.map_data);
            if (this.map_data.get("description") == "") {
                return "no description";
            }
            return this.map_data.get("description");

        }
        return "no description";
    }


    public static void main(String[] args) {
        HolidayUtil abc = new HolidayUtil();
        abc.set_date("20210411");
        System.out.println(abc.get_description());

    }
}


