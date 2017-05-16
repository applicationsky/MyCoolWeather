Welcome to MyCoolWeather!
=========================


我的酷欧天气是一个简洁的**即时**天气软件.可以查看一些简单的天气信息，
借助了litepal数据库缓存了城市信息到本地、并通过城市信息中的城市代码到和风天气查询
对应城市的天气信息，代码遵循Apache License Version 2.0发布,更多版权信息请参阅LICENSE文件。
* 本项目主要用于学习和交流，不作为商业用途。
* 您可以在法律允许的范围内，包括但不限于对本软件商用、分发、修改、专利授权、私用、附加协议
* 再发布的过程中需声明所使用的协议版权信息、变更信息
----------


功能介绍
-------------

MyCoolWeather是一款**免费**的、能够即时更新您的所在地的天气信息的软件.
* 查看您所在城市的天气信息
* 罗列出全国的所有的省市县
* 自由地切换城市
* 手动更新天气信息、自动更新天气信息


> **请注意:**

> - 免费版本的天气信息来源于**和风天气**API免费版，仅有4,000次/天访问量，只有提供一些基础的天气信息。
> - 要使程序更加**健壮**，您必须自行替换程序中的API Key为您自有的API Key


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
* 取消了使用方正字体
	由于使用方正字体涉及侵权，因此取消了使用方正兰亭极细黑体。改为AndroidClock字体

### 附加信息

1:若您没有开启即时更新服务，则该应用可能不会自动更新天气信息。另，若您的设备已开启省电模式，该应用仍不会自动更新天气信息。
2:免费版本只支持少许天气信息的浏览和有限的查询次数。
3:和风天气是一个有提供json返回的免费的天气api服务提供商
4:由于程序开源，所以程序中附带的API KEY的免费次数可能很快就会被用完