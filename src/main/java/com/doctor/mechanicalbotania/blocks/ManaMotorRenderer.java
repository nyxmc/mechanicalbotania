package com.doctor.mechanicalbotania.blocks;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class ManaMotorRenderer extends KineticBlockEntityRenderer {
    public ManaMotorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    protected SuperByteBuffer getRotatedModel(KineticBlockEntityRenderer te, BlockState state) {
        return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state);
    }
}
