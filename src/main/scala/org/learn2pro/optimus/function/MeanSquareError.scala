/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.function

/**
  *
  * @author derong.tdr
  * @version SquareError.scala, v 0.1 2019年12月25日 21:44 tang Exp $
  */
class MeanSquareError extends LossFunction[Double] {
  /**
    * eval loss
    *
    * @param actual actual value
    * @param expect expect value
    * @return the loss
    */
  override def eval(actual: Double, expect: Double): Double = ???
  
  /**
    * eval derivation
    *
    * @param actual actual value
    * @param expect expect value
    * @return the diff
    */
  override def derivation(actual: Double, expect: Double): Double = ???
}
