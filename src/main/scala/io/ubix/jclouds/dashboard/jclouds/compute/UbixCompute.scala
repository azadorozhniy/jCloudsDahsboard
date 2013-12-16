package io.ubix.jclouds.dashboard.jclouds.compute

import scala.collection.JavaConversions._
import org.jclouds.domain.Location
import scala.collection.JavaConverters._
import scala.collection.Set
import scala.Predef.Set
import org.jclouds.compute.domain.{ComputeMetadata, NodeMetadata, Hardware, Image}
import org.jclouds.domain.Location
import scala.util.Try
import org.jclouds.compute.domain.internal.TemplateImpl
import org.jclouds.openstack.nova.v2_0.compute.options.NovaTemplateOptions
import io.ubix.deployment.Context

object UbixCompute {

  val computeService = Context.context.getComputeService

  def imageList: Set[_ <: Image] = {
    computeService.listImages().asScala
  }

  def firstImage: Option[Image] = {
    imageList.headOption
  }

  def harware(size: String): Option[Hardware] = {
    computeService.listHardwareProfiles().find(image => image.getName.contains(size))
  }

  def firstLocation = {
    val locations: Set[_ <: Location] = computeService.listAssignableLocations.asScala
    locations.headOption
  }

  def createInstances(count: Int = 1, hardwareType: String = "nano", group: String = "group"): Try[Set[_ <: NodeMetadata]] = {
    val res = Try({
      val image: Image = firstImage.getOrElse(throw new Exception("Can't find any image!"))
      val hardware = harware(hardwareType).getOrElse(throw new Exception("Can't find hardware info!"))
      val location = firstLocation.getOrElse(throw new Exception("Can't find location info!"))
      val template = new TemplateImpl(image, hardware, location, NovaTemplateOptions.NONE)

      val res = computeService.createNodesInGroup("group", count, template);
      println("Created " + res.size() + "nodes")
      Context.context.unwrap()
      res.asScala
    })
    res
  }

  def listInstances: Set[_ <: ComputeMetadata] = {
    computeService.listNodes.asScala
  }

  def deleteInstances = {

  }
}
