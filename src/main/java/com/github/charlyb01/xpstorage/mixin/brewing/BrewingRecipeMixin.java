package com.github.charlyb01.xpstorage.mixin.brewing;

import com.github.charlyb01.xpstorage.XpBook;
import com.github.charlyb01.xpstorage.config.ModConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeMixin {
    @Inject(method = "isValidIngredient", at = @At("HEAD"), cancellable = true)
    private void registerXpBookIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.get().bottles.enableBrewing && stack.getItem() instanceof XpBook) {
            cir.setReturnValue(true);
        }
    }
}
