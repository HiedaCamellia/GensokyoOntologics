package github.thelawf.gensokyoontology.common.util.world;


import com.mojang.datafixers.util.Pair;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConnectionUtil {
    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean tryConnect(BlockPos startPos, BlockPos endPos) {
        if (startPos.getY() == endPos.getY()) {
            return true;
        } else if (GSKOMathUtil.distanceOf3D(
                startPos.getX(), startPos.getY(), startPos.getZ(),
                endPos.getX(), endPos.getY(), endPos.getZ()) == 0) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 获取组成贝塞尔曲线的离散直线部分的数量
     *
     * @param startPosIn 第一次点击的方块位置
     * @param endPosIn   第二次点击的方块位置
     * @return 组成曲线的直线部分的数量
     */
    public static int getSegCount(BlockPos startPosIn, BlockPos endPosIn) {
        if (startPosIn == null || endPosIn == null) {
            LOGGER.warn("Invalid Block Positions: {} , {}", startPosIn, endPosIn);
            return 0;
        } else {
            return (int) (GSKOMathUtil.distanceOf3D(
                    startPosIn.getX(), startPosIn.getY(), startPosIn.getZ(),
                    endPosIn.getX(), endPosIn.getY(), endPosIn.getZ()) / 3);
        }
    }

    public static BlockPos.Mutable getSegPositions() {
        return null;
    }

    public static List<Double> getSegRotations() {
        return new ArrayList<>();
    }

    /**
     * 获取直线的位置以及旋转
     * 用法：在外部迭代调用该方法，迭代传入起始点和终点，迭代次数为上文segment的个数。
     *
     * @return 方块位置(x, y, z)和三维向量(roll, yaw, pitch)的一对儿
     */
    public static Pair<Vec3, Vec3> getPosAndRot(Vec3 startPos, Vec3 intersection, Vec3 endPos, float time) {
        Vec3 segPos = GSKOMathUtil.bezier2(startPos, intersection, endPos, time);
        return new Pair<>(segPos, new Vec3(1, 1, 1));
    }

    public static void connectRail(Vec3 startPos, Vec3 intersection, Vec3 endPos, float time) {
        Pair<Vec3, Vec3> pair = getPosAndRot(startPos, intersection, endPos, time);
    }
}
