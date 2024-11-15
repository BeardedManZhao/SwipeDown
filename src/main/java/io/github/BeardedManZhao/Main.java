package io.github.BeardedManZhao;

import top.lingyuzhao.utils.DownUtils;
import top.lingyuzhao.utils.IOUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        if (args.length != 4){
            System.out.println("请输入参数：【数据包大小】【url或null】【每次下载完毕后中途停止时间（MS）】【一共的下载次数】");
            return;
        }
        System.out.println("数据包大小：" + args[0]);
        final URL url = args[1].equalsIgnoreCase("null") ? DownUtils.SwipeDownUrl.autopatchhk_yuanshen.getUrl() : new URL(args[1]);
        System.out.println("url：" + url);
        System.out.println("每次下载完毕后中途停止时间（MS）：" + args[2]);
        System.out.println("一共的下载次数：" + args[3]);
        System.out.println("Task begins...");
        // 设置数据包的大小 可容纳的单个数据包的值越大，则传输速度会越快！
        IOUtils.setDataPackageSize(Integer.parseInt(args[0]));
        // 开始进行刷下行操作
        final long l = DownUtils.swipeDown(url, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        System.out.println("本次下载共花费了：" + l + " size!");
    }
}