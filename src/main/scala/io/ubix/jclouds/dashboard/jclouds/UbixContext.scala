package io.ubix.jclouds.dashboard.jclouds

import org.jclouds.compute.ComputeServiceContext
import org.jclouds.ContextBuilder
import com.google.common.collect.ImmutableSet
import org.jclouds.logging.log4j.config.Log4JLoggingModule
import io.ubix.jclouds.dashboard.VendorPlatform
import org.jclouds.sshj.config.SshjSshClientModule

class UbixContext {

  def getComputeContext(provider: VendorPlatform) = {
    val computeContext : ComputeServiceContext = ContextBuilder.newBuilder(provider.name)
      .endpoint(provider.authUrl)
      .credentials(provider.identity, provider.password)
      .modules(ImmutableSet.of(new Log4JLoggingModule(), new SshjSshClientModule()))
      .buildView(classOf[ComputeServiceContext])
    computeContext
  }




}
