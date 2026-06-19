package alkalus.main.mixins.late.witchery;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.client.gui.GuiScreenBiomeBook;
import com.emoniph.witchery.item.ItemBook;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.biomebook.BiomeBookStandIn;
import alkalus.main.mixins.IBiomeBookScreen;
import alkalus.main.network.BiomeBookEntry;
import alkalus.main.network.BiomeBookRequestPacket;

@Mixin(GuiScreenBiomeBook.class)
public abstract class GuiScreenBiomeBookMixin extends GuiScreen implements IBiomeBookScreen {

    @Shadow
    private ArrayList<BiomeGenBase> biomes;
    @Shadow
    private ArrayList<Integer> sections;
    @Shadow
    private ArrayList<String> sectionNames;
    @Shadow
    private int pageIndex;
    @Shadow
    private ItemStack itemstack;

    @Unique
    private boolean witcheryextras$loading;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void witcheryextras$requestServerList(EntityPlayer player, ItemStack stack, CallbackInfo ci) {
        this.biomes.clear();
        this.sections.clear();
        this.sectionNames.clear();
        this.witcheryextras$loading = true;
        WitcheryExtras.NETWORK.sendToServer(new BiomeBookRequestPacket());
    }

    @Override
    public void witcheryextras$populate(List<List<BiomeBookEntry>> serverSections) {
        this.biomes.clear();
        this.sections.clear();
        this.sectionNames.clear();
        BiomeDictionary.Type[] types = ItemBook.BIOME_TYPES;
        for (int i = 0; i < types.length && i < serverSections.size(); i++) {
            this.sections.add(this.biomes.size());
            this.sectionNames
                    .add(Witchery.resource("witchery.book.biomes." + types[i].toString().toLowerCase() + ".name"));
            for (BiomeBookEntry e : serverSections.get(i)) {
                this.biomes.add(BiomeBookStandIn.create(e));
            }
        }
        this.pageIndex = ItemBook.getSelectedBiome(this.itemstack, this.biomes.size());
        this.witcheryextras$loading = false;
    }

    @Inject(method = "drawScreen", at = @At("TAIL"))
    private void witcheryextras$drawLoading(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (this.witcheryextras$loading) {
            this.drawCenteredString(this.fontRendererObj, "Loading...", this.width / 2, this.height / 2, 0xFFFFFF);
        }
    }
}
