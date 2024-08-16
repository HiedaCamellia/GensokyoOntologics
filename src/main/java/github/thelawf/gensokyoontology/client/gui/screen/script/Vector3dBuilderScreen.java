package github.thelawf.gensokyoontology.client.gui.screen.script;

import com.google.common.collect.Lists;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.client.layout.WidgetConfig;
import github.thelawf.gensokyoontology.client.gui.screen.widget.BlankWidget;
import github.thelawf.gensokyoontology.common.container.script.OneSlotContainer;
import github.thelawf.gensokyoontology.common.nbt.GSKONBTUtil;
import github.thelawf.gensokyoontology.common.network.GSKONetworking;
import github.thelawf.gensokyoontology.common.network.packet.CMergeScriptPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// 50, 10
// 50, 40
// 50, 70
// 50, 100
// 150, 85; 45, 20
@OnlyIn(value = Dist.CLIENT)
public class Vec3BuilderScreen extends OneSlotContainerScreen {
    public static final String TYPE = "vector3d";
    private final CompoundTag vector3dData = new CompoundTag();
    private EditBox nameInput;
    private EditBox xInput;
    private EditBox yInput;
    private EditBox zInput;
    public static final ResourceLocation TEXTURE = GensokyoOntology.withRL("textures/gui/one_slot_screen_vec.png");
    private final WidgetConfig TEXT_LABEL1 = WidgetConfig.of(new BlankWidget(0,0,0,0, withText("null")),0,0).isText(true);
    private final WidgetConfig TEXT_LABEL2 = WidgetConfig.of(new BlankWidget(0,0,0,0, withText("null")),0,0).isText(true);
    private final WidgetConfig TEXT_LABEL3 = WidgetConfig.of(new BlankWidget(0,0,0,0, withText("null")),0,0).isText(true);
    private final WidgetConfig TEXT_LABEL4 = WidgetConfig.of(new BlankWidget(0,0,0,0, withText("null")),0,0).isText(true);

    private final Component tipName = GensokyoOntology.withTranslation("gui.",".vector3d.builder.name");
    private List<WidgetConfig> WIDGETS;

    public Vec3BuilderScreen(OneSlotContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.playerInventoryTitleX = 6;
        this.playerInventoryTitleY = 115;
        this.stack = inv.player.getHeldItemMainhand();
    }


    @Override
    public void tick() {
        super.tick();
        this.nameInput.tick();
        this.xInput.tick();
        this.yInput.tick();
        this.zInput.tick();
    }

    @Override
    protected void init() {
        super.init();
        this.nameInput = new EditBox(this.font, 0,0,0,0, this.title);
        this.xInput = new EditBox(this.font, 0, 50, 100, 30, Component.literal(""));
        this.yInput = new EditBox(this.font, 60, 50, 100, 30, Component.literal(""));
        this.zInput = new EditBox(this.font, 120, 50, 100, 30, Component.literal(""));

        this.saveBtn = new Button(0, 200, 30, 30, this.saveText, (button) -> {});

        WIDGETS = Lists.newArrayList(
                WidgetConfig.of(this.nameInput, 90, 20).setXY(50, 10)
                        .withFont(this.font)
                        .withText(withText("input here")),

                TEXT_LABEL2.setXY(10, 40).withText(withText("X: ")).withFont(this.font),
                WidgetConfig.of(this.xInput, 90, 20).setXY(50, 40)
                        .withFont(this.font)
                        .withText(withText("0")),

                TEXT_LABEL3.setXY(10, 70).withText(withText("Y: ")).withFont(this.font),
                WidgetConfig.of(this.yInput, 90, 20).setXY(50, 70)
                        .withFont(this.font)
                        .withText(withText("0")),

                TEXT_LABEL4.setXY(10, 100).withText(withText("Z: ")).withFont(this.font),
                WidgetConfig.of(this.zInput, 90, 20).setXY(50, 100)
                        .withFont(this.font)
                        .withText(withText("0")),

                WidgetConfig.of(this.saveBtn, 45, 20).setXY(150, 85)
                        .withText(this.saveText)
                        .withFont(this.font)
                        .withAction(this::saveBtnAction));

        this.setRelativeToParent(WIDGETS, this.guiLeft, this.guiTop);

        if (this.stack.getTag() != null) {

            this.saveBtn.active = false;
            this.nameInput.active = false;

            this.nameInput.setText(this.stack.getTag().getString("name"));
            CompoundTag nbt = this.stack.getTag();

            this.xInput.setText(GSKONBTUtil.getMemberValueAsString(nbt, "x"));
            this.yInput.setText(GSKONBTUtil.getMemberValueAsString(nbt, "y"));
            this.zInput.setText(GSKONBTUtil.getMemberValueAsString(nbt, "z"));
        }
    }

    private void saveBtnAction(Button button) {
        if (this.checkCanSave()) saveData();
    }

    private boolean checkCanSave() {
        return !this.nameInput.getText().equals("") && !this.xInput.getText().equals("") &&
                !this.yInput.getText().equals("") && !this.zInput.getText().equals("");
    }

    private void saveData() {
        CompoundTag vector3d = new CompoundTag();
        vector3d.put("x", DoubleNBT.valueOf(parseDouble(this.xInput.getText())));
        vector3d.put("y", DoubleNBT.valueOf(parseDouble(this.yInput.getText())));
        vector3d.put("z", DoubleNBT.valueOf(parseDouble(this.zInput.getText())));

        this.vector3dData.putString("type", TYPE);
        this.vector3dData.putString("name", this.nameInput.getText());
        this.vector3dData.put("value", vector3d);

        GSKONetworking.CHANNEL.sendToServer(new CMergeScriptPacket(this.vector3dData));
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (this.minecraft != null) {
            this.renderRelativeToParent(WIDGETS, matrixStack, mouseX, mouseY, this.guiLeft, this.guiTop, partialTicks);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (this.minecraft == null) return;
        if (this.minecraft.player == null) return;
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    public CompoundTag getVec3NBT() {
        return this.vector3dData;
    }

    public Vec3 getAsVec3() {
        CompoundTag nbt = this.vector3dData;
        return new Vec3(nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"));
    }

}
