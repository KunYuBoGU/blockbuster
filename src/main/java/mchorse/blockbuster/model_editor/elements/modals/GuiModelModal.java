package mchorse.blockbuster.model_editor.elements.modals;

import mchorse.blockbuster.model_editor.elements.GuiThreeInput;
import mchorse.blockbuster.model_editor.elements.GuiTwoInput;
import mchorse.blockbuster.model_editor.modal.GuiModal;
import mchorse.metamorph.api.models.Model;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

/**
 * Model properties modal
 *
 * This modal allows users to edit properties of the currently editing model
 * such as name, visual scale and texture size.
 *
 * TODO: add ability to edit path to default texture
 */
public class GuiModelModal extends GuiModal
{
    private final String strName = I18n.format("blockbuster.gui.me.model.name");
    private final String strScale = I18n.format("blockbuster.gui.me.model.scale");
    private final String strTextureSize = I18n.format("blockbuster.gui.me.model.texture_size");
    private final String strTexture = I18n.format("blockbuster.gui.me.model.texture");

    public Model model;

    private int id;

    /* GUI fields */

    /**
     * Name of the model
     */
    public GuiTextField name;

    /**
     * Scale of the model
     */
    public GuiThreeInput scale;

    /**
     * Texture size of the model
     */
    public GuiTwoInput textureSize;

    /**
     * Path to default texture of the model
     */
    public GuiTextField texture;

    /**
     * Button which activates saving of the model
     */
    private GuiButton done;

    public GuiModelModal(int id, GuiScreen parent, FontRenderer font)
    {
        super(parent, font);
        this.height = 140;
        this.id = id;
    }

    public GuiModelModal setModel(Model model)
    {
        this.model = model;

        return this;
    }

    @Override
    public void initiate()
    {
        super.initiate();

        int x = this.x + this.width;
        int y = this.y + this.height;

        int w = this.width - 55;
        int x2 = x - 8 - w;

        this.name = new GuiTextField(0, this.font, x2 + 1, y - 104, w - 2, 18);
        this.scale = new GuiThreeInput(0, this.font, x2, y - 78, w, null);
        this.textureSize = new GuiTwoInput(0, this.font, x2 + 33, y - 53, w - 33, null);
        this.done = new GuiButton(this.id, x - this.buttonWidth - 10, y - 30, this.buttonWidth, 20, I18n.format("blockbuster.gui.done"));

        this.buttons.clear();
        this.buttons.add(this.done);

        if (this.model != null)
        {
            this.name.setText(this.model.name);

            this.scale.a.setText(String.valueOf(this.model.scale[0]));
            this.scale.b.setText(String.valueOf(this.model.scale[1]));
            this.scale.c.setText(String.valueOf(this.model.scale[2]));

            this.textureSize.a.setText(String.valueOf(this.model.texture[0]));
            this.textureSize.b.setText(String.valueOf(this.model.texture[1]));
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        this.name.textboxKeyTyped(typedChar, keyCode);
        this.scale.keyTyped(typedChar, keyCode);
        this.textureSize.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        this.name.mouseClicked(mouseX, mouseY, mouseButton);
        this.scale.mouseClicked(mouseX, mouseY, mouseButton);
        this.textureSize.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawModal(int mouseX, int mouseY, float partialTicks)
    {
        super.drawModal(mouseX, mouseY, partialTicks);

        this.name.drawTextBox();
        this.scale.draw();
        this.textureSize.draw();

        int x = this.x + 10;
        int y = this.y + this.height - 9;

        this.font.drawStringWithShadow(this.strName, x, y - 90, 0xffffffff);
        this.font.drawStringWithShadow(this.strScale, x, y - 64, 0xffffffff);
        this.font.drawStringWithShadow(this.strTextureSize, x, y - 39, 0xffffffff);
    }
}