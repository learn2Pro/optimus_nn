/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.function

/**
  * the activate function
  *
  * @author derong.tdr
  * @version Activation.scala, v 0.1 2019年12月24日 10:43 tang Exp $
  */
trait Activation {
  
  /**
    * activate function
    *
    * @param input the input numeric
    * @return
    */
  def eval(input: Double): Double
  
  /**
    * the derivative evaluate
    *
    * @param input
    * @return
    */
  def derivation(input: Double): Double
}
