package net.cyvfabric.util.parkour;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class LandingBlock {

    public BlockPos pos;
    public Box[] bb; //bounding boxes
    public LandingMode mode;
    public LandingAxis axis;
    public boolean isBox;
    public boolean neoAndNormal;

    public Double pb;
    public Double pbX;
    public Double pbZ;

    public Double lastOffsetX;
    public Double lastOffsetZ;
    public Double lastPb;

    public double xMinCond, xMaxCond, zMinCond, zMaxCond; //detection boxes (for offsets)

    public LandingBlock(BlockPos pos, LandingMode mode, LandingAxis axis, boolean isBox) {
        this.pos = pos;
        this.mode = mode;
        this.axis = axis;
        this.isBox = isBox;

        calculateBounds(); //calculate the hitbox

        //create detection box
        this.xMinCond = this.smallestX() - 1;
        this.xMaxCond = this.largestX() + 1;
        this.zMinCond = this.smallestZ() - 1;
        this.zMaxCond = this.largestZ() + 1;
    }

    private void calculateBounds() {
        if (this.pos == null) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        World world = mc.world;

        BlockState blockState = world.getBlockState(pos);
        VoxelShape collisionBox = blockState.getCollisionShape(world, pos);

        Box[] tempB = collisionBox.getBoundingBoxes().toArray(Box[]::new);
        this.bb = new Box[tempB.length];

        for (int i=0; i<tempB.length; i++) {
            bb[i] = new Box(tempB[i].minX + pos.getX(), tempB[i].minY + pos.getY(), tempB[i].minZ + pos.getZ(),
                    tempB[i].maxX + pos.getX(), tempB[i].maxY + pos.getY(), tempB[i].maxZ + pos.getZ());
        }

        System.out.println(bb);

    }

    public double smallestX() {
        Double v = null;
        for (Box bb : this.bb) {
            if (v == null || v > bb.minX) v = bb.minX;
        }
        return v;
    }

    public double smallestY() {
        Double v = null;
        for (Box bb : this.bb) {
            if (v == null || v > bb.minY) v = bb.minY;
        }
        return v;
    }

    public double smallestZ() {
        Double v = null;
        for (Box bb : this.bb) {
            if (v == null || v > bb.minZ) v = bb.minZ;
        }
        return v;
    }

    public double largestX() {
        Double v = null;
        for (Box bb : this.bb) {
            if (v == null || v < bb.maxX) v = bb.maxX;
        }
        return v;
    }

    public double largestY() {
        Double v = null;
        for (Box bb : this.bb) {
            if (v == null || v < bb.maxY) v = bb.maxY;
        }
        return v;
    }

    public double largestZ() {
        Double v = null;
        for (Box bb : this.bb) {
            if (v == null || v < bb.maxZ) v = bb.maxZ;
        }
        return v;
    }

    public void adjustCond(double x1, double x2, double z1, double z2) {
        if (x1 > x2) {
            this.xMaxCond = x1;
            this.xMinCond = x2;

        } else {
            this.xMaxCond = x2;
            this.xMinCond = x1;

        }

        if (z1 > z2) {
            this.zMaxCond = z1;
            this.zMinCond = z2;
        } else {
            this.zMaxCond = z2;
            this.zMinCond = z1;
        }

    }

}
