package github.thelawf.gensokyoontology.common.item.armor;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.client.model.KoishiHatModel;
import net.minecraft.client.entity.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.IArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class KoishiHatArmorItem extends ArmorItem {
    public static final Map<EquipmentSlotType, BipedModel<AbstractClientPlayer>> KOISHI_HAT_MODEL = new HashMap<>();
    public KoishiHatArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
        super(materialIn, slot, builderIn);
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlotType slot, String layer) {
        return GensokyoOntology.withRL("textures/entity/komeiji_koishi.png").toString();
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original) {
        return (A) KOISHI_HAT_MODEL.get(armorSlot);
    }

    @OnlyIn(Dist.CLIENT)
    public static void initArmorModel() {
        KOISHI_HAT_MODEL.put(EquipmentSlotType.HEAD, new KoishiHatModel(0.75F));
    }
}
