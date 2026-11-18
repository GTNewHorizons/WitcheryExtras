package alkalus.main.mixins.late.witchery;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.entity.EntityBolt;
import com.emoniph.witchery.entity.EntityWitchHunter;

/*
 * Fix Witchery bolt not saving its owner at all, thus being unable to restore its owner after the owner has been
 * reloaded
 */
@Mixin(EntityBolt.class)
public abstract class MixinEntityBolt extends Entity {

    @Shadow(remap = false)
    private Entity shootingEntity;

    @Unique
    private UUID witcheryExtras$cachedOwnerUUID;

    @Inject(method = "writeEntityToNBT", at = @At("TAIL"))
    private void writeOwnerToNBT(NBTTagCompound tags, CallbackInfo ci) {
        UUID uuid = shootingEntity != null ? shootingEntity.getUniqueID() : witcheryExtras$cachedOwnerUUID;
        if (uuid != null) {
            tags.setLong("OwnerUUIDMost", uuid.getMostSignificantBits());
            tags.setLong("OwnerUUIDLeast", uuid.getLeastSignificantBits());
        }
    }

    @Inject(method = "readEntityFromNBT", at = @At("TAIL"))
    private void readOwnerFromNBT(NBTTagCompound tags, CallbackInfo ci) {
        if (tags.hasKey("OwnerUUIDMost", 99)) {
            witcheryExtras$cachedOwnerUUID = new UUID(tags.getLong("OwnerUUIDMost"), tags.getLong("OwnerUUIDLeast"));
        }
    }

    @Inject(
            method = "onUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/MovingObjectPosition;<init>(Lnet/minecraft/entity/Entity;)V"))
    private void restoreOwner(CallbackInfo ci) {
        if (shootingEntity == null && witcheryExtras$cachedOwnerUUID != null) {
            // 1. Search for the owner player
            Entity entity = this.worldObj.func_152378_a(witcheryExtras$cachedOwnerUUID);

            // 2. If no player, search for the owner witch hunter
            if (entity == null) {
                for (Entity ent : this.worldObj.loadedEntityList) {
                    if (ent instanceof EntityWitchHunter && ent.getUniqueID().equals(witcheryExtras$cachedOwnerUUID)) {
                        entity = ent;
                        break;
                    }
                }
            }

            if (entity != null) {
                shootingEntity = entity;
                witcheryExtras$cachedOwnerUUID = null;
            }
        }
    }

    private MixinEntityBolt(World worldIn) {
        super(worldIn);
    }
}
