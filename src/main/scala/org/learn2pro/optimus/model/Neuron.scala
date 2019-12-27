/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.model

import org.learn2pro.optimus.UniqueGenerator

/**
  *
  * @author derong.tdr
  * @version Neuron.scala, v 0.1 2019年12月24日 10:22 tang Exp $
  */
case class Neuron(var value: Double = 0.0, var bias: Double = 0.0) {
  val id: Int = UniqueGenerator.gen
  private var bestBias: Double = _
  
  def incrValue(increment: Double): Unit = value += increment
  
  def incrBias(increment: Double): Unit = bias += increment
  
  def refresh(v: Double): Unit = value = v
  
  def fixBestBias: Unit = {
    bestBias = bias
  }
}
