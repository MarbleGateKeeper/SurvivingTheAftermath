package mod.surviving_the_aftermath.mixin;

import mod.surviving_the_aftermath.init.ModMobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager{


    public VillagerMixin(EntityType<? extends AbstractVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    //updateSpecialPrices
    @Inject( method = "updateSpecialPrices",at = @At("RETURN"), remap=false)
    private void updateSpecialPrices(Player pPlayer, CallbackInfo ci) {
        MerchantOffers offers = this.getOffers();

        if (pPlayer.hasEffect(ModMobEffects.COWARDICE.get())) {
            for (MerchantOffer offer : offers) {
                double d0 = 0.3D + 0.0625D;
                int j = (int) Math.floor(d0 * (double) offer.getBaseCostA().getCount());
                offer.addToSpecialPriceDiff(Math.max(j, 1));
            }
        }
    }
}
