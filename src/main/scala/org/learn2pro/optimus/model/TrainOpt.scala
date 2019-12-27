/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.model

import org.learn2pro.optimus.function.{Activation, LossFunction}

/**
  *
  * @author derong.tdr
  * @version TrainOpt.scala, v 0.1 2019年12月24日 15:30 tang Exp $
  */
case class TrainOpt(
  learningRate: Double = 0.0001, batch: Int = 32, maxStep: Int = 10000,
  minError: Double, tolerance: Int, activation: Activation, loss: LossFunction[Double])
