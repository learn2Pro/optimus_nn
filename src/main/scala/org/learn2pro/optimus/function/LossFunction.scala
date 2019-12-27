/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.function

/**
  *
  * @author derong.tdr
  * @version LossFunction.scala, v 0.1 2019年12月25日 11:08 tang Exp $
  */
trait LossFunction[T] {
  
  /**
    * eval loss
    *
    * @param actual actual value
    * @param expect expect value
    * @return the loss
    */
  def eval(actual: T, expect: T): Double
  
  /**
    * eval derivation
    *
    * @param actual actual value
    * @param expect expect value
    * @return the diff
    */
  def derivation(actual: T, expect: T): Double
}
