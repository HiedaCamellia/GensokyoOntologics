package github.thelawf.gensokyoontology.common.nbt;

import net.minecraft.nbt.CompoundTag;

public class GensokyoOntologyNBT implements IGensokyoOntologyNBT {
    public static CompoundTag nbtYattsume = new CompoundTag();
    public static CompoundTag nbtKoishiMousse = new CompoundTag();
    public static CompoundTag nbtWhiteSnow = new CompoundTag();
    public static CompoundTag lovePotionMasterName = new CompoundTag();

    public static CompoundTag nbtCanPlaceIn = new CompoundTag();

    int[] tagIntArrayYattsume = {CHEAP, SEAFOOD, FRESH};
    int[] tagIntArrayKoishiMousse = {HIGH_STANDARD, WESTERN, SWEET, PICTURALBE, FASCINATING};
    int tagWhiteSnow = CULTURAL;

    String canPlaceIn = "cyber_statistic_dimension";

    public GensokyoOntologyNBT() {
        nbtKoishiMousse.putIntArray("Koishi Hat Mousse", tagIntArrayKoishiMousse);
        nbtYattsume.putIntArray("Yattsume Una Yaki", tagIntArrayYattsume);
        nbtWhiteSnow.putInt("white_snow", tagWhiteSnow);
        nbtCanPlaceIn.putString("can_place_in", canPlaceIn);
    }

    public GensokyoOntologyNBT(String playerNameIn) {
        lovePotionMasterName.putString("love_potion", playerNameIn);
    }

    public static CompoundTag getLovePotionMasterName() {
        return lovePotionMasterName;
    }
}
