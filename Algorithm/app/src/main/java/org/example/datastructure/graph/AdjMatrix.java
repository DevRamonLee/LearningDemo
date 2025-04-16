/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:50:50
 * @LastEditTime: 2025-03-31 12:02:29
 * @FilePath: /Algorithm/app/src/main/java/org/example/datastructure/graph/AdjMatrix.java
 * @Description: 
 */
package org.example.datastructure.graph;

import java.util.Scanner;

//带权邻接矩阵
public class AdjMatrix {
	private static final int MaxVertices = 100; // 最多包含 100 个顶点
	private static final int MAXWEIGHT = 32767; // 不邻接时为32767，但输出时用 "∞"

	public int[] Vertices = new int[MaxVertices]; // 顶点信息的数组
	public int[][] Edge = new int[MaxVertices][MaxVertices]; // 边的权信息的数组
	public int numV; // 当前的顶点数
	public int numE; // 当前的边数

	void CreateGraph(AdjMatrix G) //图的生成函数
    {
        int n,e,vi,vj,w,i,j;
        
        Scanner sc = new Scanner(System.in);
        System.out.printf("请输入图的顶点数");
        n = sc.nextInt();
        System.out.printf("请输入图的边数");
        e = sc.nextInt();
        G.numV = n;
        G.numE = e;
        for(i = 0;i < n;i++) //图的初始化
            for(j = 0;j < n;j++)
                {
                if(i == j)
                    G.Edge[i][j] = 0;
                else
                    G.Edge[i][j] = 32767;
                }
        for(i = 0;i < G.numV;i++) //将顶点存入数组中
        {
            System.out.printf("请输入第" + (i+1) +"个顶点的信息(整型):");
            G.Vertices[i] = sc.nextInt();
        }
        System.out.println();

        for(i = 0;i < G.numE;i++)
        {
            System.out.println("请输入边的信息i,j,w(以换行分隔):");
            vi = sc.nextInt();
            vj = sc.nextInt();
            w = sc.nextInt();
            //若为不带权值的图，则w输入1
            //若为带权值的图，则w输入对应权值

            G.Edge[vi-1][vj-1]=w;//①
            G.Edge[vj-1][vi-1]=w;//②
            //无向图具有对称性的规律，通过①②实现
            //有向图不具备此性质，所以只需要①
        }
    }

	void DispGraph(AdjMatrix G) // 输出邻接矩阵的信息
	{
		int i, j;
		System.out.println("\n输出顶点的信息（整型）:\n");
		for (i = 0; i < G.numV; i++)
			System.out.printf("%8d", G.Vertices[i]);

		System.out.println("\n输出邻接矩阵:\n");
		System.out.printf("\t");
		for (i = 0; i < G.numV; i++)
			System.out.printf("%8d", G.Vertices[i]);

		for (i = 0; i < G.numV; i++) {
			System.out.printf("\n%8d", G.Vertices[i]);
			for (j = 0; j < G.numV; j++) {
				if (G.Edge[i][j] == 32767)
					// 两点之间无连接时权值为默认的32767，在无向图中一般用"0"表示，在有向图中一般用"∞",这里为了方便统一输出
					// "∞"
					System.out.printf("%8s", "∞");
				else
					System.out.printf("%8d", G.Edge[i][j]);
			}
			System.out.printf("\n");
		}
	}
	
	public static void main(String args[]){
		AdjMatrix adjMatrix = new AdjMatrix();
		adjMatrix.CreateGraph(adjMatrix);
		adjMatrix.DispGraph(adjMatrix);
	}
}
