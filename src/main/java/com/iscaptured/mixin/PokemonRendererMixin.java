package com.iscaptured.mixin;

import com.cobblemon.mod.common.client.render.pokemon.PokemonRenderer;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.iscaptured.client.CapturedIconRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Mixin to modify the Pokemon's name label to include capture status and gender indicators.
 */
@Mixin(value = PokemonRenderer.class, remap = false)
public abstract class PokemonRendererMixin {

    /**
     * Modifies the label variable after resolveBaseLabel() is called.
     * This intercepts the label text before it's rendered.
     */
    @ModifyVariable(
            method = "renderNameTag",
            at = @At(value = "INVOKE_ASSIGN", target = "Lcom/cobblemon/mod/common/client/render/pokemon/PokemonRenderer;resolveBaseLabel(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lnet/minecraft/text/MutableText;"),
            ordinal = 0
    )
    private MutableText modifyLabel(MutableText original, PokemonEntity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta) {
        if (original == null || original.getString().isEmpty()) {
            return original;
        }
        return (MutableText) CapturedIconRenderer.INSTANCE.getEnhancedDisplayName(entity, original);
    }
}
