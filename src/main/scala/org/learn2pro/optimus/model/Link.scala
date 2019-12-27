/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.model

/**
  *
  * @author derong.tdr
  * @version Link.scala, v 0.1 2019年12月24日 10:22 tang Exp $
  */
case class Link(src: Int, dst: Int, var weight: Double = Math.random() - 0.5) {
  
  private var bestWeight: Double = _
  
  def incr(increment: Double) = weight -= increment
  
  def fixBestWeight: Unit = {
    bestWeight = weight
  }
}
