package com.cacheserverdeploy.deploy;
import com.cdn.Graph;
import com.cdn.SearchRoute;
import com.cdn.MyUtils;

public class Deploy
{
    public static String[] deployServer(String[] graphContent)
    {
    	//读取第一行info
    	String str1 = graphContent[0];
    	String[] strArray = str1.split("\\s{1,}");
        int[] info = new int[3];
        for (int i = 0; i < 3; ++i) {
        	info[i] = Integer.parseInt(strArray[i]);
        }
        //读取第三行cdnCost
        String str2 = graphContent[2];
        int cdnCost = Integer.parseInt(str2);
        //网络链路转化为数组
        String[] str3 = new String[info[1]];
        for(int i = 0; i< info[1]; i++){
        	str3[i]=graphContent[i+4];
        }
        //消费节点转化为数组
        String[] str4 = new String[info[2]];
        for(int i = 0; i< info[2]; i++){
        	str4[i]=graphContent[info[1]+i+5];
        }
        //info[]是第一行数据，cdnCost是花费，netArray是网络链路，consumeArray是消费链路
        int[][] netArray = new int[info[1]][4];
        int[][] consumeArray = new int[info[2]][3];
        netArray = MyUtils.splitString(4,str3);
        consumeArray = MyUtils.splitString(3,str4);
        Graph netGraph = new Graph(info, netArray, consumeArray);
        
//        //最简单部署
//        int num = consumeArray.length;
//        String[] outString = new String[num + 2];
//        for(int i =0; i < num; i++){
//        	String str11= String.valueOf(consumeArray[i][1]);
//        	String str21= String.valueOf(consumeArray[i][0]);
//        	String str31= String.valueOf(consumeArray[i][2]);
//        	outString[i + 2] = new String(str11 + " " + str21 + " " + str31 + " ");
//        }
//        outString[0] = String.valueOf(num);
//        outString[1] = "\n";   
        
        //下一步部署策略
        SearchRoute sr = new SearchRoute(info, cdnCost, netGraph); 
//        System.out.println(sr.getOutPath());
        return sr.getOutPath();
    }

}
