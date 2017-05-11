Welcome to MyCoolWeather!
=========================


我的酷欧天气是一个**即时**[^note]天气软件.源自于书上的项目，借鉴了书上和百度上不少的东西(>_<).

----------


功能介绍
-------------

MyCoolWeather是一款**免费**[^note1]的、能够即时更新您的所在地的天气信息的软件.
* 查看您所在城市的天气信息
* 罗列出全国的所有的省市县
* 自由地切换城市
* 手动更新天气信息、自动更新天气信息


> **请注意:**

> - 免费版本的天气信息来源于**和风天气**[^note2]API免费版，仅有4,000次/天访问量，只有提供一些基础的天气信息。
> - 要使程序可用，您必须自行替换程序中的API Key为您自有的API Key


遇到的问题
-----------------

* 解决了Litepal无法写入数据库的异常:
			原因也很出乎意料 是因为litepal配置文件中数据库的文件名太长了,
一开始认为是别的原因，代码或者是什么的。
```xml
<?xml version="1.0" encoding="utf-8"?>
<litepal>
    <dbname value="weather" />    <!--这里之前的代码是<dbname value="my_cool_weather" />-->
        <version value="1" />
    <list>
        <mapping class="cn.oeaom.CoolWeather.JavaBeanForDB.City" />
        <mapping class="cn.oeaom.CoolWeather.JavaBeanForDB.Province" />
        <mapping class="cn.oeaom.CoolWeather.JavaBeanForDB.Country" />
    </list>
</litepal>
```


### 附加信息


  [^note]:若您没有开启即时更新服务，则该应用可能不会自动更新天气信息。另，若您的设备已开启省电模式，该应用仍不会自动更新天气信息。
[^note1]:xxx
[^note2]:xxx