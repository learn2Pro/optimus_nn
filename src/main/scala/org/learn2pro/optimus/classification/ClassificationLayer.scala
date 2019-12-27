/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.classification

import org.learn2pro.optimus.model.{OutputLayer, Vector => NVector}

/**
  *
  * @author derong.tdr
  * @version ClassificationLayer.scala, v 0.1 2019年12月27日 18:52 tang Exp $
  */
trait ClassificationLayer extends OutputLayer {
  def promote(input: NVector[Double]): Unit = {
    classify(input).zip(neurons).foreach { case (v, n) => n.refresh(v) }
  }
  
  def classify(inputs: NVector[Double]): Array[Double]
}
