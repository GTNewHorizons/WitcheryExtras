package alkalus.main.core.command;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.util.ReflectionUtils;
import com.emoniph.witchery.infusion.Infusion;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class DebugCommand implements ICommand {

    private final List<String> aliases;

    public DebugCommand() {
        this.aliases = new ArrayList<>();
        this.aliases.add("WD");
        this.aliases.add("wd");
    }

    @Override
    public int compareTo(final Object o) {
        if (o instanceof Comparable<?>) {
            @SuppressWarnings("unchecked")
            Comparable<ICommand> a = (Comparable<ICommand>) o;
            if (a.equals(this)) {
                return 0;
            } else {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public String getCommandName() {
        return "witcherydebug";
    }

    // Use '/gtpp' along with 'logging' or 'debug' to toggle Debug mode and Logging.
    // Using nothing after the command toggles both to their opposite states respectively.
    @Override
    public String getCommandUsage(final ICommandSender var1) {
        return "/witcherydebug";
    }

    @Override
    public List<String> getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void processCommand(final ICommandSender S, final String[] argString) {
        EntityPlayer aPlayer = getPlayer(S.getCommandSenderName());
        if (aPlayer != null) {
            if (argString != null) {
                if (argString[0] != null && argString[0].length() > 0) {
                    if (argString[0].equalsIgnoreCase("name")) {
                        GameProfile aProfile = aPlayer.getGameProfile();
                        if (!aPlayer.getCommandSenderName().equalsIgnoreCase("emoniph")) {
                            if (aProfile != null) {
                                Field aNameField = ReflectionUtils.getField(GameProfile.class, "name");
                                ReflectionUtils.setField(aProfile, aNameField, "emoniph");
                            }
                        }
                        if (!aPlayer.getDisplayName().equalsIgnoreCase("emoniph")) {
                            Field aNameField2 = ReflectionUtils.getField(EntityPlayer.class, "displayname");
                            ReflectionUtils.setField(aPlayer, aNameField2, "emoniph");
                        }
                        if (!aProfile.getName().equalsIgnoreCase("emoniph")) {
                            aProfile = new GameProfile(aPlayer.getUniqueID(), "emoniph");
                            Field aProfileField = ReflectionUtils.getField(EntityPlayer.class, "field_146106_i");
                            ReflectionUtils.setField(aPlayer, aProfileField, aProfile);
                        }
                    }
                    if (argString[0].equalsIgnoreCase("predictions") || argString[0].equalsIgnoreCase("pred")) {
                        NBTTagCompound nbtPlayer = Infusion.getNBT(aPlayer);
                        if (nbtPlayer != null && nbtPlayer.hasKey("WITCPredict")) {
                            NBTTagCompound nbtRoot = nbtPlayer.getCompoundTag("WITCPredict");
                            NBTTagList nbtList = nbtRoot.getTagList("WITCPreList", 10);
                            int aCount = 0;
                            while (nbtList.tagCount() > 0) {
                                nbtList.removeTag(0);
                                aCount++;
                            }
                            String aChatMessage = "Removed " + aCount + " Prediction Tags.";
                            if (aPlayer instanceof EntityPlayerMP && aChatMessage != null) {
                                aPlayer.addChatComponentMessage(new ChatComponentText(aChatMessage));
                            }
                            WitcheryExtras.log(1, "Removed " + aCount + " Prediction Tags.");
                        }
                    }
                }
            }
        } else {
            WitcheryExtras.log(2, "Player was null.");
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender var1) {
        return true;
    }

    @Override
    public List<?> addTabCompletionOptions(final ICommandSender var1, final String[] var2) {
        ArrayList<String> aTabCompletes = new ArrayList<String>();
        return aTabCompletes;
    }

    @Override
    public boolean isUsernameIndex(final String[] var1, final int var2) {
        return false;
    }

    public boolean playerUsesCommand(final World W, final EntityPlayer P, final int cost) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    private EntityPlayer getPlayer(final String name) {
        try {

            if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
                WitcheryExtras.log(2, "Using Clientside Player Object.");
                return getClientPlayer();
            }
            final List<EntityPlayer> i = new ArrayList<>();
            final Iterator<EntityPlayerMP> iterator = MinecraftServer.getServer()
                    .getConfigurationManager()
                    .playerEntityList
                    .iterator();
            while (iterator.hasNext()) {
                i.add((iterator.next()));
            }
            WitcheryExtras.log(2, "Looking for Player named: " + name);
            for (final EntityPlayer temp : i) {
                WitcheryExtras.log(2, "Found Player with name: " + temp.getCommandSenderName());
                if (temp.getCommandSenderName().equalsIgnoreCase(name)) {
                    return temp;
                }
            }
        } catch (final Throwable e) {
        }
        return null;
    }
}
