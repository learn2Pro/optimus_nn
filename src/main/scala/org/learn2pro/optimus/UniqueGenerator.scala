/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus

import java.util.concurrent.atomic.AtomicInteger

/**
  *
  * @author derong.tdr
  * @version UniqueGenerator.scala, v 0.1 2019年12月24日 11:45 tang Exp $
  */
object UniqueGenerator {
  
  val atom = new AtomicInteger(0)
  
  def gen: Int = atom.incrementAndGet()
}
