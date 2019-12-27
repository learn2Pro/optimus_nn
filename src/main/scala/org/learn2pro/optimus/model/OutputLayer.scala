/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.model

/**
  *
  * @author derong.tdr
  * @version OutputLayer.scala, v 0.1 2019年12月27日 18:58 tang Exp $
  */
trait OutputLayer extends Layer {
  
  def promote(input: Vector[Double]): Unit
}
