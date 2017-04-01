package com.cdn;
import com.thirdCode.*;

public class SearchRoute {
	public final static int INF = Integer.MAX_VALUE;
	static int[] info;
	static int cdnCost;
	static Graph netGraph = new Graph();
	ShortestPath[] shortestPath;
	String[] outPath = new String[1000];
//	//表示消费节点到网络节点的最短路，[0]表示consumeID，[1]表示PathId,[2]表示netId,[3]表示sumCost
//	String[][] routeInfo;
	
	public String[] getOutPath() {
		return outPath;
	}
	public SearchRoute(int[] info, int cdnCost, Graph netGraph) {
		SearchRoute.info = info;
		SearchRoute.cdnCost = cdnCost;
		SearchRoute.netGraph = netGraph;
		
		MyUtils  myUtils = new MyUtils(netGraph);
		//测试选择一个度数最大的网络节点到各个消费节点的最短路
		ArraySort.sort(netGraph.netDegree, new int[] {1, 0});
		//阈值可以调节
		double threshold = 0.2;
		int netNodeCndNum = (int) (threshold * info[0]);
		int netCdn[] = new int[netNodeCndNum];
		for(int i =0; i < netNodeCndNum; i++){
			netCdn[i] = netGraph.netDegree[info[0] - i - 1][0];
		}
		//netNodeCndNum 网络节点数，netCdn 网络节点数组， netGraph 为图
		ShortestPath shortestPath = new ShortestPath(netNodeCndNum, netCdn, netGraph);
		outPath = shortestPath.outPath(shortestPath.pathConsumeFirst);
		System.out.println(outPath);
		
	}
	
}
