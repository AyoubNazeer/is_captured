package com.iscaptured.client

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Gender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting

object CapturedIconRenderer {

    // Unicode symbols
    private const val POKEBALL_SYMBOL = "\u25CF" // Filled circle as pokeball representation
    private const val MALE_SYMBOL = "\u2642"     // ♂
    private const val FEMALE_SYMBOL = "\u2640"   // ♀

    /**
     * Renders the captured icon and gender symbol next to the Pokemon's name
     */
    fun renderCapturedOverlay(
        entity: PokemonEntity,
        matrixStack: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        nameText: Text
    ) {
        val pokemon = entity.pokemon
        val species = pokemon.species

        val hasCaught = PokedexChecker.hasCaughtSpecies(species)
        val gender = pokemon.gender

        // Build the suffix text
        val suffix = buildSuffixText(hasCaught, gender)
        if (suffix.string.isEmpty()) return

        val client = MinecraftClient.getInstance()
        val textRenderer = client.textRenderer

        // Calculate position offset (after the name)
        val nameWidth = textRenderer.getWidth(nameText)
        val xOffset = (nameWidth / 2.0f) + 2

        // Render the suffix
        matrixStack.push()
        matrixStack.translate(xOffset.toDouble(), 0.0, 0.0)

        val textColor = 0xFFFFFFFF.toInt()
        val backgroundColor = (0.25f * 255).toInt() shl 24

        textRenderer.draw(
            suffix,
            0f,
            0f,
            textColor,
            false,
            matrixStack.peek().positionMatrix,
            vertexConsumers,
            TextRenderer.TextLayerType.NORMAL,
            backgroundColor,
            light
        )

        matrixStack.pop()
    }

    /**
     * Gets the full display text including name, pokeball icon and gender
     */
    fun getEnhancedDisplayName(entity: PokemonEntity, originalName: Text): Text {
        val pokemon = entity.pokemon
        val species = pokemon.species

        val hasCaught = PokedexChecker.hasCaughtSpecies(species)
        val gender = pokemon.gender

        // If no modifications needed, return original
        if (!hasCaught && gender == Gender.GENDERLESS) {
            return originalName
        }

        val builder = Text.empty()

        // Add pokeball icon if caught
        if (hasCaught) {
            builder.append(
                Text.literal("$POKEBALL_SYMBOL ")
                    .formatted(Formatting.RED)
            )
        }

        // Add original name
        builder.append(originalName)

        // Add gender symbol
        when (gender) {
            Gender.MALE -> builder.append(
                Text.literal(" $MALE_SYMBOL")
                    .formatted(Formatting.AQUA)
            )
            Gender.FEMALE -> builder.append(
                Text.literal(" $FEMALE_SYMBOL")
                    .formatted(Formatting.LIGHT_PURPLE)
            )
            else -> {} // Genderless, no symbol
        }

        return builder
    }

    private fun buildSuffixText(hasCaught: Boolean, gender: Gender): Text {
        val builder = Text.empty()

        // Add pokeball icon if caught
        if (hasCaught) {
            builder.append(
                Text.literal(" $POKEBALL_SYMBOL")
                    .formatted(Formatting.RED)
            )
        }

        // Add gender symbol
        when (gender) {
            Gender.MALE -> builder.append(
                Text.literal(" $MALE_SYMBOL")
                    .formatted(Formatting.AQUA)
            )
            Gender.FEMALE -> builder.append(
                Text.literal(" $FEMALE_SYMBOL")
                    .formatted(Formatting.LIGHT_PURPLE)
            )
            else -> {} // Genderless, no symbol
        }

        return builder
    }
}
