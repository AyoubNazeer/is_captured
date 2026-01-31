package com.iscaptured.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.iscaptured.client.CapturedIconRenderer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to modify the Pokemon's display name to include capture status and gender indicators.
 */
@Mixin(PokemonEntity.class)
public abstract class PokemonEntityMixin {

    /**
     * Modifies the display name of the Pokemon to include capture indicator and gender symbol.
     */
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void modifyDisplayName(CallbackInfoReturnable<Text> cir) {
        PokemonEntity self = (PokemonEntity) (Object) this;
        Text originalName = cir.getReturnValue();
        Text enhancedName = CapturedIconRenderer.INSTANCE.getEnhancedDisplayName(self, originalName);
        cir.setReturnValue(enhancedName);
    }
}
