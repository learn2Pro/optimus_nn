/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.classification

import org.learn2pro.optimus.model
import org.learn2pro.optimus.model.Neuron

/**
  *
  * @author derong.tdr
  * @version SofaMaxLayer.scala, v 0.1 2019年12月27日 18:51 tang Exp $
  */
case class SoftMaxLayer(category: Int) extends ClassificationLayer {
  val initial: Array[Neuron] = Array.tabulate(category)(_ => Neuron())
  
  /**
    * the neurons in this layer
    *
    * @return
    */
  override def neurons: Array[Neuron] = initial
  
  override def classify(inputs: model.Vector[Double]): Array[Double] = {
    val sum = inputs.dims.map { v => Math.pow(v, 2) }.sum
    inputs.dims.map { v => Math.pow(v, 2) / sum }
  }
}
