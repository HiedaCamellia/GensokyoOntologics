package github.thelawf.gensokyoontology.common.entity;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.dialog.DialogTreeNode;
import github.thelawf.gensokyoontology.api.dialog.IEntityDialog;
import github.thelawf.gensokyoontology.common.entity.monster.YoukaiEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IAngerable;
import net.minecraft.world.entity.passive.TameableEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class ConversationalEntity extends YoukaiEntity implements IAngerable, IEntityDialog {

    private DialogTreeNode dialog;
    public static final DataParameter<String> DATA_DIALOG_KEY = EntityDataManager.createKey(
            ConversationalEntity.class, DataSerializers.STRING);

    protected ConversationalEntity(EntityType<? extends TameableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_DIALOG_KEY, new DialogTreeNode("dialog." + GensokyoOntology.MODID + ".hello").getName());
    }

    @Override
    public void readAdditional(@NotNull CompoundTag compound) {
        super.readAdditional(compound);
        // if (compound.contains("dialog_key")) {
        //     this.dialog = new DialogTreeNode(compound.getString("dialog_key"));
        // }
    }

    @Override
    public void writeAdditional(@NotNull CompoundTag compound) {
        super.writeAdditional(compound);
        // compound.putString("dialog_key", this.dialog.getName());
    }

    @Override
    public DialogTreeNode getDialog() {
        return this.dialog;
    }

    @Override
    public void setDialog(DialogTreeNode dialog) {
        this.dialog = dialog;
    }
}
