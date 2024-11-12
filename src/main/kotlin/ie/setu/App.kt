package ie.setu

import ie.setu.config.JavalinConfig
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import ie.setu.config.DbConfig


fun main(args: Array<String>) {

    DbConfig().getDbConnection()
    JavalinConfig().startJavalinService()
}

//fun selectionSort(arr:IntArray):IntArray {
//
//    var n = arr.size
//
//// outer loop goes until the second last element
//    for (i in 0 until n-1){
//
//// assuming minindex as the first element of the outer loop
//        var minIndex=i
//
//// inner loop goes until the last element
//        for(j in i+1 until n){
//            if(arr[j]<arr[minIndex]){
//
//                minIndex=j
//
//            }
//
//
//        }
//// does a condition check to determine  whether the minIndex have changed
//        if(minIndex!=i)
//        {
//// swap the elements if values have changed
//            var temp = arr[i]
//            arr[i] = arr[minIndex]
//            arr[minIndex] =temp
//        }
//
//    }
//
//
//return arr
//
//}