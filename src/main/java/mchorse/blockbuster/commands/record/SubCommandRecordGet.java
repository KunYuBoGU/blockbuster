package mchorse.blockbuster.commands.record;

import java.util.Map;

import mchorse.blockbuster.commands.CommandRecord;
import mchorse.blockbuster.commands.McCommandBase;
import mchorse.blockbuster.recording.actions.Action;
import mchorse.blockbuster.recording.data.Record;
import mchorse.blockbuster.utils.L10n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

/**
 * Command /record get
 *
 * This command is responsible for outputting data of action at given tick and
 * player recording.
 */
public class SubCommandRecordGet extends McCommandBase
{
    @Override
    public int getRequiredArgs()
    {
        return 2;
    }

    @Override
    public String getName()
    {
        return "get";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "blockbuster.commands.record.get";
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        String filename = args[0];
        int tick = CommandBase.parseInt(args[1], 0);
        Record record = CommandRecord.getRecord(filename);

        if (tick <= 0 || tick >= record.actions.size())
        {
            throw new CommandException("record.tick_out_range", tick);
        }

        NBTTagCompound tag = new NBTTagCompound();
        Action action = record.actions.get(tick);
        String type = "none";

        for (Map.Entry<String, Integer> entry : Action.TYPES.entrySet())
        {
            if (entry.getValue().equals(action.getType()))
            {
                type = entry.getKey();
                break;
            }
        }

        if (action == null)
        {
            throw new CommandException("record.no_action", filename, tick);
        }

        action.toNBT(tag);

        L10n.info(sender, "record.action", tick, type, tag.toString());
    }
}