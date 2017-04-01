package com.cdn;

public class ShortestPath {
	int netCdnNum;
	int[] netCdn;
	Graph netGraph = new Graph();
	Path[] path;
	PathConsumeFirst[] pathConsumeFirst; 
	//该方法计算出从图graph中选定挂载cdn的网络节点的netCdn[]的路径信息；
	public ShortestPath(int netCdnNum, int[] netCdn, Graph netGraph) {
		super();
		this.netCdnNum = netCdnNum;
		this.netCdn = netCdn;
		this.netGraph = netGraph;
		path = new Path[netCdnNum];
		
		for(int i = 0; i < netCdnNum; i++){
			path[i] = new Path(netCdn[i],netGraph);
			System.out.println("打印path(" + i + ")信息：");
			printPathInfo(path[i]);
		}
		//将path[] 转化为pathConsumeFirst
		pathConsumeFirst = new PathConsumeFirst[netGraph.consumeSize];
		for(int i = 0; i < netGraph.consumeSize; i++){
			pathConsumeFirst[i] = new PathConsumeFirst(path, netCdnNum,i);
			System.out.println("打印PathConsumeFirstInfo(" + i + ")信息：");
			printPathConsumeFirstInfo(pathConsumeFirst[i]);
		}
}
		//生成string
		String[] outPath(PathConsumeFirst[] pathConsumeFirst){
		int cnt = 0;
		int len = SearchRoute.info[2];
		int consumeBand;
		int consumeId;
		String[] outPath = new String[1000];
		
		for(int i =0; i < len; i++){
			consumeId = pathConsumeFirst[i].consumeId;
			consumeBand = netGraph.consumeArray[consumeId][2];
			int restBand = consumeBand;
			int n = 0;
			String tmp;
			int num = 0;
			while(restBand > 0){
				if(n >= SearchRoute.info[2]){
					break;
				}
				int tmpBand = Integer.parseInt(pathConsumeFirst[i].pathConsumeInfo[n][4]) ;
				restBand = restBand - tmpBand; 
				if(restBand > 0){
					outPath[num] = pathConsumeFirst[i].pathConsumeInfo[n][2];
//					outPath[num] += "   ";
					outPath[num] += pathConsumeFirst[i].pathConsumeInfo[n][4];
//					outPath[num] += "\n";
				}else{
					outPath[num] = pathConsumeFirst[i].pathConsumeInfo[n][2];
//					outPath[num] += "   ";
					outPath[num] += restBand + tmpBand;
//					outPath[num] += "\n";
				}
				n ++;
				num ++;
			}
		}
		return outPath;
	}
	//打印以consumeId信息
	private void printPathConsumeFirstInfo(PathConsumeFirst pathConsumeFirst) {
		String[][] str = pathConsumeFirst.pathConsumeInfo;
		int len = str.length;
		for(int i = 0; i < len; i++){
			System.out.print("PathConsumeFirstInfo:");
			for(int j =0; j < 5; j++){
				System.out.print(str[i][j] + " /");
			}
			System.out.println();
		}
	}
	//打印netId，后期注释掉
	void printPathInfo(Path path){
		String[][] str = path.pathInfo;
		int len = str.length;
		for(int i = 0; i < len; i++){
			System.out.print("pathInfo:");
			for(int j =0; j < 4; j++){
				System.out.print(str[i][j] + " /");
			}
			System.out.println();
		}
	}
}
