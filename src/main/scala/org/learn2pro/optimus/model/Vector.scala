/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.model

/**
  *
  * @author derong.tdr
  * @version Vector.scala, v 0.1 2019年12月24日 10:56 tang Exp $
  */
case class Vector[T](dims: Array[T]) extends Matrix2D

class Matrix2D extends Matrix {
  
  /**
    * dimension size
    *
    * @return
    */
  def dimension: Int = 2
  
}

trait Matrix {
  
  /**
    * dimension size
    *
    * @return
    */
  def dimension: Int
  
}
