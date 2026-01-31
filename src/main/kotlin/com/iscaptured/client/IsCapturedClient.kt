package com.iscaptured.client

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object IsCapturedClient : ClientModInitializer {
    const val MOD_ID = "iscaptured"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

    override fun onInitializeClient() {
        LOGGER.info("Is Captured mod initialized!")
    }
}
