package com.cdn;
import java.util.Arrays;
import java.util.Comparator;

public class MyUtils {
	static Graph netGraph = new Graph();
	
	public MyUtils(Graph netGraph) {
		this.netGraph = netGraph;
	}
	//将字符串数组按照 ， 分开成二维整形数组
	public static int[][] splitString(int elementSize, String[] str){
		int length = str.length;
		int[][] int2Array = new int[length][elementSize];
		
        for(int i = 0; i < length; i++){
            String[] strArray = str[i].split("\\s{1,}");
            int[] intArray = new int[elementSize];
            for (int j = 0; j < strArray.length; ++j) {
            	int2Array[i][j] = Integer.parseInt(strArray[j]);
            }
        }
		return int2Array;
	}
	//得到一条路径的最小宽带
	public static int getMinBand(String string) {
		String[] netNode = string.split(" ");
		int len = netNode.length;
		int start, end;
		int band= SearchRoute.INF,minBand = SearchRoute.INF;
		NetEdge tmp;
		String trimString1, trimString2;
		for(int i =0; i < len - 2; i++){
			trimString1 = netNode[i].trim();
			trimString2 = netNode[i + 1].trim();
			start = Integer.parseInt(trimString1);
			end = Integer.parseInt(trimString2);  
			tmp = netGraph.node[start].from;
			
			while(tmp.to != end){
				tmp = tmp.next;
				band = tmp.band;
			if(band < minBand)
				minBand = band;
			}
		}
		return minBand;
	}
	//按照cost排序
    public static void costSort(String[][] ob, final int order) {    
        Arrays.sort(ob, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {    
          	  String[] one = (String[]) o1;    
          	  String[] two = (String[]) o2;       
                    int k = order;    
                    if (Integer.parseInt(one[k]) >= Integer.parseInt(two[k]) ) {    
                        return 1;    
                    } else if (Integer.parseInt(one[k]) < Integer.parseInt(two[k])) {    
                        return -1;    
                    }   
                return 0;    
            }    
        });   
    }
    /**
     * @function 输入行数和路径字符串数组，按格式要求输出
     * @param linesNum
     * @param outString
     * @return
     */
	static String[] spliteString(int linesNum, String outString[]){
		String[] str = new String[linesNum + 2];
		int len = outString.length;
		str[0] = Integer.toString(linesNum);
		str[1] = "\r\n";
		for(int i =0; i < len; i++){
			str[i + 2] = outString[i];
		}
		return str;
	}
}

