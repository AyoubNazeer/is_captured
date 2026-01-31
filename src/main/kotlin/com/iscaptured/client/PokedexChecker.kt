package com.iscaptured.client

import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.pokemon.Species
import net.minecraft.client.MinecraftClient

object PokedexChecker {

    /**
     * Check if the player has caught the given species.
     * Uses the client-side Pokédex data synced from the server.
     */
    fun hasCaughtSpecies(species: Species): Boolean {
        val client = MinecraftClient.getInstance()
        if (client.player == null) return false

        return try {
            val pokedex = CobblemonClient.clientPokedexData
            val speciesId = species.resourceIdentifier
            val highestKnowledge = pokedex.getHighestKnowledgeForSpecies(speciesId)
            highestKnowledge == PokedexEntryProgress.CAUGHT
        } catch (e: Exception) {
            IsCapturedClient.LOGGER.debug("Could not check pokedex for species: ${species.name}", e)
            false
        }
    }

    /**
     * Check if the player has seen (but not necessarily caught) the given species.
     */
    fun hasSeenSpecies(species: Species): Boolean {
        val client = MinecraftClient.getInstance()
        if (client.player == null) return false

        return try {
            val pokedex = CobblemonClient.clientPokedexData
            val speciesId = species.resourceIdentifier
            val highestKnowledge = pokedex.getHighestKnowledgeForSpecies(speciesId)
            highestKnowledge != PokedexEntryProgress.NONE
        } catch (e: Exception) {
            IsCapturedClient.LOGGER.debug("Could not check pokedex for species: ${species.name}", e)
            false
        }
    }
}
