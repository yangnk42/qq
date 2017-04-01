package com.cdn;

public class Graph {
	//其中nodeSize 为网络中网络节点数，consumeSize为网络中消费节点数，
	//node[]为网络节点和消费节点邻接链表表示形式，consumeArray[][]是消费节点，
	//netDegree[][] 是每个消费节点的度数表示；
	int netSize;
	int consumeSize;
	NetNode[] node;
	int[][] consumeArray;
	int[][] netDegree;
	
	public Graph() {
		super();
	}
	//初始化邻接表
	public Graph(int[] info,int[][] netArray, int[][] consumeArray) {
		this.consumeArray = consumeArray;
		this.consumeSize = info[2];
		//初始化邻接顶点
		node = initNode(info[0]);
		//初始化网络链路
		for(int i = 0; i < info[1]; i++){
			NetEdge ne= new NetEdge();
			ne.to= netArray[i][1];
			ne.band= netArray[i][2];
			ne.cost= netArray[i][3];
			insertNetEdge(node[netArray[i][0]], ne);
		}
		//由于给出的数据集都是单向链路，需要再次初始化才能补齐所有网络节点的链路
		for(int j = 0; j < info[1]; j++){
			NetEdge ne= new NetEdge();
			ne.to= netArray[j][0];
			ne.band= netArray[j][2];
			ne.cost= netArray[j][3];
			insertNetEdge(node[netArray[j][1]], ne);	
		}
		//初始化消费节点，把消费节点链接到网络节点后，形成链表
		for(int k = 0; k < info[2]; k++){
			ConsumeEdge ce= new ConsumeEdge();
			ce.index= consumeArray[k][0];
			ce.consumeVertex= consumeArray[k][1];
			ce.consumeBand= consumeArray[k][2];
			insertConsumeEdge(node[consumeArray[k][1]], ce);
		}
		//计算每个网络节点的度数netCount,这个在后面重构的时候可以放在 初始化网络链路 中计数
		netDegree = new int[info[0]][2];
		for(int i =0; i < info[0]; i++){
			int cnt =1;
			NetEdge tmp = node[i].from.next;
			while( tmp!= null){
				cnt ++;
				tmp = tmp.next;
			}
			netDegree[i][1] = cnt;
			netDegree[i][0] = i;
		}
		//打印调试
		System.out.println("邻接表信息:");
		for(int i= 0;i <50; i++){
			System.out.print(i+": ");
			printNode(node[i]);
			System.out.println();
		}
	}
	//初始化消费节点
	private void insertConsumeEdge(NetNode node, ConsumeEdge consumeEdge) { 
		
		NetEdge tmpNode = node.from;
		while(tmpNode.next!=null){
			tmpNode = tmpNode.next;
		}
		tmpNode.vertex = consumeEdge;
	}
	//初始化邻接顶点
	public NetNode[] initNode(int netSize){
		this.netSize = netSize;
		NetNode[] netNode = new NetNode[netSize];
		for(int i = 0; i< netSize; i++){
			netNode[i] = new NetNode();
			netNode[i].vertex = i;
		}
		return netNode;
	}
	//插入邻接边
	private void insertNetEdge(NetNode node, NetEdge netEdge) {
		//start 即为顶点序号
		int to = netEdge.to;
		NetEdge end = netEdge.next;
		int band = netEdge.band;
		int cost= netEdge.cost;
		if(node.from == null){
			//第一次插入情况
			node.from = netEdge;
		}else{
			//顶点已经后接NetEdge情况
			NetEdge tmpNode = node.from;
			while(tmpNode.next!=null){
				if(tmpNode.to == to){
					break;
				}
				tmpNode = tmpNode.next;
			}
			tmpNode.next = netEdge;
		}
	}
	//打印测试
	static void printNode(NetNode testNode){
	    NetEdge tmpNode =new NetEdge();
		tmpNode = testNode.from;
		while(tmpNode != null){
			int out = tmpNode.to;
			System.out.print(out+", ");
			if(tmpNode.vertex != null){
				int cout = tmpNode.vertex.index;
				System.out.print(" ---------------------- "+cout);
			}
			tmpNode = tmpNode.next;
		}
	}
	}

class ConsumeEdge {
	int index;
	int consumeVertex;
	int consumeBand;
}
class NetEdge {
	int to;
	NetEdge next = null;
	ConsumeEdge vertex = null;
	int band = 0;
	int cost = 0;
}
class NetNode {
	int vertex;
	NetEdge from = null;
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
}

