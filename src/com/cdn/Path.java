package com.cdn;

import java.util.Arrays;

public class Path {
	int netId;
	String[][] pathInfo;//pathInfo[1]保存网络节点id，pathInfo[2]保存pathInfo[0]到pathInfo[1]路径信息,pathInfo[3]保存最短路总权重
	
	static final int INF = Integer.MAX_VALUE;

	public Path(int netNode, Graph graph) {
		dijkstra(netNode, graph);
	}
	/*功能：计算网络节点netNode到每个消费节点的最短路，运用dijkstra算法
	 * 输入：网络节点：netNode，图：netGraph
	 * */
	public void dijkstra(int netNode, Graph netGraph) {
		int netNodeNum = netGraph.netSize;
		int len = netGraph.netSize;
		int consumeSize = netGraph.consumeSize;
		this.netId = netNode; 
		pathInfo = new String[consumeSize][4];
		int[] prev = new int[netNodeNum];
		int[] sumCost = new int[netNodeNum];
		int vs = netNode;
		int[][] consumeArray = netGraph.consumeArray;

		 	//	String[][] findDijkstra(int vs, int[] prev, int[] sumCost, int[][] consumeToNet) {
	        // flag[i]=true表示"顶点vs"到"顶点i"的最短路径已成功获取。
	        boolean[] flag = new boolean[len];
	        // 初始化
	        Arrays.fill(prev, vs);
	        prev[vs] = -1;
	        for (int i = 0; i < len; i++) {
	            flag[i] = false;            // 顶点i的最短路径还没获取到。
	            sumCost[i] = getWeight(netGraph ,vs, i); // 顶点i的最短路径为"顶点vs"到"顶点i"的权。
	        }
	        // 对"顶点vs"自身进行初始化
	        flag[vs] = true;
	        sumCost[vs] = 0;
	        // 遍历mVexs.length-1次；每次找出一个顶点的最短路径。
	        int k = vs;
	        for (int i = 1; i < len; i++) {
	            // 寻找当前最小的路径；
	            // 即，在未获取最短路径的顶点中，找到离vs最近的顶点(k)。
	            int min = INF;
	            for (int j = 0; j < len; j++) {
	                if (flag[j]==false && sumCost[j]<min) {
	                    min = sumCost[j];
	                    k = j;
	                }
	            }
	            // 标记"顶点k"为已经获取到最短路径
	            flag[k] = true;

	            // 修正当前最短路径和前驱顶点
	            // 即，当已经"顶点k的最短路径"之后，更新"未获取最短路径的顶点的最短路径和前驱顶点"。
	            for (int j = 0; j < len; j++) {
	                int tmp = getWeight(netGraph, k, j);
	                tmp = (tmp==INF ? INF : (min + tmp)); // 防止溢出
	                if (flag[j]==false && (tmp<sumCost[j]) )
	                {
	                    sumCost[j] = tmp;
	                    prev[j] = k;
	                }
	            }
	        }
	        // 打印dijkstra最短路径的结果
	        int n = 0;
	        for (int i = 0; i < netNodeNum; i++)
	        	for(int j = 0; j < consumeSize; j++){
	        		if(i == consumeArray[j][1]){
	        			pathInfo[n][0] = Integer.toString(netId);
	        			pathInfo[n][1] = Integer.toString(j);
	        			pathInfo[n][2] = printPath(i, prev) + Integer.toString(consumeArray[j][0]);
	        			pathInfo[n][3] = Integer.toString(sumCost[i]);
	        			n++;
	        		}
	        	}
	    }
	//打印路径信息，后期注释掉
		String printPath(int consumeNode, int[] prev){
			String str =new String();
			int tmp = consumeNode;
			while(tmp != -1){
				str = Integer.toString(tmp) +" "+ str;
				tmp = prev[tmp];
			}
			return str;
		}
		//重写ListUDG中dijkstra的getWeight()方法
	    int getWeight(Graph netGraph,int start, int end) {
	        if (start==end)
	            return 0;
	        NetEdge ne = netGraph.node[start].from;
	        while (ne!=null) {
	            if (end==ne.to)
	                return ne.cost;
	            ne = ne.next;
	        }
	        return INF;
	    }
}

class PathConsumeFirst {
	int consumeId;
	String[][] pathConsumeInfo;
	
	//将path[]按netId构建到每个consumeId的数组改成按consumeId构建
	public PathConsumeFirst(Path[] path, int netCdnNum, int i) {
		int netSize = SearchRoute.info[0];
		int consumeSize = SearchRoute.info[2];
		int sumCost;
		
		pathConsumeInfo = new String[netCdnNum][5];
		for(int j = 0; j < netCdnNum; j++){
				pathConsumeInfo[j][0] = path[j].pathInfo[i][0];
				pathConsumeInfo[j][1] = path[j].pathInfo[i][1];
				pathConsumeInfo[j][2] = path[j].pathInfo[i][2];
				pathConsumeInfo[j][3] = path[j].pathInfo[i][3];
				sumCost = MyUtils.getMinBand(pathConsumeInfo[j][2]);
				pathConsumeInfo[j][4] = Integer.toString(sumCost);
		}
		final int a =3;
		MyUtils.costSort(pathConsumeInfo, a);
	}
}
