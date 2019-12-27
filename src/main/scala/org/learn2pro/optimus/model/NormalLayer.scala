/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.model

/**
  * the network layer
  *
  * @author derong.tdr
  * @version NormalLayer.scala, v 0.1 2019年12月24日 10:42 tang Exp $
  */
case class NormalLayer(neurons: Array[Neuron]) extends Layer

object NormalLayer {
  def initialize(size: Int): NormalLayer = {
    NormalLayer(Array.tabulate(size)(_ => Neuron()))
  }
}
