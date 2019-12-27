/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.function

/**
  *
  * @author derong.tdr
  * @version Sigmoid.scala, v 0.1 2019年12月25日 13:43 tang Exp $
  */
class Relu extends Activation {
  /**
    * activate function
    *
    * @param input the input numeric
    * @return
    */
  override def eval(input: Double): Double = if (input <= 0) 0 else input
  
  /**
    * the derivative evaluate
    *
    * @param input
    * @return
    */
  override def derivation(input: Double): Double = if (input <= 0) 0 else 1
}
