package com.example.taiwanworkdaylib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class APIUtil {
    public void update(){
        Thread GET_api_thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL("https://data.ntpc.gov.tw/api/datasets/308DCD75-6434-45BC-A95F-584DA4FED251/json?page=0&size=10000");
                    URLConnection connection = url.openConnection();
                    InputStream in = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in,"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while((line = br.readLine()) != null)
                    {
                        sb.append(line);
                    }
                    br.close();
                    isr.close();
                    in.close();
                    save_date_txt(sb.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        GET_api_thread.start();
    }

    public void save_date_txt(String api_text){
        String[] string_list = api_text.split("[}]"+"[,]");
        try {
            /* 寫入Txt檔案 */
            String joinedPath = new File(".", "sdcard").toString();
            joinedPath = new File(joinedPath, "data").toString();
            joinedPath = new File(joinedPath, "data.txt").toString();
            File file = new File(joinedPath); // 相對路徑，如果沒有則要建立一個新的output。txt檔案
//            file.createNewFile(); // 建立新檔案
            if(!file.exists())
            {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (String data : string_list){
                String string_data = data.replace("\"","")
                        .replace("[","")
                        .replace("]","")
                        .replace("{","")
                        .replace("}","");
                String[] k_vs = string_data.split(",");
                String final_string_data = "";
                for (String k_v : k_vs) {
                    String[] temp_list = k_v.split(":");
                    String key = temp_list[0];
                    String value = temp_list.length > 1 ? temp_list[1] : "";
                    if (key != null && value != null && key.equals("date")) {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//                        DateFormat df = DateFormat.getDateInstance();
//                        Date date = df.parse(value);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(date);
//                        value = sdf.format(calendar.getTime());
                        String[] split_dates = value.split("[/]");
                        String year = split_dates[0];
                        String month = split_dates[1];
                        String day = split_dates[2];
                        if(month.length() < 2){
                            month = "0"+month;
                        }
                        if(day.length() < 2){
                            day = "0"+day;
                        }
                        value = year+month+day;

                        final_string_data += key+":"+value+",";
                    }else if(key != null && value != null && key.equals("description")) {
                        final_string_data += key+":"+value+"";
                    }
                    else {
                        final_string_data += key+":"+value+",";
                    }
                }
                System.out.println(final_string_data);
                out.write(final_string_data + "\r\n");
            }
            out.flush(); // 把快取區內容壓入檔案
            out.close(); // 最後記得關閉檔案
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        APIUtil apiUtil = new APIUtil();
        apiUtil.update();
    }
}
