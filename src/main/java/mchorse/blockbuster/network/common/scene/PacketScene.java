package mchorse.blockbuster.network.common.scene;

import io.netty.buffer.ByteBuf;
import mchorse.blockbuster.CommonProxy;
import mchorse.blockbuster.common.tileentity.TileEntityDirector;
import mchorse.blockbuster.recording.scene.Scene;
import mchorse.blockbuster.recording.scene.SceneLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class PacketScene implements IMessage
{
    public SceneLocation location = new SceneLocation();

    public PacketScene()
    {}

    public PacketScene(SceneLocation location)
    {
        this.location = location;
    }

	public Scene get(World world)
	{
		if (this.location.isDirector())
		{
			BlockPos pos = this.location.getPosition();
			TileEntity te = world.isBlockLoaded(pos) ? world.getTileEntity(pos) : null;

			if (te instanceof TileEntityDirector)
			{
				return ((TileEntityDirector) te).director;
			}
		}
		else if (this.location.isScene())
		{
			return CommonProxy.scenes.get(this.location.getFilename(), world);
		}

		return null;
	}

    @Override
    public void fromBytes(ByteBuf buf)
    {
		this.location.fromByteBuf(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
		this.location.toByteBuf(buf);
    }
}