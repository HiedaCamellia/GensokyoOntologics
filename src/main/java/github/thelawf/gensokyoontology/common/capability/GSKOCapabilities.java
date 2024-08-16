package github.thelawf.gensokyoontology.common.capability;

import github.thelawf.gensokyoontology.common.capability.entity.ExtraLifeCapability;
import github.thelawf.gensokyoontology.common.capability.entity.BeliefCapability;
import github.thelawf.gensokyoontology.common.capability.entity.GSKOPowerCapability;
import github.thelawf.gensokyoontology.common.capability.entity.SecularLifeCapability;
import github.thelawf.gensokyoontology.common.capability.world.BloodyMistCapability;
import github.thelawf.gensokyoontology.common.capability.world.EternalSummerCapability;
import github.thelawf.gensokyoontology.common.capability.world.ImperishableNightCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class GSKOCapabilities {


    @CapabilityInject(BloodyMistCapability.class)
    public static Capability<BloodyMistCapability> BLOODY_MIST;
    @CapabilityInject(ImperishableNightCapability.class)
    public static Capability<ImperishableNightCapability> IMPERISHABLE_NIGHT;
    @CapabilityInject(EternalSummerCapability.class)
    public static Capability<EternalSummerCapability> ETERNAL_SUMMER;

    @CapabilityInject(GSKOPowerCapability.class)
    public static Capability<GSKOPowerCapability> POWER;
    @CapabilityInject(SecularLifeCapability.class)
    public static Capability<SecularLifeCapability> SECULAR_LIFE;
    @CapabilityInject(BeliefCapability.class)
    public static Capability<BeliefCapability> BELIEF;
    @CapabilityInject(ExtraLifeCapability.class)
    public static Capability<ExtraLifeCapability> EXTRA_LIFE;

    public static void registerCapabilities() {
        register(BeliefCapability.class);
        register(GSKOPowerCapability.class);
        register(SecularLifeCapability.class);
        register(ExtraLifeCapability.class);

        register(BloodyMistCapability.class);
        register(ImperishableNightCapability.class);
        register(EternalSummerCapability.class);
    }

    private static <T extends INBTSerializable<CompoundTag>> void register(Class<T> capClass, Capability.IStorage<T> storage) {
        CapabilityManager.INSTANCE.register(capClass, storage, capClass::newInstance);
    }

    public static boolean hasCapability(Level world, Capability<?> capability) {
        if (world.isRemote) return false;
        ServerLevel serverLevel = (ServerLevel) world;
        return serverLevel.getCapability(capability).isPresent();
    }

    public static Method getCapMethod(Player player, Capability<?> capability, String methodName) {
        AtomicReference<Method> methodRef = new AtomicReference<>();
        player.getCapability(capability).ifPresent(cap -> {
            try {
                methodRef.set(cap.getClass().getDeclaredMethod(methodName));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
        return methodRef.get();
    }

    public static INBTSerializable<CompoundTag> getCapInstance(Player player, Capability<? extends INBTSerializable<CompoundTag>> capability) {
        AtomicReference<INBTSerializable<CompoundTag>> capRef = new AtomicReference<>();
        player.getCapability(capability).ifPresent(capRef::set);
        return capRef.get();
    }

    public static <T> T getCapIns(Player player, Capability<T> capability) {
        AtomicReference<T> capRef = new AtomicReference<>();
        player.getCapability(capability).ifPresent(cap -> capRef.set(capability.getDefaultInstance()));
        return capRef.get();
    }

    public static Object getAndInvoke(Player player, Capability<? extends INBTSerializable<CompoundTag>> capability, String methodName, Object... objects) {
        if (getCapMethod(player, capability, methodName) == null) {
            throw new RuntimeException("Cap not present");
        }
        try {
            return getCapMethod(player, capability, methodName).invoke(getCapInstance(player, capability), objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Function<Object, Object> getCapFunctor(Player player, Capability<T> capability, String methodName) {
        return (parameter) -> {
            try {
                return getCapMethod(player, capability, methodName).invoke(getCapIns(player, capability));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <T> Object getFunctorResult(Player player, Capability<T> capability, String methodName, @Nullable Object... objects) {
        return getCapFunctor(player, capability, methodName).apply(objects);
    }

    private static <T extends INBTSerializable<CompoundTag>> void register(Class<T> capClass) {
        CapabilityManager.INSTANCE.register(capClass, new Capability.IStorage<T>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
                instance.deserializeNBT((CompoundTag) nbt);
            }
        }, () -> null);
    }
}
