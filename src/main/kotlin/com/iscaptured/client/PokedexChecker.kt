package com.iscaptured.client

import com.cobblemon.mod.common.CobblemonClientImplementation
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
            // Access the client-side pokedex data
            val pokedex = CobblemonClient.clientPokedexData
            val speciesId = species.resourceIdentifier
            val record = pokedex?.getSpeciesRecord(speciesId)
            record?.knowledge == PokedexEntryProgress.CAUGHT
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
            val record = pokedex?.getSpeciesRecord(speciesId)
            record != null && record.knowledge != PokedexEntryProgress.NONE
        } catch (e: Exception) {
            IsCapturedClient.LOGGER.debug("Could not check pokedex for species: ${species.name}", e)
            false
        }
    }
}
