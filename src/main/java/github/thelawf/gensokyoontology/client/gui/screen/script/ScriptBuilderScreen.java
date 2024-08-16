package github.thelawf.gensokyoontology.client.gui.screen.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.client.gui.screen.CommandBlockScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ScriptBuilderScreen extends LineralLayoutScreen {
    protected Button saveBtn;
    protected Button closeBtn;
    protected Button addBtn;
    protected Button resetBtn;
    protected Button renameBtn;
    protected ItemStack stack;
    protected final Component fieldName = GensokyoOntology.withTranslation("gui.", ".script_builder.fieldName");
    protected Component saveText = GensokyoOntology.withTranslation("gui.", ".script.button.save");

    public ScriptBuilderScreen(Component titleIn, ItemStack stack) {
        super(titleIn);
        this.stack = stack;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


}
