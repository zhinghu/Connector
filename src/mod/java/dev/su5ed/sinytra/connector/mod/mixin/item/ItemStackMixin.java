package dev.su5ed.sinytra.connector.mod.mixin.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.su5ed.sinytra.connector.mod.compat.ItemStackExtensions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStack.class, priority = 500)
public class ItemStackMixin implements ItemStackExtensions {

    @Inject(method = "lambda$useOn$3", at = @At("HEAD"), remap = false, cancellable = true)
    private void appyUseOn(UseOnContext pContext, UseOnContext c, CallbackInfoReturnable<InteractionResult> cir) {
        InteractionResult result = connector_useOn(c);
        if (result != null) {
            cir.setReturnValue(result);
        }
    }

    @Unique
    public InteractionResult connector_useOn(UseOnContext context) {
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockInWorld bow = new BlockInWorld(context.getLevel(), pos, false);
        Item item = ((ItemStack) (Object) this).getItem();
        return null;
    }

    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void ensureMutableAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        cir.setReturnValue(HashMultimap.create(cir.getReturnValue()));
    }
}
