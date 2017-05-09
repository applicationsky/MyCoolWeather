MyCoolWeather
====
使用了guolin(书上)的API
-------

* 解决了Litepal无法写入数据库的异常:
			原因也很出乎意料 是因为litepal配置文件中数据库的文件名太长了
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
```xml



#一级标题  
##二级标题  
###三级标题  
####四级标题  
#####五级标题  
######六级标题  
这是一段普通的文本，  
直接回车不能换行，<br>  
要使用\<br>  

如果希望文字变得高亮，例如 `这样` . 请 `使用` 双引号 `哈哈`

[我的博客](http://blog.csdn.net/guodongxiaren "悬停显示")

* 别名：隔壁老王  
* 英文名：Jelly 

此外还有二级圆点和三级圆点。就是多加一个Tab。

* 编程语言  
    * 脚本语言  
        * Python 
	
缩进的含义还是比较明显的:	

>数据结构  
>>树  
>>>二叉树  
>>>>平衡二叉树  
>>>>>满二叉树 

怎么插入图片呢？这样插入最简单了

![](http://www.baidu.com/img/bdlogo.gif)  

想要实现悬停的效果

![baidu](http://www.baidu.com/img/bdlogo.gif "百度logo")  

图片超链接

[![baidu]](http://baidu.com)  
[baidu]:http://www.baidu.com/img/bdlogo.gif "百度Logo" ]

插入代码

```Java
public static void main(String[] args){}//Java
```Java

```C
int main(int argc,char* argv)//C
```C

我其实不信的，这个Readme还能写出花来




