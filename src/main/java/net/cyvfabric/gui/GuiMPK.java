package net.cyvfabric.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.ColorTheme;
import net.cyvfabric.event.ConfigLoader;
import net.cyvfabric.hud.HUDManager;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class GuiMPK extends CyvGui {
    int sizeX = 100;
    int sizeY = 200;

    public ArrayList<LabelLine> labelLines = new ArrayList<>();
    int selectedIndex = -1;

    float vScroll = 0;
    float scroll = 0;
    int maxScroll = 0;
    boolean scrollClicked = false;

    TextFieldWidget searchBar;
    SubButton button;

    public GuiMPK() {
        super("MPK Gui");
    }

    @Override
    public void resize(MinecraftClient mcIn, int w, int h) {
        close();
    }

    @Override
    public void init() { //initialize the macro
        this.sizeX = 100;
        this.sizeY = sr.getScaledHeight()*3/4;

        this.updateLabels(false);

        maxScroll = Math.max(0, mc.textRenderer.fontHeight * 2 * labelLines.size() - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        this.searchBar = new TextFieldWidget(mc.textRenderer,
                sr.getScaledWidth()/2-sizeX/2 - 12,
                sr.getScaledHeight()/2-sizeY/2 - 10 - mc.textRenderer.fontHeight,
                75,
                mc.textRenderer.fontHeight, Text.of("")) {
            @Override
            public boolean charTyped(char p_146201_1_, int p_146201_2_) {
                if (super.charTyped(p_146201_1_, p_146201_2_)) {
                    updateLabels(true);
                    return true;
                } else {
                    return false;
                }
            }
        };
        this.searchBar.setDrawsBackground(false);
        this.button = new SubButton("Edit Positions", sr.getScaledWidth()/2-sizeX/2-15, sr.getScaledHeight()/2+sizeY/2 + 10);
    }

    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        {
            if (!this.searchBar.isFocused()) this.searchBar.setTextFieldFocused(true);
            this.searchBar.charTyped(typedChar, keyCode);
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) { //exit the gui
            if (this.searchBar.isFocused()) {
                this.searchBar.setTextFieldFocused(false);
                this.searchBar.setText("");
                updateLabels(true);
                return true;
            } else this.close();
        } else {
            this.searchBar.keyPressed(keyCode, scanCode, modifiers);
            updateLabels(true);
        }
        return true;
    }

    public void updateLabels(boolean fromSearch) {
        this.labelLines.clear();

        for (DraggableHUDElement l : HUDManager.registeredRenderers) {
            if (!fromSearch || l.getDisplayName().toLowerCase().contains(this.searchBar.getText().toLowerCase())
                    || l.getName().toLowerCase().contains(this.searchBar.getText().toLowerCase()))
                labelLines.add(new LabelLine(l));
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        this.renderBackground(matrices);

        maxScroll = Math.max(0, mc.textRenderer.fontHeight * 2 * labelLines.size() - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        GuiUtils.drawRoundedRect(matrices, sr.getScaledWidth()/2 - sizeX/2 - 15, sr.getScaledHeight()/2 - sizeY/2 - 4,
                sr.getScaledWidth()/2 + sizeX/2 + 14, sr.getScaledHeight()/2 + sizeY/2 + 4, 5, CyvFabric.theme.background1);

        int centerx = sr.getScaledWidth() / 2;
        int centery = sr.getScaledHeight() / 2;

        DrawableHelper.drawCenteredTextWithShadow(matrices, textRenderer, Text.of("MPK Gui:").asOrderedText(), sr.getScaledWidth()/2, 5 + sr.getScaledHeight()/2 - sizeY/2, 0xFFFFFFFF);

        //draw searchbar
        ColorTheme theme = CyvFabric.theme;
        boolean isHovered = this.searchBar.isFocused() ||
                (mouseX > searchBar.x - 3 &&
                        mouseX < searchBar.x + searchBar.getWidth() + 3 &&
                        mouseY > searchBar.y - 3.5 &&
                        mouseY < searchBar.y + searchBar.getHeight() + 2.5);

        GuiUtils.drawRoundedRect(matrices, searchBar.x - 3,
                (int) (searchBar.y - 3f),
                searchBar.x + searchBar.getWidth() + 3,
                (int) (searchBar.y + searchBar.getHeight() + 2f),
                2, theme.background1);
        GuiUtils.drawRoundedRect(matrices, (int) (searchBar.x - 1f),
                searchBar.y - 1,
                (int) (searchBar.x + searchBar.getWidth() + 1f),
                searchBar.y + searchBar.getHeight(),
                2, isHovered ? theme.main2 : theme.secondary1);
        GuiUtils.drawRoundedRect(matrices, (int) (searchBar.x - 1.5f),
                searchBar.y - 2,
                (int) (searchBar.x + searchBar.getWidth() + 1.5f),
                searchBar.y + searchBar.getHeight() + 1,
                2, theme.highlight);
        if (!this.searchBar.isFocused() && this.searchBar.getText().isEmpty()) {
            DrawableHelper.drawTextWithShadow(matrices, textRenderer, Text.of("Search"), searchBar.x + 16,
                    (int) (searchBar.y + 0.5f),
                    0xFFFFFFFF);

            //icon
            /*
            GlStateManager.enableBlend();
            GlStateManager.color(theme.borderBaseR, theme.borderBaseG, theme.borderBaseB);
            mc.getTextureManager().bindTexture(this.searchIcon);
            GuiUtils.drawModalRectWithCustomSizedTexture(searchBar.x + 1,
                    searchBar.y,
                    0, 0, 9, 9, 9, 9);
            GlStateManager.disableBlend();
             */

        } else {
        }
        this.searchBar.render(matrices, mouseX, mouseY, tickDelta);
        this.button.draw(matrices, mouseX, mouseY);

        enableScissor(centerx - ((sizeX + 10)/2),
                centery - (sizeY/2),
                centerx + ((sizeX + 10)/2), centery + sizeY/2 - (textRenderer.fontHeight * 2));


        int index = 0;
        for (LabelLine l : labelLines) {
            int yHeight = (int) ((index + 1) * textRenderer.fontHeight*2 - scroll + (sr.getScaledHeight()/2 - sizeY/2));
            l.drawEntry(matrices, index, (int) scroll, mouseX, mouseY, index == this.selectedIndex);
            index++;
        }

        RenderSystem.disableScissor();

        //draw scrollbar
        int scrollbarHeight = (int) ((sizeY - 8 - 15)/(0.01*maxScroll+1));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        int top = sr.getScaledHeight()/2-sizeY/2+4+15;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * (scroll /maxScroll));

        if (maxScroll == 0) amount = top;

        //color
        int color = theme.border2;
        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            color = theme.border1;
        }

        GuiUtils.drawRoundedRect(matrices, sr.getScaledWidth()/2+sizeX/2+2, amount,
                sr.getScaledWidth()/2+sizeX/2+8, amount+scrollbarHeight, 3, color);

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double mouseAmount) {
        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        int top = sr.getScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * (scroll /maxScroll));
        if (maxScroll == 0) amount = top;
        scrollClicked = mouseX > sr.getScaledWidth() / 2 + sizeX / 2 + 2 && mouseX < sr.getScaledWidth() / 2 + sizeX / 2 + 8 &&
                mouseY > amount && mouseY < amount + scrollbarHeight;

        if ((!scrollClicked) && mouseAmount != 0) {
            vScroll -= (float) (mouseAmount * 3);

            return true;
        }

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        int top = sr.getScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * (scroll /maxScroll));

        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            this.scrollClicked = true;
            return true;
        } else {
            this.scrollClicked = false;
        }

        if (this.button.clicked(mouseX, mouseY, mouseButton)) {
            mc.setScreen(new GuiHUDPositions(true));
            return true;
        }

        if (this.searchBar.mouseClicked(mouseX, mouseY, mouseButton)) {
            this.searchBar.setTextFieldFocused(true);
            updateLabels(true);
            return true;
        } else {
            this.searchBar.setTextFieldFocused(false);
            updateLabels(true);
        }

        if (mouseX < sr.getScaledWidth()/2-this.sizeX/2 || mouseX > sr.getScaledWidth()/2+this.sizeX/2
                || mouseY < sr.getScaledHeight()/2-this.sizeY/2 || mouseY > sr.getScaledHeight()/2+this.sizeY/2) {
            return false;
        }

        int index=0;
        for (LabelLine l : labelLines) {
            if (l.isPressed(index, (int) mouseX, (int) mouseY, mouseButton)
                    && mouseY > sr.getScaledHeight()/2 - sizeY/2 + textRenderer.fontHeight * 2
                    &&  mouseY < sr.getScaledHeight()/2 + sizeY/2 ) {
                l.mouseClicked(index, (int) mouseX, (int) mouseY, mouseButton);
                return true;
            }
            index++;
        }

        return false;

    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrollClicked) {
            int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
            int top = sr.getScaledHeight()/2-sizeY/2+4+15;
            int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;

            scroll = (int) ((float) (mouseY - (sr.getScaledHeight()/2-this.sizeY/2) - scrollbarHeight/2) /(bottom - top) * maxScroll);

            if (scroll > maxScroll) scroll = maxScroll;
            if (scroll < 0) scroll = 0;

            return true;
        }

        return false;

    }

    @Override
    public void tick() {
        //this.searchBar.updateCursorCounter();

        //smooth scrolling
        this.scroll += this.vScroll;
        this.vScroll *= 0.75F;

    }

    @Override
    public void removed() {
        //Keyboard.enableRepeatEvents(false);
        ConfigLoader.save(CyvFabric.config, false);
    }

    public class LabelLine {
        DraggableHUDElement label;
        int xStart = sr.getScaledWidth()/2 - sizeX/2 - 5;
        int width = sizeX;
        int height = textRenderer.fontHeight*2;

        public LabelLine(DraggableHUDElement label) {
            this.label = label;
        }

        public void drawEntry(MatrixStack matrices, int slotIndex, int scroll, int mouseX, int mouseY, boolean isSelected) {
            int yHeight = (slotIndex + 1) * textRenderer.fontHeight*2 - scroll + (sr.getScaledHeight()/2 - sizeY/2);
            GuiUtils.drawRoundedRect(matrices, xStart, yHeight + 1,
                    xStart + width, yHeight + height - 1,
                    3, label.isEnabled ? CyvFabric.theme.shade2 : CyvFabric.theme.secondary1);

            DrawableHelper.drawTextWithShadow(matrices, textRenderer, Text.of(label.getDisplayName()), xStart + 4, yHeight + height/3, 0xFFFFFFFF);

        }

        public boolean isPressed(int slotIndex, int mouseX, int mouseY, int mouseEvent) {
            float yHeight = (slotIndex + 1) * textRenderer.fontHeight*2 - scroll + (sr.getScaledHeight()/2 - sizeY/2);
            return mouseX > xStart && mouseX < xStart + width && mouseY > yHeight && mouseY < yHeight + height;
        }

        public void mouseClicked(int slotIndex, int mouseX, int mouseY, int mouseEvent) {
            label.setEnabled(!label.isEnabled);
        }

    }

    class SubButton {
        String text;
        int x, y;
        int sizeX = 80;
        int sizeY = 15;

        SubButton(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }

        void draw(MatrixStack matrices, int mouseX, int mouseY) {
            ColorTheme theme = CyvFabric.theme;
            boolean mouseDown = (mouseX > x && mouseX < x + sizeX && mouseY > y && mouseY < y + sizeY);
            GuiUtils.drawRoundedRect(matrices, x, y, x+sizeX, y+sizeY, 5, mouseDown ? theme.highlight : theme.background1);
            DrawableHelper.drawCenteredTextWithShadow(matrices, textRenderer, Text.of(this.text).asOrderedText(), x+sizeX/2, y+sizeY/2-textRenderer.fontHeight/2, 0xFFFFFFFF);
        }

        boolean clicked(double mouseX, double mouseY, int mouseButton) {
            return mouseX > x && mouseX < x + sizeX && mouseY > y && mouseY < y + sizeY && mouseButton == 0;
        }

    }

}
