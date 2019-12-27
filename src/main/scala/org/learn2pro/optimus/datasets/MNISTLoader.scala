/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.datasets

import java.io._
import java.util.zip.GZIPInputStream

import org.learn2pro.optimus.model.{Vector => NVector}

/**
  *
  * @author derong.tdr
  * @version MnistLoader.scala, v 0.1 2019年12月26日 19:37 tang Exp $
  */
object MNISTLoader {
  
  def downloads(): Unit = {
    val path = this.getClass.getResource("/").toString
    val p0 = Runtime.getRuntime.exec(s"wget -P $path http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz")
    outputInfo(p0)
    println(s"train dataset download succeed!")
    val p1 = Runtime.getRuntime.exec(s"wget -P $path http://yann.lecun.com/exdb/mnist/train-labels-idx1-ubyte.gz")
    outputInfo(p1)
    println(s"train label dataset download succeed!")
    val p2 = Runtime.getRuntime.exec(s"wget -P $path http://yann.lecun.com/exdb/mnist/t10k-images-idx3-ubyte.gz")
    outputInfo(p2)
    println(s"test dataset download succeed!")
    val p3 = Runtime.getRuntime.exec(s"wget -P $path http://yann.lecun.com/exdb/mnist/t10k-labels-idx1-ubyte.gz")
    outputInfo(p3)
    println(s"test label dataset download succeed!")
  }
  
  private def outputInfo(p: Process): Unit = {
    val reader = new BufferedReader(new InputStreamReader(p.getErrorStream))
    var info: String = null
    do {
      info = reader.readLine()
      println(info)
    } while (info != null)
  }
  
  def loadTrain(isTest: Boolean = false): Iterator[NVector[Double]] = {
    val name = if (isTest) "/t10k-images-idx3-ubyte.gz" else "/train-images-idx3-ubyte.gz"
    val reader = loadFile(name)
    require(reader.readInt() == 2051, "train file not match!")
    val itemSize = reader.readInt()
    val rowNum = reader.readInt()
    val colNum = reader.readInt()
    new Iterator[NVector[Double]] {
      var current: Int = 0
      
      override def hasNext: Boolean = {
        current < itemSize
      }
      
      override def next(): NVector[Double] = {
        current += 1
        readImg(rowNum, colNum, reader)
      }
    }
  }
  
  def loadLabel(isTest: Boolean = false): Iterator[NVector[Double]] = {
    val name = if (isTest) "/t10k-labels-idx1-ubyte.gz" else "/train-labels-idx1-ubyte.gz"
    val reader = loadFile(name)
    require(readInt(reader) == 2049, "train file not match!")
    val itemSize = readInt(reader)
    new Iterator[NVector[Double]] {
      var current: Int = 0
      
      override def hasNext: Boolean = {
        current < itemSize
      }
      
      override def next(): NVector[Double] = {
        current += 1
        readLabel(reader)
      }
    }
  }
  
  private def loadFile(name: String): DataInputStream = {
    val url = this.getClass.getResource(name)
    val f = new File(url.getFile)
    if (!f.exists()) {
      downloads()
    }
    //    val reader = new BufferedReader(new InputStreamReader())
    new DataInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(f))))
  }
  
  
  def readLabel(input: DataInputStream): NVector[Double] = {
    val arr = new Array[Double](10)
    val idx = input.read()
    arr(idx) = 1.0
    NVector(arr)
  }
  
  def readImg(rowNum: Int, colNum: Int, reader: DataInputStream): NVector[Double] = {
    val arr = new Array[Double](rowNum * colNum)
    for (i <- Range(0, rowNum * colNum, 1)) {
      val c = reader.read()
      arr(i) = c
    }
    NVector(arr)
  }
  
  def readInt(in: DataInputStream): Int = {
    val ch1 = in.read()
    val ch2 = in.read()
    val ch3 = in.read()
    val ch4 = in.read()
    if ((ch1 | ch2 | ch3 | ch4) < 0) throw new EOFException
    (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0)
  }
  
}
  
