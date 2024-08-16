package github.thelawf.gensokyoontology.common.container;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;

public class DanmakuBehaviorCreatorContainer extends Container {

    private static final ResourceLocation MAIN = ResourceLocation.parse(GensokyoOntology.MODID +
            "textures/client/dan_creator_main.png");

    private static final ResourceLocation SIDE_BAR = ResourceLocation.parse(GensokyoOntology.MODID +
            "textures/client/dan_creator_sidebar.png");

    private static final ResourceLocation TOOL_TAB = ResourceLocation.parse(GensokyoOntology.MODID +
            "textures/client/dan_creator_tab.png");

    private static final ResourceLocation SCRIPT_TYPE = ResourceLocation.parse(GensokyoOntology.MODID +
            "textures/client/script_type.png");

    private static final ResourceLocation BUTTON = ResourceLocation.parse(GensokyoOntology.MODID +
            "textures/client/dan_creator_buttons.png");

    private ImageButton whileLoop;
    private ImageButton foriLoop;
    private ImageButton ifBranch;
    private ImageButton elseBranch;
    private ImageButton elseIfBranch;

    private EditBox assignVector3f;
    private EditBox assignNumber;
    private EditBox assignList;
    private EditBox assignMap;
    private EditBox addComment;

    private ToggleWidget trueOrFalse;

    private String operation;

    protected DanmakuBehaviorCreatorContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }

    @Override
    public boolean canInteractWith(Player playerIn) {
        return false;
    }
}
