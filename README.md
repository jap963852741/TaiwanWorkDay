# TaiwanHolidayCheck 台灣國曆假日(工作日)查詢
資料來源:

[政府行政機關辦公日曆表 - 政府資料開放平台](https://data.gov.tw/dataset/26557)

資料範圍 : 
2013/1/1 - 2021/12/31 <br>
更新日期 : (2020/10/27)
</br>
## Install
>>  implementation 'com.github.jap963852741:TaiwanWorkDay:v1.1.11'


## Usage
- 第一次使用需先獲取資料
>>new APIUtil().update();
<br></br>
- 設定日期
>>HolidayUtil holidayutil = new HolidayUtil();<br>
>>holidayutil.set_date("20210404");
</br>


## Example
- 是否為國定假日
>> holidayutil.isHoliday();<br>
>>return &nbsp; True
</br>

- 獲取節日類別
>> holidayutil.get_holidayCategory();<br>
>> return &nbsp; "放假之紀念日及節日"
</br>

- 獲取節日名稱
>> holidayutil.get_holiday_name();<br>
>> return &nbsp; "兒童節及民族掃墓節"
</br>

- 獲取描述
>> holidayutil.get_description();<br>
>> return &nbsp; "全國各機關學校放假一日，兒童節與民族掃墓節同一日且逢星期日，分別於四月二日及四月五日各補假一日。"
</br>
<br></br><br></br>


