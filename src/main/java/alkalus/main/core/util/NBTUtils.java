package alkalus.main.core.util;

import alkalus.main.core.WitcheryExtras;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class NBTUtils {

    public static NBTTagCompound getNBT(ItemStack aStack) {
        NBTTagCompound rNBT = aStack.getTagCompound();
        return ((rNBT == null) ? new NBTTagCompound() : rNBT);
    }

    public static void setBookTitle(ItemStack aStack, String aTitle) {
        NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setString("title", aTitle);
        setNBT(aStack, tNBT);
    }

    public static String getBookTitle(ItemStack aStack) {
        NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getString("title");
    }

    public static ItemStack[] readItemsFromNBT(ItemStack itemstack) {
        NBTTagCompound tNBT = getNBT(itemstack);
        final NBTTagList list = tNBT.getTagList("Items", 10);
        ItemStack inventory[] = new ItemStack[list.tagCount()];
        for (int i = 0; i < list.tagCount(); i++) {
            final NBTTagCompound data = list.getCompoundTagAt(i);
            final int slot = data.getInteger("Slot");
            if ((slot >= 0) && (slot < list.tagCount())) {
                if (ItemStack.loadItemStackFromNBT(data) == null) {
                    inventory[slot] = null;
                } else {
                    inventory[slot] = ItemStack.loadItemStackFromNBT(data);
                }
            }
        }
        return inventory;
    }

    public static ItemStack[] readItemsFromNBT(ItemStack itemstack, String customkey) {
        NBTTagCompound tNBT = getNBT(itemstack);
        final NBTTagList list = tNBT.getTagList(customkey, 10);
        ItemStack inventory[] = new ItemStack[list.tagCount()];
        for (int i = 0; i < list.tagCount(); i++) {
            final NBTTagCompound data = list.getCompoundTagAt(i);
            final int slot = data.getInteger("Slot");
            if ((slot >= 0) && (slot < list.tagCount())) {
                if (ItemStack.loadItemStackFromNBT(data) == null) {
                    inventory[slot] = null;
                } else {
                    inventory[slot] = ItemStack.loadItemStackFromNBT(data);
                }
            }
        }
        return inventory;
    }

    public static ItemStack writeItemsToNBT(ItemStack itemstack, ItemStack[] stored) {
        NBTTagCompound tNBT = getNBT(itemstack);
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < stored.length; i++) {
            final ItemStack stack = stored[i];
            if (stack != null) {
                final NBTTagCompound data = new NBTTagCompound();
                stack.writeToNBT(data);
                data.setInteger("Slot", i);
                list.appendTag(data);
            }
        }
        tNBT.setTag("Items", list);
        itemstack.setTagCompound(tNBT);
        return itemstack;
    }

    public static ItemStack writeItemsToNBT(ItemStack itemstack, ItemStack[] stored, String customkey) {
        NBTTagCompound tNBT = getNBT(itemstack);
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < stored.length; i++) {
            final ItemStack stack = stored[i];
            if (stack != null) {
                final NBTTagCompound data = new NBTTagCompound();
                stack.writeToNBT(data);
                data.setInteger("Slot", i);
                list.appendTag(data);
            }
        }
        tNBT.setTag(customkey, list);
        itemstack.setTagCompound(tNBT);
        return itemstack;
    }

    public static void setBoolean(ItemStack aStack, String aTag, boolean aBoolean) {
        NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setBoolean(aTag, aBoolean);
        setNBT(aStack, tNBT);
    }

    public static boolean getBoolean(ItemStack aStack, String aTag) {
        NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getBoolean(aTag);
    }

    public static void setInteger(ItemStack aStack, String aTag, int aInt) {
        NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setInteger(aTag, aInt);
        setNBT(aStack, tNBT);
    }

    public static int getInteger(ItemStack aStack, String aTag) {
        NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getInteger(aTag);
    }

    public static void setLong(ItemStack aStack, String aTag, long aInt) {
        NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setLong(aTag, aInt);
        setNBT(aStack, tNBT);
    }

    public static long getLong(ItemStack aStack, String aTag) {
        NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getLong(aTag);
    }

    public static void setFloat(ItemStack aStack, String aTag, float aInt) {
        NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setFloat(aTag, aInt);
        setNBT(aStack, tNBT);
    }

    public static float getFloat(ItemStack aStack, String aTag) {
        NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getFloat(aTag);
    }

    public static void setString(ItemStack aStack, String aTag, String aString) {
        NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setString(aTag, aString);
        setNBT(aStack, tNBT);
    }

    public static String getString(ItemStack aStack, String aTag) {
        NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getString(aTag);
    }

    public static boolean doesStringExist(ItemStack aStack, String aTag) {
        NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.hasKey(aTag);
    }

    public static boolean tryIterateNBTData(ItemStack aStack) {
        try {
            NBTTagCompound aNBT = NBTUtils.getNBT(aStack);
            if (aNBT != null) {
                if (!aNBT.hasNoTags()) {
                    Map<?, ?> mInternalMap = getField(aNBT, "tagMap");
                    if (mInternalMap != null) {
                        for (Map.Entry<?, ?> e : mInternalMap.entrySet()) {
                            WitcheryExtras.log(
                                    0,
                                    "Key: " + e.getKey().toString() + " | Value: " + e.getValue() + " | Type: "
                                            + e.getValue().getClass().getName());
                        }
                        return true;
                    } else {
                        WitcheryExtras.log(0, "Data map reflected from NBTTagCompound was not valid.");
                        return false;
                    }
                }
            }
        } catch (Throwable t) {
        }
        return false;
    }

    // Botania soulbind handling
    public static boolean setBotanicaSoulboundOwner(ItemStack aStack, String aName) {
        final String TAG_SOULBIND = "soulbind";
        NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setString(TAG_SOULBIND, aName);
        setNBT(aStack, tNBT);
        if (NBTUtils.doesStringExist(aStack, TAG_SOULBIND)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getBotanicaSoulboundOwner(ItemStack aStack) {
        final String TAG_SOULBIND = "soulbind";
        NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getString(TAG_SOULBIND);
    }

    public static boolean hasKey(ItemStack stack, String key) {
        final NBTTagCompound itemData = getNBT(stack);
        if (itemData.hasKey(key)) {
            return true;
        }
        return false;
    }

    public static boolean createIntegerTagCompound(ItemStack rStack, String tagName, String keyName, int keyValue) {
        final NBTTagCompound tagMain = getNBT(rStack);
        final NBTTagCompound tagNBT = new NBTTagCompound();
        tagNBT.setInteger(keyName, keyValue);
        tagMain.setTag(tagName, tagNBT);
        rStack.setTagCompound(tagMain);
        return true;
    }

    public static boolean createLongTagCompound(ItemStack rStack, String tagName, String keyName, long keyValue) {
        final NBTTagCompound tagMain = getNBT(rStack);
        final NBTTagCompound tagNBT = new NBTTagCompound();
        tagNBT.setLong(keyName, keyValue);
        tagMain.setTag(tagName, tagNBT);
        rStack.setTagCompound(tagMain);
        return true;
    }

    public static boolean createStringTagCompound(ItemStack rStack, String tagName, String keyName, String keyValue) {
        final NBTTagCompound tagMain = getNBT(rStack);
        final NBTTagCompound tagNBT = new NBTTagCompound();
        tagNBT.setString(keyName, keyValue);
        tagMain.setTag(tagName, tagNBT);
        rStack.setTagCompound(tagMain);
        return true;
    }

    public static boolean createFloatTagCompound(ItemStack rStack, String tagName, String keyName, float keyValue) {
        final NBTTagCompound tagMain = getNBT(rStack);
        final NBTTagCompound tagNBT = new NBTTagCompound();
        tagNBT.setFloat(keyName, keyValue);
        tagMain.setTag(tagName, tagNBT);
        rStack.setTagCompound(tagMain);
        return true;
    }

    public static boolean createByteTagCompound(ItemStack rStack, String tagName, String keyName, byte keyValue) {
        final NBTTagCompound tagMain = getNBT(rStack);
        final NBTTagCompound tagNBT = new NBTTagCompound();
        tagNBT.setByte(keyName, keyValue);
        tagMain.setTag(tagName, tagNBT);
        rStack.setTagCompound(tagMain);
        return true;
    }

    public static boolean createBooleanTagCompound(ItemStack rStack, String tagName, String keyName, boolean keyValue) {
        final NBTTagCompound tagMain = getNBT(rStack);
        final NBTTagCompound tagNBT = new NBTTagCompound();
        tagNBT.setBoolean(keyName, keyValue);
        tagMain.setTag(tagName, tagNBT);
        rStack.setTagCompound(tagMain);
        return true;
    }

    public static boolean createTagCompound(ItemStack rStack, String tagName, NBTTagCompound keyValue) {
        final NBTTagCompound tagMain = getNBT(rStack);
        NBTTagCompound tagNBT = keyValue;
        tagMain.setTag(tagName, tagNBT);
        rStack.setTagCompound(tagMain);
        return true;
    }

    public static int getIntegerTagCompound(ItemStack aStack, String tagName, String keyName) {
        NBTTagCompound aNBT = getNBT(aStack);
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag(tagName);
            if (aNBT != null) {
                return aNBT.getInteger(keyName);
            }
        }
        return 0;
    }

    public static long getLongTagCompound(ItemStack aStack, String tagName, String keyName) {
        NBTTagCompound aNBT = getNBT(aStack);
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag(tagName);
            if (aNBT != null) {
                return aNBT.getLong(keyName);
            }
        }
        return 0L;
    }

    public static String getStringTagCompound(ItemStack aStack, String tagName, String keyName) {
        NBTTagCompound aNBT = getNBT(aStack);
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag(tagName);
            if (aNBT != null) {
                return aNBT.getString(keyName);
            }
        }
        return null;
    }

    public static float getFloatTagCompound(ItemStack aStack, String tagName, String keyName) {
        NBTTagCompound aNBT = getNBT(aStack);
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag(tagName);
            if (aNBT != null) {
                return aNBT.getFloat(keyName);
            }
        }
        return 0;
    }

    public static double getByteTagCompound(ItemStack aStack, String tagName, String keyName) {
        NBTTagCompound aNBT = getNBT(aStack);
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag(tagName);
            if (aNBT != null) {
                return aNBT.getByte(keyName);
            }
        }
        return 0;
    }

    public static boolean getBooleanTagCompound(ItemStack aStack, String tagName, String keyName) {
        NBTTagCompound aNBT = getNBT(aStack);
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag(tagName);
            if (aNBT != null) {
                return aNBT.getBoolean(keyName);
            }
        }
        return false;
    }

    public static NBTTagCompound getTagCompound(ItemStack aStack, String tagName) {
        NBTTagCompound aNBT = getNBT(aStack);
        if (aNBT != null && hasKey(aStack, tagName)) {
            aNBT = aNBT.getCompoundTag(tagName);
            if (aNBT != null) {
                return aNBT;
            }
        }
        return null;
    }

    public static boolean hasKeyInTagCompound(ItemStack stack, String tag, String key) {
        NBTTagCompound aNBT = stack.getTagCompound();
        if (aNBT != null) {
            aNBT = aNBT.getCompoundTag(tag);
            if (aNBT.hasKey(key)) {
                return true;
            }
        }
        return false;
    }

    public static void setNBT(final ItemStack aStack, final NBTTagCompound aNBT) {
        if (aNBT == null) {
            aStack.setTagCompound((NBTTagCompound) null);
            return;
        }
        final ArrayList<String> tTagsToRemove = new ArrayList<String>();
        for (final Object tKey : aNBT.func_150296_c()) {
            final NBTBase tValue = aNBT.getTag((String) tKey);
            if (tValue == null
                    || (tValue instanceof NBTBase.NBTPrimitive && ((NBTBase.NBTPrimitive) tValue).func_150291_c() == 0L)
                    || (tValue instanceof NBTTagString && isStringInvalid(((NBTTagString) tValue).func_150285_a_()))) {
                tTagsToRemove.add((String) tKey);
            }
        }
        for (final Object tKey : tTagsToRemove) {
            aNBT.removeTag((String) tKey);
        }
        aStack.setTagCompound(aNBT.hasNoTags() ? null : aNBT);
    }

    public static boolean isStringInvalid(final Object aString) {
        return aString == null || aString.toString().isEmpty();
    }

    public static void setPunchCardData(final ItemStack aStack, final String aPunchCardData) {
        final NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setString("GT.PunchCardData", aPunchCardData);
        setNBT(aStack, tNBT);
    }

    public static String getPunchCardData(final ItemStack aStack) {
        final NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getString("GT.PunchCardData");
    }

    public static void setLighterFuel(final ItemStack aStack, final long aFuel) {
        final NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setLong("GT.LighterFuel", aFuel);
        setNBT(aStack, tNBT);
    }

    public static long getLighterFuel(final ItemStack aStack) {
        final NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getLong("GT.LighterFuel");
    }

    public static void setMapID(final ItemStack aStack, final short aMapID) {
        final NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setShort("map_id", aMapID);
        setNBT(aStack, tNBT);
    }

    public static short getMapID(final ItemStack aStack) {
        final NBTTagCompound tNBT = getNBT(aStack);
        if (!tNBT.hasKey("map_id")) {
            return -1;
        }
        return tNBT.getShort("map_id");
    }

    public static void setBookAuthor(final ItemStack aStack, final String aAuthor) {
        final NBTTagCompound tNBT = getNBT(aStack);
        tNBT.setString("author", aAuthor);
        setNBT(aStack, tNBT);
    }

    public static String getBookAuthor(final ItemStack aStack) {
        final NBTTagCompound tNBT = getNBT(aStack);
        return tNBT.getString("author");
    }

    @SuppressWarnings("unchecked")
    public static <V> V getField(final Object object, final String fieldName) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                final Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (V) field.get(object);
            } catch (final NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (final Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }
}
