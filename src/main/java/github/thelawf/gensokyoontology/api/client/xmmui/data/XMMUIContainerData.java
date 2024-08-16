package github.thelawf.gensokyoontology.api.client.xmmui.data;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.dom4j.Element;

public class XMMUIContainerData extends XMMUIWidgetData{
    public NonNullList<ItemStack> stacks;
    public XMMUIContainerData(Element element) {
        super(element);
        element.elementIterator().forEachRemaining(e -> {
            e.attributeValue("item");
        });
    }
}
