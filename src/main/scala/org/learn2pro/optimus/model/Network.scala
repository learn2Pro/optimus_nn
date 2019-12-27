/**
  * Alipay.com Inc.
  * Copyright (c) 2004-2019 All Rights Reserved.
  */
package org.learn2pro.optimus.model

/**
  * network base model
  *
  * @author derong.tdr
  * @version Network.scala, v 0.1 2019年12月24日 10:41 tang Exp $
  */
trait Network[INPUT, OUTPUT] extends Serializable {
  
  def addLayer(size: Int): Layer
  
  def addLink(src: Int, target: Int, link: Link)
  
  def fetchSrcLink(neuron: Neuron): Seq[Link]
  
  def fetchTargetLink(neuron: Neuron): Seq[Link]
  
  def layerSize: Int
  
  def layer(index: Int): NormalLayer
  
  def inputLayer: Layer
  
  def outputLayer: Layer
  
  def train(inputs: Iterator[Vector[INPUT]], labels: Iterator[Vector[OUTPUT]], opt: TrainOpt)
  
  def predict(inputs: Vector[INPUT]): Vector[OUTPUT]
  
  def forward(vector: Vector[INPUT]): Vector[OUTPUT]
  
  def backward(actually: Vector[OUTPUT]): Unit
}
