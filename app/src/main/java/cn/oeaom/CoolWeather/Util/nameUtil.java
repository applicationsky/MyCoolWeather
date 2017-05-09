package cn.oeaom.CoolWeather.Util;

/**
 * Created by MaiBenBen on 2017/5/9.
 */

public class nameUtil {
    public static String getProvinceName(String name)
    {
        if(name.equals("北京") || name.equals("天津")||name.equals("重庆")||
                name.equals("上海")||name.equals("北京"))
        {
            name+="市";
        }else if(name.equals("香港")||name.equals("澳门"))
        {
            name+="特别行政区";
        }else if(name.equals("内蒙古")){
//                    内蒙古自治区（1947年5月1日）
//                    新疆维吾尔自治区（1955年10月1日）
//                    广西壮族自治区（1958年3月5日）
//                    宁夏回族自治区（1958年10月25日）
//                    西藏自治区（1965年9月1日）
            name+="自治区";
        }else if(name.equals("新疆")){
            name+="维吾尔自治区";
        }else if(name.equals("广西")){
            name+="壮族自治区";
        }else if(name.equals("宁夏")){
            name+="回族自治区";
        }else if(name.equals("西藏")){
            name+="自治区";
        }else{
            name+="省";
        }
        return name;
    }

}
