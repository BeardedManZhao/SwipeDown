package io.github.BeardedManZhao;

import com.alibaba.fastjson2.JSONObject;
import top.lingyuzhao.utils.DownUtils;
import top.lingyuzhao.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws InterruptedException, MalformedURLException, URISyntaxException {
        if (args.length < 4) {
            System.out.println("请输入参数：【数据包大小】【url或null】【每次下载完毕后中途停止时间（MS）】【一共的下载次数】【请求头配置JSON文件路径】");
            return;
        }
        System.out.println("数据包大小：" + args[0]);
        final URI uri = args[1].equalsIgnoreCase("null") ? DownUtils.SwipeDownUrl.autopatchhk_yuanshen.getUri() : new URI(args[1]);
        final URL url = args[1].equalsIgnoreCase("null") ? DownUtils.SwipeDownUrl.autopatchhk_yuanshen.getUrl() : new URL(args[1]);
        System.out.println("url：" + url);
        System.out.println("每次下载完毕后中途停止时间（MS）：" + args[2]);
        System.out.println("一共的下载次数：" + args[3]);
        // 设置数据包的大小 可容纳的单个数据包的值越大，则传输速度会越快！
        IOUtils.setDataPackageSize(Integer.parseInt(args[0]));
        if (args.length == 5) {
            System.out.println("使用携带请求头模式。");
            System.out.println("请求头信息：" + args[4]);
            JSONObject jsonObject;
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(Paths.get(args[4])))) {
                jsonObject = JSONObject.parseObject(IOUtils.getStringByStream(bufferedInputStream));
            } catch (IOException e) {
                throw new RuntimeException("加载请求头配置文件错误！", e);
            }
            // 开始进行刷下行操作
            HashMap<String, String> objectObjectHashMap = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                objectObjectHashMap.put(key, jsonObject.getString(key));
            }
            String remove = objectObjectHashMap.remove("swipeDown-HTTP-Method");
            final DownUtils.Method method = DownUtils.Method.valueOf(remove != null ? remove : "GET");
            System.out.println("请求模式：" + method.name());
            System.out.println("Task begins...");
            final long l = DownUtils.swipeDown(uri, Integer.parseInt(args[2]), Integer.parseInt(args[3]), method, objectObjectHashMap);
            System.out.println("本次下载共花费了：" + l + " size!");
        } else {
            // 设置数据包的大小 可容纳的单个数据包的值越大，则传输速度会越快！
            IOUtils.setDataPackageSize(Integer.parseInt(args[0]));
            // 开始进行刷下行操作
            System.out.println("Task begins...");
            final long l = DownUtils.swipeDown(url, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            System.out.println("本次下载共花费了：" + l + " size!");
        }
    }
}