package com.lanshifu.demo_module.suanfa;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 无向图
 */
public class SimpleGraph {
    private int v;                      // 顶点的个数
    private LinkedList<Integer> adj[]; //邻接表

    public SimpleGraph(int v) {
        this.v = v;
        adj = new LinkedList[v];
    }

    /**
     * 添加关联，a，b互相关注
     * @param s
     * @param t
     */
    public void addEdge(int s,int t){
        //无向图，添加两次，a-b  b-a
        adj[s].add(t);
        adj[t].add(s);

    }


    /**
     * 广度优先搜索
     * @param s
     * @param t
     */
    public void bfs(int s, int t){
        if (s == t) return;

        boolean [] visited = new boolean[v]; //记录已经访问过的顶点，避免重复访问
        int[] priv = new int[v];            //记录走过的路径
        for (int i = 0; i < v; i++) {
            priv[i] = -1;
        }

        Queue<Integer> queue = new LinkedList<>();  //队列，用来存储还没有被访问的相邻顶点
        queue.add(s);
        while (queue.size() != 0){
            int w = queue.poll();
            //从邻接表中取出
            for (int i = 0; i < adj[w].size(); i++) {
                int current = adj[w].get(i);
                if (!visited[current]){
                    priv[current] = w;
                    if (current == t){
                        System.out.println("找到了" + w);
                        print(priv,s,t);
                        return;
                    }
                    visited[current] = true;

                    //下一层添加到队列
                    queue.add(current);
                }

            }


        }

    }



    private void print(int[] prev,int s,int t){
        //递归打印s到t的路径


    }
}
