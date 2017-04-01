package com.thirdCode;
import java.util.Arrays;    
import java.util.Comparator;    
 //对度数进行排序     
  public class ArraySort {    
      
      public static void sort(int[][] ob, final int[] order) {    
          Arrays.sort(ob, new Comparator<Object>() {    
              public int compare(Object o1, Object o2) {    
                  int[] one = (int[]) o1;    
                  int[] two = (int[]) o2;    
                  for (int i = 0; i < order.length; i++) {    
                      int k = order[i];    
                      if (one[k] > two[k]) {    
                          return 1;    
                      } else if (one[k] < two[k]) {    
                          return -1;    
                      } else {    
                          continue;  //如果按一条件比较结果相等，就使用第二个条件进行比较。  
                      }    
                  }    
                  return 0;    
              }    
          });   
      }    
      
      public static void main(String[] args) {    
          int array[][] = new int[][] {     
                  { 12, 34},     
                  { 34, 72 },     
                  { 12, 34 },     
                  { 91, 10},    
                  { 12, 83},     
                  { 47, 23 },     
                  { 12, 34},     
                  { 12, 34},     
                  { 26, 78 } };    
          sort(array, new int[] {1, 0});   //先根据第一列比较，若相同则再比较第二列
          for (int i = 0; i < array.length; i++) {    
              for (int j = 0; j < array[i].length; j++) {    
                  System.out.print(array[i][j]);    
                  System.out.print("\t");    
              }    
              System.out.println();    
          }    
      }    
  }  