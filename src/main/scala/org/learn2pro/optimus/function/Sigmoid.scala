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
class Sigmoid extends Activation {
  /**
    * activate function
    *
    * @param input the input numeric
    * @return
    */
  override def eval(input: Double): Double = {
    1 / (1 + Math.exp(-1 * input))
  }
  
  
  /**
    * the derivative evaluate
    * 1/(1+e(-x))
    * z = 1+e(-x)
    * y = e(x)
    * -1 * z(-2) * -1 * y(-2) * e(x)
    *
    * @param input
    * @return
    */
  override def derivation(input: Double): Double = Math.pow(1 + Math.exp(-1 * input), -2) * Math.pow(Math.exp(input), -2) * Math.exp(input)
}
