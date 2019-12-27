/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package optimus

import org.learn2pro.optimus.classification.SoftMaxLayer
import org.learn2pro.optimus.datasets.MNISTLoader
import org.learn2pro.optimus.function.{Sigmoid, SquareError}
import org.learn2pro.optimus.model.{FullLinkedNetwork, TrainOpt}
import org.scalatest.FunSuite

/**
  *
  * @author derong.tdr
  * @version MNISTLoadTest.scala, v 0.1 2019年12月27日 10:12 tang Exp $
  */
class MNISTLoadTest extends FunSuite {
  
  test("load train") {
    val iter = MNISTLoader.loadTrain()
    var count = 0
    while (iter.hasNext) {
      iter.next()
      count += 1
    }
    println(count)
  }
  
  test("train mnist") {
    val nn = FullLinkedNetwork()
    nn.instantiateInputLayer(784)
    nn.addFullConnLayer(16)
    nn.addFullConnLayer(16)
    nn.instantiateOutputLayer(SoftMaxLayer(10))
    nn.train(MNISTLoader.loadTrain(), MNISTLoader.loadLabel(), TrainOpt(learningRate = 0.1,
      minError = 0.01, tolerance = 10, activation = new Sigmoid, loss = new SquareError))
  }
  
}
