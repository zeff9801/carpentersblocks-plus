package com.carpentersblocks.renderer.entity;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import com.carpentersblocks.entity.item.EntityCarpentersTile;
import com.carpentersblocks.renderer.helper.LightingHelper;
import com.carpentersblocks.renderer.helper.RenderHelper;
import com.carpentersblocks.util.handler.DyeHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCarpentersTile extends Render {

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity entity, double x, double y, double z, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        bindTexture(getEntityTexture(entity));
        render((EntityCarpentersTile) entity, (int) x, (int) y, (int) z);
        GL11.glPopMatrix();
    }

    private void render(EntityCarpentersTile entity, int x, int y, int z)
    {
        RenderBlocks renderBlocks = RenderBlocks.getInstance();
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        final RenderHelper renderHelper = RenderHelper.get();

        double[] bounds = entity.getBounds();
        renderBlocks.setRenderBounds(bounds[0], bounds[1], bounds[2], bounds[3], bounds[4], bounds[5]);

        float[] dyeColor = LightingHelper.getRGB(DyeHandler.getColor(entity.getDye()));
        tessellator.setColorOpaque_F(dyeColor[0], dyeColor[1], dyeColor[2]);

        IIcon icon = entity.getIcon();

        renderHelper.setTextureRotationOverride(entity.getRotation());
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderHelper.renderFaceYNeg(renderBlocks, 0, 0, 0, icon);
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderHelper.renderFaceYPos(renderBlocks, 0, 0, 0, icon);
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderHelper.renderFaceZNeg(renderBlocks, 0, 0, 0, icon);
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderHelper.renderFaceZPos(renderBlocks, 0, 0, 0, icon);
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderHelper.renderFaceXNeg(renderBlocks, 0, 0, 0, icon);
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderHelper.renderFaceXPos(renderBlocks, 0, 0, 0, icon);
        renderHelper.clearTextureRotationOverride();

        tessellator.draw();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity var1)
    {
        return TextureMap.locationBlocksTexture;
    }

}
