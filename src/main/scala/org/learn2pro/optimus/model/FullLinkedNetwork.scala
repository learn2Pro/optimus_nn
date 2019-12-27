/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.model

import java.util

import scala.collection.mutable.ArrayBuffer

/**
  *
  * @author derong.tdr
  * @version FullLinkedNetwork.scala, v 0.1 2019年12月24日 17:52 tang Exp $
  */
case class FullLinkedNetwork(var inputLayer: Layer = null, var outputLayer: OutputLayer = null) extends Network[Double, Double] {
  private val hiddenLayers: ArrayBuffer[NormalLayer]              = new ArrayBuffer[NormalLayer]()
  private val neuronLookup: java.util.Map[Int, Neuron]            = new util.HashMap[Int, Neuron]()
  private val linkLookup  : java.util.Map[(Int, Int), Link]       = new util.HashMap[(Int, Int), Link]()
  private val srcLookup   : java.util.Map[Int, ArrayBuffer[Link]] = new util.HashMap[Int, ArrayBuffer[Link]]()
  private val dstLookup   : java.util.Map[Int, ArrayBuffer[Link]] = new util.HashMap[Int, ArrayBuffer[Link]]()
  private var opt         : TrainOpt                              = _
  
  def refreshInputLayer(v: Vector[Double]): Unit = {
    inputLayer = NormalLayer(v.dims.map(Neuron(_)))
  }
  
  def refreshOutputLayer: Vector[Double] = {
    outputLayer.promote(Vector(outputLayer.neurons.map { n =>
      opt.activation.eval(calculus(n))
    }))
    Vector(outputLayer.neurons.map { n => n.value })
  }
  
  def addFullConnLayer(size: Int): Unit = {
    hiddenLayers.append(instantiateNewLayer(size).asInstanceOf[NormalLayer])
  }
  
  def instantiateInputLayer(size: Int): Unit = {
    inputLayer = addLayer(size)
  }
  
  def instantiateOutputLayer(output: OutputLayer): Unit = {
    this.outputLayer = output
    this.outputLayer.neurons.foreach(neuron => neuronLookup.put(neuron.id, neuron))
    instantiate(this.outputLayer)
  }
  
  private def instantiateNewLayer(size: Int): Layer = {
    require(inputLayer != null, "the input layer is null,pls check!")
    val newLayers = addLayer(size)
    instantiate(newLayers)
  }
  
  private def instantiate(newLayers: Layer) = {
    val N = hiddenLayers.length
    val previous = if (N == 0) {
      inputLayer
    } else {
      hiddenLayers.last
    }
    newLayers.neurons.foreach { outer =>
      previous.neurons.foreach { inner =>
        addLink(inner.id, outer.id, Link(inner.id, outer.id))
      }
    }
    newLayers
  }
  
  override def addLayer(size: Int): Layer = {
    val newLayers = NormalLayer.initialize(size)
    newLayers.neurons.foreach(neuron => neuronLookup.put(neuron.id, neuron))
    newLayers
  }
  
  override def addLink(src: Int, target: Int, link: Link): Unit = {
    linkLookup.put((src, target), link)
    if (srcLookup.containsKey(src)) {
      srcLookup.get(src).append(link)
    } else {
      srcLookup.put(src, ArrayBuffer(link))
    }
    if (dstLookup.containsKey(target)) {
      dstLookup.get(target).append(link)
    } else {
      dstLookup.put(target, ArrayBuffer(link))
    }
  }
  
  override def layerSize: Int = hiddenLayers.size
  
  override def layer(index: Int): NormalLayer = hiddenLayers(index)
  
  override def forward(input: Vector[Double]): Vector[Double] = {
    refreshInputLayer(input)
    hiddenLayers.foreach { layer =>
      layer.neurons.foreach { n =>
        n.refresh(opt.activation.eval(calculus(n)))
      }
    }
    refreshOutputLayer
  }
  
  private def calculus(n: Neuron) = {
    fetchTargetLink(n).map { link =>
      link.weight * neuronLookup.get(link.src).value + n.bias
    }.sum
  }
  
  /**
    * d(zL)/d(wL) = previousY
    * d(zL)/d(bL) = 1
    * d(aL)/d(zL) = activation'(zL)
    * d(C0)/d(aL) = 2(currentY-expect)
    *
    * @param expect
    */
  override def backward(expect: Vector[Double]): Unit = {
    /*update output layer*/
    val partialDerivative = outputLayer.neurons.zip(expect.dims).map { case (neuron, label) =>
      val ans = calculus(neuron)
      val partial = opt.loss.derivation(neuron.value, label) * opt.activation.derivation(ans)
      fetchTargetLink(neuron).foreach { link =>
        val previousY = neuronLookup.get(link.src).value
        val delta = partial * previousY * opt.learningRate
        link.incr(delta)
      }
      /*bias*/
      //      neuron.incrBias(partial)
      partial
    }
    
    /*update hidden layer*/
    hiddenLayers.foldRight(partialDerivative) { case (layer, derivative) =>
      layer.neurons.map { n =>
        val sumDerivative = fetchSrcLink(n).zip(derivative).map { case (link, partial) =>
          val wjk = link.weight
          wjk * partial
        }.sum
        val previous = opt.activation.derivation(calculus(n)) * sumDerivative
        /*update relate link*/
        fetchTargetLink(n).foreach { link =>
          val previousY = neuronLookup.get(link.src).value
          val delta = previous * previousY * opt.learningRate
          link.incr(delta)
        }
        previous
      }
    }
    
  }
  
  override def train(inputs: Iterator[Vector[Double]], labels: Iterator[Vector[Double]], opt: TrainOpt): Unit = {
    this.opt = opt
    var round = 0
    var minLossError = Double.MaxValue
    var exceedMinTimes = 0
    while (round < opt.maxStep && minLossError > opt.minError && exceedMinTimes < opt.tolerance) {
      //      inputs.grouped(opt.batch).zip(labels.grouped(opt.batch)).foreach {
      inputs.zip(labels).foreach { case (left, right) =>
        round += 1
        val lossError = forward(left).dims.zip(right.dims).map { case (r0, r1) =>
          opt.loss.eval(r0, r1)
        }.sum
        backward(right)
        println(s"round:$round,error:$lossError,minError:$minLossError!")
        if (lossError < minLossError) {
          minLossError = lossError
          optimizeParameters
        }
        if (lossError > minLossError) exceedMinTimes += 1
      }
    }
    if (round == opt.maxStep) optimizeParameters
  }
  
  private def optimizeParameters: Unit = {
    hiddenLayers.foreach(layers => layers.neurons.foreach(_.fixBestBias))
    outputLayer.neurons.foreach(_.fixBestBias)
    import scala.collection.JavaConversions._
    linkLookup.foreach(_._2.fixBestWeight)
  }
  
  override def predict(inputs: Vector[Double]): Vector[Double] = {
    forward(inputs)
  }
  
  override def fetchSrcLink(neuron: Neuron): Seq[Link] = {
    srcLookup.getOrDefault(neuron.id, throw new RuntimeException(s"not found src links from neuron:${neuron.id}"))
  }
  
  override def fetchTargetLink(neuron: Neuron): Seq[Link] = {
    dstLookup.getOrDefault(neuron.id, throw new RuntimeException(s"not found Target links from neuron:${neuron.id}"))
  }
}
