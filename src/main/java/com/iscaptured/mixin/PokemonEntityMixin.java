package com.iscaptured.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.iscaptured.client.CapturedIconRenderer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to modify the Pokemon's name to include capture status and gender indicators.
 * Targets PokemonEntity.getName() which is inherited from Entity (Minecraft class).
 */
@Mixin(PokemonEntity.class)
public abstract class PokemonEntityMixin {

    /**
     * Modifies the display name of the Pokemon to include capture indicator and gender symbol.
     */
    @Inject(method = "getName", at = @At("RETURN"), cancellable = true)
    private void iscaptured$decorateName(CallbackInfoReturnable<Text> cir) {
        Text original = cir.getReturnValue();
        if (original != null) {
            PokemonEntity self = (PokemonEntity) (Object) this;
            Text enhanced = CapturedIconRenderer.INSTANCE.getEnhancedDisplayName(self, original);
            cir.setReturnValue(enhanced);
        }
    }
}
